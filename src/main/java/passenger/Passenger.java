package src.main.java.passenger;

public class Passenger {
    private String passengerId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean hasVisa;

    // Constructor
    public Passenger(String passengerId, String name, String email, String password, String phoneNumber, boolean hasVisa) {
        this.passengerId = passengerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.hasVisa = hasVisa;
    }

    // Getters and Setters
    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean hasVisa() {
        return hasVisa;
    }

    public void setHasVisa(boolean hasVisa) {
        this.hasVisa = hasVisa;
    }

    @Override
    public String toString() {
        return "Passenger [ID=" + passengerId + ", Name=" + name + ", Email=" + email + 
               ", Phone=" + phoneNumber + ", Has Visa=" + (hasVisa ? "Yes" : "No") + "]";
    }
}