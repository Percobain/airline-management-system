package src.main.java.admin;

import src.main.java.flight.FlightController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private List<Admin> admins;
    private FlightController flightController; // Admin works with FlightController for managing flights

    public AdminController(FlightController flightController) {
        this.admins = new ArrayList<>();
        this.flightController = flightController;
        
        // Adding a default admin for testing
        admins.add(new Admin("admin", "admin321"));
    }

    // Admin login logic
    public boolean login(String username, String password) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    // Add a new flight
    public void addFlight(String flightNumber, String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, String status) {
        flightController.addNewFlight(flightNumber, origin, destination, departureTime, arrivalTime, status); // Delegate the addition of flight to the FlightController
        System.out.println("Flight added successfully: " + flightNumber);
    }

    // Remove a flight based on flight number
    public void removeFlight(String flightNumber) {
        flightController.removeFlight(flightNumber); // Delegate removal to FlightController
    }

    // Update flight details
    public void updateFlight(String flightNumber, String newOrigin, String newDestination, LocalDateTime newDepartureTime, LocalDateTime newArrivalTime, String newStatus) {
        flightController.updateFlight(flightNumber, newOrigin, newDestination, newDepartureTime, newArrivalTime, newStatus); // Delegate update to FlightController
        System.out.println("Flight updated successfully: " + flightNumber);
    }

    // View all flights in the schedule
    public void viewFlightSchedule() {
        flightController.viewAllFlights(); // Delegate viewing flights to FlightController
    }
}
