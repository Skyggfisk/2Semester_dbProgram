

/**
 * The values which can be pulled from the controller.
 * Refreshrate has been appended..?
 */
public enum FieldTypes {
WORKINGTEAM(900000), SPEED(60000), AVGWEIGHT(60000), ORGANIC(60000),
SLAUGTHERAMOUNTNIGHT(60000), SLAUGTHERAMOUNTDAY(60000),
STOPNIGHT(300000), STOPDAY(300000),
DAYEXPECTED(600000),
TOTALSLAUGTHERAMOUNT(60000), EXPECTEDFINISH(60000),
PRODUCTIONSTOPS(600000), DAILYMESSAGES(600000), EXPECTEDPERHOUR(60000), CURRENTSLAUGHTERAMOUNT(60000);

	private int refreshrate;
	FieldTypes(int refreshrate){
		this.refreshrate = refreshrate;
	}
	
	public int getRefreshRate(){
		return refreshrate;
	}
}
