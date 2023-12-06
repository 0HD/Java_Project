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

        welcome();

    }

    public static String userInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public static void printMenu(User user) {
        while (true) {
            System.out.print(" _ Welcome, " + user.getFullName() + ".\n");
            System.out.print("|  You are");

            if (user.getClass() == Admin.class);
                System.out.print("an admin.\n");



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

    public static void welcome() {
        boolean invalidInput = false;
        boolean invalidUser = false;

        while (true) {
            clear();

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
                if (in == '1') {
                    if (login() == -1) {
                        invalidUser = true;
                    }
                }
                if (in == '2' && registeredUsers.size() == 0)
                    loadDemoDatabase();
                if (in == '0')
                    break;
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

    public static int login() {
        if (registeredUsers.size() == 0) {
            String adminName = "", adminPass = "";
            while (true) {
                clear();
                System.out.print("Welcome to the Real Estate Management System.\n" +
                        "Preparing admin account.\n" +
                        "Please enter a new username:");

                adminName = userInput();
                if (adminName.length() != 0)
                    break;
            }
            while (true) {
                clear();
                System.out.print("Welcome to the Real Estate Management System.\n" +
                        "Creating admin account: " + adminName + "\n" +
                        "Please enter a new password:");

                adminPass = userInput();
                if (adminPass.length() != 0)
                    break;
            }
            registeredUsers.add(new Admin(adminName, adminPass));
            while (true) {
                clear();
                System.out.print("Welcome to your new account, " + adminName + ".\n" +
                                 "Please enter your full name to continue: ");

                 registeredUsers.get(0).setFullName(userInput());
                if (registeredUsers.get(0).getFullName().length() != 0)
                    break;
            }
            return 0;
        }
        else {
            String username = "", password = "";
            while (true) {
                clear();
                System.out.print("Welcome to the Real Estate Management System.\n" +
                        "Logging into existing user.\n" +
                        "Please enter your username:");

                username = userInput();
                if (username.length() != 0)
                    break;
            }
            while (true) {
                clear();
                System.out.print("Welcome to the Real Estate Management System.\n" +
                        "Logging into user: " + username + "\n" +
                        "Please enter your password:");

                password = userInput();
                if (password.length() != 0)
                    break;
            }
            int id = -1;
            for (User user : registeredUsers) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    id = user.getId();
                    break;
                }
            }
            if (id == -1) {
                return -1;
            }
            else {
                printMenu((Admin)registeredUsers.get(id));
                return 0;
            }

        }
    }

    public static void loadDemoDatabase() {
        registeredUsers.add(new Admin("real_admin", "admin12345"));
        registeredUsers.add(new Seller("janed", "iamjane555"));
        registeredUsers.add(new Buyer("mohammadali_", "theboxer0_"));
        Output.viewUsers(registeredUsers, 0, registeredUsers.size(), 0);
    }




    public static void clear() {
        // from StackOverflow
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ignored) {}
    }
}
