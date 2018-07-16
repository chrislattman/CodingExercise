package app;

import java.util.ArrayList;

public class Blog {
	private String name;
	private User author;
	private ArrayList<User> members;
	
	public Blog(User author, String blogname) {
		this.author = author;
		name = blogname;
		members = new ArrayList<User>();
		members.add(author);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public void addUser(User user) {
		members.add(user);
	}
	public boolean containsUser(User user) {
		for (int i = 0; i < members.size(); i++) {
			if (members.contains(user)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		String result = "Members of the " + name + " blog:\n";
		for (int i = 0; i < members.size(); i++) {
			result += members.get(i) + "\n";
		}
		return result;
	}
}
