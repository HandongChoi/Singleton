
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Viewer {
	Statement s;
	Viewer(){};
	Viewer(Connection con){
		try{
			this.s = con.createStatement();	
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("Viewer error message: " + e.getMessage ());
			System.err.println ("Viewer error number: " + e.getErrorCode ());
			try {
				con.close();
			} catch (SQLException e1) {
				System.err.println ("ViewerClose error message: " + e1.getMessage ());
				System.err.println ("ViewerClose error number: " + e1.getErrorCode ());
			}
		}
	}
	
	public void display(){

		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		boolean flag2 = true;
		int menu;
		
		do{
			System.out.println("Select the menu");
			System.out.println("1. Search");
			System.out.println("2. FeedBack");
			System.out.println("3. Quit");
			menu = sc.nextInt();
			if(menu == 1){
				System.out.print("Search(writer or title or actor): ");
				String Searchkeyword = sc.next();
				try {
					s.execute("UPDATE Drama SET hit = hit + 1 where title "
				              + "LIKE '%" + Searchkeyword +"%' OR writer LIKE '%" + Searchkeyword + "%'");
					s.executeQuery("select * from Drama LEFT JOIN Genre ON Drama.id = Genre.drama_id where title "
							+ "LIKE '%" + Searchkeyword +"%' OR writer LIKE '%" + Searchkeyword + "%'");

					ResultSet res = s.getResultSet();
					if(!res.next()){
						//System.out.println("There is no writer or title");
						s.executeQuery("SELECT * FROM Drama LEFT JOIN Actor ON Drama.id = Actor.drama_id WHERE name = '"+Searchkeyword+"'");
						res = s.getResultSet();
						if(!res.next()){
							System.out.println("empty");
						}
						else{
							do{
							String title = res.getString("title");
							String writer = res.getString("writer");
							Integer first_aired = res.getInt("first_aired");
							Integer last_aired = res.getInt("last_aired");
							String broad_station = res.getString("broad_station");
							Integer running_time = res.getInt("running_time");
							//String replay_site_link = res.getString("replay_site_link");
							Float average_score = res.getFloat("average_score");
							String age_limit = res.getString("age_limit");
							Integer drama_id = res.getInt("id");
							System.out.println("title: " + title + "\nwriter: " + writer + "\nfirst_aired: " + first_aired + "\nlast_aired: " + last_aired + "\nbroad_station: " + broad_station + 
									"\nrunning_time: " + running_time + "\naverage_score: " + average_score + 
									"\nage_limit: " + age_limit + "\ndrama_id: " + drama_id + "\n======================");
							}while(res.next());
						}
					}
					else{
						//System.out.println("There is writer or title");
						res = s.getResultSet();
						do{
							String title = res.getString("title");
							String writer = res.getString("writer");
							String genre = res.getString("genre");
							Integer first_aired = res.getInt("first_aired");
							Integer last_aired = res.getInt("last_aired");
							String broad_station = res.getString("broad_station");
							Integer running_time = res.getInt("running_time");
							//String replay_site_link = res.getString("replay_site_link");
							Float average_score = res.getFloat("average_score");
							String age_limit = res.getString("age_limit");
							Integer drama_id = res.getInt("id");
							System.out.println("title: " + title + "\nwriter: " + writer + "\ngenre: " + genre + 
									"\nfirst_aired: " + first_aired + "\nlast_aired: " + last_aired + "\nbroad_station: " + broad_station + 
									"\nrunning_time: " + running_time + "\naverage_score: " + average_score + //"\nreplay_site_link: " + replay_site_link + 
									"\nage_limit: " + age_limit  + "\ndrama_id: " + drama_id + "\n======================");
							}while(res.next());
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println ("Viewer Search error message: " + e.getMessage ());
					System.err.println ("Viewer Search error number: " + e.getErrorCode ());
				}
			}
			//}
			else if(menu == 2){
					System.out.println("Welcome to Feedback Zone!!");
					System.out.println("1. Write down your own feedback");
					System.out.println("2. Read someone else's feedback");
					System.out.println("3. Quit");
					int feed = sc.nextInt();
					
					if(feed == 1)
					{
						System.out.println("Input your id: ");
						String input_id = sc.next();
						System.out.println("Input drama id: ");
						int input_drama_id = sc.nextInt();
						System.out.println("Input your score(0~10): ");
						int input_score = sc.nextInt();
						sc.nextLine();
						System.out.println("Write down your review(Within 300 Characters) : ");
						String input_review = sc.nextLine();
						try {
							//calculate and insert average_score
							s.executeQuery("SELECT * FROM Feedback WHERE drama_id = " + input_drama_id); //call the all of each score
							ResultSet rs = s.getResultSet();
							Integer Sumofscore = 0;
							if(rs.next()){
								do{
									Integer eachscore = rs.getInt("score");
									Sumofscore = Sumofscore + eachscore; //sum all eachscore
								}while(rs.next());
							}
								
							s.executeQuery("SELECT COUNT(drama_id) FROM Feedback WHERE drama_id = " + input_drama_id);
							rs = s.getResultSet();
							if(rs.next()){
								Integer count = rs.getInt("COUNT(drama_id)");
								Float average_score = (float)Sumofscore/count; //calculate average_score
								s.executeUpdate("UPDATE Drama SET average_score = " + average_score
										+ " WHERE id = " + input_drama_id); //insert average_score
							}
								s.execute("INSERT INTO Feedback(viewer_id,drama_id,score,review,review_date) VALUES('"+input_id +"','"+input_drama_id+"','"+input_score+"','"+input_review+"',"+"NOW())");
						} catch (SQLException e) {
						e.printStackTrace();
							System.err.println ("Feedback write error message: " + e.getMessage ());
							System.err.println ("Feedback write error number: " + e.getErrorCode ());
						}
					}
					else if(feed == 2)
					{
						try {
							s.execute("SELECT * FROM Feedback");

							ResultSet res = s.getResultSet();
							if(!res.next()){
								System.out.println("empty");
							}
							else{
								do{
									String viewer_id = res.getString("viewer_id");
									Integer drama_id = res.getInt("drama_id");
									Integer score = res.getInt("score");
									String review = res.getString("review");
									Integer review_date = res.getInt("review_date");
									
									System.out.println("======================");
									System.out.println("viewer_id: " + viewer_id + "\ndrama_id: " + drama_id + "\nscore: " + score + 
											"\nreview: " + review + "\nreview_date: " + review_date + "\n======================");
								}while(res.next());
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.err.println ("Feedback READ error message: " + e.getMessage ());
							System.err.println ("Feedback READ error number: " + e.getErrorCode ());
						}
							
					}
					else if(feed == 3){
						flag = false;
						System.out.println("Bye!!");
					}
					else
						System.out.println("Please, select the number 1 or 2");				
			}
			else if(menu == 3){
				flag = false;
				System.out.println("Bye!!");
			}
			else
				System.out.println("Please, select the number 1 or 2 or 3");
		}while(flag);
	}

	public void popup(String v_genre){
		try {
			s.executeQuery("select * from Drama LEFT JOIN Genre ON Drama.id = Genre.drama_id where genre = '"+v_genre+"'");
			ResultSet res = s.getResultSet();
			if(!res.next()){
				JOptionPane.showMessageDialog(null, "There is no suitable drama for you");
			}
			else{
				String recom_drama = res.getString("title");
				
				JOptionPane.showMessageDialog(null, "Recommend Drama : "+recom_drama+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println ("Viewer pop_up error message: " + e.getMessage ());
			System.err.println ("Viewer pop_up error number: " + e.getErrorCode ());
		}
	
	}
}

