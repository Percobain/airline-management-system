package src.main.java.passenger;

import src.main.java.flight.Flight;
import src.main.java.flight.FlightController;

import java.util.ArrayList;
import java.util.List;

public class PassengerController {
    private List<Passenger> passengers;
    private List<Booking> bookings;
    private FlightController flightController;  // Passenger works with FlightController for flight information

    // Constructor
    public PassengerController(FlightController flightController) {
        this.passengers = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.flightController = flightController;
    }

    // Method for passenger signup
    public void signup(String passengerId, String name, String email, String password, String phoneNumber, boolean hasVisa) {
        Passenger newPassenger = new Passenger(passengerId, name, email, password, phoneNumber, hasVisa);
        passengers.add(newPassenger);
        System.out.println("Passenger signed up successfully: " + newPassenger);
    }

    // Method for passenger login (simplified for this example)
    public Passenger login(String email, String password) {
        for (Passenger passenger : passengers) {
            if (passenger.getEmail().equals(email) && passenger.getPassword().equals(password)) {
                System.out.println("Passenger logged in: " + passenger);
                return passenger;
            }
        }
        System.out.println("Invalid email or password.");
        return null;
    }
    

    // Method for booking a flight
    public void bookFlight(String bookingId, Passenger passenger, String flightNumber) {
        Flight flight = flightController.getFlightSchedule().getFlight(flightNumber);  // Retrieve flight from FlightController
        if (flight == null) {
            System.out.println("Flight with number " + flightNumber + " not found.");
            return;
        }

        // Prevent booking international flight if passenger does not have a visa
        if (flight.getDestination().equalsIgnoreCase("International") && !passenger.hasVisa()) {
            System.out.println("Cannot book international flight. Passenger does not have a visa.");
        } else {
            Booking newBooking = new Booking(bookingId, flight, passenger, flight.getDepartureTime(), false);
            bookings.add(newBooking);
            System.out.println("Flight booked successfully: " + newBooking);
        }
    }

    // Method for updating passenger profile
    public void updateProfile(Passenger passenger, String newName, String newPhoneNumber, boolean newHasVisa) {
        passenger.setName(newName);
        passenger.setPhoneNumber(newPhoneNumber);
        passenger.setHasVisa(newHasVisa);
        System.out.println("Profile updated successfully: " + passenger);
    }

    // Method to view all bookings of a passenger
    public void viewPassengerBookings(Passenger passenger) {
        System.out.println("Bookings for passenger " + passenger.getName());
        for (Booking booking : bookings) {
            if (booking.getPassenger().getPassengerId().equals(passenger.getPassengerId())) {
                System.out.println(booking);
            }
        }
    }

    // Method to cancel a booking
    public void cancelBooking(String bookingId) {
        Booking bookingToRemove = null;
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                bookingToRemove = booking;
                break;
            }
        }
        if (bookingToRemove != null) {
            bookings.remove(bookingToRemove);
            System.out.println("Booking canceled: " + bookingToRemove);
        } else {
            System.out.println("Booking with ID " + bookingId + " not found.");
        }
    }

    // Method to confirm a booking
    public void confirmBooking(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                booking.confirmBooking();
                System.out.println("Booking confirmed: " + booking);
                return;
            }
        }
        System.out.println("Booking with ID " + bookingId + " not found.");
    }

    // Method to get all passengers
    public List<Passenger> getAllPassengers() {
        return passengers;
    }

    // Method to get all bookings
    public List<Booking> getAllBookings() {
        return bookings;
    }
}
