

/**
 * The values which can be pulled from the controller.
 * Refreshrate has been appended..?
 */
public enum FieldTypes {
WORKINGTEAM(900), SPEED(5), AVGWEIGHT(5), ORGANIC(5),
SLAUGTHERAMOUNTNIGHT(5), SLAUGTHERAMOUNTDAY(5),
STOPNIGHT(5), STOPDAY(5),
DAYEXPECTED(5),
TOTALSLAUGTHERAMOUNT(5), EXPECTEDFINISH(5),
PRODUCTIONSTOPS(5), DAILYMESSAGES(5), EXPECTEDPERHOUR(5), CURRENTSLAUGHTERAMOUNT(5);

	private int refreshrate;
	FieldTypes(int refreshrate){
		this.refreshrate = refreshrate;
	}
	
	public int getRefreshRate(){
		return refreshrate;
	}
}
