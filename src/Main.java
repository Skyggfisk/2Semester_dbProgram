import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
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
		while(true){
			addValuesToSpeed();
			addValuesToBatch();
			addValuesToSlaughterAmount();
			try {
				System.out.println("sleeping ...");
				TimeUnit.SECONDS.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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

	private static void fillDailyMessages() {
		for (int i = 0; i < 4; i++) {
			long stoptime = rand.nextLong();
			String dmessage = "my message" + stoptime;
			System.out.println("daily messages: " + AddValuesToDB.addValuesDailyMessage(dmessage, time.getTime(), time.getTime()+100000, time.getTime()+10000));	
		}		
	}

	public static void fillProductionStop(){
		long stoptime = rand.nextLong();
		String stopdescription = "something happened" + stoptime;
		int stoplength = rand.nextInt(2) + 1;
		int teamid = rand.nextInt(10) +1;
		System.out.println("production stop: " + AddValuesToDB.addValuesProductionStop(stoptime, stoplength, stopdescription, teamid));
	}
	
    public void startWorker(int refreshrate){
    	Worker speedWorker = new Worker();
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

		public Worker() {
			//nothing to do  ... set field type??
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
