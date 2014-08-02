package youtube;

public class VideoBean {
	private String id;
	private String name;
	private String url;
	private String avgRating;
	private String addedTime;
	private String currentUserRating;
	private String authUser;
	
	public String getAuthUser() {
		return authUser;
	}
	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(String avgRating) {
		this.avgRating = avgRating;
	}
	public String getAddedTime() {
		return addedTime;
	}
	public void setAddedTime(String addedTime) {
		this.addedTime = addedTime;
	}
	public String getCurrentUserRating() {
		return currentUserRating;
	}
	public void setCurrentUserRating(String currentUserRating) {
		this.currentUserRating = currentUserRating;
	}
}
