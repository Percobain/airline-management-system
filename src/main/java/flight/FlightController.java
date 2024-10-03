package src.main.java.flight;

import java.time.LocalDateTime;

public class FlightController {
    private FlightSchedule flightSchedule;

    // Constructor
    public FlightController() {
        flightSchedule = new FlightSchedule();
    }

    // Method to add a new flight
    public void addNewFlight(String flightNumber, String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, String status) {
        Flight newFlight = new Flight(flightNumber, origin, destination, departureTime, arrivalTime, status);
        flightSchedule.addFlight(newFlight);
        System.out.println("Flight added successfully: " + newFlight);
    }

    // Method to remove a flight
    public void removeFlight(String flightNumber) {
        Flight flight = flightSchedule.getFlight(flightNumber);
        if (flight != null) {
            flightSchedule.removeFlight(flightNumber);
            System.out.println("Flight removed: " + flight);
        } else {
            System.out.println("Flight with flight number " + flightNumber + " not found.");
        }
    }

    // Method to update an existing flight
    public void updateFlight(String flightNumber, String newOrigin, String newDestination, LocalDateTime newDepartureTime, LocalDateTime newArrivalTime, String newStatus) {
        Flight updatedFlight = new Flight(flightNumber, newOrigin, newDestination, newDepartureTime, newArrivalTime, newStatus);
        flightSchedule.updateFlight(flightNumber, updatedFlight);
        System.out.println("Flight updated: " + updatedFlight);
    }

    // Method to view all flights
    public void viewAllFlights() {
        System.out.println("Flight Schedule:");
        for (Flight flight : flightSchedule.getAllFlights()) {
            System.out.println(flight);
        }
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }
}