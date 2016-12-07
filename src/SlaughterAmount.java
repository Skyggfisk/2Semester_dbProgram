import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SlaughterAmount implements Runnable {
	private DBSingleConnection dbSinCon;
	private int iterations = Main.getIterations();
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;";
	private ArrayList<String> records;
	
	public SlaughterAmount(DBSingleConnection dbSinCon){
		this.dbSinCon = dbSinCon;
		records = new ArrayList<>(Main.getIterations()*480);
	}

	private void fillSlaughterAmountTable() {
		long daystart = 1480305600000L;
		long oneday = 86400000L;
		String tmpString = databasename;
		String teamtimetablesql = "(SELECT TOP 1 id FROM teamtimetable WHERE " + daystart + " BETWEEN starttimestamp AND endtimestamp)";
		ArrayList<Integer> batch = AddValuesToDB.getBatchId(teamtimetablesql, dbSinCon);
		int batchnr = batch.get(0);
		int batchvalue = batch.get(1);
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 480; j++) {
					System.out.println("SA: " + i);
					int slaughtervalue = rand.nextInt(16) + 200;
					Long satimestamp = daystart + (j * 60000L);
					teamtimetablesql = "(SELECT TOP 1 id FROM teamtimetable WHERE " + satimestamp + " BETWEEN starttimestamp AND endtimestamp)";
					tmpString += System.lineSeparator() + "INSERT INTO slaughteramount (value, batchid, satimestamp, teamtimetableid) VALUES (" + slaughtervalue + ", " + batchnr + ", " + satimestamp + ", " + teamtimetablesql + ");";
					records.add(tmpString);
					tmpString = "";
					batchvalue -= slaughtervalue;
					if(batchvalue < 1){
						batch = AddValuesToDB.getBatchId(teamtimetablesql, dbSinCon);
						batchnr = batch.get(0);
						batchvalue = batch.get(1);
					}
				}
			}
			daystart += oneday;
		}
		writeToFile(records, "Slaughter");
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
		fillSlaughterAmountTable();
	}
}
