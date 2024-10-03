package src.main.java.flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlightManagementUI extends JFrame {
    private FlightController flightController;

    // Components for the form
    private JTextField flightNumberField, originField, destinationField, departureField, arrivalField, statusField;
    private JTextArea flightListArea;

    public FlightManagementUI() {
        flightController = new FlightController();
        initComponents();
    }

    private void initComponents() {
        // Frame settings
        setTitle("Flight Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel (input fields)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 5, 5));

        JLabel flightNumberLabel = new JLabel("Flight Number:");
        flightNumberField = new JTextField(10);
        JLabel originLabel = new JLabel("Origin:");
        originField = new JTextField(10);
        JLabel destinationLabel = new JLabel("Destination:");
        destinationField = new JTextField(10);
        JLabel departureLabel = new JLabel("Departure (yyyy-MM-dd HH:mm):");
        departureField = new JTextField(10);
        JLabel arrivalLabel = new JLabel("Arrival (yyyy-MM-dd HH:mm):");
        arrivalField = new JTextField(10);
        JLabel statusLabel = new JLabel("Status:");
        statusField = new JTextField(10);

        // Add input components to the form panel
        formPanel.add(flightNumberLabel);
        formPanel.add(flightNumberField);
        formPanel.add(originLabel);
        formPanel.add(originField);
        formPanel.add(destinationLabel);
        formPanel.add(destinationField);
        formPanel.add(departureLabel);
        formPanel.add(departureField);
        formPanel.add(arrivalLabel);
        formPanel.add(arrivalField);
        formPanel.add(statusLabel);
        formPanel.add(statusField);

        // Button panel (CRUD operations)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Flight");
        JButton updateButton = new JButton("Update Flight");
        JButton removeButton = new JButton("Remove Flight");
        JButton viewAllButton = new JButton("View All Flights");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewAllButton);

        // Flight list area to display the flight schedule
        flightListArea = new JTextArea();
        flightListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(flightListArea);

        // Add formPanel, buttonPanel, and scrollPane to the frame
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFlight();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFlight();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFlight();
            }
        });

        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllFlights();
            }
        });
    }

    private void addFlight() {
        String flightNumber = flightNumberField.getText();
        String origin = originField.getText();
        String destination = destinationField.getText();
        String departure = departureField.getText();
        String arrival = arrivalField.getText();
        String status = statusField.getText();

        try {
            // Convert string input to LocalDateTime for departure and arrival
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime departureTime = LocalDateTime.parse(departure, formatter);
            LocalDateTime arrivalTime = LocalDateTime.parse(arrival, formatter);

            flightController.addNewFlight(flightNumber, origin, destination, departureTime, arrivalTime, status);
            JOptionPane.showMessageDialog(this, "Flight added successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please check your date format.");
        }
    }

    private void updateFlight() {
        String flightNumber = flightNumberField.getText();
        String origin = originField.getText();
        String destination = destinationField.getText();
        String departure = departureField.getText();
        String arrival = arrivalField.getText();
        String status = statusField.getText();

        try {
            // Convert string input to LocalDateTime for departure and arrival
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime departureTime = LocalDateTime.parse(departure, formatter);
            LocalDateTime arrivalTime = LocalDateTime.parse(arrival, formatter);

            flightController.updateFlight(flightNumber, origin, destination, departureTime, arrivalTime, status);
            JOptionPane.showMessageDialog(this, "Flight updated successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please check your date format.");
        }
    }

    private void removeFlight() {
        String flightNumber = flightNumberField.getText();
        flightController.removeFlight(flightNumber);
        JOptionPane.showMessageDialog(this, "Flight removed successfully!");
        clearFields();
    }

    private void viewAllFlights() {
        flightListArea.setText("");  // Clear the text area
        flightListArea.append("Flight Schedule:\n");
        for (Flight flight : flightController.getFlightSchedule().getAllFlights()) { // Use the new method here
            flightListArea.append(flight.toString() + "\n");
        }
    }    

    private void clearFields() {
        flightNumberField.setText("");
        originField.setText("");
        destinationField.setText("");
        departureField.setText("");
        arrivalField.setText("");
        statusField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlightManagementUI().setVisible(true);
            }
        });
    }
}
