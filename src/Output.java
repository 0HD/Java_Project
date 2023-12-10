import java.io.IOException;
import java.math.BigDecimal;
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

    private static String fillSpaces (String str, int max) {
        if (str.length() > max)
            return str.substring(0, max - 2) + "...";
        else while (str.length() < max)
            str += " ";

        return str + " ";
    }

    private static void printColumns(int[] columns, String start, String col1, String col2, String col3, String col4, String col5) {
        for (int i = 0; i < 5; i++) {
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

    public static void viewTable(ArrayList<?> array, int first, int last, int highlight) {

        String col1 = "", col2 = "", col3 = "", col4 = "", col5 = "";
        if (array.get(0) instanceof User) {
            col1 = "ID";
            col2 = "Full Name";
            col3 = "Username";
            col4 = "Password";
            col5 = "User Type";
        }
        if (array.get(0) instanceof Property) {
            col1 = "ID";
            col2 = "Type";
            col3 = "Property Name";
            col4 = "Address";
            col5 = "Total Cost";
        }


        if (showNumbers) {
            col1 = "1. " + col1;
            col2 = "2. " + col2;
            col3 = "3. " + col3;
            col4 = "4. " + col4;
            col5 = "5. " + col5;
        }

        // Sets default character count in each column to the column's title length
        int max1 = col1.length(), max2 = col2.length(), max3 = col3.length();
        int max4 = col4.length(), max5 = col5.length();

        // Replace the default length with the largest character count in each column for each row
        for (int i = first; i < last; i++) {
            if (array.get(i) instanceof User) {
                User temp = (User)array.get(i);

                max1 = Math.max ((temp.getId() + "").length(), max1);
                max2 = Math.max (temp.getFullName().length(), max2);
                max3 = Math.max (temp.getUsername().length(), max3);
                max4 = Math.max (temp.getPassword().length(), max4);
                max5 = Math.max (array.get(i).getClass().toString().substring(6).length(), max5);
            }
            if (array.get(i) instanceof Property) {
                Property x = (Property)array.get(i);

                max1 = Math.max ((x.getPropertyId() + "").length(), max1);
                max2 = Math.max ((x.getType().toString().charAt(0) + x.getType().toString().substring(1).toLowerCase()).length(), max2);
                max3 = Math.max (x.getName().length(), max3);
                max4 = Math.max (x.getAddress().length(), max3);
                BigDecimal totalCost = BigDecimal.valueOf(x.calculateTotal());
                max5 = Math.max (("SAR " + totalCost.toPlainString()).length(), max4);
            }

        }

        switch (highlight) {
            case 1:
                col1 = highlightColumn(col1); max1 += 2; break;
            case 2:
                col2 = highlightColumn(col2); max2 += 2; break;
            case 3:
                col3 = highlightColumn(col3); max3 += 2;  break;
            case 4:
                col4 = highlightColumn(col4); max4 += 2; break;
            case 5:
                col5 = highlightColumn(col5); max5 += 2; break;
        }
        print(" ");
        // Prints the top border line, with the total width adjusting for the largest character count in each row
        System.out.print(" " + underscores(14 + max1 + max2 + max3 + max4 + max5) + "\n");
        print(" ");
        // Prints the title for every column, and adds spaces if the data is too small, then adds the separator "|" character
        printColumns(columns, "| ", fillSpaces("", max1), fillSpaces("", max2), fillSpaces("", max3), fillSpaces("", max4),
                fillSpaces("", max5));
        print(" ");
        printColumns(columns, "| ", fillSpaces(col1, max1), fillSpaces(col2, max2), fillSpaces(col3, max3),
                fillSpaces(col4, max4), fillSpaces(col5, max5));
        print(" ");
        printColumns(columns, "|_", underscores(max1 + 1), underscores(max2 + 1), underscores(max3 + 1), underscores(max4 + 1),
                underscores(max5 + 1));
        print(" ");
        // Prints the line below the column titles, with the total width adjusting for the largest character count in each row, separated by "|"
        printColumns(columns, "| ", fillSpaces("", max1), fillSpaces("", max2), fillSpaces("", max3), fillSpaces("", max4),
                fillSpaces("", max5));

        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        // Prints every row's data on separate lines, with the total width adjusting for the largest character count in each row
        for (int i = first; i < last; i++) {
            String item1 = "", item2 = "", item3 = "", item4 = "", item5 = "";
            // Converts all data to strings
            if (array.get(0) instanceof User) {
                User x = (User) array.get(i);
                item1 = x.getId() + "";
                item2 = x.getFullName();
                item3 = x.getUsername();
                item4 = x.getPassword();
                item5 = x.getClass().toString().substring(6);
            }
            if (array.get(0) instanceof Property) {
                Property x = (Property) array.get(i);
                item1 = x.getPropertyId() + "";
                item2 = x.getType().toString().charAt(0) + x.getType().toString().substring(1).toLowerCase();
                item3 = x.getName();
                item4 = x.getAddress();
                BigDecimal totalCost = BigDecimal.valueOf(x.calculateTotal());
                item5 = "SAR " + totalCost.toPlainString();
            }

            print(" ");
            // Prints the data for every column, and adds spaces if the data is too small, then adds the separator "|" character
            printColumns(columns, "| ", fillSpaces(item1, max1), fillSpaces(item2, max2), fillSpaces(item3, max3),
                         fillSpaces(item4, max4), fillSpaces(item5, max5));
        }
        print(" ");
        printColumns(columns, "|_", underscores(max1 + 1), underscores(max2 + 1), underscores(max3 + 1), underscores(max4 + 1),
                underscores(max5 + 1));
    }

    private static String highlightColumn (String column) {
        return ">" + column + "<";
    }

    public static void printOptions (String[] options) {
        print("  _________________________________________________\n");
        print(" |                                                 |\n");
        int counter = 0;
        for (String option : options) {
            if (counter == 0) { counter++; continue; }
            print(" |      [" + (counter++) + "] " + fillSpaces(option, 35) + "   |\n");
        }
        if (!options[0].isEmpty()) {
            print(" |   -------------------------------------------   |\n");
            print(" |      [0] " + fillSpaces(options[0], 35) + "   |\n");
        }
        print(" |_________________________________________________|\n");
    }

    public static void printMessage (String message) {
        int length = message.length();
        int start = 0;

        print("  _________________________________________________\n");
        print(" |                                                 |\n");
//        print(" |   " + fillSpaces(message, 49) + "   |\n");

        while (message.length() - start > 0) {
            if ((start + 42) >= message.length()) {
                print(" |   " + fillSpaces(message.substring(start), 42) + "   |\n");
                break;
            }
            else if (Character.isLetter(message.charAt(start + 41)) && Character.isLetter(message.charAt(start + 42))) {
                print(" |   " + message.substring(start, start + 42) + "-   |\n");
                length -= 42; start += 42;
            }
            else if (Character.isLetter(message.charAt(start + 42))) {
                print(" |   " + message.substring(start, start + 41) + "     |\n");
                length -= 41; start += 42;
            }
            else {
                print(" |   " + message.substring(start, start + 43) + "   |\n");
                length -= 43; start += 43;
            }
        }

        print(" |_________________________________________________|\n");
    }

    public static void printMessageML (String[] lines) {
        print("  _________________________________________________\n");
        print(" |                                                 |\n");
        for (String line : lines) {
            print(" |   " + fillSpaces(line, 42) + "   |\n");
        }
        print(" |_________________________________________________|\n");
    }

    public static void printMessageML (String[] lines, int override, int oline) {
        print("  _________________________________________________\n");
        print(" |                                                 |\n");
        int counter = 0;
        for (String line : lines) {
                counter++;
                if (counter == oline)
                    print(" |   " + fillSpaces(line, override) + "   |\n");
                else
                    print(" |   " + fillSpaces(line, 42) + "   |\n");
        }
        print(" |_________________________________________________|\n");
    }

    public static void printError (String message) {
        print("    .\n   / \\  .-----------------------------------------.\n");
        print("  / | \\(  " + fillSpaces(message, 39) + " )\n");
        print(" /__'__\\'-----------------------------------------'\n");
    }

    public static void printInputMessage (String message) {
        print("  _____________ _ ____ _____  ___  _ _ ___  _   _\n |\n");
        print(" |   " + message + "\n" + " |\n '-----> ");
    }
}