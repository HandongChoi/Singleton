import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TrigerSet {
	Statement s;
	
	TrigerSet(Connection con){
		try{
			this.s = con.createStatement();
			// 트리거에 관한 것은 여기에다가 다 넣어주세요.
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("TrigerSet error message: " + e.getMessage ());
			System.err.println ("TrigerSet error number: " + e.getErrorCode ());
			try {
				con.close();
			} catch (SQLException e1) {
				System.err.println ("TrigerSetClose error message: " + e1.getMessage ());
				System.err.println ("TrigerSetClose error number: " + e1.getErrorCode ());
			}
		}
	}
}
