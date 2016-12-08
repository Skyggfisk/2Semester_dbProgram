

/**
 * The values which can be pulled from the controller.
 * Refreshrate has been appended..?
 */
public enum FieldTypes {
WORKINGTEAM(900), SPEED(60), AVGWEIGHT(60), ORGANIC(60),
SLAUGTHERAMOUNTNIGHT(60), SLAUGTHERAMOUNTDAY(60),
STOPNIGHT(300), STOPDAY(300),
DAYEXPECTED(600),
TOTALSLAUGTHERAMOUNT(60), EXPECTEDFINISH(60),
PRODUCTIONSTOPS(600), DAILYMESSAGES(600), EXPECTEDPERHOUR(60), CURRENTSLAUGHTERAMOUNT(60);

	private int refreshrate;
	FieldTypes(int refreshrate){
		this.refreshrate = refreshrate;
	}
	
	public int getRefreshRate(){
		return refreshrate;
	}
}
