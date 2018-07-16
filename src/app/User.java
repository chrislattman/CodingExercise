package app;

public class User {
	private String username;
	private String firstName;
	private String lastName;
	
	public User(String username, String firstName, String lastName) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "username: " + username + ", first name: " + firstName + ", last name: " + lastName;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass().equals(this.getClass())) {
			User user = (User) obj;
			if (user.getUsername().equals(username) 
				&& user.getFirstName().equals(firstName) 
				&& user.getLastName().equals(lastName)) {
				return true;
			}
		}
		return false;
	}
}
