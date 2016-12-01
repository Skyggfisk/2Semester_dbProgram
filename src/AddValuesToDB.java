import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddValuesToDB {

	
	public static String addValuesSpeed(int value, int targetvalue, long stimestamp) {
		PreparedStatement statement = null;
		String query = "INSERT INTO speed (value, targetvalue, stimestamp) VALUES (?, ?, ?)";
		Connection con = null;
		
		try {
			con = DBConnection.getInstance().getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setInt(1, value);
			statement.setInt(2, targetvalue);
			statement.setLong(3, stimestamp);
			statement.executeQuery();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.closeConnection();
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
				DBConnection.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}
	
	public static String addValuesSlaughterAmount(int value, int batchid, int teamid, long satimestamp) {
		PreparedStatement statement = null;
		String query = "INSERT INTO slaughteramount (value, batchid, satimestamp, teamtimetableid) VALUES (?, ?, ?, ?)";
		Connection con = null;
		
		try {
			con = DBConnection.getInstance().getDBcon();
			con.setAutoCommit(false);
			statement = con.prepareStatement(query);
			statement.setInt(1, value);
			statement.setInt(2, batchid);
			statement.setLong(3, satimestamp);
			statement.setInt(4, teamid);
			statement.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.closeConnection();
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
				DBConnection.closeConnection();
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
				DBConnection.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}

	public static String addValuesToEmptyBraces(long timestamp, int value, int teamid) {
		PreparedStatement statement = null;
		String query = "INSERT INTO emptybraces (starttimestamp, value, teamtimetableid) VALUES (?, ?, ?)";
		Connection con = null;
		
		try {
			con = DBConnection.getInstance().getDBcon();
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
				DBConnection.closeConnection();
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
				DBConnection.closeConnection();
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
				DBConnection.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "true";
	}
	
	public static int getCurrentTeamId(long currentTime){
		
		PreparedStatement statement = null;
		String query = "DECLARE @time BIGINT = ?; SELECT team, id FROM teamtimetable WHERE (starttimestamp < @time AND @time < endtimestamp)";
		ResultSet result = null;
		int teamId = 0;
		Connection con = null;
		try {
			con = DBConnection.getDBcon();
			con.setAutoCommit(true);
			statement = con.prepareStatement(query);
			statement.setLong(1, currentTime);
			result = statement.executeQuery();
			result.next();
			teamId = result.getInt("team");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnection.closeConnection();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return teamId;
	}
}
