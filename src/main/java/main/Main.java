package src.main.java.main;

import src.main.java.admin.AdminController;
import src.main.java.flight.Flight;
import src.main.java.flight.FlightController;
import src.main.java.passenger.Passenger;
import src.main.java.passenger.PassengerController;
// import src.main.java.user.UserController;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // if you're using DateTimeFormatter
import java.time.format.DateTimeParseException;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Main extends JFrame {
    private AdminController adminController;
    private PassengerController passengerController;
    private FlightController flightController;
    // private UserController userController;

    private JTabbedPane tabbedPane;
    private JPanel adminPanel, passengerPanel, flightSchedulePanel;

    public Main() {
        // Initialize controllers
        flightController = new FlightController();
        adminController = new AdminController(flightController);
        passengerController = new PassengerController(flightController);
        // userController = new UserController(flightController);

        setTitle("Airline Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.LIGHT_GRAY);
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
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.setBorder(new EmptyBorder(5, 10, 25, 400));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(50, 40));

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        JTextArea outputArea = new JTextArea();
        outputArea.setBorder(new EmptyBorder(25, 30, 15, 15));
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
        JPanel dialogPanel = new JPanel();
        dialogPanel.setBorder(new EmptyBorder(20, 20, 0, 20)); // Set border on the panel
        dialogPanel.setLayout(new GridLayout(5, 1));

        JButton addFlightButton = new JButton("Add New Flight");
        addFlightButton.setPreferredSize(new Dimension(50, 40));
        JButton removeFlightButton = new JButton("Remove Flight");
        JButton updateFlightButton = new JButton("Update Flight Schedule");
        JButton viewFlightsButton = new JButton("View All Flights");

        addFlightButton.addActionListener(e -> showAddFlightDialog());
        removeFlightButton.addActionListener(e -> showRemoveFlightDialog());
        updateFlightButton.addActionListener(e -> showUpdateFlightDialog());
        viewFlightsButton.addActionListener(e -> showAllFlights());

        dialogPanel.add(addFlightButton);
        dialogPanel.add(removeFlightButton);
        dialogPanel.add(updateFlightButton);
        dialogPanel.add(viewFlightsButton);

        dialog.add(dialogPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showAddFlightDialog() {
        JDialog dialog = new JDialog(this, "Add New Flight", true);

        // Create an outer panel with border
        JPanel outerPanel = new JPanel();
        outerPanel.setBorder(new EmptyBorder(20, 20, 10, 20)); // Set border for the outer panel
        outerPanel.setLayout(new BorderLayout());

        // Create the inner dialog panel with GridLayout
        JPanel dialogPanel = new JPanel(new GridLayout(7, 2));

        // Create your JTextFields and JLabels
        JTextField flightNumberField = new JTextField();
        flightNumberField.setPreferredSize(new Dimension(50, 40));
        JTextField originField = new JTextField();
        JTextField destinationField = new JTextField();
        JTextField departureTimeField = new JTextField();
        JTextField arrivalTimeField = new JTextField();
        JTextField statusField = new JTextField();

        // Add labels and fields to the dialogPanel
        dialogPanel.add(new JLabel("Flight Number:"));
        dialogPanel.add(flightNumberField);
        dialogPanel.add(new JLabel("Origin:"));
        dialogPanel.add(originField);
        dialogPanel.add(new JLabel("Destination:"));
        dialogPanel.add(destinationField);
        dialogPanel.add(new JLabel("Departure Time (YYYY-MM-DDTHH:MM):"));
        dialogPanel.add(departureTimeField);
        dialogPanel.add(new JLabel("Arrival Time (YYYY-MM-DDTHH:MM):"));
        dialogPanel.add(arrivalTimeField);
        dialogPanel.add(new JLabel("Status:"));
        dialogPanel.add(statusField);

        // Create a panel for the button and set it to FlowLayout centered
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Flight");
        addButton.setPreferredSize(new Dimension(200, 40));

        // Add action listener for the button
        addButton.addActionListener(e -> {
            String flightNumber = flightNumberField.getText();
            String origin = originField.getText();
            String destination = destinationField.getText();
            String status = statusField.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            // Input validation
            if (flightNumber.isEmpty() || origin.isEmpty() || destination.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    LocalDateTime departureTime = LocalDateTime.parse(departureTimeField.getText(), formatter);
                    LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeField.getText(), formatter);

                    // If parsing is successful, proceed with adding the flight
                    adminController.addFlight(flightNumber, origin, destination, departureTime, arrivalTime, status);
                    JOptionPane.showMessageDialog(dialog, "Flight added successfully.");
                    dialog.dispose();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Time format not correct. Use YYYY-MM-DDTHH:MM.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(addButton); // Add button to buttonPanel

        // Add the dialogPanel and buttonPanel to the outerPanel
        outerPanel.add(dialogPanel, BorderLayout.CENTER); // Add inner panel to the outer panel
        outerPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom of the outer panel

        dialog.add(outerPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center the dialog
        dialog.setVisible(true);
    }

    private void showRemoveFlightDialog() {
        // Create a dialog for removing a flight
        JDialog dialog = new JDialog(this, "Remove Flight", true);

        // Create a panel for the dialog content
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Set border for the outer panel
        panel.add(new JLabel("Enter Flight Number to Remove:"));
        JTextField flightNumberField = new JTextField();
        flightNumberField.setPreferredSize(new Dimension(20, 40));
        panel.add(flightNumberField);

        // Add the panel to the dialog
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center the dialog relative to the main frame

        // Create a button for confirming the removal
        JButton removeButton = new JButton("Remove Flight");
        removeButton.addActionListener(e -> {
            String flightNumber = flightNumberField.getText();
            if (flightNumber != null && !flightNumber.isEmpty()) {
                adminController.removeFlight(flightNumber);
                JOptionPane.showMessageDialog(dialog, "Flight removed successfully.");
                dialog.dispose(); // Close the dialog after removing the flight
            } else {
                JOptionPane.showMessageDialog(dialog, "Flight number cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add the button to the panel
        panel.add(removeButton);

        // Show the dialog
        dialog.setVisible(true);
    }

    private void updateFlight(String flightNumber, String origin, String destination, LocalDateTime departureTime,
            LocalDateTime arrivalTime, String status) {
        adminController.updateFlight(flightNumber, origin, destination, departureTime, arrivalTime, status);
    }

    private void showUpdateFlightDialog() {
        String flightNumber = JOptionPane.showInputDialog(this, "Enter flight number to update:");
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            return;
        }

        Flight flight = flightController.getFlightByNumber(flightNumber);
        if (flight == null) {
            JOptionPane.showMessageDialog(this, "Flight not found.");
            return;
        }

        JTextField originField = new JTextField(flight.getOrigin());
        originField.setPreferredSize(new Dimension(200, 40));
        JTextField destinationField = new JTextField(flight.getDestination());
        JTextField departureTimeField = new JTextField(flight.getDepartureTime().toString());
        JTextField arrivalTimeField = new JTextField(flight.getArrivalTime().toString());
        JTextField statusField = new JTextField(flight.getStatus());

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Set border for the outer panel
        panel.add(new JLabel("Origin:"));
        panel.add(originField);
        panel.add(new JLabel("Destination:"));
        panel.add(destinationField);
        panel.add(new JLabel("Departure Time (yyyy-MM-dd'T'HH:mm):"));
        panel.add(departureTimeField);
        panel.add(new JLabel("Arrival Time (yyyy-MM-dd'T'HH:mm):"));
        panel.add(arrivalTimeField);
        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        // Use this as the parent component to center the dialog
        int result = JOptionPane.showConfirmDialog(this, panel, "Update Flight", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String newOrigin = originField.getText();
                String newDestination = destinationField.getText();
                LocalDateTime newDepartureTime = LocalDateTime.parse(departureTimeField.getText(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                LocalDateTime newArrivalTime = LocalDateTime.parse(arrivalTimeField.getText(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                String newStatus = statusField.getText();

                // Call the local updateFlight method
                updateFlight(flightNumber, newOrigin, newDestination, newDepartureTime, newArrivalTime, newStatus);
                JOptionPane.showMessageDialog(this, "Flight updated successfully.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Time format not correct. Use YYYY-MM-DDTHH:MM.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating flight: " + e.getMessage());
            }
        }
    }

    private void refreshFlightSchedule(DefaultTableModel model) {
        // Clear existing rows
        model.setRowCount(0);

        // Get all flights from the FlightController
        List<Flight> flights = flightController.getAllFlights();

        // Populate the table with flight data
        for (Flight flight : flights) {
            Object[] row = {
                    flight.getFlightNumber(),
                    flight.getOrigin(),
                    flight.getDestination(),
                    flight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")),
                    flight.getArrivalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")),
                    flight.getStatus()
            };
            model.addRow(row);
        }
    }

    private void showAllFlights() {
        JTextArea flightList = new JTextArea();
        flightList.setEditable(false);

        // Get flight details as a string from the flightController
        String flightsInfo = flightController.viewAllFlights();

        // Set the text of flightList to the flightsInfo
        flightList.setText(flightsInfo);

        JOptionPane.showMessageDialog(this, new JScrollPane(flightList), "All Flights",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createPassengerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JTabbedPane passengerTabs = new JTabbedPane();

        passengerTabs.addTab("Login", createPassengerLoginPanel());
        passengerTabs.addTab("Signup", createPassengerSignupPanel());

        panel.add(passengerTabs, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPassengerLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(new EmptyBorder(20, 20, 300, 400));
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(150, 25)); // Set preferred size of the email field
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
        panel.setBorder(new EmptyBorder(20, 20, 100, 300));
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
            String passengerId = passengerIdField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String phoneNumber = phoneNumberField.getText().trim();
            boolean hasVisa = hasVisaCheckBox.isSelected();

            // Input validation
            if (passengerId.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()
                    || phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "All fields are required for signup.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (!email.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(panel, "Email must be a valid @gmail.com address.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (!phoneNumber.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(panel, "Phone number must contain exactly 10 digits.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // If all validations pass, proceed with signup
                passengerController.signup(passengerId, name, email, password, phoneNumber, hasVisa);
                JOptionPane.showMessageDialog(panel, "Passenger signup successful.");
            }
        });

        return panel;
    }

    private void showPassengerActionsDialog(Passenger passenger) {
        JDialog dialog = new JDialog(this, "Passenger Actions", true);
        JPanel dialogPanel = new JPanel();
        dialogPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Set border on the panel
        dialogPanel.setLayout(new GridLayout(5, 1));

        JButton bookFlightButton = new JButton("Book a Flight");
        bookFlightButton.setPreferredSize(new Dimension(50, 40));
        JButton viewBookingsButton = new JButton("View My Bookings");
        JButton updateProfileButton = new JButton("Update Profile");
        JButton cancelBookingButton = new JButton("Cancel Booking");
        JButton confirmBookingButton = new JButton("Confirm Booking");

        bookFlightButton.addActionListener(e -> showBookFlightDialog(passenger));
        viewBookingsButton.addActionListener(e -> showPassengerBookings(passenger));
        updateProfileButton.addActionListener(e -> showUpdateProfileDialog(passenger));
        cancelBookingButton.addActionListener(e -> showCancelBookingDialog());
        confirmBookingButton.addActionListener(e -> showConfirmBookingDialog());

        dialogPanel.add(bookFlightButton);
        dialogPanel.add(viewBookingsButton);
        dialogPanel.add(updateProfileButton);
        dialogPanel.add(cancelBookingButton);
        dialogPanel.add(confirmBookingButton);

        dialog.add(dialogPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showBookFlightDialog(Passenger passenger) {
        JDialog dialog = new JDialog(this, "Book a Flight", true);
        JPanel dialogPanel = new JPanel();
        dialogPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Set border on the panel
        dialogPanel.setLayout(new GridLayout(3, 2));

        JTextField flightNumberField = new JTextField();
        flightNumberField.setPreferredSize(new Dimension(50, 40));
        JTextField bookingIdField = new JTextField();
        JButton bookButton = new JButton("Book Flight");

        dialogPanel.add(new JLabel("Flight Number:"));
        dialogPanel.add(flightNumberField);
        dialogPanel.add(new JLabel("Booking ID:"));
        dialogPanel.add(bookingIdField);
        dialogPanel.add(new JLabel());
        dialogPanel.add(bookButton);

        bookButton.addActionListener(e -> {
            String flightNumber = flightNumberField.getText();
            String bookingId = bookingIdField.getText();
            Flight flight = flightController.getFlightByNumber(flightNumber);
            if (flight == null) {
                JOptionPane.showMessageDialog(dialog, "Flight number not found. Please enter a valid flight number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean success = passengerController.bookFlight(bookingId, passenger, flightNumber);
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Flight booked successfully.");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to book flight. Please check your details.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.add(dialogPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showPassengerBookings(Passenger passenger) {
        JTextArea bookingsList = new JTextArea();
        bookingsList.setEditable(false);
        String bookingInfo = passengerController.viewPassengerBookings(passenger); // Modify this method to return a
        // bookingsList.setText(passengerController.viewPassengerBookings(passenger));

        bookingsList.setText(bookingInfo);
        JOptionPane.showMessageDialog(this, new JScrollPane(bookingsList), "My Bookings",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showUpdateProfileDialog(Passenger passenger) {
        JDialog dialog = new JDialog(this, "Update Profile", true);
        JPanel dialogPanel = new JPanel();
        dialogPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        dialogPanel.setLayout(new GridLayout(4, 2));

        JTextField nameField = new JTextField(passenger.getName());
        nameField.setPreferredSize(new Dimension(50, 40));
        JTextField phoneNumberField = new JTextField(passenger.getPhoneNumber());
        JCheckBox hasVisaCheckBox = new JCheckBox("", passenger.hasVisa());
        JButton updateButton = new JButton("Update Profile");

        dialogPanel.add(new JLabel("New Name:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("New Phone Number:"));
        dialogPanel.add(phoneNumberField);
        dialogPanel.add(new JLabel("Has Visa:"));
        dialogPanel.add(hasVisaCheckBox);
        dialogPanel.add(new JLabel());
        dialogPanel.add(updateButton);

        updateButton.addActionListener(e -> {
            String newName = nameField.getText();
            String newPhoneNumber = phoneNumberField.getText();
            boolean newHasVisa = hasVisaCheckBox.isSelected();

            passengerController.updateProfile(passenger, newName, newPhoneNumber, newHasVisa);
            dialog.dispose();
        });

        dialog.add(dialogPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showCancelBookingDialog() {
        String bookingId = JOptionPane.showInputDialog(this, "Enter Booking ID to Cancel:");
        if (bookingId != null && !bookingId.isEmpty()) {
            boolean success = passengerController.cancelBooking(bookingId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking canceled successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Booking ID not found. Please enter a valid ID.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showConfirmBookingDialog() {
        String bookingId = JOptionPane.showInputDialog(this, "Enter Booking ID to Confirm:");
        if (bookingId != null && !bookingId.isEmpty()) {
            boolean success = passengerController.confirmBooking(bookingId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking confirmed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Booking ID not found. Please enter a valid ID.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createFlightSchedulePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create a table model with column names
        String[] columnNames = { "Flight Number", "Origin", "Destination", "Departure Time", "Arrival Time", "Status" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Create a JTable with the model
        JTable flightTable = new JTable(model);
        flightTable.setRowHeight(30); // Increase the row height (default is usually around 16-20)

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(flightTable);

        // Create a refresh button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton refreshButton = new JButton("Refresh Schedule");
        refreshButton.setPreferredSize(new Dimension(120, 30));
        refreshButton.addActionListener(e -> refreshFlightSchedule(model));

        // Add components to the panel
        buttonPanel.add(refreshButton);
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