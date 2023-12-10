import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main
{

    static ArrayList<User> registeredUsers = new ArrayList<>();
    static ArrayList<Property> listedProperties = new ArrayList<>();

    private static boolean isExit = false;

    enum Status {
        OK,
        NO_USERS,
        USER_NOT_FOUND,
        NO_PROPERTIES,
        NO_OWNED_PROPERTIES,
        SIGNUP_CANCELLED,
        SIGNUP_SUCCESS,
        INVALID_LOGIN
    }

    public static void main(String[] args) {
        Output.print("\u001B[30m\u001B[107m");
        welcome();
        Output.resetColor();
    }

    public static String userInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public static void printUserMenu(User user) {

        Status code = Status.OK;
        while (true) {
            Output.clear();

            if (code == Status.NO_USERS) Output.printError("There are no registered users yet.");
            if (code == Status.USER_NOT_FOUND) Output.printError("That username does not exist.");
            if (code == Status.NO_PROPERTIES) Output.printError("There are no properties listed yet.");
            if (code == Status.NO_OWNED_PROPERTIES) Output.printError("You do not own any property yet.");
            code = Status.OK;

            // welcome message
            String []message = {"Welcome, " + user.getFullName() + ".", ""};





            String []options = new String[0];
            Integer lastOption = 0;

            if (user.getClass() == Admin.class) {
                message[1] = "You can \u001B[31mmanage, update, and delete users\u001B[30m.";
                options = new String[]{"Log out from your account.", "Show list of all users.",
                        "Update a user's information.", "Delete a user from the system."};
                lastOption = 3;
            }
            else if (user.getClass() == Seller.class) {
                message[1] = "You can \u001B[32mview and sell your own properties\u001B[30m.";
                options = new String[]{"Log out from your account.", "Show list of all properties.",
                        "List a new property for sale.",};
                lastOption = 2;
            }
            else if (user.getClass() == Buyer.class) {
                message[1] = "You can \u001B[34mview and purchase properties\u001B[30m.";
                options = new String[]{"Log out from your account.", "Show list of all properties.",
                        "View properties you own.", "Buy a new property."};
                lastOption = 3;
            }

            Output.printMessageML(message, 52, 2);
            Output.printOptions(options);

            Output.printInputMessage("Please enter a number.");
            String a = userInput();

            a = a.trim();

            if (a.length() == 1 && Character.isDigit(a.charAt(0))) {
                if (a.charAt(0) == '0')
                    return;
                else if (a.charAt(0) > lastOption.toString().charAt(0))
                    continue;
            }
            else continue;

            if (user instanceof Admin)
                switch (a.charAt(0)) {
                    case '1': Admin.viewAllUsers(); break;
                    case '2': {
                        if (findUser() == -1) {
                            code = Status.USER_NOT_FOUND;
                        }
                        break;
                    }
                    case '3': {
                        if (deleteUser() == -1) {
                            code = Status.USER_NOT_FOUND;
                        }
                        break;
                    }
                }
            else if (user instanceof Seller)
                switch (a.charAt(0)) {
                    case '1': ((Seller) user).viewMyListings(); break;
                    case '2': printNewListingMenu((Seller) user); break;
                }
            else if (user instanceof Buyer)
                switch (a.charAt(0)) {
                    case '1': ((Buyer) user).listAllProperties(); break;
                    case '2': ((Buyer) user).listMyProperties(); break;
                    case '3': printBuyMenu((Buyer) user); break;
                }
        }
    }

    public static void printBuyMenu (Buyer buyer) {
        boolean invalidInput = false;
        do {
            System.out.println("Which property would you like to buy?");

            String input = userInput();
            invalidInput = false;

            if (input.isEmpty()) continue;

            for (char c : input.toCharArray()) {
                if (!Character.isDigit(c)) {
                    invalidInput = true;
                }
            }

            for (Property property : listedProperties) {
                if (property.getPropertyId() == Integer.valueOf(input))
                    buyer.purchaseProperty(property.getOwner(), property);
            }
        } while (invalidInput);


    }

    public static int deleteUser () {
        Output.clear();
        Output.printMessage("Finding a user account by username.");
        Output.printInputMessage("Enter the username of the user.");
        String input = userInput();

        User foundUser = new Admin("", "");
        boolean found = false;
        for (User user : registeredUsers) {
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
        Status code = Status.OK;

        while (true) {
            Output.clear();
            Output.printMessageML(new String[]{"Are you sure you want to delete this user?",
                    "- Username: " + foundUser.getUsername()});
            Output.printOptions(new String[]{"Back to main menu.", "Yes, I'm sure."});
            if (code == Status.OK) Output.printInputMessage("Please enter a number.");
            else Output.printInputMessage("Invalid option. Please enter 0 or 1.");
            String a = userInput();

            a = a.trim();

            if (a.length() == 1 && Character.isDigit(a.charAt(0))) {
                if (a.charAt(0) == '0')
                    return 0;
                else if (a.charAt(0) != '1') {
                    code = Status.INVALID_LOGIN;
                    continue;
                }
            }
            else {
                code = Status.INVALID_LOGIN;
                continue;
            }

            break;
        }

        Output.clear();
        Output.printMessage("Deleting user: \"" + foundUser.getUsername() + "\".");
        Output.printInputMessage("Please enter \"" + foundUser.getUsername() + "\" to proceed.");
        if (!userInput().equals(foundUser.getUsername())) {
            Output.clear();
            Output.printMessage("Account deletion cancelled. Enter to continue.");
            userInput();
            return 0;
        }

        int id = foundUser.getId();
        registeredUsers.remove(foundUser);

        for (int i = id; i < registeredUsers.size(); i++) {
            registeredUsers.get(i).setUserId(registeredUsers.get(i).getId() - 1);
        }

        Output.clear();
        Output.printMessage("The account was deleted. Enter to continue.");
        userInput();
        return 0;
    }

    public static void printNewListingMenu (Seller seller) {
        boolean invalidInput = false;
        String propertyName = "", value = "", address = "";
        do {
            System.out.println("What's the name of the property?");

            String input = userInput();
            invalidInput = false;

            if (input.isEmpty()) continue;

            for (char c : input.toCharArray()) {
                if (!Character.isLetter(c)) {
                    invalidInput = true;
                }
            }

            if (!invalidInput)
                propertyName = input;
        } while (invalidInput);

        do {
            System.out.println("What's the address of the property?");

            String input = userInput();
            invalidInput = false;

            if (input.isEmpty()) continue;

            for (char c : input.toCharArray()) {
                if (!Character.isLetter(c)) {
                    invalidInput = true;
                }
            }

            if (!invalidInput)
                address = input;
        } while (invalidInput);

        do {
            System.out.println("What's the value of the property? (Numbers only)");

            String input = userInput();
            invalidInput = false;

            if (input.isEmpty()) continue;

            for (char c : input.toCharArray()) {
                if (!Character.isDigit(c)) {
                    invalidInput = true;
                }
            }

            if (!invalidInput)
                value = input;
        } while (invalidInput);
        double dblValue = Double.valueOf(value);

        Property property = new Property(seller.getId(), propertyName, dblValue);
        property.setAddress(address);
        property.setTax(0.15);
        Main.listedProperties.add(property);
        seller.addProperty(property);
    }

    public static int findUser() {
        Output.clear();
        Output.printMessage("Finding a user account by username.");
        Output.printInputMessage("Enter the username of the user.");
        String input = userInput();

        User foundUser = new Admin("", "");
        boolean found = false;
        for (User user : registeredUsers) {
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

        if (editUser(foundUser) == 0)
            return 0;
        else
            return -1;
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
            String a = userInput();

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
                    user.setFullName(userInput());
                    break;
                }
                case '2': {
                    Output.clear();
                    Output.viewTable(singleArray, 0, 1, 3);
                    Output.printInputMessage("Enter the new username.");
                    user.setUsername(userInput());
                    break;
                }
                case '3': {
                    Output.clear();
                    Output.viewTable(singleArray, 0, 1, 4);
                    Output.printInputMessage("Enter the new password.");
                    user.setPassword(userInput());
                    break;
                }
            }
        }
    }
    public static void welcome() {
        boolean invalidInput = false;

        Status code = Status.OK;

        while (true) {
            Output.clear();

            if (code == Status.INVALID_LOGIN)
                Output.printError("Invalid username and/or password.");
            else if (code == Status.SIGNUP_CANCELLED)
                Output.printError("Account creation cancelled.");

            if (registeredUsers.isEmpty()){
                Output.print("\n");
                String[] message = {"        Welcome to the Real Estate", "            Management System!"};
                Output.printMessageML(message);
                String []menuOptions = {"Exit the system.", "Create an empty database.", "Load the demo database."};
                Output.printOptions(menuOptions);
            }
            else {
                Output.print("\n");
                String[] message = {"        Welcome to the Real Estate", "            Management System!"};
                if (code == Status.SIGNUP_SUCCESS) {
                    message[0] = "Account creation complete!";
                    message[1] = "Try logging in with \"" + registeredUsers.get(registeredUsers.size() - 1).getUsername() + "\".";
                }
                Output.printMessageML(message);
                String []menuOptions = {"Exit the system.", "Log into existing account.", "Create a new account."};
                Output.printOptions(menuOptions);
            }

            if (invalidInput)
                Output.printInputMessage("Invalid input! Please enter a number from 0-3.");
            else
                Output.printInputMessage("Please enter a number from 0-3.");
            invalidInput = false;
            code = Status.OK;
            String input = userInput() + " ";
            char in = input.charAt(0);

            if (input.length() != 2 || in < '0' || in > '2') {
                invalidInput = true;
                continue;
            }
            else {
                if (in == '0')
                    return;
                else if (in == '1') {
                    if (login() == -1) {
                        code = Status.INVALID_LOGIN;
                    }
                }
                else if (in == '2' && registeredUsers.isEmpty())
                    Demo.loadDemoDatabase();
                else if (in == '2' && !registeredUsers.isEmpty())
                    if (signup(false) == -1) {
                        code = Status.SIGNUP_CANCELLED;
                    }
                    else
                        code = Status.SIGNUP_SUCCESS;
            }
        }
    }

    public static int signup(boolean forAdmin) {
        String username = "", password = "", fullName = "";

        boolean invalid = false;
        do {
            invalid = false;
            Output.clear();
            Output.printMessage("Creating a new account.");
            Output.printInputMessage("Please enter your real name (not username).");

            fullName = userInput();
            if (fullName.isEmpty()) {
                invalid = true;
                continue;
            }

            for (char i : fullName.toCharArray()) {
                if (!Character.isLetter(i) && i != ' ')
                    invalid = true;
            }

        } while (invalid);

        do {
            Output.clear();
            Output.printMessage("Welcome, " + fullName + ".");
            if (invalid) {
                Output.printMessage("Your username must be less than 12 characters, and can only contain letters," +
                        " digits, underscores, and dots.");
            }
            invalid = false;
            Output.printInputMessage("Please enter a new username for your account.");

            username = userInput();
            if (username.isEmpty() || username.length() > 12)
                invalid = true;

            for (char i : username.toCharArray()) {
                if (!Character.isLetter(i) && !Character.isLetter(i) && i != '_' && i != '.')
                    invalid = true;
            }

        } while (invalid);

        do {

            Output.clear();
            Output.printMessageML(new String[]{"Welcome, " + fullName + ".", "Username: " + username});
            if (invalid) {
                Output.printMessage("Your password must be at least 8 characters long, and at most 24 characters long.");
            }
            invalid = false;
            Output.printInputMessage("Please enter a new password for your account.");

            password = userInput();
            if (password.length() < 8 || password.length() > 24)
                invalid = true;
        } while (invalid);

        if (!forAdmin) do {
            Output.clear();

            if (invalid) {
                Output.printError("Please enter a valid option.");
            }
            Output.printMessageML(new String[]{"Welcome, " + fullName + ".", "Username: " + username});

            Output.printOptions(new String[]{"Cancel account creation.", "I will buy properties.", "I will sell properties."});

            String input = userInput();
            char in = input.charAt(0);

            if (in < '0' || in > '2') {
                invalid = true;
            }
            else {
                if (in == '0')
                    return -1;
                else if (in == '1') {
                    Buyer buyer = new Buyer(username, password);
                    buyer.setFullName(fullName);
                    for (User user : registeredUsers) {
                        if (user.getUsername().equals(buyer.getUsername())) {
                            return -1;
                        }
                    }
                    registeredUsers.add(buyer);
                    return 0;
                }
                else if (in == '2') {
                    Seller seller = new Seller(username, password);
                    seller.setFullName(fullName);
                    for (User user : registeredUsers) {
                        if (user.getUsername().equals(seller.getUsername())) {
                            return -1;
                        }
                    }
                    registeredUsers.add(seller);
                    return 0;
                }
            }
        } while (true);
        else {
            Admin admin = new Admin(username, password);
            admin.setFullName(fullName);
            registeredUsers.add(admin);
        }
        return 0;
    }

    public static int login() {
        if (registeredUsers.isEmpty()) {
            signup(true);
            return 0;
        }
        else {
            String username = "", password = "";
            while (true) {
                Output.clear();
                Output.printMessage("Logging into existing account.");
                Output.printInputMessage("Please enter your username.");
                username = userInput();
                if (!username.isEmpty())
                    break;
            }
            while (true) {
                Output.clear();
                Output.printMessage("Logging into: " + username);
                Output.printInputMessage("Please enter your password.");

                password = userInput();
                if (!password.isEmpty())
                    break;
            }
            int index = 0;
            for (User user : registeredUsers) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    printUserMenu(registeredUsers.get(index));
                    return 0;
                }
                index++;
            }
            return -1;
        }
    }





}
