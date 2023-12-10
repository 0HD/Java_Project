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

        int firstUser = 0, lastUser = 10;
        final int numOfPages = (int) (Math.ceil((double) Main.registeredUsers.size() / 10.0));
        boolean showFull = false;
        while (true) {
            Output.clear();
            int currentPage = (int) (Math.ceil((double) lastUser / 10.0));

            if (lastUser > Main.registeredUsers.size())
                lastUser = Main.registeredUsers.size();
            if (currentPage > numOfPages) {
                firstUser -= 10; lastUser = firstUser + 10;
                code = Main.Status.OK;
                continue;
            }
            if (firstUser < 0) {
                firstUser = 0;
                lastUser = firstUser + 10;
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
                case '1': firstUser += 10; lastUser = firstUser + 10; continue;
                case '2': if (firstUser != 1) {
                    firstUser -= 10; lastUser = firstUser + 10; continue;
                }
                case '3': showFull = true; break;
                case '4': Admin.findUser(); break;
            }
        }
    }

    public static int editUser(User user) {
        while (true) {
            Output.clear();
            ArrayList<User> singleArray = new ArrayList<>();
            singleArray.add(user);

            Output.viewTable(singleArray, 0, 1, 0);

            String []options = new String[]{"Back to main menu.", "Update the full name.",
                    "Update the username.", "Update the password."};
            Integer lastOption = 3;
            int index = 0;

            Output.printOptions(options);

            Output.printInputMessage("Please enter a number.");
            String a = Main.userInput();

            a = a.trim();

            if (a.length() == 1 && Character.isDigit(a.charAt(0))) {
                if (a.charAt(0) == '0')
                    return 0;
                else if (a.charAt(0) > lastOption.toString().charAt(0))
                    return -1;
            }
            else
                return -1;

            switch (a.charAt(0)) {
                case '1': {
                    Output.clear();
                    Output.viewTable(singleArray, 0, 1, 2);
                    Output.printInputMessage("Enter the new full name.");
                    user.setFullName(Main.userInput());
                    break;
                }
                case '2': {
                    Output.clear();
                    Output.viewTable(singleArray, 0, 1, 3);
                    Output.printInputMessage("Enter the new username.");
                    user.setUsername(Main.userInput());
                    break;
                }
                case '3': {
                    Output.clear();
                    Output.viewTable(singleArray, 0, 1, 4);
                    Output.printInputMessage("Enter the new password.");
                    user.setPassword(Main.userInput());
                    break;
                }
            }
        }
    }

    public static int findUser() {
        Output.clear();
        Output.printMessage("Finding a user account by username.");
        Output.printInputMessage("Enter the username of the user.");
        String input = Main.userInput();

        User foundUser = new Admin("", "");
        boolean found = false;
        for (User user : Main.registeredUsers) {
            if (user.getUsername().equals(input)) {
                foundUser = user;
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No user found.");
            return -1;
        }

        if (Admin.editUser(foundUser) == 0)
            return 0;
        else
            return -1;
    }

    public static int deleteUser () {
        Output.clear();
        Output.printMessage("Finding a user account by username.");
        Output.printInputMessage("Enter the username of the user.");
        String input = Main.userInput();

        User foundUser = new Admin("", "");
        boolean found = false;
        for (User user : Main.registeredUsers) {
            if (user.getUsername().equals(input)) {
                foundUser = user;
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No user found.");
            return -1;
        }
        Main.Status code = Main.Status.OK;

        while (true) {
            Output.clear();
            Output.printMessageML(new String[]{"Are you sure you want to delete this user?",
                    "- Username: " + foundUser.getUsername()});
            Output.printOptions(new String[]{"Back to main menu.", "Yes, I'm sure."});
            if (code == Main.Status.OK) Output.printInputMessage("Please enter a number.");
            else Output.printInputMessage("Invalid option. Please enter 0 or 1.");
            String a = Main.userInput();

            a = a.trim();

            if (a.length() == 1 && Character.isDigit(a.charAt(0))) {
                if (a.charAt(0) == '0')
                    return 0;
                else if (a.charAt(0) != '1') {
                    code = Main.Status.INVALID_LOGIN;
                    continue;
                }
            }
            else {
                code = Main.Status.INVALID_LOGIN;
                continue;
            }

            break;
        }

        Output.clear();
        Output.printMessage("Deleting user: \"" + foundUser.getUsername() + "\".");
        Output.printInputMessage("Please enter \"" + foundUser.getUsername() + "\" to proceed.");
        if (!Main.userInput().equals(foundUser.getUsername())) {
            Output.clear();
            Output.printMessage("Account deletion cancelled. Enter to continue.");
            Main.userInput();
            return 0;
        }

        int id = foundUser.getId();
        Main.registeredUsers.remove(foundUser);

        for (int i = id; i < Main.registeredUsers.size(); i++) {
            Main.registeredUsers.get(i).setUserId(Main.registeredUsers.get(i).getId() - 1);
        }

        Output.clear();
        Output.printMessage("The account was deleted. Enter to continue.");
        Main.userInput();
        return 0;
    }

}