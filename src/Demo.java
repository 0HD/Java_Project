public class Demo {
    public static void loadDemoDatabase() {
        Main.registeredUsers.add(new Admin("real_admin", "admin12345"));
        Main.registeredUsers.add(new Seller("janed", "iamjane555"));
        Main.registeredUsers.add(new Buyer("mohammadali_", "theboxer0_"));
        Output.viewUsers(Main.registeredUsers, 0, Main.registeredUsers.size(), 0);
    }
}
