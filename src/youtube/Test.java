package youtube;

import java.sql.Connection;

import youtube.DBcon;

public class Test {

	public static void main(String[] args) {
		Connection con=null;
		try {
			con=DBcon.connect();
			System.out.println("sadasd");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
