import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registirationf extends JDialog {
    private JTextField school_email;
    private JTextField passport_number;
    private JTextField first_name;
    private JTextField last_name;
    private JPasswordField password;
    private JButton registerButton;
    private JButton cancelButton;
    private JPanel RegisterPanel;
    private JPanel abc;

    public registirationf() {
        setContentPane(RegisterPanel);
        setModal(true);
        setTitle("PPRS-Registration");
        getRootPane().setDefaultButton(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRegister();
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void onRegister() {
        String id = school_email.getText();
        String p_id = passport_number.getText();
        String name = first_name.getText();
        String surname = last_name.getText();
        String passw = new String(password.getPassword());

        if (id.isEmpty() || name.isEmpty() || surname.isEmpty() || p_id.isEmpty() || passw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields",
                    "Try Again", JOptionPane.ERROR_MESSAGE); return; }

        try {
            Connection connection = getConnection();
            String query = "INSERT INTO users (id, p_id, name, surname, passw) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, p_id);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, surname);
            preparedStatement.setString(5, passw);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration Successful!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void onCancel() {
        dispose();
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/parking_permit";
        String user = "root";
        String password = "986532";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        registirationf dialog = new registirationf();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
