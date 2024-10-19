package src.main.java.main;

import src.main.java.admin.AdminController;
import src.main.java.flight.Flight;
import src.main.java.flight.FlightController;
import src.main.java.passenger.Passenger;
import src.main.java.passenger.PassengerController;
import src.main.java.user.UserController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Main extends JFrame {
    private AdminController adminController;
    private PassengerController passengerController;
    private FlightController flightController;
    private UserController userController;

    private JTabbedPane tabbedPane;
    private JPanel adminPanel, passengerPanel, flightSchedulePanel;

    public Main() {
        // Initialize controllers
        flightController = new FlightController();
        adminController = new AdminController(flightController);
        passengerController = new PassengerController(flightController);
        userController = new UserController(flightController);

        setTitle("Airline Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        adminPanel = createAdminPanel();
        passengerPanel = createPassengerPanel();
        flightSchedulePanel = createFlightSchedulePanel();

        tabbedPane.addTab("Admin", adminPanel);
        tabbedPane.addTab("Passenger", passengerPanel);
        tabbedPane.addTab("Flight Schedule", flightSchedulePanel);

        add(tabbedPane);
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (adminController.login(username, password)) {
                outputArea.setText("Admin login successful.");
                showAdminActionsDialog();
            } else {
                outputArea.setText("Invalid Admin credentials.");
            }
        });

        panel.add(loginPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        return panel;
    }

    private void showAdminActionsDialog() {
        JDialog dialog = new JDialog(this, "Admin Actions", true);
        dialog.setLayout(new GridLayout(4, 1));

        JButton addFlightButton = new JButton("Add New Flight");
        JButton removeFlightButton = new JButton("Remove Flight");
        JButton updateFlightButton = new JButton("Update Flight");

        addFlightButton.addActionListener(e -> showAddFlightDialog());
        removeFlightButton.addActionListener(e -> showRemoveFlightDialog());
        updateFlightButton.addActionListener(e -> showUpdateFlightDialog());

        dialog.add(addFlightButton);
        dialog.add(removeFlightButton);
        dialog.add(updateFlightButton);

        dialog.pack();
        dialog.setVisible(true);
    }

    private void showAddFlightDialog() {
        JDialog dialog = new JDialog(this, "Add New Flight", true);
        dialog.setLayout(new GridLayout(7, 2));

        JTextField flightNumberField = new JTextField();
        JTextField originField = new JTextField();
        JTextField destinationField = new JTextField();
        JTextField departureTimeField = new JTextField();
        JTextField arrivalTimeField = new JTextField();
        JTextField statusField = new JTextField();

        dialog.add(new JLabel("Flight Number:"));
        dialog.add(flightNumberField);
        dialog.add(new JLabel("Origin:"));
        dialog.add(originField);
        dialog.add(new JLabel("Destination:"));
        dialog.add(destinationField);
        dialog.add(new JLabel("Departure Time (YYYY-MM-DDTHH:MM):"));
        dialog.add(departureTimeField);
        dialog.add(new JLabel("Arrival Time (YYYY-MM-DDTHH:MM):"));
        dialog.add(arrivalTimeField);
        dialog.add(new JLabel("Status:"));
        dialog.add(statusField);

        JButton addButton = new JButton("Add Flight");
        addButton.addActionListener(e -> {
            String flightNumber = flightNumberField.getText();
            String origin = originField.getText();
            String destination = destinationField.getText();
            LocalDateTime departureTime = LocalDateTime.parse(departureTimeField.getText());
            LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeField.getText());
            String status = statusField.getText();

            adminController.addFlight(flightNumber, origin, destination, departureTime, arrivalTime, status);
            dialog.dispose();
        });

        dialog.add(addButton);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void showRemoveFlightDialog() {
        String flightNumber = JOptionPane.showInputDialog(this, "Enter Flight Number to Remove:");
        if (flightNumber != null && !flightNumber.isEmpty()) {
            adminController.removeFlight(flightNumber);
        }
    }

    private void updateFlight(String flightNumber, String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, String status) {
        adminController.updateFlight(flightNumber, origin, destination, departureTime, arrivalTime, status);
    }

    private void showUpdateFlightDialog() {
        String flightNumber = JOptionPane.showInputDialog("Enter flight number to update:");
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            return;
        }

        Flight flight = flightController.getFlightByNumber(flightNumber);
        if (flight == null) {
            JOptionPane.showMessageDialog(null, "Flight not found.");
            return;
        }

        JTextField originField = new JTextField(flight.getOrigin());
        JTextField destinationField = new JTextField(flight.getDestination());
        JTextField departureTimeField = new JTextField(flight.getDepartureTime().toString());
        JTextField arrivalTimeField = new JTextField(flight.getArrivalTime().toString());
        JTextField statusField = new JTextField(flight.getStatus());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Origin:"));
        panel.add(originField);
        panel.add(new JLabel("Destination:"));
        panel.add(destinationField);
        panel.add(new JLabel("Departure Time (yyyy-MM-dd HH:mm):"));
        panel.add(departureTimeField);
        panel.add(new JLabel("Arrival Time (yyyy-MM-dd HH:mm):"));
        panel.add(arrivalTimeField);
        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Flight", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String newOrigin = originField.getText();
                String newDestination = destinationField.getText();
                LocalDateTime newDepartureTime = LocalDateTime.parse(departureTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LocalDateTime newArrivalTime = LocalDateTime.parse(arrivalTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String newStatus = statusField.getText();

                updateFlight(flightNumber, newOrigin, newDestination, newDepartureTime, newArrivalTime, newStatus);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Invalid date/time format. Please use yyyy-MM-dd HH:mm");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error updating flight: " + e.getMessage());
            }
        }
    }

    private void refreshFlightSchedule(DefaultTableModel model) {
        // Clear existing rows
        model.setRowCount(0);
        
        // Get all flights from the FlightController
        List<Flight> flights = flightController.viewAllFlights();
        
        // Populate the table with flight data
        for (Flight flight : flights) {
            Object[] row = {
                flight.getFlightNumber(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                flight.getArrivalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                flight.getStatus()
            };
            model.addRow(row);
        }
    }

    private JPanel createPassengerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTabbedPane passengerTabs = new JTabbedPane();

        passengerTabs.addTab("Login", createPassengerLoginPanel());
        passengerTabs.addTab("Signup", createPassengerSignupPanel());

        panel.add(passengerTabs, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPassengerLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            Passenger passenger = passengerController.login(email, password);
            if (passenger != null) {
                showPassengerActionsDialog(passenger);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Email or Password.");
            }
        });

        return panel;
    }

    private JPanel createPassengerSignupPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2));
        JTextField passengerIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField phoneNumberField = new JTextField();
        JCheckBox hasVisaCheckBox = new JCheckBox();
        JButton signupButton = new JButton("Signup");

        panel.add(new JLabel("Passenger ID:"));
        panel.add(passengerIdField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Has Visa:"));
        panel.add(hasVisaCheckBox);
        panel.add(new JLabel());
        panel.add(signupButton);

        signupButton.addActionListener(e -> {
            String passengerId = passengerIdField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String phoneNumber = phoneNumberField.getText();
            boolean hasVisa = hasVisaCheckBox.isSelected();

            passengerController.signup(passengerId, name, email, password, phoneNumber, hasVisa);
            JOptionPane.showMessageDialog(this, "Passenger signup successful.");
        });

        return panel;
    }

    private void showPassengerActionsDialog(Passenger passenger) {
        JDialog dialog = new JDialog(this, "Passenger Actions", true);
        dialog.setLayout(new GridLayout(6, 1));

        JButton bookFlightButton = new JButton("Book a Flight");
        JButton viewBookingsButton = new JButton("View My Bookings");
        JButton updateProfileButton = new JButton("Update Profile");
        JButton cancelBookingButton = new JButton("Cancel Booking");
        JButton confirmBookingButton = new JButton("Confirm Booking");

        bookFlightButton.addActionListener(e -> showBookFlightDialog(passenger));
        viewBookingsButton.addActionListener(e -> showPassengerBookings(passenger));
        updateProfileButton.addActionListener(e -> showUpdateProfileDialog(passenger));
        cancelBookingButton.addActionListener(e -> showCancelBookingDialog());
        confirmBookingButton.addActionListener(e -> showConfirmBookingDialog());

        dialog.add(bookFlightButton);
        dialog.add(viewBookingsButton);
        dialog.add(updateProfileButton);
        dialog.add(cancelBookingButton);
        dialog.add(confirmBookingButton);

        dialog.pack();
        dialog.setVisible(true);
    }

    private void showBookFlightDialog(Passenger passenger) {
        JDialog dialog = new JDialog(this, "Book a Flight", true);
        dialog.setLayout(new GridLayout(3, 2));

        JTextField flightNumberField = new JTextField();
        JTextField bookingIdField = new JTextField();
        JButton bookButton = new JButton("Book Flight");

        dialog.add(new JLabel("Flight Number:"));
        dialog.add(flightNumberField);
        dialog.add(new JLabel("Booking ID:"));
        dialog.add(bookingIdField);
        dialog.add(new JLabel());
        dialog.add(bookButton);

        bookButton.addActionListener(e -> {
            String flightNumber = flightNumberField.getText();
            String bookingId = bookingIdField.getText();
            passengerController.bookFlight(bookingId, passenger, flightNumber);
            dialog.dispose();
        });

        dialog.pack();
        dialog.setVisible(true);
    }

    private void showPassengerBookings(Passenger passenger) {
        JTextArea bookingsList = new JTextArea(20, 40);  // Specify rows and columns for better sizing
        bookingsList.setEditable(false);
        bookingsList.setLineWrap(true);
        bookingsList.setWrapStyleWord(true);
        
        String bookingsInfo = passengerController.viewPassengerBookings(passenger);
        bookingsList.setText(bookingsInfo);
    
        JScrollPane scrollPane = new JScrollPane(bookingsList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    
        JOptionPane.showMessageDialog(this, scrollPane, "My Bookings", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showUpdateProfileDialog(Passenger passenger) {
        JDialog dialog = new JDialog(this, "Update Profile", true);
        dialog.setLayout(new GridLayout(4, 2));

        JTextField nameField = new JTextField(passenger.getName());
        JTextField phoneNumberField = new JTextField(passenger.getPhoneNumber());
        JCheckBox hasVisaCheckBox = new JCheckBox("", passenger.hasVisa());
        JButton updateButton = new JButton("Update Profile");

        dialog.add(new JLabel("New Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("New Phone Number:"));
        dialog.add(phoneNumberField);
        dialog.add(new JLabel("Has Visa:"));
        dialog.add(hasVisaCheckBox);
        dialog.add(new JLabel());
        dialog.add(updateButton);

        updateButton.addActionListener(e -> {
            String newName = nameField.getText();
            String newPhoneNumber = phoneNumberField.getText();
            boolean newHasVisa = hasVisaCheckBox.isSelected();

            passengerController.updateProfile(passenger, newName, newPhoneNumber, newHasVisa);
            dialog.dispose();
        });

        dialog.pack();
        dialog.setVisible(true);
    }

    private void showCancelBookingDialog() {
        String bookingId = JOptionPane.showInputDialog(this, "Enter Booking ID to Cancel:");
        if (bookingId != null && !bookingId.isEmpty()) {
            passengerController.cancelBooking(bookingId);
        }
    }

    private void showConfirmBookingDialog() {
        String bookingId = JOptionPane.showInputDialog(this, "Enter Booking ID to Confirm:");
        if (bookingId != null && !bookingId.isEmpty()) {
            passengerController.confirmBooking(bookingId);
        }
    }

    private JPanel createFlightSchedulePanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        // Create a table model with column names
        String[] columnNames = {"Flight Number", "Origin", "Destination", "Departure Time", "Arrival Time", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        // Create a JTable with the model
        JTable flightTable = new JTable(model);
        
        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(flightTable);
        
        // Create a refresh button
        JButton refreshButton = new JButton("Refresh Schedule");
        refreshButton.addActionListener(e -> refreshFlightSchedule(model));
        
        // Add components to the panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);
        
        // Initial population of the table
        refreshFlightSchedule(model);
        
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main ui = new Main();
            ui.setVisible(true);
        });
    }
}