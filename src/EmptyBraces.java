import java.util.ArrayList;
import java.util.Random;

public class EmptyBraces implements Runnable{
	private int iterations = Main.getIterations();
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;" + System.lineSeparator();
	private ArrayList<String> records;
	
	public EmptyBraces(){
		records = new ArrayList<>(Main.getIterations()*930);
	}

	public void fillEmptyBraces(){
		long oneday = Main.getOneday();
		long nightstart = Main.getNightStart();
		String tmpString = databasename;
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 930; j++) {
					System.out.println("EB: " + i);
					Long starttimestamp = nightstart + (j * 60000L);
					String teamtimetablesql = "(SELECT TOP 1 id FROM teamtimetable WHERE " + starttimestamp + " BETWEEN starttimestamp AND endtimestamp)";
					int value = rand.nextInt(10);
					tmpString += System.lineSeparator() + "INSERT INTO emptybraces (starttimestamp, value, teamtimetableid) VALUES (" + starttimestamp + ", " + value + ", " + teamtimetablesql + ");";
					records.add(tmpString);
					tmpString = "";
				}
			}
			nightstart += oneday;
		}
		Main.writeToFile(records, "EmptyBraces");
	}

	@Override
	public void run() {
		fillEmptyBraces();
	}
}
