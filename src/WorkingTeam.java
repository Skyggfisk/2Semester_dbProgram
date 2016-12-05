

public class WorkingTeam {

	private static WorkingTeam instance;
	private static int teamId = 0;
	private static int teamTimeTableId = 0;
	private static long startTime = 0;
	private static long endTime = 0;
	

	private WorkingTeam() {
		// Empty constructor
	}
	
	public static WorkingTeam getInstance() {
		if(instance == null) {
			instance = new WorkingTeam();
		}
		return instance;
	}
	
	public void setEverything(int teamId, int teamTimeTableId, long startTime, long endTime) {
		WorkingTeam.teamId = teamId;
		WorkingTeam.teamTimeTableId = teamTimeTableId;
		WorkingTeam.startTime = startTime;
		WorkingTeam.endTime = endTime;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		WorkingTeam.teamId = teamId;
	}

	public static int getTeamTimeTableId() {
		return teamTimeTableId;
	}

	public static void setTeamTimeTableId(int teamTimeTableId) {
		WorkingTeam.teamTimeTableId = teamTimeTableId;
	}

	public static long getStartTime() {
		return startTime;
	}

	public static void setStartTime(long startTime) {
		WorkingTeam.startTime = startTime;
	}

	public static long getEndTime() {
		return endTime;
	}

	public static void setEndTime(long endTime) {
		WorkingTeam.endTime = endTime;
	}

	public static void setInstance(WorkingTeam instance) {
		WorkingTeam.instance = instance;
	}

	@Override
	public String toString() {
		return "WorkingTeam [getTeamId()=" + getTeamId() + "]";
	}
	
}
