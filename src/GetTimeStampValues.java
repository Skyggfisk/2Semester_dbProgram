import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTimeStampValues {
	
	public long chooser(int type, DBSingleConnection dbSinCon){
		switch(type){
		case 1: 
			return getTimeStampSA(dbSinCon);
		case 2:
			return getTimeStampBraces(dbSinCon);
		case 3:
			return getTimeStampSpeed(dbSinCon);
		case 4:
			return getTimeStampWT(dbSinCon);
		default:
			return 0L;
		}
	}
	
	public long getTimeStampSpeed(DBSingleConnection dbSinCon){
		PreparedStatement statement = null;
		String query = "SELECT TOP 1 stimestamp FROM speed ORDER BY stimestamp DESC";
		Connection con = null;
		ResultSet results = null;
		long res = 0L;
		try {
			con = dbSinCon.getDBcon();
			statement = con.prepareStatement(query);
			results = statement.executeQuery();
			con.commit();
			if(results.isBeforeFirst()){
				res = results.getLong("stimestamp");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			dbSinCon.closeConnection();
		}
		return res;
	}
	
	public long getTimeStampBraces(DBSingleConnection dbSinCon){
		PreparedStatement statement = null;
		String query = "SELECT TOP 1 starttimestamp FROM emptybraces ORDER BY starttimestamp DESC";
		Connection con = null;
		ResultSet results = null;
		long res = 0L;
		try {
			con = dbSinCon.getDBcon();
			statement = con.prepareStatement(query);
			results = statement.executeQuery();
			con.commit();
			if(results.isBeforeFirst()){
				res = results.getLong("starttimestamp");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			dbSinCon.closeConnection();
		}		
		return res;
	}
	
	public long getTimeStampSA(DBSingleConnection dbSinCon){
		PreparedStatement statement = null;
		String query = "SELECT TOP 1 satimestamp FROM slaughteramount ORDER BY satimestamp DESC";
		Connection con = null;
		ResultSet results = null;
		long res = 0L;
		try {
			con = dbSinCon.getDBcon();
			statement = con.prepareStatement(query);
			results = statement.executeQuery();
			con.commit();
			if(results.isBeforeFirst()){
				res = results.getLong("satimestamp");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			dbSinCon.closeConnection();
		}		
		return res;
	}
	
	public long getTimeStampWT(DBSingleConnection dbSinCon){
		PreparedStatement statement = null;
		String query = "SELECT * FROM workingteamfunction(?)";
		Connection con = null;
		ResultSet results = null;
		long now = System.currentTimeMillis();
		long res = 0L;
		try {
			con = dbSinCon.getDBcon();
			statement = con.prepareStatement(query);
			statement.setLong(1, now);
			results = statement.executeQuery();
			con.commit();
			if(results.isBeforeFirst()){
				res = results.getLong("starttimestamp");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			dbSinCon.closeConnection();
		}		
		return res;
	}
}
