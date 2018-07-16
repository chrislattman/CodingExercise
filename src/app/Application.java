package app;

import java.util.*;

/**
 * Create a simple CLI blogging platform.  Create model for Blog (has an author of type User and other fields of your choice).
 * The CLI has several options, add User, add Blog, view Users, view Blogs.
 * Prompt the user to enter ALL to see all or a username to see objects for that user.
 * prompt which action to take and take the action.  Store your items in memory.  Allow canceling any of the actions by entering CANCEL.
 * 
 * BONUS: Add another command SEARCH <User, Blog> that prompts for a search string and returns a list of matching Users or Blogs
 * SUPER DUPER BONUS: have the search Blog command use "OR" or "AND" and delineate terms by whitespace 
 * such as: "SEARCH Blog AND" would search for Blogs containing all of the terms, 
 * "SEARCH Blog OR" would search for Blogs containing any of the terms
 * 
 * I've started you off with a User class and a prompt to construct a user.  You are welcome to change any of my code.
 * You can write any tests you like, or not.  Please stick with with JDK, no outside libraries.
 * 
 * Enjoy!
 * 
 * @author tpianta
 *
 */
public class Application {	
	private static int currentUserIndex = -1;
	private static int currentBlogIndex = -1;
	private static ArrayList<User> users;
	private static ArrayList<Blog> blogs;	
	private static Stack<String> commands;

