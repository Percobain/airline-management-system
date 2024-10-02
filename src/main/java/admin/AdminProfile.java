package src.main.java.admin;

import javax.swing.*;

public class AdminProfile {
    private Admin admin;

    public AdminProfile(Admin admin) {
        this.admin = admin;
    }

    public void displayProfile() {
        JOptionPane.showMessageDialog(null, "Admin Profile\nUsername: " + admin.getUsername());
    }

    // Additional Methods
}
