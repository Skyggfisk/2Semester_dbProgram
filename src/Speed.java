import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Speed implements Runnable {
	private DBSingleConnection dbSinCon;
	private int iterations = 83;
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;";
	private ArrayList<Integer> teamids;
	private ArrayList<String> records = new ArrayList<>();
	
	public Speed(DBSingleConnection dbSinCon, ArrayList<Integer> teamids){
		this.dbSinCon = dbSinCon;
		this.teamids = teamids;
	}

	public void fillSpeed(){
		long daystart = 1480305600000L;
		long oneday = 86400000L;
		int targetval = 13000;
		String tmpString = databasename;
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 480; j++) {
					System.out.println("Speed: " + i);
					Long stimestamp = daystart + (j * 60000L);
					int speedval = rand.nextInt(5) + 13000;
					if(AddValuesToDB.getOrganic(teamids.get(i+j),dbSinCon)){
						targetval = 6000;
						speedval = rand.nextInt(5) + 6000;
					}
					tmpString += System.lineSeparator() + "INSERT INTO speed (value, targetvalue, stimestamp) VALUES (" + speedval + ", " + targetval + ", " + stimestamp + ");";
					records.add(tmpString);
					tmpString = "";
				}
			}
			daystart += oneday;
		}
		writeToFile(records, "Speed");
	}

	private void writeToFile(ArrayList<String> records, String fileName){
		File file = new File(fileName, ".sql");
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
