import java.util.ArrayList;

class Seller extends User {
    private int numberOfSeller;
    private ArrayList<Property> propertiesOwned = new ArrayList<>();

    public Seller(String username, String password) {
        super(username, password);
//        propertiesOwned = new Property[0];
        numberOfSeller++;
    }



    public void addProperty(Property property) {
        propertiesOwned.add(property);
    }

    public void removeProperty(Property property) { propertiesOwned.remove(property); }
    public void viewMyListings() {
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

//    public Property[] getPropertiesOwned() {
//        return propertiesOwned;
//    }
}