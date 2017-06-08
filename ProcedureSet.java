import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProcedureSet {
	Statement s;
	
	ProcedureSet(Connection con){
		try{
			this.s = con.createStatement();
			//여기에 모든 Procedure 정의해주세요.
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("ProcedureSet error message: " + e.getMessage ());
			System.err.println ("ProcedureSet error number: " + e.getErrorCode ());
			try {
				con.close();
			} catch (SQLException e1) {
				System.err.println ("ProcedureSetClose error message: " + e1.getMessage ());
				System.err.println ("ProcedureSetClose error number: " + e1.getErrorCode ());
			}
		}
	}
}