	public static void main(String[] args) {
		Scanner scanner = new Scanner( System.in );
		users = new ArrayList<User>();
		blogs = new ArrayList<Blog>();
		commands = new Stack<String>();
		

		System.out.println( "Welcome to Useless Blog!");
		helpMenu();
		System.out.println( "For your first action, please add a user with ADD User, all commands are case sensitive");
		//prompt for action
		System.out.println( "What would you like to do? Enter HELP for more info or press Enter to quit." );
		// Read a line of text from the user.
		String input = scanner.nextLine();
        while (input.length() > 0) {
        	if (input.equals("HELP")) {
            	helpMenu();
            	String comm = "HELP";
            	commands.push(comm);
            }
            else if (input.contains("ADD")) {
            	if (input.contains("<") || input.contains(">")) {
            		addMenu();
            	}
            	else {
            		String[] newInput = input.split(" ");
            		if (newInput.length == 3) {
            			boolean match = searchUser(newInput[1]);
            			if (!match) {
            				String newCommand = "ADD";
            				int j = searchBlog(newInput[2]);
            				if (j < 0) {
            					blogs.add(new Blog(new User(newInput[1], "", ""), newInput[2]));
            					newCommand = incrementBlog(newCommand, newInput[2]);
            				}
            				else {
            					blogs.get(j).addUser(new User(newInput[1], "", ""));
            				}
            				users.add(new User(newInput[1], "", ""));
            				System.out.println("username " + newInput[1] + " added to blog " + newInput[2]);
            				incrementUser(newCommand);
            			}
            		}
            		else if (newInput.length == 4) {
            			boolean match = searchUser(newInput[1]);
            			if (!match) {
            				String newCommand = "ADD";
            				int j = searchBlog(newInput[3]);
            				if (j < 0) {
            					blogs.add(new Blog(new User(newInput[1], newInput[2], ""), newInput[3]));
            					newCommand = incrementBlog(newCommand, newInput[3]);
            				}
            				else {
            					blogs.get(j).addUser(new User(newInput[1], newInput[2], ""));
            				}
            				users.add(new User(newInput[1], newInput[2], ""));
            				System.out.println("username " + newInput[1] + " added to blog " + newInput[3]);
            				incrementUser(newCommand);
            			}
            		}
            		else if (newInput.length == 5) {
            			boolean match = searchUser(newInput[1]);
            			if (!match) {
            				String newCommand = "ADD";
            				int j = searchBlog(newInput[4]);
            				if (j < 0) {
            					blogs.add(new Blog(new User(newInput[1], newInput[2], newInput[3]), newInput[4]));
            					newCommand = incrementBlog(newCommand, newInput[4]);
            				}
            				else {
            					blogs.get(j).addUser(new User(newInput[1], newInput[2], newInput[3]));
            				}
            				users.add(new User(newInput[1], newInput[2], newInput[3]));
            				System.out.println("username " + newInput[1] + " added to blog " + newInput[4]);
            				incrementUser(newCommand);
            			}
            		}
            		else {
            			addMenu();
            		}
            	}
            }
            else if (input.contains("VIEW")) {
            	if (input.contains("<") || input.contains(">")) {
            		viewMenu();
            	}
            	else {
            		String[] newInput = input.split(" ");
            		if (newInput.length == 3) {
            			boolean match = false;
            			if (newInput[1].equals("User")) {
            				for (int i = 0; i < users.size(); i++) {
            					if (users.get(i).getUsername().equals(newInput[2])) {
            						System.out.println(users.get(i));
            						match = true;
            						break;
            					}
            				}
            				String comm = "VIEW User";
            				commands.push(comm);
            				printBoolean(match);
            			}
            			else if (newInput[1].equals("Blog")) {
            				for (int i = 0; i < blogs.size(); i++) {
            					if (blogs.get(i).getName().equals(newInput[2])) {
            						System.out.println(blogs.get(i));
            						match = true;
            						break;
            					}
            				}
            				String comm = "VIEW Blog";
            				commands.push(comm);
            				printBoolean(match);
            			}
            			else {
            				viewMenu();
            			}
            		}
            		else {
            			viewMenu();
            		}
            	}
            }
            else if (input.equals("ALL")) {
            	System.out.println("Blogs:");
            	for (int i = 0; i < blogs.size(); i++) {
            		System.out.println(blogs.get(i));
            	}
            	System.out.println("All users:");
            	for (int j = 0; j < users.size(); j++) {
            		System.out.println(users.get(j));
            	}
            	String comm = "ALL";
            	commands.push(comm);
            }
            else if (input.equals("CANCEL")) {
            	if (commands.empty()) {
            		System.out.println("No commands entered.");
            	}
            	else if (!commands.peek().contains("blog") && 
            		!commands.peek().contains("user")) {
            		System.out.println("Sorry, last successful command was " + commands.peek());
            	}
            	else {
            		if (!commands.empty() && commands.pop().equals("ADD blog user")) {
            			users.remove(currentUserIndex);
            			currentUserIndex--;
            			blogs.remove(currentBlogIndex);
            			currentBlogIndex--;
            			System.out.println("Removed most recent blog and user created");
            		}
            		else if (!commands.empty() && commands.pop().equals("ADD user")) {
            			users.remove(currentUserIndex);
            			currentUserIndex--;
            			System.out.println("Removed most recent user created");
            		}
            		else {
            			System.out.println("No more ADD commands to cancel.");
            		}
            	}
            	String comm = "CANCEL";
            	commands.push(comm);
            }
            else if (input.contains("SEARCH")) {
            	if (input.contains("<") || input.contains(">")) {
            		searchMenu();
            	}
            	else {
            		String[] newInput = input.split(" ");
            		boolean match = false;
            		boolean anymatch = false;
            		if (newInput.length == 3 && newInput[1].equals("User")) {
            			for (int i = 0; i < users.size(); i++) {
            				if (users.get(i).getUsername().contains(newInput[2]) || 
            					users.get(i).getFirstName().contains(newInput[2]) || 
            					users.get(i).getLastName().contains(newInput[2])) {
            					System.out.println(users.get(i));
            					match = true;
            				}
            			}
            			printBoolean(match);
            		}
            		else if (newInput.length == 3 && newInput[1].equals("Blog")) {
            			for (int i = 0; i < blogs.size(); i++) {
            				if (blogs.get(i).getName().contains(newInput[2])) {
            					System.out.println(blogs.get(i));
            					match = true;
            				}
            				else {
            					for (int j = 0; j < users.size(); j++) {
            						if (users.get(j).getUsername().contains(newInput[2]) && blogs.get(i).containsUser(users.get(j))) {
            							System.out.println(blogs.get(i));
            							match = true;
            							break;
            						}
            						else if (users.get(j).getFirstName().contains(newInput[2]) && blogs.get(i).containsUser(users.get(j))) {
            							System.out.println(blogs.get(i));
            							match = true;
            							break;
            						}
            						else if (users.get(j).getLastName().contains(newInput[2]) && blogs.get(i).containsUser(users.get(j))) {
            							System.out.println(blogs.get(i));
            							match = true;
            							break;
            						}
            					}
            				}
            			}
            			printBoolean(match);
            		}
            		else if (newInput.length >= 5 && newInput[1].equals("User") && newInput[2].equals("AND")) {
            			for (int k = 0; k < users.size(); k++) {
            				for (int m = newInput.length - 3; m < newInput.length; m++) {
            					if (!users.get(k).getUsername().contains(newInput[m]) && 
            						!users.get(k).getFirstName().contains(newInput[m]) && 
            						!users.get(k).getLastName().contains(newInput[m])) {
            						match = false;
            						break;
            					}
            					else {
            						match = true;
            					}
            				}
            				if (match) {
            					System.out.println(users.get(k));
            					anymatch = true;
            				}
            			}
            			printBoolean(anymatch);
            		}
            		else if (newInput.length >= 5 && newInput[1].equals("User") && newInput[2].equals("OR")) {
            			for (int k = 0; k < users.size(); k++) {
            				for (int m = newInput.length - 3; m < newInput.length; m++) {
            					if (users.get(k).getUsername().contains(newInput[m]) || 
            						users.get(k).getFirstName().contains(newInput[m]) || 
            						users.get(k).getLastName().contains(newInput[m])) {
            						match = true;
            						break;
            					}
            				}
            				if (match) {
            					System.out.println(users.get(k));
            					anymatch = true;
            				}
            			}
            			printBoolean(anymatch);
            		}
            		else if (newInput.length >= 5 && newInput[1].equals("Blog") && newInput[2].equals("AND")) {
            			for (int k = 0; k < blogs.size(); k++) {
            				outerloop:
            				for (int m = newInput.length - 3; m < newInput.length; m++) {
            					if (!blogs.get(k).getName().contains(newInput[m])) {
            						for (int n = 0; n < users.size(); n++) {
            							if (!blogs.get(k).containsUser(users.get(n))) {
            								continue;
            							}
            							else if (blogs.get(k).containsUser(users.get(n)) && 
            								!users.get(n).getUsername().contains(newInput[m]) && 
            								!users.get(n).getFirstName().contains(newInput[m]) && 
            								!users.get(n).getLastName().contains(newInput[m])) {
            								match = false;
            								break outerloop;
            							}
            							else if (blogs.get(k).containsUser(users.get(n))) {
            								match = true;
            							}
            						}
            					}
            					else {
            						match = true;
            					}
            				}
            				if (match) {
            					System.out.println(users.get(k));
            					anymatch = true;
            				}
            			}
            			printBoolean(anymatch);
            		}
            		else if (newInput.length >= 5 && newInput[1].equals("Blog") && newInput[2].equals("OR")) {
            			for (int k = 0; k < blogs.size(); k++) {
            				outerloop:
            				for (int m = newInput.length - 3; m < newInput.length; m++) {
            					if (!blogs.get(k).getName().contains(newInput[m])) {
            						for (int n = 0; n < users.size(); n++) {
            							if (!blogs.get(k).containsUser(users.get(n))) {
            								continue;
            							}
            							else if (blogs.get(k).containsUser(users.get(n)) && 
            								users.get(n).getUsername().contains(newInput[m]) || 
            								users.get(n).getFirstName().contains(newInput[m]) || 
            								users.get(n).getLastName().contains(newInput[m])) {
            								match = true;
            								break outerloop;
            							}
            						}
            					}
            					else {
            						match = true;
            					}
            				}
            				if (match) {
            					System.out.println(users.get(k));
            					anymatch = true;
            				}
            			}
            			printBoolean(anymatch);
            		}
            		else {
            			searchMenu();
            		}
            	}
            }
            else {
            	System.out.println("Unrecognized input. All commands are case sensitive.");
            }
            System.out.println( "What would you like to do? Enter HELP for more info or press Enter to quit." );
            input = scanner.nextLine();
        }
        System.out.println("Thanks for using the blog!");
        scanner.close();
    }
	
