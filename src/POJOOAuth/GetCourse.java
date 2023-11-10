package POJOOAuth;

public class GetCourse {

	private String url;
	private String services;
	private	String expertise;
	private Courses Courses;
	private String instructor;
	private String linkedIn;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public POJOOAuth.Courses getCourses() {
		return Courses;
	}
	public void setCourses(POJOOAuth.Courses courses) {
		Courses = courses;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getLinkedin() {
		return linkedIn;
	}
	public void setLinkedin(String linkedin) {
		this.linkedIn = linkedin;
	}
}
