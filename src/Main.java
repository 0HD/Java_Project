import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main
{

    static ArrayList<User> registeredUsers = new ArrayList<>();

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
                options = new String[]{"My Listings", "List a Property", "Buy Requests"};
                lastOption = 3;
            }
            else if (user.getClass() == Buyer.class) {
                Output.print("a" + "\u001B[34m buyer\u001B[30m.\n");
                options = new String[]{"View Listings", "Owned Properties", "Sent Requests"};
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

            if (user.getClass() == Admin.class)
                switch (a.charAt(0)) {
                    case '1': Admin.viewAllUsers(); break;
                    case '2': break;
                    case '3': break;
                    case '4': break;
                    case '5': break;
                }
            else if (user.getClass() == Seller.class)
                switch (a.charAt(0)) {
                    case '1': break;
                    case '2': break;
                    case '3': break;
                }
            else if (user.getClass() == Buyer.class)
                switch (a.charAt(0)) {
                    case '1': break;
                    case '2': break;
                    case '3': break;
                }
        }
    }

    public static void welcome() {
        boolean invalidInput = false;
        boolean invalidUser = false;

        while (true) {
            Output.clear();

            if (invalidUser)
                System.out.print("Invalid username and/or password. Please try again.\n\n");

            if (registeredUsers.size() == 0){
                System.out.print("Welcome to the Real Estate Management System.\n" +
                                 "The database is currently empty.\n" +
                                 "Would you like to proceed, or load a demo database?\n");
                System.out.print("[1] Continue\n" +
                                 "[2] Load demo\n" +
                                 "[0] Exit system\n");
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
