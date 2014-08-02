package youtube;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AllVideos extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(true);
    	UserBean user = (UserBean)session.getAttribute("user");
		
		Connection con=null;
		try {
			con=DBcon.connect();
			PreparedStatement pst=con.prepareStatement("select * from video order by avgrating desc");
			ResultSet rs=pst.executeQuery();
			List<VideoBean> videos = new ArrayList<VideoBean>();
			while(rs.next()){
				VideoBean vid = new VideoBean();
				vid.setId(rs.getString(1));
				vid.setName(rs.getString(2));
				vid.setUrl(rs.getString(3));
				vid.setAvgRating(rs.getString(4));
				vid.setAuthUser(rs.getString(5));
				vid.setAddedTime(rs.getString(6));
				if(user != null){
					PreparedStatement pst1 = con.prepareStatement("select rating from rating where user=? and videoId=?");
					pst1.setString(1, user.getUserName());
					pst1.setInt(2, Integer.parseInt(rs.getString(1)));
					ResultSet rs1=pst1.executeQuery();
					if(rs1.next()){
						vid.setCurrentUserRating(rs1.getString(1));
					}
				}
				videos.add(vid);
			}
			session.setAttribute("videos", videos);
		    RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.forward(request,response);
		}catch (ClassNotFoundException e) {
			request.setAttribute("message", e.getMessage());
			RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.forward(request,response);
		}catch (SQLException e) {
			request.setAttribute("message", "Problem loading video list");
			RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.forward(request,response);
		}finally{
			try {
				if(con != null){
					con.close();
				}
			} 
			catch (SQLException e) {
				request.setAttribute("message", "Server busy..please try again later");
				/*RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request,response);*/
			}
			catch(NullPointerException e) {
				e.printStackTrace();
				request.setAttribute("message", "Server not connected..please try again later");
				/*RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request,response);*/
			}
	    }
		/*RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
		rd.forward(request,response);*/
	}
}
