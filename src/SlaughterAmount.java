import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SlaughterAmount implements Runnable {
	private DBSingleConnection dbSinCon;
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;";
	
	public SlaughterAmount(DBSingleConnection dbSinCon){
		this.dbSinCon = dbSinCon;
	}

	private void fillSlaughterAmountTable() {
		String tmpString = databasename;
		long timestamp = 123;
		long beforeTimStamp = 0;
		int teamid = 0;
		long oneminute = 60000L;
		ArrayList<String> records = new ArrayList<>();
		ArrayList<SlaughterAmountModel> slaughterArr = AddValuesToDB.getFillSlaughterAmountTable(dbSinCon);
		for (int i = 0; i < slaughterArr.size(); i++) {
			if( slaughterArr.get(i).getStarttimestamp() != beforeTimStamp){
				timestamp = slaughterArr.get(i).getStarttimestamp();
				beforeTimStamp = slaughterArr.get(i).getStarttimestamp();
			}
			for(int j = 0; j < slaughterArr.get(i).getNumOfMinBatch(); j++){
				if(timestamp > slaughterArr.get(i).getEndtimestamp()){
					teamid = slaughterArr.get(i).getTeamdaytimetableid();
				}else{
					teamid =  slaughterArr.get(i).getTeamnighttimetableid();
				}
				timestamp += oneminute;
				tmpString += System.lineSeparator() + "INSERT INTO slaughteramount (value, batchid, satimestamp, teamtimetableid) VALUES (" + slaughterArr.get(i).getValue() + ", " + slaughterArr.get(i).getId() + ", " + timestamp + ", " + teamid + ");";
				records.add(tmpString);
				tmpString = "";
			}
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
