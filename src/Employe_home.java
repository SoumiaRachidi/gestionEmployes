import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Employe_home extends JFrame {
    public Employe_home(int id) {
        JFrame frame = new JFrame("Employee Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNomEmploye = new JLabel("Votre ID "+id, SwingConstants.CENTER);
        lblNomEmploye.setFont(new Font("Arial", Font.HANGING_BASELINE, 16));
        lblNomEmploye.setForeground(new Color(74, 127, 172));
        mainPanel.add(lblNomEmploye, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton buttonAfficher = new JButton("Informations Personnel");
        buttonAfficher.setFont(new Font("Arial", Font.BOLD, 14));
        buttonAfficher.setBackground(new Color(74, 127, 172));
        buttonAfficher.setForeground(Color.WHITE);
        buttonAfficher.setFocusPainted(false);
        buttonAfficher.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton buttonConsulter = new JButton("Consulter");
        buttonConsulter.setFont(new Font("Arial", Font.BOLD, 14));
        buttonConsulter.setBackground(new Color(74, 127, 172));
        buttonConsulter.setForeground(Color.WHITE);
        buttonConsulter.setFocusPainted(false);
        buttonConsulter.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton buttonDemande = new JButton("Demande");
        buttonDemande.setFont(new Font("Arial", Font.BOLD, 14));
        buttonDemande.setBackground(new Color(74, 127, 172));
        buttonDemande.setForeground(Color.WHITE);
        buttonDemande.setFocusPainted(false);
        buttonDemande.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton buttonMsg = new JButton("Envoyer une declaration");
        buttonMsg.setFont(new Font("Arial", Font.BOLD, 14));
        buttonMsg.setBackground(new Color(74, 127, 172));
        buttonMsg.setForeground(Color.WHITE);
        buttonMsg.setFocusPainted(false);
        buttonMsg.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        buttonPanel.add(buttonAfficher);
        buttonPanel.add(buttonConsulter);
        buttonPanel.add(buttonDemande);
        buttonPanel.add(buttonMsg);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
        buttonMsg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Envoyer une declaration");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(500, 400);
                frame.setLocationRelativeTo(null);
                JPanel mainPanel = new JPanel(new BorderLayout());
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                frame.add(mainPanel, BorderLayout.CENTER);
                JLabel Msg = new JLabel("Saisir la declaration ", SwingConstants.CENTER);
                JTextField textField = new JTextField(100);
                textField.setFont(new Font("Arial", Font.BOLD, 14));
                textField.setBackground(new Color(234, 239, 255));
                textField.setForeground(Color.black);
                JButton button = new JButton("Envoyer une declaration");
                button.setFont(new Font("Arial", Font.BOLD, 14));
                button.setBackground(new Color(74, 127, 172));
                button.setForeground(Color.WHITE);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection conn = new Connection_DB().getConnection();
                            String sql = "insert into msg(id_emp,message) values(?,?)";
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setInt(1, id);
                            ps.setString(2, textField.getText());
                            ps.executeUpdate();

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                mainPanel.add(Msg, BorderLayout.NORTH);
                mainPanel.add(textField, BorderLayout.CENTER);
                mainPanel.add(button, BorderLayout.SOUTH);
                frame.setVisible(true);


            }
        });

        buttonAfficher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame_info = new JFrame("Employee Informations");
                frame_info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame_info.setSize(500, 400);
                frame_info.setLocationRelativeTo(null);

                JPanel panel_info = new JPanel();
                panel_info.setLayout(new BoxLayout(panel_info, BoxLayout.Y_AXIS));
                panel_info.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                panel_info.setBackground(new Color(240, 240, 240));

                try {
                    Connection connection = new Connection_DB().getConnection();
                    String sql = "SELECT * FROM employe WHERE id_emp = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        JLabel lblId = new JLabel("ID: " + resultSet.getInt("id_emp"));
                        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lblId.setForeground(new Color(50, 50, 50));
                        lblId.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel_info.add(lblId);

                        panel_info.add(Box.createRigidArea(new Dimension(0, 10)));

                        JLabel lblName = new JLabel("Nom: " + resultSet.getString("nom_emp"));
                        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lblName.setForeground(new Color(50, 50, 50));
                        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel_info.add(lblName);

                        panel_info.add(Box.createRigidArea(new Dimension(0, 10)));

                        JLabel lblEmail = new JLabel("Email: " + resultSet.getString("mail"));
                        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lblEmail.setForeground(new Color(50, 50, 50));
                        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel_info.add(lblEmail);

                        panel_info.add(Box.createRigidArea(new Dimension(0, 10)));

                        JLabel lbltele = new JLabel("Numéro de téléphone: " + resultSet.getInt("num_tel"));
                        lbltele.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lbltele.setForeground(new Color(50, 50, 50));
                        lbltele.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel_info.add(lbltele);

                        panel_info.add(Box.createRigidArea(new Dimension(0, 10)));

                        JLabel lbldepart = new JLabel("Département: " + resultSet.getString("departement"));
                        lbldepart.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lbldepart.setForeground(new Color(50, 50, 50));
                        lbldepart.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel_info.add(lbldepart);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                frame_info.add(panel_info);
                frame_info.setVisible(true);
            }
        });
        buttonConsulter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame_consulter = new JFrame("Consulter Informations");
                frame_consulter.setSize(500, 400);
                frame_consulter.setLocationRelativeTo(null);

                JPanel panel_consulter = new JPanel();
                panel_consulter.setLayout(new BoxLayout(panel_consulter, BoxLayout.Y_AXIS));
                panel_consulter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                panel_consulter.setBackground(new Color(240, 240, 240));

                try {
                    Connection connection = new Connection_DB().getConnection();
                    String sql = "SELECT * FROM employe WHERE id_emp = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        JLabel lblabs = new JLabel("Absence: " + resultSet.getInt("abs"));
                        lblabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lblabs.setForeground(new Color(50, 50, 50));
                        lblabs.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel_consulter.add(lblabs);

                        panel_consulter.add(Box.createRigidArea(new Dimension(0, 10)));

                        JLabel lblconge = new JLabel("Jours de congé restant: " + resultSet.getInt("conge_rest"));
                        lblconge.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lblconge.setForeground(new Color(50, 50, 50));
                        lblconge.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel_consulter.add(lblconge);

                        panel_consulter.add(Box.createRigidArea(new Dimension(0, 10)));

                        JLabel lblheure = new JLabel("Heures supplémentaires: " + resultSet.getInt("heure_supp"));
                        lblheure.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lblheure.setForeground(new Color(50, 50, 50));
                        lblheure.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel_consulter.add(lblheure);

                        panel_consulter.add(Box.createRigidArea(new Dimension(0, 10)));

                        JLabel lblsal = new JLabel("Salaire: " + resultSet.getFloat("salaire"));
                        lblsal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lblsal.setForeground(new Color(50, 50, 50));
                        lblsal.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panel_consulter.add(lblsal);
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame_consulter, "Erreur de connexion à la base de données : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                frame_consulter.add(panel_consulter);
                frame_consulter.setVisible(true);
            }
        });


        buttonDemande.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame_demande = new JFrame("Demande de Congé");
                frame_demande.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame_demande.setSize(500, 300);
                frame_demande.setLocationRelativeTo(null);
                frame_demande.setLayout(new BorderLayout());

                JPanel panel_demande = new JPanel();
                panel_demande.setLayout(new GridBagLayout());
                panel_demande.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                panel_demande.setBackground(new Color(240, 240, 240));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel label = new JLabel("Date début de congé :");
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setForeground(new Color(50, 50, 50));
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel_demande.add(label, gbc);

                JTextField Ddebut = new JTextField(20);
                Ddebut.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                Ddebut.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
                gbc.gridx = 1;
                gbc.gridy = 0;
                panel_demande.add(Ddebut, gbc);

                JLabel label2 = new JLabel("Date fin de congé :");
                label2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label2.setForeground(new Color(50, 50, 50));
                gbc.gridx = 0;
                gbc.gridy = 1;
                panel_demande.add(label2, gbc);

                JTextField DFin = new JTextField(20);
                DFin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                DFin.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
                gbc.gridx = 1;
                gbc.gridy = 1;
                panel_demande.add(DFin, gbc);

                JButton submitButton = new JButton("Soumettre");
                submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
                submitButton.setBackground(new Color(70, 130, 180));
                submitButton.setForeground(Color.WHITE);
                submitButton.setFocusPainted(false);
                submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.CENTER;
                panel_demande.add(submitButton, gbc);

                frame_demande.add(panel_demande, BorderLayout.CENTER);
                frame_demande.setVisible(true);

                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection connection = new Connection_DB().getConnection();
                            String sql = "INSERT INTO congee(id_emp, date_deb, date_fin, conge) VALUES (?, ?, ?, ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(sql);
                            int a = checkdate(Ddebut.getText(), DFin.getText());
                            preparedStatement.setInt(1, id);
                            preparedStatement.setString(2, Ddebut.getText());
                            preparedStatement.setString(3, DFin.getText());
                            preparedStatement.setInt(4, a);
                            preparedStatement.executeUpdate();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });

        frame.setVisible(true);
    }
    public int checkdate(String Date_debut, String Date_fin) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateStart = LocalDate.parse(Date_debut, formatter);
        LocalDate dateEnd = LocalDate.parse(Date_fin, formatter);
        int count = 0;

        while (!dateStart.isAfter(dateEnd)) {
            DayOfWeek dayOfWeek = dateStart.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                count++;
            }
            dateStart = dateStart.plusDays(1);
        }

        return count;
    }

    public static void main(String[] args) {




    }

}
