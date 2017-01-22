import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class nonusedcode {
	@SuppressWarnings({ "static-access", "unused" })
	private ArrayList<Integer> prepareArray() {
		int iterations = 0;
		ArrayList<Integer> teamidList = new ArrayList<>(iterations);
		long daystart = 1480305600000L;
		long oneday = 86400000L;
		for (int i = 0; i < iterations; i++) {
			int day = i%7;
			if(day == 5 ||day == 6){
				//tmpString += System.lineSeparator() + " sunday or saturday";
			} else {
				for (int j = 0; j < 480; j++) {
					System.out.println("prep: " + i);
					Long satimestamp = daystart + (j * 60000L);
					DBSingleConnection dbSinCon = null;
					AddValuesToDB.getCurrentTeamId(satimestamp, dbSinCon);
					teamidList.add(WorkingTeam.getInstance().getTeamTimeTableId());
				}
			}
			daystart += oneday;
		}
		return teamidList;
	}
	
	public static boolean dataTest(long now, int type, DBSingleConnection dbSinCon, LocalDateTime localdate){
		long topoflastminute = localdate.withSecond(0).atZone(ZoneId.systemDefault()).toEpochSecond();
		GetTimeStampValues gtsv = new GetTimeStampValues();
		long lastentry = gtsv.chooser(type, dbSinCon);
		long allowedTimeSinceLastMin = 15000;
		long minimumTimeSinceLastEntry = 60000;
		if ((now - topoflastminute < allowedTimeSinceLastMin) && (now - lastentry) > minimumTimeSinceLastEntry) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private void dataTest2(int type, DBSingleConnection dbSinCon) {
		GetTimeStampValues gtsv = new GetTimeStampValues();
		//return gtsv.chooser2(type, dbSinCon);
	}
}
