package youtube;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class Upload extends HttpServlet {

	private static final long serialVersionUID = -358111537769656638L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bucketName     = "saiyoutube";
		String cdnLink = "https://d22vt3lk3ko1wk.cloudfront.net";
		/*int colonIndex = uploadFileName.indexOf(":");
		String pre = uploadFileName.substring(0, colonIndex+3);
		String post = uploadFileName.substring(colonIndex+3);
		post.replaceAll("\", "\");
		uploadFileName.replaceAll(regex, replacement)*/
		HttpSession session=request.getSession(true);
    	UserBean user = (UserBean)session.getAttribute("user");
		AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
        AmazonS3 s3client = new AmazonS3Client(credentialsProvider);
        try {
        	List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        	File file = null;
        	for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fileName = FilenameUtils.getName(item.getName());
                    String root = getServletContext().getRealPath("/");
					File path = new File(root + "/uploads");
					if (!path.exists()) {
						boolean status = path.mkdirs();
					}
					// 1. save to local file system
					file = new File(path + "/" + fileName);
					item.write(file);
                }
            }
        	if(file != null){
        		if(file.getName().toUpperCase().contains("GIF") || file.getName().toUpperCase().contains("JPG") || file.getName().toUpperCase().contains("JPEG") 
        				|| file.getName().toUpperCase().contains("PDF") || file.getName().toUpperCase().contains("DOC") || file.getName().toUpperCase().contains("TXT")){
                	request.setAttribute("message", "Incompatible file types");
        			RequestDispatcher rd=request.getRequestDispatcher("upload.jsp");
        			rd.forward(request,response);
                }else{
	                s3client.putObject(new PutObjectRequest(bucketName, file.getName(), file).withCannedAcl(CannedAccessControlList.PublicRead));
	                Connection con=null;
	                try {
	        			con=DBcon.connect();
	        			PreparedStatement pst=con.prepareStatement("insert into video(name,url,avgrating,user) values(?,?,?,?)");
	        			pst.setString(1,  file.getName());
	        			pst.setString(2,  cdnLink + "/" + file.getName());
	        			pst.setFloat(3,  0);
	        			pst.setString(4, user.getUserName());
	        			if(pst.executeUpdate() > 0 ){
	        				List<VideoBean> videos = (ArrayList<VideoBean>)session.getAttribute("videos");
	        				if(videos == null){
	        					videos = new ArrayList<VideoBean>();
	        				}
	        				PreparedStatement pst1 = con.prepareStatement("select * from video where url=?");
	        				pst1.setString(1, cdnLink + "/" + file.getName());
	        				ResultSet rs = pst1.executeQuery();
	        				rs.next();
	        				VideoBean vid = new VideoBean();
	        				vid.setId(rs.getString(1));
	        				vid.setName(rs.getString(2));
	        				vid.setUrl(rs.getString(3));
	        				vid.setAvgRating(rs.getString(4));
	        				vid.setAuthUser(rs.getString(5));
	        				vid.setAddedTime(rs.getString(6));
	        				videos.add(0, vid);
	        				session.setAttribute("videos", videos);
	        				request.setAttribute("message", "Video uploaded successfully!!!!");
	        			}else{
	        				request.setAttribute("message", "Video uploaded failed!!!! Try again!!!!");
	        			}
	        		    RequestDispatcher rd=request.getRequestDispatcher("upload.jsp");
	        			rd.forward(request,response);
	                }catch (ClassNotFoundException e) {
	        			request.setAttribute("message", "Server connection failed..please try again");
	        			RequestDispatcher rd=request.getRequestDispatcher("upload.jsp");
	        			rd.forward(request,response);
	        		}catch (SQLException e) {
	        			request.setAttribute("message", "Problem loading video list");
	        			RequestDispatcher rd=request.getRequestDispatcher("upload.jsp");
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
	        				e.printStackTrace();
	        				request.setAttribute("message", "Server not connected..please try again later");
	        			}
	        	    }
                }
        	}
         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            request.setAttribute("message", "Server not connected..please try again later");
			RequestDispatcher rd=request.getRequestDispatcher("upload.jsp");
			rd.forward(request,response);
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            request.setAttribute("message", "Server not connected..please try again later");
			RequestDispatcher rd=request.getRequestDispatcher("upload.jsp");
			rd.forward(request,response);
        } catch (FileUploadException e) {
        	e.printStackTrace();
			request.setAttribute("message", "Server not connected..please try again later");
			RequestDispatcher rd=request.getRequestDispatcher("upload.jsp");
			rd.forward(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
