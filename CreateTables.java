import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {

	Statement s;
	
	CreateTables(Connection con){
		try{
			this.s = con.createStatement();

			s.execute("create table if not exists Drama("
					+ "title varchar(30) not null,"
					+ "writer varchar(30),"
					+ "first_aired DATE,"
					+ "last_aired DATE,"
					+ "hit int default '0',"
					+ "broad_station varchar(30),"
					+ "running_time int(5),"
					+ "average_score float(5) default '0',"
					+ "replay_site_link varchar(30),"
					+ "confirm ENUM('true', 'false', 'unknown'),"
					+ "age_limit ENUM('0', '7', '12', '15', '19'),"
					+ "id INT PRIMARY KEY AUTO_INCREMENT)");
			
			s.execute("create table if not exists Genre("
					+ "drama_id int NOT NULL,"
					+ "genre ENUM('horror', 'comedy', 'scifi', 'history', 'romance', 'action'),"
					+ "FOREIGN KEY(drama_id) REFERENCES Drama(id)"
					+ "ON DELETE CASCADE ON UPDATE CASCADE)");

			s.execute("create table if not exists Actor("
					+ "drama_id int NOT NULL,"
					+ "name varchar(20) not null,"
					+ "role varchar(30),"
					+ "FOREIGN KEY(drama_id) REFERENCES Drama(id)"
					+ "ON DELETE CASCADE ON UPDATE CASCADE)");
			
			s.execute("create table if not exists Subtitle("
					+ "drama_id int NOT NULL,"
					+ "subtitle ENUM('Tai', 'Korea', 'Chinese', 'Japanese', 'Philipinese', 'Taiwan', 'English', 'Spanish', 'French'),"
					+ "FOREIGN KEY(drama_id) REFERENCES Drama(id)"
					+ "ON DELETE CASCADE ON UPDATE CASCADE)");
			
			s.execute("create table if not exists Viewer("
					+ "id varchar(30) not null,"
					+ "password varchar(30) not null,"
					+ "preference_genre varchar(30),"
					+ "gender ENUM('M', 'F'),"
					+ "birth date,"
					+ "address varchar(30),"
					+ "recommendation_accept bool DEFAULT '1',"
					+ "preference_actor varchar(30),"
					+ "nationality ENUM('Tai', 'Korea', 'China', 'Japan', 'Philipine', 'Taiwan', 'America', 'EU', 'SouthAmerica', 'Africa'),"
					+ "primary key(id, password))");
			
			s.execute("create table if not exists Officer("
					+ "id varchar(30) not null,"
					+ "password varchar(30) not null,"
					+ "company_name varchar(30) not null,"
					+ "employee_number varchar(30) not null,"
					+ "confirm ENUM ('true', 'false', 'unknown'),"
					+ "primary key(id, password))");
			
			s.execute("create table if not exists Analyst("
					+ "id varchar(30) not null,"
					+ "password varchar(30) not null,"
					+ "primary key(id, password))");
			
			s.execute("create table if not exists System_manager("
					+ "id varchar(30) NOT NULL,"
					+ "password varchar(30) NOT NULL,"
					+ "primary key(id, password))");
			
			s.execute("CREATE TABLE IF NOT EXISTS Feedback("
					+ "viewer_id varchar(30) NOT NULL,"
					+ "drama_id int NOT NULL,"
					+ "score ENUM('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10'),"
					+ "review VARCHAR(100),"
					+ "review_date DATE,"
					+ "FOREIGN KEY(viewer_id) REFERENCES Viewer(id)"
					+ "ON DELETE CASCADE ON UPDATE CASCADE,"
					+ "FOREIGN KEY(drama_id) REFERENCES Drama(id)"
					+ "ON DELETE CASCADE ON UPDATE CASCADE)");
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println ("CreateTables error message: " + e.getMessage ());
			System.err.println ("CreateTables error number: " + e.getErrorCode ());
			try {
				con.close();
			} catch (SQLException e1) {
				System.err.println ("CreateTablesClose error message: " + e1.getMessage ());
				System.err.println ("CreateTablesClose error number: " + e1.getErrorCode ());
			}
		}
	}
	
}
