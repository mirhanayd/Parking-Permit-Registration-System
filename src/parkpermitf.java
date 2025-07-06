import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class parkpermitf extends JDialog {
    private JPanel panel1;
    private JTextField studentID;
    private JTextField name_surname;
    private JTextField plate;
    private JComboBox parka;
    private JComboBox combosemester;
    private JTable table1;
    private JButton addbutton;
    private JComboBox delPlate;
    private JButton delbutton;

    // Add this method to populate the delPlate ComboBox
    private void populateDelPlateComboBox(String userId) {
        try (Connection connection = getConnection()) {
            String query = "SELECT plateno FROM parkpermit WHERE s_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            delPlate.addItem("SELECT PLATE");

            // Add all plates
            while (resultSet.next()) {
                delPlate.addItem(resultSet.getString("plateno"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading plates: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public parkpermitf(String userId) {
        setContentPane(panel1);
        setModal(true);
        setTitle("PPRS-Permits");

        populateStudentInfo(userId);

        populateDelPlateComboBox(userId);

        populateParkingPermitTable(userId);

        addbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addParkingPermit(userId);
                populateDelPlateComboBox(userId);
            }
        });

        delbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlate = (String) delPlate.getSelectedItem();

                if (selectedPlate == null || selectedPlate.equals("SELECT PLATE")) {
                    JOptionPane.showMessageDialog(panel1, "Please select a plate to delete");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(panel1,
                        "Are you sure you want to delete permit for plate: " + selectedPlate + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection connection = getConnection()) {
                        String query = "DELETE FROM parkpermit WHERE plateno = ? AND s_id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, selectedPlate);
                        preparedStatement.setString(2, userId);

                        int result = preparedStatement.executeUpdate();
                        if (result > 0) {
                            JOptionPane.showMessageDialog(panel1, "Permit deleted successfully");

                            populateDelPlateComboBox(userId);
                            populateParkingPermitTable(userId);

                            delPlate.setSelectedIndex(0);
                        } else {
                            JOptionPane.showMessageDialog(panel1, "Failed to delete permit");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(panel1, "Error deleting permit: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void populateStudentInfo(String userId) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                studentID.setText(resultSet.getString("id"));
                name_surname.setText(resultSet.getString("name"));
                studentID.setEditable(false);
                name_surname.setEditable(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching student information: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateParkingPermitTable(String userId) {
        try (Connection connection = getConnection()) {
            String query = "SELECT plateno, areas, semester FROM parkpermit WHERE s_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = new DefaultTableModel(

                    new String[]{"Plate No", "Park Area", "Semester"},0);

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("plateno"),
                        resultSet.getString("areas"),
                        resultSet.getString("semester")
                });
            }

            table1.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching parking permits: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addParkingPermit(String userId) {
        String plateNo = plate.getText();
        String parkArea = (String) parka.getSelectedItem();
        String semester = (String) combosemester.getSelectedItem();

        if (plateNo.isEmpty() || parkArea == null || parkArea == "SELECT" || semester == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = getConnection()) {
            String query = "INSERT INTO parkpermit (s_id, plateno, areas, semester) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, plateNo);
            preparedStatement.setString(3, parkArea);
            preparedStatement.setString(4, semester);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Parking Permit Added Successfully!");

                populateParkingPermitTable(userId);

                plate.setText("");
                parka.setSelectedIndex(0);
                combosemester.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add Parking Permit",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/parking_permit";
        String user = "root";
        String password = "986532";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                parkpermitf dialog = new parkpermitf("exampleStudentId");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }
}