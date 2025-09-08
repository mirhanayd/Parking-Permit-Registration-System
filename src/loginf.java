import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginf extends JDialog {
    private JPanel LoginPanel;
    private JTextField stuID;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel hey;
/*public loginf() {
        setContentPane(LoginPanel);
        setModal(true);
        setTitle("PPRS-Login");
        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationForm();
            }
        });
    }

    private void onLogin() {
        String username = stuID.getText();
        String pass = new String(password.getPassword());

        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM users WHERE id = ? AND passw = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                parkpermitf parkPermitDialog = new parkpermitf(username);
                parkPermitDialog.pack();
                parkPermitDialog.setVisible(true);

                dispose();
            } else {
                // Login failed
                JOptionPane.showMessageDialog(this, "Invalid username or password",
                        "Login Error", JOptionPane.ERROR_MESSAGE);
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegistrationForm() {
        // Open the registration form
        registirationf registrationDialog = new registirationf();
        registrationDialog.pack();
        registrationDialog.setVisible(true);
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
                loginf dialog = new loginf();
                dialog.pack();
                dialog.setVisible(true);
                System.exit(0);
            }
        });
    }
}*/
    public loginf() {
        setContentPane(LoginPanel);
        setModal(true);
        setTitle("PPRS-Login");
        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationForm();
            }
        });
    }

    private void onLogin() {
        String username = stuID.getText();
        String pass = new String(password.getPassword());

        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM users WHERE id = ? AND passw = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                parkpermitf parkPermitDialog = new parkpermitf(username);
                parkPermitDialog.pack();
                parkPermitDialog.setVisible(true);

                dispose();
            } else {
                // Login failed
                JOptionPane.showMessageDialog(this, "Invalid username or password",
                        "Login Error", JOptionPane.ERROR_MESSAGE);
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegistrationForm() {
        // Open the registration form
        registirationf registrationDialog = new registirationf();
        registrationDialog.pack();
        registrationDialog.setVisible(true);
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
                loginf dialog = new loginf();
                dialog.pack();
                dialog.setVisible(true);
                System.exit(0);
            }
        });
    }
}