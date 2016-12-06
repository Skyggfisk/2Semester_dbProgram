import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class EmptyBraces implements Runnable{
	private DBSingleConnection dbSinCon = new DBSingleConnection();
	private int iterations = 83;
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;";

	public void fillEmptyBraces(){
		long daystart = 1480305600000L;
		long oneday = 86400000L;
		String tmpString = databasename;
		BufferedWriter bf = createFile("EmptyBraces");
		AddValuesToDB.getCurrentTeamId(daystart, dbSinCon);
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 480; j++) {
					System.out.println("EB: " + i);
					Long starttimestamp = daystart + (j * 60000L);
					int value = rand.nextInt(10);
					AddValuesToDB.getCurrentTeamId(starttimestamp, dbSinCon);
					tmpString += System.lineSeparator() + "INSERT INTO emptybraces (starttimestamp, value, teamtimetableid) VALUES (" + starttimestamp + ", " + value + ", " + WorkingTeam.getInstance().getTeamTimeTableId() + ");";
					AddValuesToDB.getCurrentTeamId(starttimestamp, dbSinCon);
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
		fillEmptyBraces();
	}
}
