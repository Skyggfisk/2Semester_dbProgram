

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class DBSingleConnection {

	private static final String server = "aur.dk";
	private static final String databaseName = "UCN_dmaa0216_2Sem_1";
	private static final String userName = "UCN_dmaa0216_2Sem_1";
	private static final String passWord = "Password1!";
	
	@SuppressWarnings("unused")
	private DatabaseMetaData dma;
	private static Connection con;
	private boolean inuse = false;
	
	
	public DBSingleConnection() {
		
	}
	
	private void openConnection() {
		String connectionString = "jdbc:sqlserver://" + server + ";databaseName=" + databaseName + ";user=" + userName + ";password=" + passWord;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//System.out.println("Driver find the driver");
		} catch (Exception e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(connectionString);
			con.setAutoCommit(true);
			dma = con.getMetaData();
		} catch (Exception e) {
			System.out.println("Connection problem");
			e.printStackTrace();
		}

	}
	
	
	
	public synchronized void closeConnection() {
		try {
			con.close();
			inuse = false;
			notifyAll();
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}
	
	public synchronized Connection getDBcon()
	{
		while (inuse) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		inuse = true;
		openConnection();
		return con;
	}
}
