package src.main.java.main;


import src.main.java.admin.AdminController;
import src.main.java.flight.FlightController;
import src.main.java.passenger.Passenger;
import src.main.java.passenger.PassengerController;
import src.main.java.user.UserController;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static AdminController adminController;
    private static PassengerController passengerController;
    private static FlightController flightController;
    private static UserController userController;

    public static void main(String[] args) {
        // Initialize controllers
        flightController = new FlightController();
        adminController = new AdminController(flightController);
        passengerController = new PassengerController(flightController);
        userController = new UserController(flightController);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Airline Management System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Passenger Portal");
            System.out.println("3. View Flight Schedule (Without Registration)");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline

            switch (choice) {
                case 1:
                    adminLogin(scanner);
                    break;
                case 2:
                    passengerMenu(scanner);
                    break;
                case 3:
                    userController.viewFlightSchedule();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }

    // Admin Login and Actions
    private static void adminLogin(Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (adminController.login(username, password)) {
            System.out.println("Admin login successful.");
            adminActions(scanner);
        } else {
            System.out.println("Invalid Admin credentials.");
        }
    }

    // Admin actions like adding/removing flights
    private static void adminActions(Scanner scanner) {
        boolean adminLoggedIn = true;

        while (adminLoggedIn) {
            System.out.println("\n===== Admin Actions =====");
            System.out.println("1. Add New Flight");
            System.out.println("2. Remove Flight");
            System.out.println("3. Update Flight");
            System.out.println("4. View All Flights");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline

            switch (choice) {
                case 1:
                    addNewFlight(scanner);
                    break;
                case 2:
                    removeFlight(scanner);
                    break;
                case 3:
                    updateFlight(scanner);
                    break;
                case 4:
                    flightController.viewAllFlights();
                    break;
                case 5:
                    adminLoggedIn = false;
                    System.out.println("Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Admin function to add new flight
    private static void addNewFlight(Scanner scanner) {
        System.out.print("Enter Flight Number: ");
        String flightNumber = scanner.nextLine();
        System.out.print("Enter Origin: ");
        String origin = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter Departure Time (YYYY-MM-DDTHH:MM): ");
        LocalDateTime departureTime = LocalDateTime.parse(scanner.nextLine());
        System.out.print("Enter Arrival Time (YYYY-MM-DDTHH:MM): ");
        LocalDateTime arrivalTime = LocalDateTime.parse(scanner.nextLine());
        System.out.print("Enter Status: ");
        String status = scanner.nextLine();

        adminController.addFlight(flightNumber, origin, destination, departureTime, arrivalTime, status);
    }

    // Admin function to remove flight
    private static void removeFlight(Scanner scanner) {
        System.out.print("Enter Flight Number to Remove: ");
        String flightNumber = scanner.nextLine();
        adminController.removeFlight(flightNumber);
    }

    // Admin function to update flight
    private static void updateFlight(Scanner scanner) {
        System.out.print("Enter Flight Number to Update: ");
        String flightNumber = scanner.nextLine();
        System.out.print("Enter New Origin: ");
        String newOrigin = scanner.nextLine();
        System.out.print("Enter New Destination: ");
        String newDestination = scanner.nextLine();
        System.out.print("Enter New Departure Time (YYYY-MM-DDTHH:MM): ");
        LocalDateTime newDepartureTime = LocalDateTime.parse(scanner.nextLine());
        System.out.print("Enter New Arrival Time (YYYY-MM-DDTHH:MM): ");
        LocalDateTime newArrivalTime = LocalDateTime.parse(scanner.nextLine());
        System.out.print("Enter New Status: ");
        String newStatus = scanner.nextLine();

        adminController.updateFlight(flightNumber, newOrigin, newDestination, newDepartureTime, newArrivalTime, newStatus);
    }

    // Passenger Signup/Login and Actions
    private static void passengerMenu(Scanner scanner) {
        System.out.println("\n===== Passenger Menu =====");
        System.out.println("1. Signup");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the newline

        switch (choice) {
            case 1:
                passengerSignup(scanner);
                break;
            case 2:
                passengerLogin(scanner);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private static void passengerSignup(Scanner scanner) {
        System.out.print("Enter Passenger ID: ");
        String passengerId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Do you have a Visa? (true/false): ");
        boolean hasVisa = scanner.nextBoolean();
        scanner.nextLine();  // consume the newline

        passengerController.signup(passengerId, name, email, password, phoneNumber, hasVisa);
        System.out.println("Passenger signup successful.");
    }

    private static void passengerLogin(Scanner scanner) {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
    
        Passenger passenger = passengerController.login(email, password);
        if (passenger != null) {
            System.out.println("Passenger login successful.");
            passengerActions(scanner, passenger);  // Pass the logged-in passenger to passengerAction
        } else {
            System.out.println("Invalid Email or Password.");
        }
    }

    // // Passenger actions
    private static void passengerActions(Scanner scanner, Passenger passenger) {
        boolean passengerLoggedIn = true;
    
        while (passengerLoggedIn) {
            System.out.println("\n===== Passenger Actions =====");
            System.out.println("1. Book a Flight");
            System.out.println("2. View All Flights");
            System.out.println("3. View My Bookings");
            System.out.println("4. Update Profile");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Confirm Booking");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline
    
            switch (choice) {
                case 1:
                    bookFlight(scanner, passenger);  // Book a flight
                    break;
                case 2:
                    flightController.viewAllFlights();  // View all available flights
                    break;
                case 3:
                    passengerController.viewPassengerBookings(passenger);  // View passenger's bookings
                    break;
                case 4:
                    updateProfile(scanner, passenger);  // Update passenger's profile
                    break;
                case 5:
                    cancelBooking(scanner);  // Cancel a booking
                    break;
                case 6:
                    confirmBooking(scanner);  // Confirm a booking
                    break;
                case 7:
                    passengerLoggedIn = false;
                    System.out.println("Passenger logged out.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
    
    private static void bookFlight(Scanner scanner, Passenger passenger) {
        System.out.print("Enter Flight Number to Book: ");
        String flightNumber = scanner.nextLine();
    
        // Call passengerController to book a flight for the logged-in passenger
        System.out.print("Enter Booking ID: ");
        String bookingId = scanner.nextLine();
    
        passengerController.bookFlight(bookingId, passenger, flightNumber);
    }

    private static void updateProfile(Scanner scanner, Passenger passenger) {
        System.out.print("Enter New Name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter New Phone Number: ");
        String newPhoneNumber = scanner.nextLine();
        System.out.print("Do you have a Visa? (true/false): ");
        boolean newHasVisa = scanner.nextBoolean();
        scanner.nextLine();  // consume the newline
    
        passengerController.updateProfile(passenger, newName, newPhoneNumber, newHasVisa);
    }

    private static void cancelBooking(Scanner scanner) {
        System.out.print("Enter Booking ID to Cancel: ");
        String bookingId = scanner.nextLine();
        passengerController.cancelBooking(bookingId);
    }

    private static void confirmBooking(Scanner scanner) {
        System.out.print("Enter Booking ID to Confirm: ");
        String bookingId = scanner.nextLine();
        passengerController.confirmBooking(bookingId);
    }    
}
