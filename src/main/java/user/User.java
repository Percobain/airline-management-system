package src.main.java.user;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;

    // Constructor for anonymous users (no user ID or other details)
    public User() {
    }

    // Constructor for registered users
    public User(String userId, String name, String email, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User [ID=" + userId + ", Name=" + name + ", Email=" + email + ", Phone=" + phoneNumber + "]";
    }
}
