import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Analyst {
	Statement s;
	Scanner sc = new Scanner(System.in);
	
	Analyst(Connection con){
		try{
			this.s = con.createStatement();	
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("Analyst error message: " + e.getMessage ());
			System.err.println ("Analyst error number: " + e.getErrorCode ());
			try {
				con.close();
			} catch (SQLException e1) {
				System.err.println ("AnalystClose error message: " + e1.getMessage ());
				System.err.println ("AnalystClose error number: " + e1.getErrorCode ());
			}
		}
	}
	
	public void periodViewer()
	{
		String input;
		System.out.println("Select period (1: a week, 2: a month, 3: three month, 4: a year)");
		try
		{
			input = sc.nextLine();
			if(input.equals("1"))
				input = "7";
			else if(input.equals("2"))
				input = "30";
			else if(input.equals("3"))
				input = "90";
			else if(input.equals("4"))
				input = "365";
			
			System.out.println("Select category 1: preferred genre, 2: preferred actor");
			String category;
			category = sc.nextLine();
			if(category.equals("1"))
			{
				//category = "preference_genre";
				s.executeQuery(" select preference_genre, COUNT(id) from Viewer "
						+ "where to_days(now())-to_days(join_date) <=" + input 
						+ " group by preference_genre order by COUNT(id) desc");
				
				int count = 0; 
				String genre;
				ResultSet rs;
				int memberCount;
				rs = s.getResultSet();
				for(count = 0; rs.next(); count++)
				{
			
					genre = rs.getString("preference_genre");
					memberCount = rs.getInt("count(id)");
					if(count == 0)
					{
						System.out.printf("%20s|%10s\n", "preference genre", "number");
					}
					
					System.out.printf("%20s|%10s\n", genre, memberCount);
				}
				
				
			}
			else if(category.equals("2"))
			{
				//category = "preference_actor";
				s.executeQuery(" select preference_actor, COUNT(id) from Viewer "
						+ "where to_days(now())-to_days(join_date) <=" + input 
						+ " group by preference_actor order by COUNT(id) desc");
				
				int count = 0; 
				String actor;
				ResultSet rs;
				int memberCount;
				rs = s.getResultSet();
				for(count = 0; rs.next(); count++)
				{
			
					actor = rs.getString("preference_actor");
					memberCount = rs.getInt("count(id)");
					if(count == 0)
					{
						System.out.printf("%20s|%10s\n", "preference actor", "number");
					}
					
					System.out.printf("%20s|%10s\n", actor, memberCount);
				}
			}else
			{
				System.out.println("Please select 1 to 3");
			}
		
		}catch(SQLException e1)
		{
			System.err.println (e1.getMessage ());
			System.err.println (e1.getErrorCode ());
		}
	}
	
	public void ageViewer()
	{
		String input = null;

		do{

			try
			{
				System.out.println("Select menu");
				System.out.println("1. Total number of member");
				System.out.println("2. Number of members by age group");
				System.out.println("3. preference genre of members by age group");
				System.out.println("4. Preference actor of members by age group");
				System.out.println("5. quit");
				ResultSet rs;
				int memberCount = 0;
				input = sc.nextLine();
				if(input.equals("1"))
				{
					s.executeQuery("select count(id) from Viewer");
					rs = s.getResultSet();
					if(!rs.next())
					{
						System.out.println("There is no member in this system");
					}else
					{
						memberCount = rs.getInt("count(id)");
						System.out.println("The number of memeber: " + memberCount);
					}
					
				}
				else if(input.equals("2"))
				{
					System.out.println("Select age goup (1: 10s, 2: 20s, 3: 30s, 4: 40s, 5: 50s)");
					input = sc.nextLine();
					int numInt = Integer.parseInt(input);
					s.executeQuery("select count(id) from Viewer where to_days(now())-to_days(birth) >=" + 3650*numInt +
							" and to_days(now())-to_days(birth) <= " + 3650*(numInt+1) + " order by count(id) desc");
					rs = s.getResultSet();
					if(!rs.next())
					{
						System.out.println("There is no member who are in " + numInt*10 +"s");
					}else
					{
						memberCount = rs.getInt("count(id)");
						System.out.println("The number of " + numInt*10 + "s memebers: " + memberCount);
					}
				}
				else if(input.equals("3"))			//preference genre
				{
					System.out.println("Select age goup (1: 10s, 2: 20s, 3: 30s, 4: 40s, 5: 50s)");
					input = sc.nextLine();
					int numInt = Integer.parseInt(input);
					s.executeQuery("select count(id), preference_genre from Viewer where to_days(now())-to_days(birth) >= " + 3650*numInt +
							" and to_days(now())-to_days(birth) <= " + 3650*(numInt+1) + " group by preference_genre order by count(id) desc");
					
					int count = 0; 
					String genre;
					rs = s.getResultSet();
					for(count = 0; rs.next(); count++)
					{
						genre = rs.getString("preference_genre");
						memberCount = rs.getInt("count(id)");
						if(count == 0)
						{
							System.out.println("Famous genres for " + numInt*10 +"s are");
						}
						
						System.out.println(genre + " - " + memberCount + "number");
					}
				}
				else if(input.equals("4"))
				{
					System.out.println("Select age goup (1: 10s, 2: 20s, 3: 30s, 4: 40s, 5: 50s)");
					input = sc.nextLine();
					int numInt = Integer.parseInt(input);
					s.executeQuery("select count(id), preference_actor from Viewer where to_days(now())-to_days(birth) >= " + 3650*numInt +
							" and to_days(now())-to_days(birth) <= " + 3650*(numInt+1) + " group by preference_actor order by count(id) desc");
					
					int count = 0; 
					String actor;
					rs = s.getResultSet();
					for(count = 0; rs.next(); count++)
					{
				
						actor = rs.getString("preference_actor");
						memberCount = rs.getInt("count(id)");
						if(count == 0)
						{
							System.out.println("Famous actors for " + numInt*10 +"s are");
						}
						
						System.out.println(actor + " - " + memberCount + "number");
					}
					}else
					{
						System.out.println("Please insert number 1 to 5");
					}
				//input = sc.nextLine();
			}catch(SQLException e1){}

		}while(!input.equals("5"));
	}
	
	public void viewerAnalyze()
	{
		String input;
		do
		{
			System.out.println("1. Period analyse");
			System.out.println("2. Age analyse");
			System.out.println("3. Quit");
			input = sc.nextLine();
			
			if(input.equals("1"))
			{
				periodViewer();
				
			}else if(input.equals("2"))
			{
				ageViewer();		
			}else if(input.equals("3"))
			{
				break;
			}else
			{
				System.out.println("Please select 1 to 3");
			}
		}while(input.equals("3"));
	}
	
	public void scoreTrend()
	{
		String input;

		do{
			System.out.println("Select menu");
			System.out.println("1. List of dramas with high scores");
			System.out.println("2. List of dramas by scores");
			System.out.println("3. Genre by scores");
			System.out.println("4. Quit");
			input = sc.nextLine();
			try
			{
				ResultSet rs;
				int memberCount = 0;
				if(input.equals("1"))
				{
					//평점이 가장 높은 드라마 20개의 아이디, 타이틀, 장르, 평점
					s.executeQuery("select distinct id, title, genre, average_score from Drama, Genre order by average_score desc, id asc");
					rs = s.getResultSet();
					int count = 0;
					int id;
					int prev = 0;
					String title;
					String genre;
					float avgScore;
					for(count = 0; rs.next(); count++)
					{
						id = rs.getInt("id");
						title = rs.getString("title");
						avgScore = rs.getFloat("average_score");
						genre = rs.getString("genre");
						if(count == 0)
						{
							System.out.printf("%5s|%15s|%10s|%10s\n", "ID", "Title", "Avg Score", "Genre");
							System.out.println("==========================================================");
						}
						if(prev!= id)
						{
							System.out.printf("%5d|%15s|%10f|%10s\n", id, title, avgScore, genre);
						}else
						{
							System.out.printf("%5s|%15s|%10s|%10s\n", " ", " ", " ", genre);
						}
						prev = id;
					}
					if(count ==0)
					{
						System.out.println("There is no member in this system");	
						
					}
					
				}
				else if(input.equals("2"))
				{
					System.out.println("Select Average Score (1~10)");
					input = sc.nextLine();
					int numInt = Integer.parseInt(input);
					s.executeQuery("select id, title, genre, average_score from Drama, Genre where average_score >= " + numInt
							+ " and average_score <= " + (numInt+1) + " order by average_score desc, id asc");
					rs = s.getResultSet();
					int count = 0;
					int id;
					int prev = 0;
					String title;
					String genre;
					float avgScore;
					for(count = 0; rs.next(); count++)
					{
						id = rs.getInt("id");
						title = rs.getString("title");
						avgScore = rs.getFloat("average_score");
						genre = rs.getString("genre");
						if(count == 0)
						{
							System.out.printf("%5s|%15s|%10s|%10s\n", "ID", "Title", "Avg Score", "Genre");
						}
						if(prev!= id)
						{
							System.out.println("========================================================");
							System.out.printf("%5d|%15s|%10f|%10s\n", id, title, avgScore, genre);
						}else
						{
							System.out.printf("%5s|%15s|%10s|%10s\n", "", "", "", genre);
						}
						prev = id;
					}
					if(count ==0)
					{
						System.out.println("There is no member in this system");	
						
					}
					
				}
				else if(input.equals("3"))			//preference genre
				{
					System.out.println("List genres with high average scores");
					
					s.executeQuery("select genre, avg(avgS) from (select id, genre, average_score as avgS from Genre, Drama where drama_id = id) "
							+ "as derivedTable group by genre "
							+ "order by avg(avgS) desc, id asc");
					
					int count = 0; 
					String genre;
					float avg = 0;
					rs = s.getResultSet();
					for(count = 0; rs.next(); count++)
					{
						genre = rs.getString("genre");
						avg = rs.getFloat("avg(avgS)");
						if(count == 0)
						{
							System.out.printf("%15s|%15s\n", "genre", "average score");
							System.out.println("=====================================");
						}
						
						System.out.printf("%15s|%15f\n", genre, avg);
						}
				}
				else if(input.equals("4"))
				{
					;
				}else
				{
					System.out.println("Please insert number 1 to 5");
					
				}
				
			}catch(SQLException e1){}

		}while(!input.equals("4"));
	}
	
	public void hitTrend()
	{
		String input = null;
			do{

				System.out.println("Select menu");
				System.out.println("1. List of dramas with high hits");
				System.out.println("2. List of dramas over hit number");
				System.out.println("3. Genre with high hit number");
				System.out.println("4. Quit");
				input = sc.nextLine();
				try
				{
					ResultSet rs;
					int memberCount = 0;
					if(input.equals("1"))
					{
						//평점이 가장 높은 드라마 20개의 아이디, 타이틀, 장르, 평점
						s.executeQuery("select distinct id, title, genre, hit from Drama, Genre order by hit desc, id asc");
						rs = s.getResultSet();
						int count = 0;
						int id;
						int prev = 0;
						String title;
						String genre;
						int hit;
						for(count = 0; rs.next(); count++)
						{
							id = rs.getInt("id");
							title = rs.getString("title");
							hit = rs.getInt("hit");
							genre = rs.getString("genre");
							if(count == 0)
							{
								System.out.printf("%5s|%15s|%10s|%10s\n", "ID", "Title", "hit", "Genre");
								System.out.println("==========================================================");
							}
							if(prev!= id)
							{
								System.out.printf("%5d|%15s|%10d|%10s\n", id, title, hit, genre);
							}else
							{
								System.out.printf("%5s|%15s|%10s|%10s\n", " ", " ", " ", genre);
							}
							prev = id;
						}
						if(count ==0)
						{
							System.out.println("There is no member in this system");	
							
						}
						
					}
					else if(input.equals("2"))
					{
						System.out.println("Select hit number (0~10000)");
						input = sc.nextLine();
						int numInt = Integer.parseInt(input);
						s.executeQuery("select id, title, genre, hit from Drama, Genre where hit >= " + numInt
								+ " and hit >= " + (numInt) + " order by hit desc, id asc");
						rs = s.getResultSet();
						int count = 0;
						int id;
						int prev = 0;
						String title;
						String genre;
						int hit;
						for(count = 0; rs.next(); count++)
						{
							id = rs.getInt("id");
							title = rs.getString("title");
							hit = rs.getInt("hit");
							genre = rs.getString("genre");
							if(count == 0)
							{
								System.out.printf("%5s|%15s|%10s|%10s\n", "ID", "Title", "hit", "Genre");
							}
							if(prev!= id)
							{
								System.out.println("========================================================");
								System.out.printf("%5d|%15s|%10d|%10s\n", id, title, hit, genre);
							}else
							{
								System.out.printf("%5s|%15s|%10s|%10s\n", "", "", "", genre);
							}
							prev = id;
						}
						if(count ==0)
						{
							System.out.println("There is no member in this system");	
							
						}
						
					}
					else if(input.equals("3"))			//preference genre
					{
						System.out.println("List genres with high hit scores");
						
						s.executeQuery("select genre, sum(hits) from (select id, genre, hit as hits from Genre, Drama where drama_id = id) "
								+ "as derivedTable group by genre "
								+ "order by sum(hits) desc");
						
						int count = 0; 
						String genre;
						int hit = 0;
						rs = s.getResultSet();
						for(count = 0; rs.next(); count++)
						{
					
							genre = rs.getString("genre");
							hit = rs.getInt("sum(hits)");
							if(count == 0)
							{
								System.out.printf("%15s|%15s\n", "genre", "hit number");
							}
							
							System.out.printf("%15s|%15d\n", genre, hit);
							}
					}
					else if(input.equals("4"))
					{
						;
					}else
					{
						System.out.println("Please insert number 1 to 5");
					
					}
					
				}catch(SQLException e1){}

			}while(!input.equals("4"));
		
	}
	
	
	public void trendAnalyze()
	{
		String input;
		do
		{
			System.out.println("1. Scores analyze");
			System.out.println("2. Hit analyze");
			System.out.println("3. Quit");
			input = sc.nextLine();
			
			if(input.equals("1"))
			{
				scoreTrend();
				
			}else if(input.equals("2"))
			{
				hitTrend();		
			}else if(input.equals("3"))
			{
				break;
			}else
			{
				System.out.println("Please select 1 to 3");
			}
		}while(input.equals("3"));
	}
	
	public void display(){
		
		boolean flag = true;
		String menu;
		
		do{
			System.out.println("Select the menu");
			System.out.println("1. Viewer Analyse");
			System.out.println("2. Drama Trend Analyse");
			System.out.println("3. quit");
			menu = sc.nextLine();
			String input;
			if(menu.equals("1")){
				
				viewerAnalyze();
			}
			else if(menu.equals("2")){
				//drama trend analyze
				trendAnalyze();
			}
			else if(menu.equals("3")){
				flag = false;
				System.out.println("Bye!!");
			}
			else
				System.out.println("Please, select the number 1 to 3");
		}while(flag);
	}
}
