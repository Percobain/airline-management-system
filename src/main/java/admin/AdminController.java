package src.main.java.admin;

import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private List<Admin> admins;

    public AdminController() {
        this.admins = new ArrayList<>();
        
        // Adding a default admin for testing
        admins.add(new Admin("admin", "admin321"));
    }

    // Admin Logic
    public boolean login(String username, String password) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    public void addFlight() {

    }

    public void removeFlight() {

    }

    public void updateFlight() {

    }

    public void removePassengerBooking() {
        
    }

    public void manageProfile() {

    }

    public void managePassenger() {

    }
}
