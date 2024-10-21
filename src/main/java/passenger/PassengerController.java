package src.main.java.passenger;

import src.main.java.flight.Flight;
import src.main.java.flight.FlightController;

import java.util.ArrayList;
import java.util.List;

public class PassengerController {
    private List<Passenger> passengers;
    private List<Booking> bookings;
    private FlightController flightController; // Passenger works with FlightController for flight information

    // Constructor
    public PassengerController(FlightController flightController) {
        this.passengers = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.flightController = flightController;
    }

    // Method for passenger signup with input validation
    public void signup(String passengerId, String name, String email, String password, String phoneNumber,
            boolean hasVisa) {
        // Check for null or empty fields
        if (passengerId == null || passengerId.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                phoneNumber == null || phoneNumber.trim().isEmpty()) {
            System.out.println("All fields are required for signup.");
            return;
        }

        // Validate email format
        if (!email.endsWith("@gmail.com")) {
            System.out.println("Email must be a valid @gmail.com address.");
            return;
        }

        // Validate phone number format
        if (!phoneNumber.matches("\\d{10}")) {
            System.out.println("Phone number must contain exactly 10 digits.");
            return;
        }

        // If all validations pass, create a new passenger and add to the list
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
    public boolean bookFlight(String bookingId, Passenger passenger, String flightNumber) {
        Flight flight = flightController.getFlightSchedule().getFlight(flightNumber); // Retrieve flight from

        if (flight == null) {
            System.out.println("Flight with number " + flightNumber + " not found.");
            return false; // Return false if flight is not found
        }

        // Prevent booking international flight if passenger does not have a visa
        if (flight.getDestination().equalsIgnoreCase("International") && !passenger.hasVisa()) {
            System.out.println("Cannot book international flight. Passenger does not have a visa.");
            return false; // Return false if the passenger does not have a visa
        } else {
            Booking newBooking = new Booking(bookingId, flight, passenger, flight.getDepartureTime(), false);
            bookings.add(newBooking);
            System.out.println("Flight booked successfully: " + newBooking);
            return true; // Return true if booking is successful
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
    public String viewPassengerBookings(Passenger passenger) {
        StringBuilder bookingsInfo = new StringBuilder();
        bookingsInfo.append("Bookings for passenger ").append(passenger.getName()).append(":\n\n");

        boolean bookingsExist = false; // Flag to check if there are bookings for the passenger

        // Loop through bookings and check for the current passenger
        for (Booking booking : bookings) {
            if (booking.getPassenger().getPassengerId().equals(passenger.getPassengerId())) {
                bookingsInfo.append(booking.toString()).append("\n\n");
                bookingsExist = true; // Found at least one booking
            }
        }

        // If no bookings found, append a message
        if (!bookingsExist) {
            bookingsInfo.append("No bookings found!");
        }

        // Optionally print to console for debugging
        System.out.println(bookingsInfo.toString());

        return bookingsInfo.toString(); // Return the complete bookings info
    }

    // Method to cancel a booking
    public boolean cancelBooking(String bookingId) {
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
            return true;
        } else {
            System.out.println("Booking with ID " + bookingId + " not found.");
            return false;
        }
    }

    // Method to confirm a booking
    public boolean confirmBooking(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                booking.confirmBooking();
                System.out.println("Booking confirmed: " + booking);
                return true;
            }
        }
        System.out.println("Booking with ID " + bookingId + " not found.");
        return false;
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
