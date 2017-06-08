import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class Connect {
	private Connection con = null;
	
	Connect (){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://peace.handong.edu", "db21000763", "21000763");
			if (con.isClosed() == false)
				System.out.println("Successfully connected to MySQL server."); 
		} catch(Exception e1) {System.err.println("Exception: " + e1.getMessage()); }
	}
	
	public Connection getConnection(){
		return this.con;
	}
	
}


