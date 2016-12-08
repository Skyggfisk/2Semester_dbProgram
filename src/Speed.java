import java.util.ArrayList;
import java.util.Random;

public class Speed implements Runnable {
	private int iterations = Main.getIterations();
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;" + System.lineSeparator() + "TRUNCATE TABLE speed";
	private ArrayList<String> records;

	
	public Speed(){
		records = new ArrayList<>(Main.getIterations()*930);
	}

	public void fillSpeed(){
		int k = 0;
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
					tmpString += System.lineSeparator() + "DECLARE @id" + k + " INT = (SELECT TOP 1 id FROM teamtimetable WHERE " + stimestamp + " BETWEEN starttimestamp AND endtimestamp) IF (SELECT TOP 1 organic FROM batch WHERE (teamnighttimetableid = @id" + k + " OR teamdaytimetableid = @id" + k + ") AND organic = 1) = 1 INSERT INTO speed (value, targetvalue, stimestamp) VALUES (6000, 6000, " + stimestamp + ") ELSE INSERT INTO speed (value, targetvalue, stimestamp) VALUES (13000, 13000, " + stimestamp + ")" + System.lineSeparator() + "GO";
					records.add(tmpString);
					tmpString = "";
					k++;
				}
			}
			nightstart += oneday;
		}
		Main.writeToFile(records, "Speed");
	}
	
	@Override
	public void run() {
		fillSpeed();
	}

}
