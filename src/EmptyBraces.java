import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class EmptyBraces implements Runnable{
	private int iterations = Main.getIterations();
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;";
	private ArrayList<String> records;
	
	public EmptyBraces(){
		records = new ArrayList<>(Main.getIterations()*480);
	}

	public void fillEmptyBraces(){
		long daystart = 1480305600000L;
		long oneday = 86400000L;
		String tmpString = databasename;
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 480; j++) {
					System.out.println("EB: " + i);
					Long starttimestamp = daystart + (j * 60000L);
					String teamtimetablesql = "(SELECT TOP 1 id FROM teamtimetable WHERE " + starttimestamp + " BETWEEN starttimestamp AND endtimestamp)";
					int value = rand.nextInt(10);
					tmpString += System.lineSeparator() + "INSERT INTO emptybraces (starttimestamp, value, teamtimetableid) VALUES (" + starttimestamp + ", " + value + ", " + teamtimetablesql + ");";
					records.add(tmpString);
					tmpString = "";
				}
			}
			daystart += oneday;
		}
		writeToFile(records, "EmptyBraces");
	}
	
	private void writeToFile(ArrayList<String> records, String fileName){
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

	@Override
	public void run() {
		fillEmptyBraces();
	}
}
