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
        while (true) {
            Output.clear();

            // welcome message
            Output.print(" _ Welcome, \u001B[1m" + user.getFullName() + "\u001B[2m.\n");
            Output.print("|  You are ");

            String []options = new String[0];
            Integer lastOption = 0;

            if (user.getClass() == Admin.class) {
                Output.print("an" + "\u001B[31m admin\u001B[30m.\n");
                options = new String[]{"View All Users", "Find User", "Edit User", "Add User", "Delete User"};
                lastOption = 5;
            }
            else if (user.getClass() == Seller.class) {
                Output.print("a" + "\u001B[32m seller\u001B[30m.\n");
                options = new String[]{"View Owned Properties", "List New Property",};
                lastOption = 2;
            }
            else if (user.getClass() == Buyer.class) {
                Output.print("a" + "\u001B[34m buyer\u001B[30m.\n");
                options = new String[]{"View Listings", "View Owned Properties", "Buy Property"};
                lastOption = 3;
            }

            Output.print("|\n");
            Output.print("'-> Main Menu\n");

            int index = 0;
            for (String option : options) {
                Output.print("[" + (++index) + "] " + option + "\n");
            }

            Output.print("[0] Log out\n\n");

            Output.print(".-- Please enter a number:\n");
            Output.print("'->");
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
                    case '2': findUser(); break;
                    case '3': editUser(); break;
                    case '4': signup(false); break;
                    case '5': deleteUser(); break;
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
        System.out.println("Enter the ID of the user to find:");
        String input = userInput();

        User foundUser = new Admin("", "");
        boolean found = false;
        for (User user : registeredUsers) {
            if (user.getId() == Integer.valueOf(input)) {
                foundUser = user;
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No user found.");
            return -1;
        }

        int id = foundUser.getId();
        registeredUsers.remove(foundUser);

        for (int i = id; i < registeredUsers.size(); i++) {
            registeredUsers.get(i).setUserId(registeredUsers.get(i).getId() - 1);
        }

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
        System.out.println("Enter the username of the user to find:");
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

        ArrayList<User> singleArray = new ArrayList<>();
        singleArray.add(foundUser);

        Output.viewTable(singleArray, 0, 1, 0);

        return 0;
    }
    public static int editUser() {
        System.out.println("Enter the ID of the user to find:");
        String input = userInput();

        User foundUser = new Admin("", "");
        boolean found = false;
        for (User user : registeredUsers) {
            if (user.getId() == Integer.valueOf(input)) {
                foundUser = user;
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No user found.");
            return -1;
        }

        ArrayList<User> singleArray = new ArrayList<>();
        singleArray.add(foundUser);

        Output.viewTable(singleArray, 0, 1, 0);
        System.out.println("What would you like to do with this account?");

        String []options = new String[]{"Change full name", "Change username", "Change password"};
        Integer lastOption = 3;
        int index = 0;

        for (String option : options) {
            Output.print("[" + (++index) + "] " + option + "\n");
        }

        Output.print("[0] Back\n\n");

        Output.print(".-- Please enter a number:\n");
        Output.print("'->");
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
                System.out.println("Enter the new full name:");
                foundUser.setFullName(userInput());
                break;
            }
            case '2': {
                System.out.println("Enter the new username:");
                foundUser.setUsername(userInput());
                break;
            }
            case '3': {
                System.out.println("Enter the new password:");
                foundUser.setPassword(userInput());
                break;
            }
        }
        return 0;
    }


    public static void welcome() {
        boolean invalidInput = false;
        boolean invalidUser = false;

        while (true) {
            Output.clear();

            if (invalidUser)
                System.out.print("Invalid username and/or password. Please try again.\n\n");

            if (registeredUsers.isEmpty()){
                System.out.print("Welcome to the Real Estate Management System.\n" +
                                 "The database is currently empty.\n" +
                                 "Would you like to proceed, or load a demo database?\n");
//                System.out.print("[1] Continue\n" +
//                                 "[2] Load demo\n" +
//                                 "[0] Exit system\n");
                String []menuOptions = {"Exit system", "Continue", "Load demo"};
                Output.printOptions(menuOptions);
            }

            else {
                System.out.print("Welcome to the Real Estate Management System.\n" +
                                 "If you have an existing account, please select [1].\n");
                System.out.print("[1] Log into existing user\n" +
                                 "[2] Create new user\n" +
                                 "[0] Exit system\n");
            }

            if (invalidInput)
                System.out.print("\nInvalid Input! Please enter a number from 0 to 2: ");
            else
                System.out.print("\nPlease enter a number: ");
            invalidInput = false;
            invalidUser = false;
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
                        invalidUser = true;
                    }
                }
                else if (in == '2' && registeredUsers.isEmpty())
                    Demo.loadDemoDatabase();
                else if (in == '2' && !registeredUsers.isEmpty())
                    if (signup(false) == -1) {
                        invalidUser = true;
                    }
            }
        }
    }

    public static void adminMenu (Admin user) {
        while (true) {
            System.out.print(" _ Welcome, " + user.getFullName() + ".\n");
            System.out.print("|  You are an admin.\n");
            System.out.print("|\n");
            System.out.print("'-> Main Menu\n");
            System.out.print("[1] View All Users\n" +
                             "[2] Find User\n" +
                             "[3] Edit User\n" +
                             "[4] Add User\n" +
                             "[4] Delete User\n" +
                             "[0] Log out\n");
            System.out.print(".-- Please enter a number:\n");
            System.out.print("'->");
            String a = userInput();
            if (a.length() > 0 && a.charAt(0) == '0') {
                break;
            }
        }
    }

    public static int signup(boolean forAdmin) {
        String username = "", password = "", fullName = "";

        boolean invalid = false;
        do {
            invalid = false;
            Output.clear();
            System.out.print("Welcome to the Real Estate Management System.\n" +
                    "Creating a new account.\n" +
                    "Please enter your full name.\n" +
                    "> ");

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
            invalid = false;
            Output.clear();
            System.out.print("Welcome to the Real Estate Management System.\n" +
                    "Welcome, " + fullName + ".\n" +
                    "Please enter a new username.\n" +
                    "(Must be less than 12 characters, only letters, digits, and _ or .)\n" +
                    "> ");

            username = userInput();
            if (username.isEmpty())
                invalid = true;

            for (char i : username.toCharArray()) {
                if (!Character.isLetter(i) && !Character.isLetter(i) && i != '_' && i != '.')
                    invalid = true;
            }

        } while (invalid);

        do {
            invalid = false;
            Output.clear();
            System.out.print("Welcome to the Real Estate Management System.\n" +
                    "Welcome, " + fullName + ".\n" +
                    "Please enter a new password\n" +
                    "(Must be between 8-24 characters)\n" +
                    "> ");

            password = userInput();
            if (password.length() < 8 || password.length() > 24)
                invalid = true;
        } while (invalid);

        if (!forAdmin) do {
            Output.clear();
            System.out.print("Welcome to the Real Estate Management System.\n" +
                    "Welcome, " + fullName + ".\n" +
                    "Would you like to buy, or sell properties?\n" +
                    "> ");

            String answer = userInput();
            if (answer.contains(" buy ") && answer.contains(" sell "))
                continue;
            else if (answer.contains(" buy ")) {
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
            else if (answer.contains(" sell ")) {
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
                System.out.print("Welcome to the Real Estate Management System.\n" +
                        "Logging into existing user.\n" +
                        "Please enter your username:");

                username = userInput();
                if (!username.isEmpty())
                    break;
            }
            while (true) {
                Output.clear();
                System.out.print("Welcome to the Real Estate Management System.\n" +
                        "Logging into user: " + username + "\n" +
                        "Please enter your password:");

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
