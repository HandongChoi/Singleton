import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SignUp {
	Statement s;
	Scanner scan = new Scanner(System.in);
	
	SignUp(Connection con){
		try{
			this.s = con.createStatement();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("SignUp error message: " + e.getMessage ());
			System.err.println ("SignUp error number: " + e.getErrorCode ());
			try {
				con.close();
			} catch (SQLException e1) {
				System.err.println ("SignUpClose error message: " + e1.getMessage ());
				System.err.println ("SignUpClose error number: " + e1.getErrorCode ());
			}
		}
	}
	
	public void processSignUp(){
		while(true){
			//여기에서 완성시켜 주세요
			System.out.println("Who are you? Select the number");
			System.out.println("1. Viewer");
			System.out.println("2. Officer");
			System.out.println("3. Analyst");
			System.out.println("4. System Manager");
			System.out.println("5. Quit");
			int user = scan.nextInt();
			
			if(user == 1){
				System.out.println("Please type your ID ");
				String type_id = scan.next();
				System.out.println("Please type your password : ");
				String type_password = scan.next();
				System.out.println("What kind of genre do you prefer? ");
				System.out.println("Please select only the genres displayed on the next line");
				System.out.println("'horror','comedy','scifi', 'history', 'romance', 'action'");
				String type_pf_genre = scan.next();
				System.out.println("What is your gender? (M/F) ");
				String type_gender = scan.next();
				System.out.println("When is your birthdate(YYYY-MM-DD) : ");
				String type_birth = scan.next();
				System.out.println("Let me know your address ");
				String type_address = scan.next();
				System.out.println("Do you want to accept our drama reccommendation? : (YES = 1, NO = 0)");
				int type_recommend = scan.nextInt();
				System.out.println("Who Is your favorite actor? ");
				String type_actor = scan.next();
				System.out.println("Where is your nationality? ");
				System.out.println("Please select only the nationalities displayed on the next line");
				System.out.println("'Tai', 'Korea', 'China', 'Japan', 'Philipine', 'Taiwan', 'America', 'EU', 'SouthAmerica', 'Africa'");
				String type_nation = scan.next();
				
				try {
					s.execute("INSERT INTO Viewer (id,password,preference_genre,gender,birth,address,recommendation_accept,preference_actor,nationality) VALUES('"+type_id +"','"+type_password+"','"+type_pf_genre+"','"+type_gender+"','"+type_birth+"','"+type_address+"','"+type_recommend+"','"+type_actor+"','"+type_nation+"')");
					break;
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println ("Viewer signup error message: " + e.getMessage ());
					System.err.println ("Viewer signup error number: " + e.getErrorCode ());
				}
				break;
			}
			else if(user == 2){
				System.out.println("Please type your ID ");
				String type_id = scan.next();
				System.out.println("Please type your password : ");
				String type_password = scan.next();
				System.out.println("Which company do you work for? ");
				String type_company = scan.next();
				System.out.println("What is your employee_number : ");
				String type_emp_num = scan.next();
				
				try {
					s.execute("INSERT INTO Officer (id,password,company_name,employee_number) VALUES('"+type_id +"','"+type_password+"','"+type_company+"','"+type_emp_num+"')");
					break;
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println ("Officer signup error message: " + e.getMessage ());
					System.err.println ("Officer signup error number: " + e.getErrorCode ());
				}
				break;
			}
			else if(user == 3){
				System.out.println("Please type your ID ");
				String type_id = scan.next();
				System.out.println("Please type your password : ");
				String type_password = scan.next();
				
				try {
					s.execute("INSERT INTO Analyst (id,password) VALUES('"+type_id +"','"+type_password+"')");
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println ("Analyst signup error message: " + e.getMessage ());
					System.err.println ("Analyst signup error number: " + e.getErrorCode ());
				}
				break;
			}
			else if(user == 4){
				System.out.println("Please type your ID ");
				String type_id = scan.next();
				System.out.println("Please type your password : ");
				String type_password = scan.next();
				
				try {
					s.execute("INSERT INTO System_manager (id,password) VALUES('"+type_id +"','"+type_password+"')");
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println ("System_manager signup error message: " + e.getMessage ());
					System.err.println ("System_manager signup error number: " + e.getErrorCode ());
				}
				break;
			}
			else if(user == 5)
				break;
			else{
				System.out.println("Please, select the number from 1 to 5");
			}
		}
	}
}
