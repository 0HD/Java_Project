abstract class User {
    private int userId;
    private String fullName;
    private String username;
    private String password;
    private static int numberOfUsers;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        userId = numberOfUsers;
        numberOfUsers++;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    protected void setUserId (int userId) {
        this.userId = userId;
    }

    int getId() {
        return userId;
    }

}