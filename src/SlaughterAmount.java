import java.util.ArrayList;
import java.util.Random;

public class SlaughterAmount implements Runnable {
	private DBSingleConnection dbSinCon;
	Random rand = new Random();
	private String databasename = "USE UCN_dmaa0216_2Sem_1;"  + System.lineSeparator() + "TRUNCATE TABLE slaughteramount";
	
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
		for (int k = 0; k <= 1; k++) {
			for (int i = (slaughterArr.size()/3)*k; i < (slaughterArr.size()/3)*k+slaughterArr.size()/3; i++) {
				if( slaughterArr.get(i).getStarttimestamp() != beforeTimStamp){
					timestamp = slaughterArr.get(i).getStarttimestamp();
					beforeTimStamp = slaughterArr.get(i).getStarttimestamp();
				}
				for(int j = 0; j <= slaughterArr.get(i).getNumOfMinBatch()/(k==0 ? 1 : 2); j++){
					if(timestamp > slaughterArr.get(i).getEndtimestamp()){
						teamid = slaughterArr.get(i).getTeamdaytimetableid();
					}else{
						teamid =  slaughterArr.get(i).getTeamnighttimetableid();
					}
					timestamp += oneminute;
					tmpString += System.lineSeparator() + "INSERT INTO slaughteramount (value, batchid, satimestamp, teamtimetableid) VALUES (" + (slaughterArr.get(i).getOrganic() ? 88 : 212) + ", " + slaughterArr.get(i).getId() + ", " + timestamp + ", " + teamid + ");";
					records.add(tmpString);
					System.out.println("SA: " + i +" " + j + " timestamp: " + timestamp);
					tmpString = "";
				}
			}
		}
		
		Main.writeToFile(records, "Slaughter");
		System.out.println("done!");
	}

	@Override
	public void run() {
		fillSlaughterAmountTable();
	}
}
