import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Speed implements Runnable {
	private int iterations = Main.getIterations();
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;";
	private ArrayList<String> records;
	
	public Speed(){
		records = new ArrayList<>(Main.getIterations()*480);
	}

	public void fillSpeed(){
		long nightstart = 1480278600000L;
		long oneday = 86400000L;
		String tmpString = databasename;
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 930; j++) {
					System.out.println("Speed: " + i);
					Long stimestamp = nightstart + (j * 60000L);
					tmpString += System.lineSeparator() + "DECLARE @id INT = (SELECT TOP 1 id FROM teamtimetable WHERE " + stimestamp + " BETWEEN starttimestamp AND endtimestamp); IF (SELECT organic FROM batch WHERE teamnighttimetableid = @id OR teamdaytimetableid = @id) = 1 INSERT INTO speed (value, targetvalue, stimestamp) VALUES (6000, 6000, " + stimestamp + "); ELSE INSERT INTO speed (value, targetvalue, stimestamp) VALUES (13000, 13000, " + stimestamp + ");";
					records.add(tmpString);
					tmpString = "";
				}
			}
			nightstart += oneday;
		}
		writeToFile(records, "Speed");
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
		fillSpeed();
	}

}
