package src.main.java.passenger;

import src.main.java.flight.Flight;
// import src.main.java.passenger.Passenger;
import java.time.LocalDateTime;

public class Booking {
    private String bookingId;
    private Flight flight;
    private Passenger passenger;
    private LocalDateTime bookingTime;
    private boolean confirmed;

    public Booking(String bookingId, Flight flight, Passenger passenger, LocalDateTime bookingTime, boolean confirmed) {
        this.bookingId = bookingId;
        this.flight = flight;
        this.passenger = passenger;
        this.bookingTime = bookingTime; // Automatically sets the booking time to the current time
        this.confirmed = confirmed; // Starts as unconfirmed
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void confirmBooking() {
        this.confirmed = true;
    }

    @Override
    public String toString() {
        return "Booking [Booking ID=" + bookingId + ", Flight=" + flight.getFlightNumber() +
               ", Passenger=" + passenger.getName() + ", Booking Time=" + bookingTime +
               ", Confirmed=" + (confirmed ? "Yes" : "No") + "]";
    }
}
