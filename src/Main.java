import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Templates;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Main {
	private static String databasename = "USE UCN_dmaa0216_2Sem_1;";
	static Date time = new Date();
	static Random rand = new Random();
	public static void main(String[] args) {
			//fillProductionStop();
			//fillDailyMessages();
			fillBatches();
			fillTeams();
			fillTimeTable();
			addValuesToEmptyBraces();
		//}
		//refresh rate (seconds) and job; 1 = slaughter, 2 = empty braces, 3 = speed
		//startWorker(60, 1);
		//startWorker(60, 2);
		//startWorker(60, 3);
	}

	/**
	 * good stuff
	 */
	private static void fillTimeTable() {
		//1480278600000 sunday 20.30	// 1480305600000 monday 04.00 // nightteam
		//1480305600000 monday 04.00	// 1480334400000 monday 12.00 // dayteam
		String tmpString = databasename;
		long oneday = 86400000L;
		long nightstart = 1480278600000L; long nightend = 1480305600000L;
		long daystart = 1480305600000L; long dayend = 1480334400000L;
		for (int i = 0; i < 83; i++) { 
				int day = i%7;
				if(day == 5 ||day == 6){
					//tmpString += System.lineSeparator() + " sunday or saturday";
				} else{
					tmpString += System.lineSeparator() + " INSERT INTO teamtimetable (starttimestamp, endtimestamp, team) VALUES ("+ daystart +", "+ dayend +" , (SELECT id FROM team WHERE teamname = 'dag' AND department = 1));";
					tmpString += System.lineSeparator() + " INSERT INTO teamtimetable (starttimestamp, endtimestamp, team) VALUES ("+ nightstart +", "+ nightend +" , (SELECT id FROM team WHERE teamname = 'nat' AND department = 1));";
				}
				nightstart += oneday;
				nightend += oneday;
				daystart += oneday;
				dayend += oneday;
		}
		printToSQLFile(tmpString, "TeamTimeTable");
	}

	private static void printToSQLFile(String tmpString, String filename) {
		try {
			filename += ".sql";
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.print(tmpString);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void fillTeams() {
		String tmpString = databasename;
		tmpString += System.lineSeparator() + " INSERT INTO team (teamname, workers, department) VALUES ('nat', 46, 1);";
		tmpString += System.lineSeparator() + " INSERT INTO team (teamname, workers, department) VALUES ('dag', 50, 1);";
		printToSQLFile(tmpString, "Teams");
	}

	private static void addValuesToSlaughterAmount() {
		int batchnr = rand.nextInt(10) + 1;
		int slaughtervalue = rand.nextInt(100000) + 50000;
		int teamid = rand.nextInt(2);
		System.out.println("slaughteramount: " + AddValuesToDB.addValuesSlaughterAmount(slaughtervalue, batchnr, teamid, time.getTime()));
	}
	
	private static void fillBatches() {
		long dayend = 1480334400000L;
		long nightend = 1480305600000L;
		long oneday = 86400000L;
		String tmpString = databasename;
		for (int i = 0; i < 83; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 9; j++) {
					boolean organic = false;
					if(day == 0 || day == 2){
						organic = rand.nextBoolean();
					}
					Long timeofslaughter = dayend - 150000;
					int batchnr = rand.nextInt(10) + 1;
					String farmer = "lars" + rand.nextInt(10);
					int batchvalue = rand.nextInt(10000) + 10000;
					int housenr = rand.nextInt(10) + 1;
					int avgweight = rand.nextInt(2000) + 1500;
					String teamnighttimetableid = "(SELECT id FROM teamtimetable WHERE endtimestamp = '" + nightend +"')";
					String teamdaytimetableid = "(SELECT id FROM teamtimetable WHERE endtimestamp = '" + dayend + "')";
					tmpString += System.lineSeparator() + " INSERT INTO batch (value, timeofslaughter, organic, batchnr, housenr, farmer, avgweight, teamnighttimetableid, teamdaytimetableid) VALUES (" + batchvalue + ", " + timeofslaughter + ", '" + organic + "', " + batchnr + ", " + housenr + ", '" + farmer + "', " + avgweight + ", " + teamnighttimetableid + ", " + teamdaytimetableid + ");";
				}
			}
			dayend += oneday;
			nightend += oneday;
		}
		printToSQLFile(tmpString, "Batches");
	}
	
	private static void addValuesToEmptyBraces(){
		int value = rand.nextInt(1);
		int teamid = rand.nextInt(2); //fix
		System.out.println("empty braces: " + AddValuesToDB.addValuesToEmptyBraces(value, teamid));
	}

	private static void addValuesToSpeed() {
		int speedval = rand.nextInt(1000) + 50;
		System.out.println("speed: " + AddValuesToDB.addValuesSpeed(speedval, 650, time.getTime()));
	}

	/**
	 * one time run, needs work
	 */
	private static void fillDailyMessages() {
		for (int i = 0; i < 4; i++) { // fix amount to ????
			long stoptime = rand.nextLong();
			String dmessage = "my message" + stoptime;
			System.out.println("daily messages: " + AddValuesToDB.addValuesDailyMessage(dmessage, time.getTime(), time.getTime()+100000, time.getTime()+10000));	
		}
		printToSQLFile(null, null);
	}

	/**
	 * one time run, needs work
	 */
	public static void fillProductionStop(){
		for (int i = 0; i < 4; i++) {
			long stoptime = rand.nextLong();
			String stopdescription = "something happened" + stoptime;
			int stoplength = rand.nextInt(2) + 1;
			int teamid = rand.nextInt(1);
			System.out.println("production stop: " + AddValuesToDB.addValuesProductionStop(stoptime, stoplength, stopdescription, teamid));
		}
		printToSQLFile(null, null);
	}
	
    /**
     * @param refreshrate
     */
    public static void startWorker(int refreshrate, int type){
    	Worker speedWorker = new Worker(type);
    	speedWorker.setPeriod(Duration.seconds(refreshrate));
    	speedWorker.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			/* (non-Javadoc)
			 * @see javafx.event.EventHandler#handle(javafx.event.Event)
			 * What happens, upon entering the succeeded state. "getvalue" is the result. Set to null if reused.
			 */
			@Override
			public void handle(WorkerStateEvent event) {
				String value = (String)event.getSource().getValue();
				System.out.println(value);
			}
		});
    	speedWorker.start();
    }
    
    private static class Worker extends ScheduledService<String> {
    	int type = 0;
		public Worker(int type) {
			this.type = type;
		}
		
		/**
		 * Task which should be executed
		 */
		@Override
		protected Task<String> createTask() {
			return new Task<String>() {
				protected String call(){
					switch(type){
					case 1:
						addValuesToSlaughterAmount();
						break;
					case 2:
						addValuesToEmptyBraces();
						break;
					case 3:
						addValuesToSpeed();
						break;
					default:
						return "did not do work";
					}
					return "did some work";
				}
			};
		}
    	
    }
}
