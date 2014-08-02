package youtube;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

public class DeleteVideo extends HttpServlet {
	
	private static final long serialVersionUID = 8087433210989611193L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int videoId = Integer.parseInt(request.getParameter("vid"));
		String bucketName     = "saiyoutube";
		Connection con=null;
		try{
			con=DBcon.connect();
			PreparedStatement ps  = con.prepareStatement("delete from rating where videoid=?");
			ps.setInt(1, videoId);
			ps.executeUpdate();
			ps  = con.prepareStatement("delete from video where id=?");
			ps.setInt(1, videoId);
			if (ps.executeUpdate() == 0){
				request.setAttribute("message", "Problem occured while deleteing the video!!!!!");
		    	RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request,response);
			}else{
				HttpSession session=request.getSession(true);
				List<VideoBean> videos = (ArrayList<VideoBean>)session.getAttribute("videos");
				VideoBean match = null;
				for(VideoBean vid : videos){
					if(Integer.parseInt(vid.getId()) == videoId){
						match = vid;
						break;
					}
				}
				AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
				AmazonS3 s3client = new AmazonS3Client(credentialsProvider);
				s3client.deleteObject(new DeleteObjectRequest(bucketName, match.getName()));
				request.setAttribute("message", "Video deleted Successfully!!!!!");
				videos.remove(match);
				session.setAttribute("videos", videos);
		    	RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request,response);
			}
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
