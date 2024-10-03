package src.main.java.flight;

import java.util.ArrayList;
import java.util.List;

public class FlightSchedule {
    private List<Flight> flights;

    public FlightSchedule() {
        this.flights = new ArrayList<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void removeFlight(String flightNumber) {
        flights.removeIf(flight -> flight.getFlightNumber().equals(flightNumber));
    }

    public void updateFlight(String flightNumber, Flight updatedFlight) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNumber().equals(flightNumber)) {
                flights.set(i, updatedFlight);
                break;
            }
        }
    }

    public Flight getFlight(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    public List<Flight> getAllFlights() {
        return flights;
    }
}
