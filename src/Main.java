import java.util.Date;
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
		if(args[1] == "y"){
			fillProductionStop();
		}
		if(args[2] == "y"){
			fillDailyMessages();
		}
		startWorker(5, 1); //refresh rate and job; 1 = slaughter, 2 = batch, 3 = speed
	}

	private static void addValuesToSlaughterAmount() {
		int batchnr = rand.nextInt(10) + 1;
		int slaughtervalue = rand.nextInt(100000) + 50000;
		int teamid = rand.nextInt(10) +1;
		System.out.println("slaughteramount: " + AddValuesToDB.addValuesSlaughterAmount(slaughtervalue, batchnr, teamid, time.getTime()));
		
	}

	private static void addValuesToBatch() {
		int batchnr = rand.nextInt(10) + 1;
		String farmer = "lars";
		int batchvalue = rand.nextInt(10000) + 10000;
		long timeofslaughter = rand.nextInt(90000) + 100000;
		boolean organic = rand.nextBoolean();
		int housenr = rand.nextInt(10) + 1;
		int avgweight = rand.nextInt(500) + 500;
		int teamnighttimetableid = rand.nextInt(10)+1;
		int teamdaytimetableid = rand.nextInt(10)+1;
		System.out.println("batch: " + AddValuesToDB.addValuesBatch(batchvalue, timeofslaughter, organic, batchnr, housenr, farmer, avgweight, teamnighttimetableid, teamdaytimetableid));
		
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
	}

	/**
	 * one time run, needs work
	 */
	public static void fillProductionStop(){
		long stoptime = rand.nextLong();
		String stopdescription = "something happened" + stoptime;
		int stoplength = rand.nextInt(2) + 1;
		int teamid = rand.nextInt(10) +1;
		System.out.println("production stop: " + AddValuesToDB.addValuesProductionStop(stoptime, stoplength, stopdescription, teamid));
	}
	
    /**
     * start these bitches from main; 3 pcs, one for each value to insert.
     * @param refreshrate
     */
    public static void startWorker(int refreshrate, int type){
    	Worker speedWorker = new Worker(type);
    	speedWorker.setPeriod(Duration.seconds(refreshrate));
    	speedWorker.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				//MyTypeHolder value = (MyTypeHolder)event.getSource().getValue();				
			}
		});
    	speedWorker.start();
    }
    
    private static class Worker extends ScheduledService<String> {
    	// TODO this needs work
		public Worker(int type) {
			switch(type){
			case 1:
				addValuesToSlaughterAmount();
				break;
			case 2:
				addValuesToBatch();
				break;
			case 3:
				addValuesToSpeed();
				break;
			}
		}
		
		/**
		 * Task which should be executed
		 */
		@Override
		protected Task<String> createTask() {
			return new Task<String>() {
				protected String call(){
					// TODO WORK WORK WORK
					return "something";
				}
			};
		}
    	
    }
}
