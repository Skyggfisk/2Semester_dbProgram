import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddValuesToDB {

	
	public static String addValuesSpeed(int value, int targetvalue, long stimestamp, DBSingleConnection dbSinCon) {
		PreparedStatement statement = null;
		String query = "INSERT INTO speed (value, targetvalue, stimestamp) VALUES (?, ?, ?)";
		Connection con = null;
		
		try {
			con = dbSinCon.getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setInt(1, value);
			statement.setInt(2, targetvalue);
			statement.setLong(3, stimestamp);
			statement.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				con.setAutoCommit(true);
				dbSinCon.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}
	
	public static String addValuesBatch(int value, long timeofslaughter, boolean organic, int batchnr, int housenr, String farmer, int avgweight, int teamnighttimetableid, int teamdaytimetableid) {
		PreparedStatement statement = null;
		String query = "INSERT INTO batch (value, timeofslaughter, organic, batchnr, housenr, farmer, avgweight, teamnighttimetableid, teamdaytimetableid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		
		try {
			con = DBConnection.getInstance().getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setInt(1, value);
			statement.setLong(2, timeofslaughter);
			statement.setBoolean(3, organic);
			statement.setInt(4, batchnr);
			statement.setInt(5, housenr);
			statement.setString(6, farmer);
			statement.setInt(7, avgweight);
			statement.setInt(8, teamnighttimetableid);
			statement.setInt(9, teamdaytimetableid);
			statement.executeQuery();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}
	
	public static String addValuesSlaughterAmount(int value, int batchid, int teamid, long satimestamp, DBSingleConnection dbSinCon) {
		//GetTimeStampValues gtsv = new GetTimeStampValues();
		
		/*if(gtsv.getTimeStampSA(dbSinCon)){
			return "værdi er sat ind";
		}*/
		
		
		
		PreparedStatement statement = null;
		long currentTime = System.currentTimeMillis();
		long oneMinAgo = currentTime - 60000;
		String query = "BEGIN IF NOT EXISTS(SELECT * FROM slaughteramount WHERE satimestamp > ?) BEGIN INSERT INTO slaughteramount (value, batchid, satimestamp, teamtimetableid) VALUES (?, ?, ?, ?) END END";
		//String query = "INSERT INTO slaughteramount (value, batchid, satimestamp, teamtimetableid) VALUES (?, ?, ?, ?)";
		Connection con = null;
		
		try {
			con = dbSinCon.getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setLong(1, oneMinAgo);
			statement.setInt(2, value);
			statement.setInt(3, batchid);
			statement.setLong(4, satimestamp);
			statement.setInt(5, teamid);
			statement.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				dbSinCon.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}
	
	public static String addValuesProductionStop(long stoptime, long stoplength, String stopdescription, int teamid) {
		PreparedStatement statement = null;
		String query = "INSERT INTO productionstop (stoptime, stoplength, stopdescription, teamid) VALUES (?, ?, ?, ?)";
		Connection con = null;
		
		try {
			con = DBConnection.getInstance().getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setLong(1, stoptime);
			statement.setLong(2, stoplength);
			statement.setString(3, stopdescription);
			statement.setInt(4, teamid);
			statement.executeQuery();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}
	
	public static String addValuesDailyMessage(String dmessage, long dtimestamp, long expire, long showdate) {
		PreparedStatement statement = null;
		String query = "INSERT INTO dailymessages (dmessage, dtimestamp, expire, showdate) VALUES (?, ?, ?, ?)";
		Connection con = null;
		
		try {
			con = DBConnection.getInstance().getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setString(1, dmessage);
			statement.setLong(2, dtimestamp);
			statement.setLong(3, expire);
			statement.setLong(4, showdate);
			statement.executeQuery();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}

	public static String addValuesToEmptyBraces(long timestamp, int value, int teamid, DBSingleConnection dbSinCon) {
		PreparedStatement statement = null;
		String query = "INSERT INTO emptybraces (starttimestamp, value, teamtimetableid) VALUES (?, ?, ?)";
		Connection con = null;
		
		try {
			con = dbSinCon.getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setLong(1, timestamp);
			statement.setInt(2, value);
			statement.setInt(3, teamid);
			statement.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				dbSinCon.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}

	public static String addValuesToTimeTable(long starttimestamp, long endtimestamp, int teamid) {
		PreparedStatement statement = null;
		String query = "INSERT INTO team (starttimestamp, endtimestamp, teamid) VALUES (?, ?, ?)";
		Connection con = null;
		
		try {
			con = DBConnection.getInstance().getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setLong(1, starttimestamp);
			statement.setLong(2, endtimestamp);
			statement.setInt(3, teamid);
			statement.executeQuery();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String addValuesToTeams(String teamname, int workers, int department) {
		PreparedStatement statement = null;
		String query = "INSERT INTO team (teamname, workers, department) VALUES (?, ?, ?)";
		Connection con = null;
		
		try {
			con = DBConnection.getInstance().getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setString(1, teamname);
			statement.setInt(2, workers);
			statement.setInt(3, department);
			statement.executeQuery();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.getInstance().closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}
	
	public static void getCurrentTeamId(long currentTime, DBSingleConnection dbSinCon){
		PreparedStatement statement = null;
		String query = "SELECT TOP 1 id, starttimestamp, endtimestamp, teamid FROM teamtimetable WHERE ? BETWEEN starttimestamp AND endtimestamp";
		ResultSet result = null;		
		int teamTimeTableId = 0;
		long startTime = 0;
		long endTime = 0;
		int teamId = 0;
		Connection con = null;
		try {
			con = dbSinCon.getDBcon();
			statement = con.prepareStatement(query);
			statement.setLong(1, currentTime);
			result = statement.executeQuery();
			
			if(result.isBeforeFirst()){
				result.next();
				teamTimeTableId = result.getInt("id");
				startTime = result.getLong("starttimestamp");
				endTime = result.getLong("endtimestamp");
				teamId = result.getInt("teamid");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				dbSinCon.closeConnection();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		WorkingTeam.getInstance().setEverything(teamId, teamTimeTableId, startTime, endTime);
	}
	
	public static boolean getOrganic(String currentTeam, DBSingleConnection dbSinCon){
		PreparedStatement statement = null;
		String query = "DECLARE @id INT = " + currentTeam + "; SELECT * FROM batch WHERE teamnighttimetableid = @id OR teamdaytimetableid = @id";
		Connection con = null;
		ResultSet res = null;
		Boolean returnval = false;
		
		try {
			con = dbSinCon.getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			res = statement.executeQuery();
			try {
				while(res.next()){
					if(res.getBoolean("organic")){
						returnval = true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				dbSinCon.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnval;
	}
	
	public static ArrayList<Integer> getBatchId(String teamtimetableid, DBSingleConnection dbSinCon){
		PreparedStatement statement = null;
		String query = "DECLARE @id INT = " + teamtimetableid + "; SELECT TOP 1 id, value FROM batch WHERE teamnighttimetableid = @id OR teamdaytimetableid = @id";
		Connection con = null;
		ResultSet res = null;
		ArrayList<Integer> result = new ArrayList<>();
		int value = 0;
		int id = 0;
		
		try {
			con = dbSinCon.getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			res = statement.executeQuery();
			if(res.isBeforeFirst()){
				res.next();
				id = res.getInt("id");
				value = res.getInt("value");
				result.add(id);
				result.add(value);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				dbSinCon.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static ArrayList<SlaughterAmountModel> getFillSlaughterAmountTable(DBSingleConnection dbSinCon) {
		PreparedStatement statement = null;
		String query = "SELECT IIF(organic = 1, value/88, value/212) as iterations, value, teamdaytimetableid, teamnighttimetableid, organic, batch.id, teamtimetable.starttimestamp, teamtimetable.endtimestamp FROM batch join teamtimetable ON teamnighttimetableid = teamtimetable.id";
		Connection con = null;
		ResultSet res = null;
		ArrayList<SlaughterAmountModel> result = new ArrayList<>();
		
		try {
			con = dbSinCon.getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			res = statement.executeQuery();
			while (res.next()) {
					SlaughterAmountModel something = new SlaughterAmountModel(res.getLong("endtimestamp"), res.getInt("iterations"), res.getInt("value"), res.getInt("teamdaytimetableid"), res.getInt("teamnighttimetableid"), res.getBoolean("organic"), res.getInt("id"), res.getLong("starttimestamp"));
					result.add(something);
				}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				dbSinCon.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
