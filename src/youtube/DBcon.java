package youtube;


import java.sql.*;
public class DBcon {

	public static Connection connect() throws ClassNotFoundException, SQLException{
	
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://myyoutubedb.ck0g1lzestbc.us-west-2.rds.amazonaws.com:3306/myyoutube";
		String username= "myyoutubeuser"; 
		String password= "myyoutubepwd";
		Connection conn = null;
		conn = DriverManager.getConnection(url,username,password);
		return conn;
	}
}