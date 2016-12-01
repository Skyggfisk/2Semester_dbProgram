

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class DBConnection {

	private static final String server = "kraka.ucn.dk";
	private static final String databaseName = "UCN_dmaa0216_2Sem_1";
	private static final String userName = "UCN_dmaa0216_2Sem_1";
	private static final String passWord = "Password1!";
	
	private DatabaseMetaData dma;
	private static Connection con;
	
	private static DBConnection instance = null;
	
	
	private DBConnection() {
		String connectionString = "jdbc:sqlserver://" + server + ";databaseName=" + databaseName + ";user=" + userName + ";password=" + passWord;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//System.out.println("Driver find the driver");
		} catch (Exception e) {
			System.out.println("Driver not found");
			System.out.println(e.getMessage());
			
		}
		
		try {
			con = DriverManager.getConnection(connectionString);
			con.setAutoCommit(true);
			dma = con.getMetaData();
			//System.out.println("connection suceded");
		} catch (Exception e) {
			System.out.println("Con problem");
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	public static void closeConnection() {
		try {
			con.close();
			instance = null;
			//System.out.println("con closed");
		} catch (Exception e) {
			System.out.println("error");
		}
	}
	
	public static Connection getDBcon()
	{
		return con;
	}
	
	public static DBConnection getInstance() {
		if(instance == null){
			instance = new DBConnection();
		}
		return instance;
	}
}
