import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Officer {
	Statement s;
	
	Officer(Connection con){
		try{
			this.s = con.createStatement();	
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("Officer error message: " + e.getMessage ());
			System.err.println ("Officer error number: " + e.getErrorCode ());
			try {
				con.close();
			} catch (SQLException e1) {
				System.err.println ("OfficerClose error message: " + e1.getMessage ());
				System.err.println ("OfficerClose error number: " + e1.getErrorCode ());
			}
		}
	}
	
	
	public void checkInput(String input, String category)
	{
		
	}
	
	public void display(){
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		String menu;
		
		do{
			System.out.println("Select the menu");
			System.out.println("1. Insert a information of drama");
			System.out.println("2. Confirm-Check");
			System.out.println("3. Modify inserted information");
			System.out.println("4. Quit");
			menu = sc.nextLine();
			
			if(menu.equals("1")){
				String title = null, writer= null, firstAired= null, lastAired= null, broadStation= null, runningTime= null;
				String ageLimit= null, replaySite= null, genre, actors= null;
				
				do
				{
					try{
						System.out.println("Insert Drama title(blank is not allowed): ");
						title = sc.nextLine();
						//checkInput(title, "text");
						//System.out.println("")
						s.executeUpdate("insert into Drama(title)" + "values('"+title + "')");
					}catch(SQLException e1)
					{
						System.out.println("Please check input again");
					//	System.out.println(e1.getMessage());
						title = null;
					}
				}while(title == null || title.length() <= 0);
				
				do
				{
					try{
						System.out.println("Insert Drama writer(blank is not allowed): ");
						writer = sc.nextLine();
						//checkInput(title, "text");
						//System.out.println("")
						s.executeUpdate("update Drama set writer = " + "' " + writer +"' where title = '" + title+"'");
					}catch(SQLException e1)
					{
						System.out.println("Please check input again");
						//System.out.println(e1.getMessage());
						writer = null;
					}
				}while(writer == null || writer.length() <= 0);
					
				boolean flagInput = true;
				while(flagInput)
				{
					try{
						System.out.println("Insert first aired date(2017-00-00): ");
						firstAired = sc.nextLine();
						s.executeUpdate("update Drama set first_aired = " + "' " + firstAired +"' where title = '" + title+"'");
						flagInput = false;
					}catch(SQLException e1)
					{
						System.out.println("Please check input again");
					//	System.out.println(e1.getMessage());
					}
				}
				
				flagInput = true;
				while(flagInput)
				{
					try{
						System.out.println("Insert last aired date(2017-00-00): ");
						lastAired = sc.nextLine();
						s.executeUpdate("update Drama set last_aired = " + "' " + lastAired +"' where title = '" + title+"'");
						flagInput = false;
					}catch(SQLException e1)
					{
						System.out.println("Please check input again");
						//System.out.println(e1.getMessage());
					}
				}
				
				do
				{
					try{
						System.out.println("Insert boradcasting station: blank is not allowed)");
						broadStation = sc.nextLine();
						s.executeUpdate("update Drama set broad_station = " + "' " + broadStation +"' where title = '" + title+"'");
					}catch(SQLException e1)
					{
						System.out.println("Please check input again");
						//System.out.println(e1.getMessage());
						broadStation = null;
					}
				}while(broadStation == null || broadStation.length() <= 0);
				
				
				do
				{
					try{
						System.out.println("Insert running_time(blank is not allowed): ");
						runningTime = sc.nextLine();
						s.executeUpdate("update Drama set running_time = " +  runningTime + " where title = '" + title+"'");
					}catch(SQLException e1)
					{
						System.out.println("Please check input again");
						System.out.println(e1.getMessage());
						runningTime = null;
					}
				}while(runningTime == null || runningTime.length() <= 0);
				
				flagInput = true;
				while(flagInput)
				{
					try{
						
						System.out.println("Insert age limit(blank is not allowed): ");
						ageLimit = sc.nextLine();
						s.executeUpdate("update Drama set age_limit = " + "'" + ageLimit +"' where title = '" + title+"'");
						flagInput = false;
					}catch(SQLException e1)
					{
						System.out.println("Please check input again");
						//System.out.println(e1.getMessage());
					}
				}
				
				do
				{
					try{
						System.out.println("Insert replaySite(blank is not allowed): ");
						replaySite = sc.nextLine();
						s.executeUpdate("update Drama set replay_site_link = " + "' " + replaySite +"' where title = '" + title+"'");
					}catch(SQLException e1)
					{
						System.out.println("Please check input again");
						//System.out.println(e1.getMessage());
						broadStation = null;
					}
				}while(broadStation == null || broadStation.length() <= 0);
				
			//===============================================================================================//
			//================================= insert genre and actors =====================================// 
				int countGenre = 0;
				boolean flagGenre = false;
				do{
					System.out.println("Insert Genre(horror, comedy, scifi, history, romance, action, q: quit): ");
					genre = sc.nextLine();
					try {
						if(genre.length() > 0 && !genre.equalsIgnoreCase("q"))
						{
							s.executeUpdate("insert into Genre(drama_id, genre) select id, '" + genre + "' from Drama where title = '" + title + "'");
						}else if(genre.equalsIgnoreCase("q"))
						{
							s.executeQuery("select genre from Genre, Drama where drama_id = id and title = '" + title + "'");
							ResultSet rs = s.getResultSet();
							if(rs.next())
							{
								flagGenre = true;
							}else
							{
								System.out.println("You should insert at least one genre");
							}
						}
							
					} catch (SQLException e) {
						System.out.println("Please check input again");
						e.printStackTrace();
					}
				}while(!flagGenre);
				
				
				System.out.println("Insert main acctors(s: supporting actor, q: quit): ");
				actors = sc.nextLine();
				boolean mainActor = true;
				
				while(!actors.equalsIgnoreCase("q")){
					if(actors.equalsIgnoreCase("s") || mainActor == false){
						if(mainActor == false)
							;
						else{	
							System.out.println("Insert supporting actors(m: main actor, q: quit): ");
							actors = sc.nextLine();
						}
						try{
							if(actors!= null && actors.length() >0)
							s.executeUpdate("insert into Actor(drama_id, name, role) select id, '" + actors + "', 'Supporting'"
									+ " from Drama where title = '" + title + "'");
						} catch (SQLException e){
							e.printStackTrace();
						}
						System.out.println("Insert supporting actors(m: main actor, q: quit): ");
						mainActor = false;
					}
					else{
						if(mainActor == false){
							System.out.println("Insert main actors(s: supporting actor, q: quit): ");
							actors = sc.nextLine();
							mainActor = true;
						}
						else
						{
							try {
								if(actors!= null && actors.length() >0)
								s.executeUpdate("insert into Actor(drama_id, name, role) select id, '" + actors + "', 'Main'"
										+ " from Drama where title = '" + title + "'");
							} catch (SQLException e) {
								e.printStackTrace();
							}
							System.out.println("Insert main actors(s: supporting actor, q: quit): ");
						}
					}
					actors = sc.nextLine();
				}	
			}
			
			// ------------------------------It has to be completed------------------------------
			else if(menu.equals("2")){
				try {
					s.executeQuery("select id, title, broad_station, confirm from Drama");
					ResultSet rs;
					int dramaId;
					String title;
					String broad_station;
					
					rs = s.getResultSet();
					int count = 0;
					for(count = 0; rs.next(); count++){
						String checked;
						dramaId = rs.getInt("id");
						title = rs.getString("title");
						if(rs.getBoolean("confirm") == true)
							checked = "Checked";
						else checked = "Unchecked";
						if(count == 0){
							System.out.printf("%10s|%20s|%12s\n", "Drama ID", "Title", "Status");
							System.out.printf("======================================================================\n");
						}
						System.out.printf("%10d|%20s|%12s\n", dramaId, title, checked);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			// ------------------------------It has to be completed------------------------------
			else if(menu.equals("3")){
				System.out.println("Select category to be modified");
				System.out.println("1: title, 2: writer, 3: broadcasting station, 4: first aired date, "
						+ "5: last aired date. 6. genre, 7:actor");
				String input = sc.nextLine();
				if(input.equals("1"))
				{
					;
				}else if(input.equals("2"))
				{
					;
				}else if(input.equals("3"))
				{
					;
				}else if(input.equals("4"))
				{
					;
				}else if(input.equals("5"))
				{
					;
				}else if(input.equals("6"))
				{
					;
				}else if(input.equals("7"))
				{
					;
				}else
				{
					System.out.println("please select number between 1~7");
				}
			}
			else if(menu.equals("4")){
				flag = false;
				System.out.println("Bye!!");
			}
			else
				System.out.println("Please, select the number from 1 to 3");
		}while(flag);
	}
}