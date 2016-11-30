import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Main {
	static Date time = new Date();
	static Random rand = new Random();
	public static void main(String[] args) {
		args[1] = "n";	// insert production stop messages? y/n
		args[2] = "n";	// insert daily messages? y/n
		args[3] = "n";  // insert batches? y/n
		args[4] = "n";	// insert teams? y/n
		args[5] = "n";	// insert teamtimetable? y/n
		if(args[1] == "y"){
			fillProductionStop();
		}
		if(args[2] == "y"){
			fillDailyMessages();
		}
		if(args[3] == "y"){
			fillBatches();
		}
		if(args[4] == "y"){
			fillTeams();
		}
		if(args[5] == "y"){
			fillTimeTable();
		}
		startWorker(60, 1); //refresh rate (seconds) and job; 1 = slaughter, 2 = emptybraces, 3 = speed
		startWorker(60, 2);
		startWorker(60, 3);
	}

	private static void fillTimeTable() {
		for (int i = 0; i < 12; i++) { // weeks (84 days total)
			for (int j = 0; j < 5; j++) { // days
				//TODO
				System.out.println(AddValuesToDB.addValuesToTimeTable(i, i, i));
			}
		}
		printToSQLFile();
	}

	private static void printToSQLFile() {
		// TODO Auto-generated method stub
		
	}

	private static void fillTeams() {
		System.out.println(AddValuesToDB.addValuesToTeams("nat", 46, 1));
		System.out.println(AddValuesToDB.addValuesToTeams("dag", 50, 1));
		printToSQLFile();
	}

	private static void addValuesToSlaughterAmount() {
		int batchnr = rand.nextInt(10) + 1;
		int slaughtervalue = rand.nextInt(100000) + 50000;
		int teamid = rand.nextInt(1);
		System.out.println("slaughteramount: " + AddValuesToDB.addValuesSlaughterAmount(slaughtervalue, batchnr, teamid, time.getTime()));
	}
	
	private static void fillBatches() {
		for (int i = 0; i < 5; i++) { //TODO fixed amount
			int batchnr = rand.nextInt(10) + 1;
			String farmer = "lars";
			int batchvalue = rand.nextInt(10000) + 10000;
			long timeofslaughter = rand.nextInt(90000) + 100000;
			boolean organic = rand.nextBoolean();
			int housenr = rand.nextInt(10) + 1;
			int avgweight = rand.nextInt(500) + 500;
			int teamnighttimetableid = rand.nextInt(10)+1;	//TODO
			int teamdaytimetableid = rand.nextInt(10)+1;	//TODO
			System.out.println("batch: " + AddValuesToDB.addValuesBatch(batchvalue, timeofslaughter, organic, batchnr, housenr, farmer, avgweight, teamnighttimetableid, teamdaytimetableid));	
		}
		printToSQLFile();
	}
	
	private static void addValuesToEmptyBraces(){
		int value = rand.nextInt(1);
		int teamid = rand.nextInt(1);
		System.out.println("empty braces: " + AddValuesToDB.addValuesToEmptyBraces(time.getTime(), value, teamid));
	}

	private static void addValuesToSpeed() {
		int speedval = rand.nextInt(1000) + 50;
		System.out.println("speed: " + AddValuesToDB.addValuesSpeed(speedval, 650, time.getTime()));		
	}

	/**
	 * one time run, needs work
	 */
	private static void fillDailyMessages() {
		for (int i = 0; i < 4; i++) {
			long stoptime = rand.nextLong();
			String dmessage = "my message" + stoptime;
			System.out.println("daily messages: " + AddValuesToDB.addValuesDailyMessage(dmessage, time.getTime(), time.getTime()+100000, time.getTime()+10000));	
		}
		printToSQLFile();
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
		printToSQLFile();
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
