package DramaWorld;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Login {
	Statement s;
	Scanner sc = new Scanner(System.in);
	Login(Connection con){
		try{
			this.s = con.createStatement();
			//필요한 execution 있으면 여기서 실행해도 됨. 밑에서 따로 실행해도 되고.
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("Login error message: " + e.getMessage ());
			System.err.println ("Login error number: " + e.getErrorCode ());
			try {
				con.close();
			} catch (SQLException e1) {
				System.err.println ("LoginClose error message: " + e1.getMessage ());
				System.err.println ("LoginClose error number: " + e1.getErrorCode ());
			}
		}
	}
	
	//여기서부터 id, pw를 사용자로부터 입력받고 java를 통해 입력 받고 그것을 가지고 DB table과 매칭 시켜서 login 정보가 일치할 경우 result = true, 다를경우 result = false
	public boolean checkViewer(){
		String ID,  PW, v_genre;
		System.out.println("Insert ID: ");
		ID = sc.nextLine();
		System.out.println("Insert PW: ");
		PW = sc.nextLine();
		try {
			s.executeQuery("select * from Viewer where id = '"+ID+"' AND password = '"+PW+"'");
			ResultSet res = s.getResultSet();
			if(!res.next())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("CheckViewer error message: " + e.getMessage ());
			System.err.println ("CheckViewer error number: " + e.getErrorCode ());
		}
		return true;
	}
	
	// recommend부분 수정중
	public boolean checkViewer(Connection server_connection){
		
		String ID,  PW, v_genre, v_id;
		System.out.println("Insert ID: ");
		ID = sc.nextLine();
		System.out.println("Insert PW: ");
		PW = sc.nextLine();
		try {
			s.executeQuery("select * from Viewer where id = '"+ID+"' AND password = '"+PW+"'");
			ResultSet res = s.getResultSet();
			if(!res.next())
				return false;
	
			else{
				v_id = res.getString("id");
				v_genre = res.getString("preference_genre");
				Viewer viewer = new Viewer(server_connection);
				viewer.popup(v_genre);
				viewer.display(v_id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("CheckViewer error message: " + e.getMessage ());
			System.err.println ("CheckViewer error number: " + e.getErrorCode ());
		}
		return true;
	}
	
	public boolean checkOfficer(){
		String ID,  PW;
		System.out.println("Insert ID: ");
		ID = sc.nextLine();
		System.out.println("Insert PW: ");
		PW = sc.nextLine();
		try {
			s.executeQuery("select * from Officer where id = '"+ID+"' AND password = '"+PW+"' AND confirm = 'true'");
			ResultSet res = s.getResultSet();
			if(!res.next())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("CheckOfficer error message: " + e.getMessage ());
			System.err.println ("CheckOfficer error number: " + e.getErrorCode ());
		}
		return true;
	}
	
	public boolean checkAnalyst(){
		String ID,  PW;
		System.out.println("Insert ID: ");
		ID = sc.nextLine();
		System.out.println("Insert PW: ");
		PW = sc.nextLine();
		try {
			s.executeQuery("select * from Analyst where id = '"+ID+"' AND password = '"+PW+"'");
			ResultSet res = s.getResultSet();
			if(!res.next())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("CheckAnalyst error message: " + e.getMessage ());
			System.err.println ("CheckAnalyst error number: " + e.getErrorCode ());
		}
		return true;
	}
	
	public boolean checkSystemManager(){
		String ID,  PW;
		System.out.println("Insert ID: ");
		ID = sc.nextLine();
		System.out.println("Insert PW: ");
		PW = sc.nextLine();
		try {
			s.executeQuery("select * from System_manager where id = '"+ID+"' AND password = '"+PW+"'");
			ResultSet res = s.getResultSet();
			if(!res.next())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("CheckSystem_Manager error message: " + e.getMessage ());
			System.err.println ("CheckSystem_Manager error number: " + e.getErrorCode ());
		}
		return true;
	}
	
}
