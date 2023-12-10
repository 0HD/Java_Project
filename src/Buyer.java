import java.util.ArrayList;
import java.util.Arrays;

class Buyer extends User {

    private int numberOfBuyers;
    private ArrayList<Property> propertiesOwned = new ArrayList<>();

    public Buyer(String username, String password) {
        super(username, password);
        numberOfBuyers++;
    }

    public void listAllProperties() {
        Main.Status code = Main.Status.OK;

        int firstProperty = 0, lastProperty = 10;
        final int numOfPages = (int) (Math.ceil((double) Main.listedProperties.size() / 10.0));
        boolean showFull = false;
        if (Main.listedProperties.isEmpty()) {
            Output.printMessage("There are no properties listed for sale now.");
            Main.userInput();
        }
        else while (true) {
            Output.clear();
            int currentPage = (int) (Math.ceil((double) lastProperty / 10.0));

            if (lastProperty > Main.listedProperties.size())
                lastProperty = Main.listedProperties.size();
            if (currentPage > numOfPages) {
                firstProperty -= 10; lastProperty = firstProperty + 10;
                code = Main.Status.OK;
                continue;
            }
            if (firstProperty < 0) {
                firstProperty = 0;
                lastProperty = firstProperty + 10;
            }

            Output.print("\n [ Page " + currentPage + "/" + numOfPages + " ] [ Showing 10 properties at a time. ]\n");
            if (showFull)
                Output.viewTable(Main.listedProperties, firstProperty, Main.listedProperties.size(), 0);
            else
                Output.viewTable(Main.listedProperties, firstProperty, lastProperty, 0);
            Output.printOptions(new String[]{"Back to main menu.", "Show the next page.",
                    "Show the previous page.", "Show the full list at once."});

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
                case '1': firstProperty += 10; lastProperty = firstProperty + 10; continue;
                case '2': if (firstProperty != 1) {
                    firstProperty -= 10; lastProperty = firstProperty + 10; continue;
                }
                case '3': showFull = true; break;
            }
        }
    }

    public void listMyProperties() {
        Main.Status code = Main.Status.OK;

        int firstProperty = 0, lastProperty = 10;
        final int numOfPages = (int) (Math.ceil((double) propertiesOwned.size() / 10.0));
        boolean showFull = false;
        if (propertiesOwned.isEmpty()) {
            Output.printMessage("You currently have no properties for sale.");
            Main.userInput();
        }
        else while (true) {
            Output.clear();
            int currentPage = (int) (Math.ceil((double) lastProperty / 10.0));

            if (lastProperty > propertiesOwned.size())
                lastProperty = propertiesOwned.size();
            if (currentPage > numOfPages) {
                firstProperty -= 10; lastProperty = firstProperty + 10;
                code = Main.Status.OK;
                continue;
            }
            if (firstProperty < 0) {
                firstProperty = 0;
                lastProperty = firstProperty + 10;
            }

            Output.print("\n [ Page " + currentPage + "/" + numOfPages + " ] [ Showing 10 properties at a time. ]\n");
            if (showFull)
                Output.viewTable(propertiesOwned, firstProperty, propertiesOwned.size(), 0);
            else
                Output.viewTable(propertiesOwned, firstProperty, lastProperty, 0);
            Output.printOptions(new String[]{"Back to main menu.", "Show the next page.",
                    "Show the previous page.", "Show the full list at once."});

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
                case '1': firstProperty += 10; lastProperty = firstProperty + 10; continue;
                case '2': if (firstProperty != 1) {
                    firstProperty -= 10; lastProperty = firstProperty + 10; continue;
                }
                case '3': showFull = true; break;
            }
        }
    }

    public void purchaseProperty(int ownerId, Property property) {
        for (Property prop : Main.listedProperties) {
            if (prop.equals(property)) {
                ((Seller) Main.registeredUsers.get(ownerId)).removeProperty(property);
                propertiesOwned.add(property);
            }
        }
    }


    public Property searchProperties(double minValue, double maxValue) {

        Property property = null;
        for (Property prop : propertiesOwned) {
            if (prop.getValue() >= minValue && prop.getValue() <= maxValue) {
                property = prop;
                break;
            }
        }
        return property;
    }
}