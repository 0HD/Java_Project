import java.io.IOException;
import java.util.ArrayList;

public class Output {
    public static boolean showNumbers = false;
    public static int[] columns = {1, 2, 3, 4, 5, 6};

    public static void printBox (String message) {
        System.out.println("  _________________________________\n");
        System.out.println(" |                                 |\n");
        System.out.println(" |"        + message +            "|\n");
        System.out.println(" |_________________________________|\n");
    }

    private static int maxCharacters (String str, int max) {
        return Math.max(str.length(), max);
    }

    private static String fillSpaces (String str, int max) {

        while (str.length() < max)
            str += " ";

        return str + " ";
    }

    private static void printColumns(int[] columns, String start, String col1, String col2, String col3, String col4, String col5, String col6) {
        for (int i = 0; i < 6; i++) {
            System.out.print(start);
            switch (columns[i]) {
                case 1:
                    System.out.print(col1); break;
                case 2:
                    System.out.print(col2); break;
                case 3:
                    System.out.print(col3); break;
                case 4:
                    System.out.print(col4); break;
                case 5:
                    System.out.print(col5); break;
                case 6:
                    System.out.print(col6); break;
            }
        }
        System.out.print("|\n");
    }

    public static void resetColor () {
        System.out.print("\u001B[0m");
        clear();
    }

    public static void print(String output) {
        System.out.print(output);
    }

    public static void clear() {
        print("\n\n==================================================\n\n");
        // from StackOverflow
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ignored) {}
        //
    }

    private static String underscores (int amount) {

        String underscores = "";

        for (int i = 1; i <= amount; i++)
            underscores += "_";

        return underscores;
    }

    public static void viewUsers(ArrayList<User> array, int first, int last, int highlight) {

        String ID = "ID", name = "Full Name", username = "Username", password = "Password", type = "User Type", active = "Active";

        if (showNumbers) {
            ID = "1. " + ID;
            name = "2. " + name;
            username = "3. " + username;
            password = "4. " + password;
            type = "5. " + type;
            active = "6. " + active;
        }

        // Sets default character count in each column to the column's title length
        int maxID = ID.length(), maxName = name.length(), maxUsername = username.length();
        int maxPassword = password.length(), maxType = type.length(), maxActive = active.length();

        // Replace the default length with the largest character count in each column for each row
        for (int i = first; i < last; i++) {
            maxID = maxCharacters (array.get(i).getId() + "", maxID);
            maxName = maxCharacters (array.get(i).getFullName() + "", maxName);
            maxUsername = maxCharacters (array.get(i).getUsername() + "", maxUsername);
            maxPassword = maxCharacters (array.get(i).getPassword() + "", maxPassword);
            maxType = maxCharacters (array.get(i).getClass().toString() + "", maxType);
            maxActive = maxCharacters ("Yes", maxActive);
        }

        switch (highlight) {
            case 1:
                ID = ">" + ID + "<"; maxID += 2; break;
            case 2:
                name = ">Full Name<"; maxName += 2; break;
            case 3:
                username = ">Username<"; maxUsername += 2;  break;
            case 4:
                password = ">Password<"; maxPassword += 2; break;
            case 5:
                type = ">User Type<"; maxType += 2; break;
            case 6:
                active = ">Active<"; maxActive += 2; break;
        }

        // Prints the top border line, with the total width adjusting for the largest character count in each row
        System.out.print(" " + underscores(20 + maxID + maxName + maxUsername + maxPassword + maxType + maxActive) + "\n");

        // Prints the title for every column, and adds spaces if the data is too small, then adds the separator "|" character
        printColumns(columns, "| ", fillSpaces("", maxID), fillSpaces("", maxName), fillSpaces("", maxUsername), fillSpaces("", maxPassword),
                fillSpaces("", maxType), fillSpaces("", maxActive));

        printColumns(columns, "| ", fillSpaces(ID, maxID), fillSpaces(name, maxName), fillSpaces(username, maxUsername),
                fillSpaces(password, maxPassword), fillSpaces(type, maxType), fillSpaces(active, maxActive));

        printColumns(columns, "|_", underscores(maxID + 1), underscores(maxName + 1), underscores(maxUsername + 1), underscores(maxPassword + 1),
                underscores(maxType + 1), underscores(maxActive + 1));

        // Prints the line below the column titles, with the total width adjusting for the largest character count in each row, separated by "|"
        printColumns(columns, "| ", fillSpaces("", maxID), fillSpaces("", maxName), fillSpaces("", maxUsername), fillSpaces("", maxPassword),
                fillSpaces("", maxType), fillSpaces("", maxActive));

        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        // Prints every row's data on separate lines, with the total width adjusting for the largest character count in each row
        for (int i = first; i < last; i++) {

            // Converts all data to strings
            String _id = array.get(i).getId() + "";
            String _fullName = array.get(i).getFullName() + "";
            String _username = array.get(i).getUsername() + "";
            String _password = array.get(i).getPassword() + "";
            String _type = array.get(i).getClass().toString() + "";
            String _active = "Yes";

            // Prints the data for every column, and adds spaces if the data is too small, then adds the separator "|" character
            printColumns(columns, "| ", fillSpaces(_id, maxID), fillSpaces(_fullName, maxName), fillSpaces(_username, maxUsername),
                    fillSpaces(_password, maxPassword), fillSpaces(_type, maxType), fillSpaces(_active, maxActive));
        }

        printColumns(columns, "|_", underscores(maxID + 1), underscores(maxName + 1), underscores(maxUsername + 1), underscores(maxPassword + 1),
                underscores(maxType + 1), underscores(maxActive + 1));
    }
}