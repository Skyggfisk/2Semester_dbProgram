import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SlaughterAmount implements Runnable {
	private DBSingleConnection dbSinCon = new DBSingleConnection();
	private int iterations = 83;
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;";

	private void fillSlaughterAmountTable() {
		long daystart = 1480305600000L;
		long oneday = 86400000L;
		String tmpString = databasename;
		BufferedWriter bf = createFile("SlaughterAmount");
		AddValuesToDB.getCurrentTeamId(daystart, dbSinCon);
		ArrayList<Integer> batch = AddValuesToDB.getBatchId(WorkingTeam.getInstance().getTeamTimeTableId(), dbSinCon);
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
					AddValuesToDB.getCurrentTeamId(satimestamp, dbSinCon);
					tmpString += System.lineSeparator() + "INSERT INTO slaughteramount (value, batchid, satimestamp, teamtimetableid) VALUES (" + slaughtervalue + ", " + batchnr + ", " + satimestamp + ", " + WorkingTeam.getInstance().getTeamTimeTableId() + ");";
					batchvalue -= slaughtervalue;
					if(batchvalue < 1){
						batch = AddValuesToDB.getBatchId(WorkingTeam.getInstance().getTeamTimeTableId(), dbSinCon);
						batchnr = batch.get(0);
						batchvalue = batch.get(1);
					}
				}
				addStringToFileBuffer(tmpString, bf);
				tmpString = "";
			}
			daystart += oneday;
		}
		closeFile(bf);
}
	
	private BufferedWriter createFile(String filename){
		FileWriter fw;
		BufferedWriter bf = null;
		try {
			fw = new FileWriter(filename);
			bf = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return bf;
	}
	
	private void addStringToFileBuffer(String tmpString, BufferedWriter bf) {
			try {
				bf.write(tmpString);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void closeFile(BufferedWriter bf){
		try {
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		fillSlaughterAmountTable();
	}
}