	public static void helpMenu() {
		System.out.println( "To add something, type ADD <username> <firstname> <lastname> <blogname> (without brackets)");
		System.out.println( "To view something, type VIEW User <username> or VIEW Blog <blogname> (without brackets)");
		System.out.println( "To search for a user or blog, type SEARCH User <user> or SEARCH Blog <blog> (without brackets)");
		System.out.println( "Type ALL to see all blogs and all users");
		System.out.println( "Type CANCEL to undo the last command (only applies to ADD)");
	}
	
	public static void addMenu() {
		System.out.println("Usage: ADD <username> <blogname> (without brackets)");
		System.out.println("     : ADD <username> <firstname> <blogname> (without brackets)");
		System.out.println("     : ADD <username> <firstname> <lastname> <blogname> (without brackets)");
	}
	
	public static void viewMenu() {
		System.out.println("Usage: VIEW User <username> (without brackets)");
		System.out.println("     : VIEW Blog <blogname> (without brackets)");
	}
	
	public static void searchMenu() {
		System.out.println("Usage: SEARCH User <term> (without brackets)");
		System.out.println("     : SEARCH User AND <term>... (without brackets) for exclusive search");
		System.out.println("     : SEARCH User OR <term>... (without brackets) for inclusive search");
		System.out.println("     : SEARCH Blog <term> (without brackets)");
		System.out.println("     : SEARCH Blog AND <term>... (without brackets) for exclusive search");
		System.out.println("     : SEARCH Blog OR <term>...  (without brackets) for inclusive search");
	}
	
	public static boolean searchUser(String nameInput) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(nameInput)) {
				System.out.println("Username already exists.");
				return true;
			}
		}
		return false;
	}
	
	public static int searchBlog(String blogNameInput) {
		for (int j = 0; j < blogs.size(); j++) {
			if (blogs.get(j).getName().equals(blogNameInput)) {
				return j;
			}
		}
		return -1;
	}
	
	public static String incrementBlog(String newCommand, String input2) {
		currentBlogIndex++;
		System.out.println(input2 + " blog created");
		return newCommand += " blog";
	}
	
	public static void incrementUser(String newCommand) {
		currentUserIndex++;
		commands.push(newCommand + " user");
	}
	
	public static void printBoolean(boolean match) {
		if (!match) {
			System.out.println("No match");
		}
	}
}
