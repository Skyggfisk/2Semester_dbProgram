
public class SlaughterAmountModel {

	private int numOfMinBatch, value, teamdaytimetableid, teamnighttimetableid, organic, id;
	private long starttimestamp, endtimestamp;
	
	public SlaughterAmountModel(long endtimestamp, int numOfMinBatch, int value, int teamdaytimetableid, int teamnighttimetableid, int organic, int id, long starttimestamp) {
		this.endtimestamp = endtimestamp;
		this.numOfMinBatch = numOfMinBatch;
		this.value = value;
		this.teamdaytimetableid = teamdaytimetableid;
		this.teamnighttimetableid = teamnighttimetableid;
		this.organic = organic;
		this.id = id;
		this.starttimestamp = starttimestamp;
	}
	
	public int getNumOfMinBatch() {
		return numOfMinBatch;
	}
	public void setNumOfMinBatch(int numOfMinBatch) {
		this.numOfMinBatch = numOfMinBatch;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getTeamdaytimetableid() {
		return teamdaytimetableid;
	}
	public void setTeamdaytimetableid(int teamdaytimetableid) {
		this.teamdaytimetableid = teamdaytimetableid;
	}
	public int getTeamnighttimetableid() {
		return teamnighttimetableid;
	}
	public void setTeamnighttimetableid(int teamnighttimetableid) {
		this.teamnighttimetableid = teamnighttimetableid;
	}
	public int getOrganic() {
		return organic;
	}
	public void setOrganic(int organic) {
		this.organic = organic;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getStarttimestamp() {
		return starttimestamp;
	}
	public void setStarttimestamp(long starttimestamp) {
		this.starttimestamp = starttimestamp;
	}
	public long getEndtimestamp() {
		return endtimestamp;
	}
	public void setEndtimestamp(long endtimestamp) {
		this.endtimestamp = endtimestamp;
	}
	
}
