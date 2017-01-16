import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

@SuppressWarnings("static-access")
public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	private DBSingleConnection dbSinCon = new DBSingleConnection();

	private static int iterations = 83;

	private static String databasename = "USE UCN_dmaa0216_2Sem_1;";
	private static String pathname = System.getenv("HOMEPATH") + "\\Desktop\\SQLQueries\\";
	static Date time = new Date();
	static Random rand = new Random();

	/**
	 * works
	 */
	private static void fillTimeTable() {
		//1480278600000 sunday 20.30	// 1480305600000 monday 04.00 // nightteam
		//1480305600000 monday 04.00	// 1480334400000 monday 12.00 // dayteam
		String tmpString = databasename;
		long oneday = 86400000L;
		long nightstart = 1480278600000L; long nightend = 1480305600000L;
		long daystart = 1480305600000L; long dayend = 1480334400000L;
		for (int i = 0; i < iterations; i++) { 
				int day = i%7;
				if(day == 5 ||day == 6){
					//tmpString += System.lineSeparator() + " sunday or saturday";
				} else{
					tmpString += System.lineSeparator() + " INSERT INTO teamtimetable (starttimestamp, endtimestamp, teamid) VALUES ("+ daystart +", "+ dayend +" , (SELECT id FROM team WHERE teamname = 'dag' AND department = 1));";
					tmpString += System.lineSeparator() + " INSERT INTO teamtimetable (starttimestamp, endtimestamp, teamid) VALUES ("+ nightstart +", "+ nightend +" , (SELECT id FROM team WHERE teamname = 'nat' AND department = 1));";
				}
				nightstart += oneday;
				nightend += oneday;
				daystart += oneday;
				dayend += oneday;
		}
		printToSQLFile(tmpString, "TeamTimeTable");
	}

	/**
	 * works
	 * @param tmpString
	 * @param filename
	 */
	private static void printToSQLFile(String tmpString, String filename) {
		new File(pathname).mkdirs();
		try {
			filename += ".sql";
			PrintWriter writer = new PrintWriter(pathname + filename, "UTF-8");
			writer.print(tmpString);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * works
	 */
	private static void fillTeams() {
		String tmpString = databasename + System.lineSeparator() + "TRUNCATE TABLE team";
		tmpString += System.lineSeparator() + " INSERT INTO team (teamname, workers, department) VALUES ('nat', 46, 1);";
		tmpString += System.lineSeparator() + " INSERT INTO team (teamname, workers, department) VALUES ('dag', 50, 1);";
		printToSQLFile(tmpString, "Teams");
	}

	/**
	 * works? test. 
	 */
	
	private static void addValuesToSlaughterAmount(DBSingleConnection dbSinCon) {
		int batchnr = rand.nextInt(10) + 1;
		int slaughtervalue = rand.nextInt(16) + 200;
		System.out.println(WorkingTeam.getInstance().getTeamTimeTableId());
		System.out.println("slaughteramount: " + AddValuesToDB.addValuesSlaughterAmount(slaughtervalue, batchnr, WorkingTeam.getInstance().getTeamTimeTableId(), time.getTime(), dbSinCon));
	}
	
	private static void fillRefreshRates() {
		String tmpString = databasename + System.lineSeparator() + "TRUNCATE TABLE information";
		
		for (FieldTypes entry : FieldTypes.values()) {
			tmpString += System.lineSeparator() + "INSERT INTO information(informationname, refreshrate) VALUES ('" + entry + "', " + entry.getRefreshRate() + ");" + System.lineSeparator() + " GO";
		}
		printToSQLFile(tmpString, "RefreshRates");
	}
	
	private static void fillBatches() {
		long dayend = 1480334400000L;
		Long tos = 1480330800000L;
		long nightend = 1480305600000L;
		long oneday = 86400000L;
		String tmpString = databasename + System.lineSeparator() + "TRUNCATE TABLE batch";
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 9; j++) {
					boolean organic = false;
					if(day == 0 || day == 2){
						organic = rand.nextBoolean();
					}
					Long timeofslaughter = tos;
					int batchnr = j + 1 + i + i*j;
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
			tos += oneday;
		}
		printToSQLFile(tmpString, "Batches");
	}
	
	/**
	 * works? test.
	 */
	private static void addValuesToEmptyBraces(DBSingleConnection dbSinCon){
		int value = rand.nextInt(10);
		System.out.println("empty braces: " + AddValuesToDB.addValuesToEmptyBraces(time.getTime(), value, WorkingTeam.getInstance().getTeamTimeTableId(), dbSinCon));
	}

	/**
	 * works? test.
	 */
	private static void addValuesToSpeed(DBSingleConnection dbSinCon) {
		int targetval = 13000;
		int speedval = rand.nextInt(5) + 13000;
		String teamtimetablesql = "(SELECT TOP 1 id FROM teamtimetable WHERE " + time.getTime() + " BETWEEN starttimestamp AND endtimestamp)";
		if(AddValuesToDB.getOrganic(teamtimetablesql,dbSinCon)){
			targetval = 6000;
			speedval = rand.nextInt(5) + 6000;
		}
		System.out.println("speed: " + AddValuesToDB.addValuesSpeed(speedval, targetval, time.getTime(), dbSinCon));
	}
	
	/**
	 * works
	 */
	private static void getTeamId(DBSingleConnection connection) {
		AddValuesToDB.getCurrentTeamId(time.getTime(), connection);
	}

	/**
	 * works
	 */
	private static void fillDailyMessages() {
		long dayend = 1480334400000L;
		long oneday = 86400000L;
		String tmpString = databasename + System.lineSeparator() + "TRUNCATE TABLE dailymessages";
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 || day == 6){
				//do nothing (saturday / sunday)
			} else{
				for (int j = 0; j < rand.nextInt(5)+1; j++) {
					int stoptime = rand.nextInt(200);
					String dmessage = "my message" + stoptime;
					long creationTime = dayend-3600000;
					long expireTime = dayend+oneday;
					long showTime = dayend-(oneday/2);
					tmpString += System.lineSeparator() + "INSERT INTO dailymessages (dmessage, dtimestamp, expire, showdate) VALUES ('" + dmessage + "', " + creationTime + ", " + expireTime + ", " + showTime + ");";
				}
			}
			dayend += oneday;
		}
		
		printToSQLFile(tmpString, "DailyMessages");
	}

	/**
	 * works
	 */
	public static void fillProductionStop(){
		long dayend = 1480334400000L;
		long oneday = 86400000L;
		String tmpString = databasename + System.lineSeparator() + "TRUNCATE TABLE productionstop";
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 || day == 6){
				//do nothing (saturday / sunday)
			} else {
				for (int j = 0; j < rand.nextInt(5)+1; j++) {
					long stoptimeOffSet = rand.nextInt(3600000) * (rand.nextInt(4) + 1);
					long stoptime = dayend - stoptimeOffSet;
					String stopdescription = "Something Broke at time: " + stoptimeOffSet;
					int stoplength = rand.nextInt(2) + 1;
					String teamid = "SELECT id FROM teamtimetable WHERE endtimestamp = " + dayend;
					tmpString += System.lineSeparator() + "INSERT INTO productionstop (stoptime, stoplength, stopdescription, teamtimetableid) VALUES (" + stoptime + ", " + stoplength + ", '" + stopdescription + "', (" + teamid + "));";
				}
			}
			dayend += oneday;
		}
		printToSQLFile(tmpString, "ProductionStops");
	}
	
    /**
     * @param refreshrate
     */
    public void startWorker(int refreshrate, int type){
    	Worker speedWorker = new Worker(type, dbSinCon);
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
    	DBSingleConnection dbSinCon;
		public Worker(int type, DBSingleConnection dbSinCon) {
			this.type = type;
			this.dbSinCon = dbSinCon;
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
						addValuesToSlaughterAmount(dbSinCon);
						break;
					case 2:
						addValuesToEmptyBraces(dbSinCon);
						break;
					case 3:
						addValuesToSpeed(dbSinCon);
						break;
					case 4:
						getTeamId(dbSinCon);
						break;
					default:
						return "did not do work";
					}
					return "did some work";
				}
			};
		}
    }


    
    /* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		 Parameters parameters = getParameters ();

		Map<String, String> namedParameters = parameters.getNamed();
		Set<String> keys = namedParameters.keySet();
		for (String myKeys : keys) {
			String value = namedParameters.get(myKeys);
			switch (myKeys) {
			case "iterations":
				setIterations(value);
				break;
			case "runsingletime":
				//ArrayList<Integer> teamids = prepareArray();
				new Thread(new SlaughterAmount(dbSinCon)).start();
				new Thread(new Speed()).start();
				new Thread(new EmptyBraces()).start();
				fillProductionStop();
				fillDailyMessages();
				fillBatches();
				fillTeams();
				fillTimeTable();
				fillRefreshRates();
				break;
			default:
				break;
			}
		}
		getTeamId(dbSinCon);
		if(namedParameters.containsKey("stopworker")){
			
		}else{
			//refresh rate (seconds) and job; 1 = slaughter, 2 = empty braces, 3 = speed, 4 = teamid
			startWorker(60, 1);
			startWorker(60, 2);
			startWorker(60, 3);
			startWorker(60, 4);
		}
		

	}

	private ArrayList<Integer> prepareArray() {
		ArrayList<Integer> teamidList = new ArrayList<>(iterations);
		long daystart = 1480305600000L;
		long oneday = 86400000L;
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 480; j++) {
					System.out.println("prep: " + i);
					Long satimestamp = daystart + (j * 60000L);
					AddValuesToDB.getCurrentTeamId(satimestamp, dbSinCon);
					teamidList.add(WorkingTeam.getInstance().getTeamTimeTableId());
				}
			}
			daystart += oneday;
		}
		return teamidList;
	}

	private void setIterations(String value) {
		this.iterations  = Integer.parseInt(value);
	}

	public static int getIterations(){
		return iterations;
	}

	public static String getPathname() {
		return pathname;
	}

	public static void setPathname(String pathname) {
		Main.pathname = pathname;
	}
	
	public static void writeToFile(ArrayList<String> records, String fileName){
		new File(pathname).mkdirs();
		File file = new File(Main.getPathname() + fileName +".sql");
		try {
			FileWriter fw = new FileWriter(file);
			for (String record : records) {
				fw.write(record);
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
