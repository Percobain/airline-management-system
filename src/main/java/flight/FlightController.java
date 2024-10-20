package src.main.java.flight;

import java.time.LocalDateTime;
import java.util.List;

public class FlightController {
    private FlightSchedule flightSchedule;

    // Constructor
    public FlightController() {
        flightSchedule = new FlightSchedule();
    }

    // Method to add a new flight
    public void addNewFlight(String flightNumber, String origin, String destination, LocalDateTime departureTime,
            LocalDateTime arrivalTime, String status) {
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
    public void updateFlight(String flightNumber, String newOrigin, String newDestination,
            LocalDateTime newDepartureTime, LocalDateTime newArrivalTime, String newStatus) {
        Flight updatedFlight = new Flight(flightNumber, newOrigin, newDestination, newDepartureTime, newArrivalTime,
                newStatus);
        flightSchedule.updateFlight(flightNumber, updatedFlight);
        System.out.println("Flight updated: " + updatedFlight);
    }

    public List<Flight> getAllFlights() {
        return flightSchedule.getAllFlights(); // Ensure that this method exists in FlightSchedule
    }

    // Method to view all flights
    public String viewAllFlights() {
        StringBuilder flightDetails = new StringBuilder();
        List<Flight> flights = getAllFlights(); // Assuming this method returns the list of flights.

        if (flights.isEmpty()) {
            return "No flights available.";
        }

        for (Flight flight : flights) {
            flightDetails.append("Flight Number: ").append(flight.getFlightNumber())
                    .append(", Origin: ").append(flight.getOrigin())
                    .append(", Destination: ").append(flight.getDestination())
                    .append(", Departure Time: ").append(flight.getDepartureTime())
                    .append(", Arrival Time: ").append(flight.getArrivalTime())
                    .append(", Status: ").append(flight.getStatus())
                    .append("\n");
        }

        return flightDetails.toString();
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public Flight getFlightByNumber(String flightNumber) {
        return flightSchedule.getFlight(flightNumber);
    }

}