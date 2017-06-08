
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;


public class Main {
	public static void main(String[] args) throws SQLException{
		
		
		Connect server = new Connect();
		Connection server_connection = server.getConnection();
		Scanner sc = new Scanner(System.in);
		
		try{
			Statement s = server_connection.createStatement();
			s.executeQuery("use 21000763_Hyoeun");
		}
		catch (SQLException e) {
			//e.printStackTrace();
			System.err.println ("Main error message: " + e.getMessage ());
			System.err.println ("Main error number: " + e.getErrorCode ());
			try {
				server_connection.close();
			} catch (SQLException e1) {
				System.err.println ("Close error message: " + e1.getMessage ());
				System.err.println ("Close error number: " + e1.getErrorCode ());
			}
		}
		
		
		CreateTables ct = new CreateTables(server_connection);
		TrigerSet ts = new TrigerSet(server_connection);
		ProcedureSet ps = new ProcedureSet(server_connection);
		
		
		
		System.out.println("");
		System.out.println("Welcom to Drama World!");
		
		boolean flag = true;
		
		do{
		System.out.println("Are you member of DramaWorld?(Y/N), if you want to quit(Q).");
		String answer = sc.next();
		if(answer.equals("Y")){
			Login login = new Login(server_connection);
			boolean loginSuccess = false;
			int user;
			while(true){
				System.out.println("Who are you? Select the number");
				System.out.println("1. Viewer");
				System.out.println("2. Officer");
				System.out.println("3. Analyst");
				System.out.println("4. System Manager");
				System.out.println("5. Quit");
				user = sc.nextInt();
				
				if(user == 1){
					loginSuccess = login.checkViewer(server_connection);
					break;
				}
				else if(user == 2){
					loginSuccess = login.checkOfficer();
					break;
				}
				else if(user == 3){
					loginSuccess = login.checkAnalyst();
					break;
				}
				else if(user == 4){
					loginSuccess = login.checkSystemManager();
					break;
				}
				else if(user == 5)
					break;
				else{
					System.out.println("Please, select the number from 1 to 5");
				}
			}
			if(loginSuccess){
				if(user == 1){
					Viewer viewer = new Viewer(server_connection);
					viewer.display();
				}
				else if(user == 2){
					Officer official = new Officer(server_connection);
					official.display();
				}
				else if(user == 3){
					Analyst analyst = new Analyst(server_connection);
					analyst.display();
				}
				else if(user == 4){
					SystemManager systemManager = new SystemManager(server_connection);
					systemManager.display();
				}
			}
			else{
				System.out.println("Login Fail");
			}
			
		}
		else if(answer.equals("N")){
			SignUp signup = new SignUp(server_connection);
			signup.processSignUp();
		}
		else if(answer.equals("Q")){
			flag = false;
		}
		else
			System.out.println("Please, insert Y or N or Q");
		}while(flag);
		
		System.out.println("Bye!");
		
		
		try {
			if (server_connection != null)
				server_connection.close();
			
		} catch(SQLException e) {}
	}
}
