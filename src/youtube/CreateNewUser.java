package youtube;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateNewUser extends HttpServlet {

	private static final long serialVersionUID = -3943332921989081053L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String userName=request.getParameter("userName");
		String password=request.getParameter("pwd");
		try{
			con=DBcon.connect();
			PreparedStatement ps  = con.prepareStatement("insert into user values(?,?,?,?) ");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, userName);
			ps.setString(4, password);
			ps.executeUpdate();
			request.setAttribute("message", "User created successfully. Click on login!!!");
	    	RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.forward(request,response);
		}catch (ClassNotFoundException e) {
			request.setAttribute("message", "Server connection failed..please try again");
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request,response);
		}catch (SQLException e) {
			request.setAttribute("message", "User cannot be created");
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request,response);
		}finally{
			try {
				if(con != null){
					con.close();
				}
			} 
			catch (SQLException e) {
				request.setAttribute("message", "Server busy..please try again later");
			}
			catch(NullPointerException e) {
				request.setAttribute("message", "Server not connected..please try again later");
			}
	    }
	}
}
