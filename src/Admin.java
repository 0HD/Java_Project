import java.util.ArrayList;
import java.util.List;

class Admin extends User {
    private int numberOfAdmins;
    public Admin(String username, String password) {
        super(username, password);
        numberOfAdmins++;
    }

    public String getPassword(int id) {
        return super.getPassword();
    }

    public void setPassword(int id, String password) {
        super.setPassword(password);
    }

    public String getUsername(int id) {
        return super.getUsername();
    }

    public void setUsername(int id, String username) {
        super.setUsername(username);
    }

//    public void deleteUser(int id) {
//        List<User> users = getUsers();
//        for (int i = 0; i < users.size(); i++) {
//            User currentUser = users.get(i);
//            if (currentUser.getId() == id) {
//                users.remove(i);
//                break;
//            }
//        }
//    }

    public static void viewAllUsers() {
        Output.viewTable(Main.registeredUsers, 1, Main.registeredUsers.size(), 3);
        Output.print("Enter anything to go back.");
        Main.userInput();
    }

//    private List<User> getUsers() {
//        List<User> users = new ArrayList<>();
//        users.add(new User("user1", "password1"));
//        users.add(new User("user2", "password2"));
//        users.add(new User("user3", "password3"));
//        return users;
//    }
}