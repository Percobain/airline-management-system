package src.main.java.user;

import src.main.java.flight.FlightController;

public class UserController {
    private FlightController flightController;

    // Constructor takes a FlightController to access flight schedule
    public UserController(FlightController flightController) {
        this.flightController = flightController;
    }

    // Method for anonymous users to view all flights
    public void viewFlightSchedule() {
        System.out.println("Flight Schedule:");
        flightController.viewAllFlights();
    }
}
