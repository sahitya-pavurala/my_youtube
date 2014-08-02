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


public class UpdateRating extends HttpServlet {

	private static final long serialVersionUID = 7008072784104674455L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Float newRating = Float.parseFloat(request.getParameter("newRating"));
		int videoId = Integer.parseInt(request.getParameter("videoId"));
		HttpSession session=request.getSession(true);
		UserBean user = (UserBean)session.getAttribute("user");
		Connection con=null;
		try {
			con=DBcon.connect();
			List<VideoBean> videos = (ArrayList<VideoBean>)session.getAttribute("videos");
			VideoBean match = null;
			for(VideoBean vid : videos){
				if(Integer.parseInt(vid.getId()) == videoId){
					match = vid;
					break;
				}
			}
			if(match.getCurrentUserRating() != null && !match.getCurrentUserRating().isEmpty()){
				PreparedStatement pst=con.prepareStatement("update rating set rating=? where videoid=? and user=?");
				pst.setFloat(1, newRating);
				pst.setInt(2, videoId);
				pst.setString(3, user.getUserName());
				pst.executeUpdate();
				match.setCurrentUserRating(Float.toString(newRating));
				request.setAttribute("message", "Rating updated successfully!!!!");
			}else{
				PreparedStatement pst=con.prepareStatement("insert into rating(rating,videoId,user) values(?,?,?)");
				pst.setFloat(1, newRating);
				pst.setInt(2, videoId);
				pst.setString(3, user.getUserName());
				pst.executeUpdate();
				match.setCurrentUserRating(Float.toString(newRating));
				request.setAttribute("message", "Your rating is saved successfully!!!!");
			}
			PreparedStatement pst1=con.prepareStatement("select sum(rating),count(rating) from rating where videoid=?");
			pst1.setInt(1, videoId);
			ResultSet rs = pst1.executeQuery();
			rs.next();
			float avgRating = rs.getFloat(1)/rs.getInt(2);
			match.setAvgRating(Float.toString(avgRating));
			PreparedStatement pst2=con.prepareStatement("update video set avgrating=? where id=?");
			pst2.setFloat(1, avgRating);
			pst2.setInt(2, videoId);
			pst2.executeUpdate();
			session.setAttribute("videos", videos);
		    RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.forward(request,response);
		}catch (ClassNotFoundException e) {
			request.setAttribute("message", "Server connection failed..please try again");
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
			}
			catch(NullPointerException e) {
				request.setAttribute("message", "Server not connected..please try again later");
			}
	    }
	}
}
