import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;
public class SystemManager {
	Statement s;
	
	SystemManager(Connection con){
		try{
			this.s = con.createStatement();	
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("SystemManager error message: " + e.getMessage ());
			System.err.println ("SystemManager error number: " + e.getErrorCode ());
			try {
				con.close();
			} catch (SQLException e1) {
				System.err.println ("SystemManagerClose error message: " + e1.getMessage ());
				System.err.println ("SystemManagerClose error number: " + e1.getErrorCode ());
			}
		}
	}
	
	public void display(){
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		String menu;
		do{
			System.out.println("Select the menu");
			System.out.println("1. Authorize users");
			System.out.println("2. Confirm drama informations ");
			System.out.println("3. Quit");
			menu = sc.nextLine();
			if(menu.equals("1")){
				try {
					String input = null;
					do
					{
						System.out.println("Select Operation");
						System.out.println("1. Show new inserted officers");
						System.out.println("2. Show all registered officers");
						System.out.println("3. Go to menu");
						ResultSet rs;
						String userId;
						String companyName;
						String employeeNumber;
						int count;
						input = sc.nextLine(); 
						if(input.equals("1"))
						{
							s.executeQuery("select distinct * from Officer where confirm is null");
							rs = s.getResultSet();
							count = 0;
							do{
								userId = rs.getString("id");
								companyName = rs.getString("company_name");
								employeeNumber = rs.getString("employee_number");
								if(count == 0)
									System.out.printf("%20s|%12s|%20s\n", "userId", "company name", "employee number");
								System.out.printf("%20s|%12s|%20s\n", userId, companyName, employeeNumber);	
								count++;
							}while(!rs.next());
							
						/*	if(count == 0)
							{
								System.out.println("There is no new information");
								break;
							}
						*/	
							System.out.println("Select Operation");
							System.out.println("1. Accept/Deny applicant for membership");
							System.out.println("2. Go to menu");
							
							input = sc.nextLine();
							while(!input.equals("2") && !input.equalsIgnoreCase("q"))
							{
								
								s.executeQuery("select distinct * from Officer where confirm is null");
								rs = s.getResultSet();
								for(count = 0; rs.next(); count++)
								{
									userId = rs.getString("id");
									companyName = rs.getString("company_name");
									employeeNumber = rs.getString("employee_number");

									System.out.printf("%10s|%12s|%20s\n", "userId", "company name", "employee number");
									System.out.printf("%10s|%12s|%20s\n", userId, companyName, employeeNumber);	
								
									System.out.println("Do you accept this user? (Y: Accept, N: Reject, Q:quit)");
									input = sc.nextLine();
									if(input.equalsIgnoreCase("Y"))
									{
										//accept - update
										s.executeUpdate("update Officer set confirm = true where id = " + userId);
										break;
										
									}else if(input.equalsIgnoreCase("N"))
									{
									//reject
										s.executeUpdate("update Officer set confirm = false where id = " + userId);
										break;
									}
									else if(input.equalsIgnoreCase("q"))
									{
										break;
									}
								}
								
								if(count == 0)
									System.out.println("No registered officers");
								else
									System.out.println("All new applicants are checked");
							}	
						}else if(input.equals("2"))
						{
							s.executeQuery(" select distinct * from Officer where confirm is true");
							rs = s.getResultSet();
							
							for(count = 0; rs.next(); count++)
							{
								userId = rs.getString("id");
								companyName = rs.getString("company_name");
								employeeNumber = rs.getString("employee_number");
								if(count == 0)
									System.out.printf("%15s|%12s|%20s\n", "userId", "company name", "employee number");
								System.out.printf("%15s|%12s|%20s\n", userId, companyName, employeeNumber);	
							}
							if(count == 0)
								System.out.println("No registered officers");
							else
								System.out.println("All new applicants are checked");
						}
					}while(!input.equals("3"));
					//print¸¦ ÇÏ°í 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//System.out.println(e.getMessage());
					//System.out.println(e.getErrorCode());
					if(e.getErrorCode() == 0)
						System.out.println("No new applicant");
				}
				
				
			}
			else if(menu.equals("2")){
				try {
					String input = null;
					do
					{
						System.out.println("Select Operation");
						System.out.println("1. Show new inserted dramas");
						System.out.println("2. Show all registered dramas");
						System.out.println("3. Go to menu");
						ResultSet rs;
						String title, writer, station, actor, genre;
						int runningTime, ageLimit, id = -1;
						int count = 0;
						input = sc.nextLine(); 
						int nextId = -1;
						
						if(input.equals("1"))
						{
							//s.executeQuery("select * from Drama, Genre, Actor where Drama.id = Genre.drama_id and Genre.drama_id = Actor.drama_id");
							boolean empty = false;
							do
							{
								s.executeQuery("select distinct * from Drama where confirm is null and id > "+ id + " order by id" );
								rs = s.getResultSet();
								empty = true;
								for(count = 0; rs.next(); count++)
								{
									empty = false;
									id = rs.getInt("id");
									title = rs.getString("title");
									writer = rs.getString("writer");
									station = rs.getString("broad_station");
									runningTime = rs.getInt("running_time");
									ageLimit = rs.getInt("age_limit");
									
									nextId = rs.getInt("id");
									if(count == 0)
									System.out.printf("%3s|%15s|%15s|%15s|%10s|%10s|\n", "id", "title", "writer", 
											"broadstation", "running time", "age limit");
									System.out.println("===============================================================================");
									System.out.printf("%3d|%15s|%15s|%15s|%10d|%10d\n", id, title, writer, 
										station, runningTime, ageLimit);
									System.out.println("First aired: "+rs.getDate("first_aired"));
									
									s.executeQuery("select distinct * from Actor where drama_id = " + id);
									rs = s.getResultSet();
									for(count = 0; rs.next(); count++)
									{
										actor = rs.getString("name");
										if(count == 0)
											System.out.print("Actors: ");
										System.out.print(" "+actor +", ");
									}
									System.out.println("");
									
									s.executeQuery("select distinct * from Genre where drama_id = " + id);
									rs = s.getResultSet();
									for(count = 0; rs.next(); count++)
									{
										genre = rs.getString("genre");
										if(count == 0)
											System.out.print("Genres: ");
										System.out.print(genre + ", ");
									}
									System.out.println("\n----------------------------------------------------------------------------------");
									System.out.println("");
								}
								
							}while(!empty);
								
							System.out.println("Select Operation");
							System.out.println("1. Confirm information");
							System.out.println("2. Go to menu");
							
							input = sc.nextLine();
							while(!input.equals("2") && !input.equalsIgnoreCase("q"))
							{
								System.out.println("Insert drama id: ");
								String tempInput = sc.nextLine();
								
									System.out.println("Do you accept this information? (Y: Accept, N: Reject, Q:quit)");
									input = sc.nextLine();
									if(input.equalsIgnoreCase("Y"))
									{
										//accept - update
										s.executeUpdate("update Drama set confirm = true where id = " + tempInput);
										break;
										
									}else if(input.equalsIgnoreCase("N"))
									{
									//reject
										s.executeUpdate("update Drama set confirm = false where id = " + tempInput);
										break;
									}
									else if(input.equalsIgnoreCase("q"))
									{
										break;
									}
								}
						}else if(input.equals("2"))
						{
							s.executeQuery("select distinct * from Drama where confirm is true" );
							rs = s.getResultSet();
							for(count = 0; rs.next(); count++)
							{
								id = rs.getInt("id");
								title = rs.getString("title");
								writer = rs.getString("writer");
								station = rs.getString("broad_station");
								runningTime = rs.getInt("running_time");
								ageLimit = rs.getInt("age_limit");
								
								nextId = rs.getInt("id");
								if(count == 0)
								System.out.printf("%3s|%15s|%15s|%15s|%10s|%10s|\n", "id", "title", "writer", 
										"broadstation", "running time", "age limit");
								System.out.println("===============================================================================");
								System.out.printf("%3d|%15s|%15s|%15s|%10d|%10d\n", id, title, writer, 
									station, runningTime, ageLimit);
								System.out.println("First aired at: "+rs.getDate("first_aired") + " Last aired at : "+ rs.getDate("last_aired"));
								
							}
						}
					}while(!input.equals("3"));
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//System.out.println(e.getMessage());
					//System.out.println(e.getErrorCode());
					if(e.getErrorCode() == 0)
						System.out.println("No new data");
				}
				
			}
			else if(menu.equals("3")){
				flag = false;
				System.out.println("Bye!!");
			}
			else
				System.out.println("Please, select the number from 1 to 3");
		}while(flag);
	}
}
