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
//        Output.viewTable(Main.registeredUsers, 1, Main.registeredUsers.size(), 3);
//        Output.print("Enter anything to go back.");
//        Main.userInput();

        Main.Status code = Main.Status.OK;

        int firstUser = 1, lastUser = 10;
        final int numOfPages = (int) (Math.ceil((double) Main.registeredUsers.size() / 10.0));
        boolean showFull = false;
        while (true) {
            Output.clear();
            int currentPage = (int) (Math.ceil((double) lastUser / 10.0));

            if (lastUser > Main.registeredUsers.size())
                lastUser = Main.registeredUsers.size();
            if (currentPage > numOfPages) {
                firstUser -= 10; lastUser = firstUser + 9;
                code = Main.Status.OK;
                continue;
            }

            Output.print("\n [ Page " + currentPage + "/" + numOfPages + " ] [ Showing 10 users at a time. ]\n");
            if (showFull)
                Output.viewTable(Main.registeredUsers, firstUser, Main.registeredUsers.size(), 0);
            else
                Output.viewTable(Main.registeredUsers, firstUser, lastUser, 0);
            Output.printOptions(new String[]{"Back to main menu.", "Show the next page.",
                    "Show the previous page.", "Show the full list at once.", "Find a user from the list."});

            if (code != Main.Status.OK) Output.printInputMessage("Invalid option! Please enter a number from 0-4.");
            else Output.printInputMessage("Please enter a number.");
            code = Main.Status.OK;
            showFull = false;
            String a = Main.userInput();

            a = a.trim();

            if (a.length() == 1 && Character.isDigit(a.charAt(0))) {
                if (a.charAt(0) == '0')
                    return;
                else if (a.charAt(0) > 4)
                    code = Main.Status.USER_NOT_FOUND;
            } else {
                code = Main.Status.USER_NOT_FOUND;
                continue;
            }

            switch (a.charAt(0)) {
                case '1': firstUser += 10; lastUser = firstUser + 9; continue;
                case '2': if (firstUser != 1) {
                    firstUser -= 10; lastUser = firstUser + 9; continue;
                }
                case '3': showFull = true; break;
                case '4': Main.findUser(); break;
            }
        }
    }

}