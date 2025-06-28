import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_view {
    public static int ID;

    public static int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Login_view(int ID) {
        this.ID = ID;
    }

    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("Login Interface");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(70, 130, 180)); // Bleu acier
        leftPanel.setBounds(0, 0, 250, 300);
        frame.add(leftPanel);

        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon("rh.jpg");
        Image image = icon.getImage().getScaledInstance(360, 330, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
        imageLabel.setBounds(25, 50, 200, 200);
        leftPanel.add(imageLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(240, 248, 255));
        rightPanel.setBounds(250, 0, 350, 300);
        rightPanel.setLayout(null);
        frame.add(rightPanel);

        JLabel userLabel = new JLabel("Nom d'utilisateur:");
        userLabel.setBounds(30, 50, 150, 25);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(150, 50, 150, 30);
        userField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        rightPanel.add(userField);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setBounds(30, 100, 150, 25);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 150, 30);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        rightPanel.add(passwordField);

        JButton loginButton = new JButton("Se connecter");
        loginButton.setBounds(100, 170, 150, 35);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    Connection connection = new Connection_DB().getConnection();
                    String user = "select id_emp,departement from employe where nom_emp=? and password_emp=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(user);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {

                        JOptionPane.showMessageDialog(frame, "Connexion réussie !");
                        Login_view loginView = new Login_view(resultSet.getInt("id_emp"));
                        if (resultSet.getString("departement").equals("RH")) {
                            Admin_home a = new Admin_home(loginView.getID());
                            a.setVisible(true);
                        }
                        else if (!resultSet.getString("departement").equals("RH")) {
                            Employe_home a = new Employe_home(loginView.getID());
                            a.setVisible(true);
                            frame.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Votre mot de passe incorrect");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}