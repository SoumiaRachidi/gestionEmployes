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

public class Admin_home extends JFrame {

    public Admin_home(int id) {

        JFrame frame = new JFrame("Tableau de Bord Admin");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Centre la fenêtre

        // Header Panel avec gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(70, 130, 180), w, h, new Color(100, 149, 237));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setPreferredSize(new Dimension(800, 60));
        JLabel label = new JLabel("ID utilisateur connecté : " + id);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        headerPanel.add(label);

        // Button Panel avec style moderne
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton BtnAdd = createStyledButton("Ajouter Employé");
        JButton BtnRemove = createStyledButton("Retirer Employé");
        JButton BtnEdit =createStyledButton("Modifier Employé");
        JButton Btnlister = createStyledButton("Lister tous les employes");
        JButton BtnConge = createStyledButton("Demande Conge");
        JButton BtnDec = createStyledButton("Affichier Les Declarations");

        buttonPanel.add(BtnAdd);
        buttonPanel.add(BtnRemove);
        buttonPanel.add(BtnEdit);
        buttonPanel.add(Btnlister);
        buttonPanel.add(BtnConge);
        buttonPanel.add(BtnDec);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Footer Panel avec gradient
        JPanel footerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(100, 149, 237), w, h, new Color(70, 130, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };


        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);
        BtnDec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Declaration soumis");
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                String[] name = {"Nom complet","Message"};
                DefaultTableModel model = new DefaultTableModel(name, 0);
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane, BorderLayout.CENTER);
                try {
                    Connection connection = new Connection_DB().getConnection();
                    String sql= "select nom_emp,prenom_emp,message from msg,employe where msg.id_emp=employe.id_emp";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String fullName = rs.getString("nom_emp") + " " + rs.getString("prenom_emp");
                        String message = rs.getString("message");
                        model.addRow(new Object[]{fullName, message});

                }



                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frame.setVisible(true);

            }
        });

        // Action Listeners
        BtnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame formFrame = new JFrame("Formulaire d'ajout");
                formFrame.setSize(600, 500);
                formFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                formFrame.setLocationRelativeTo(null);

                JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
                mainPanel.setBackground(new Color(240, 240, 240));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JPanel formPanel = new JPanel();
                formPanel.setLayout(new GridLayout(12, 2, 10, 10));
                formPanel.setBackground(new Color(240, 240, 240));

                // Création des champs avec style
                addFormField(formPanel, "Mot de passe:", new JTextField());
                JTextField mdpField = (JTextField) formPanel.getComponent(1);
                addFormField(formPanel, "Nom:", new JTextField());
                JTextField nomField = (JTextField) formPanel.getComponent(3);
                addFormField(formPanel, "Prénom:", new JTextField());
                JTextField prenomField = (JTextField) formPanel.getComponent(5);
                addFormField(formPanel, "Email:", new JTextField());
                JTextField mailField = (JTextField) formPanel.getComponent(7);
                addFormField(formPanel, "Date de naissance:", new JTextField());
                JTextField dateNaissanceField = (JTextField) formPanel.getComponent(9);
                addFormField(formPanel, "Numéro de téléphone:", new JTextField());
                JTextField telField = (JTextField) formPanel.getComponent(11);
                addFormField(formPanel, "Salaire:", new JTextField());
                JTextField salaireField = (JTextField) formPanel.getComponent(13);
                addFormField(formPanel, "Département:", new JTextField());
                JTextField departementField = (JTextField) formPanel.getComponent(15);

                JButton submitButton = createStyledButton("Enregistrer");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection connection = new Connection_DB().getConnection();
                            String user = "INSERT INTO employe(nom_emp, prenom_emp, mail, date_naissance, num_tel, salaire, departement,password_emp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(user);
                            preparedStatement.setString(1, nomField.getText());
                            preparedStatement.setString(2, prenomField.getText());
                            preparedStatement.setString(3, mailField.getText());
                            preparedStatement.setString(4, dateNaissanceField.getText());
                            preparedStatement.setString(5, telField.getText());
                            preparedStatement.setString(6, salaireField.getText());
                            preparedStatement.setString(7, departementField.getText());
                            preparedStatement.setString(8, mdpField.getText());

                            preparedStatement.executeUpdate();
                            JOptionPane.showMessageDialog(formFrame, "Employé ajouté avec succès !");
                            formFrame.dispose();
                        } catch (SQLException ee) {
                            JOptionPane.showMessageDialog(formFrame, "Erreur : " + ee.getMessage());
                        }
                    }
                });

                mainPanel.add(formPanel, BorderLayout.CENTER);
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.setBackground(new Color(240, 240, 240));
                buttonPanel.add(submitButton);
                mainPanel.add(buttonPanel, BorderLayout.SOUTH);

                formFrame.add(mainPanel);
                formFrame.setVisible(true);
            }
        });
        BtnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame1 = new JFrame("Modifier Employé");
                frame1.setSize(400, 400);
                frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame1.setLocationRelativeTo(null);

                JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
                mainPanel.setBackground(new Color(240, 240, 240));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                // Panel pour la recherche
                JPanel searchPanel = new JPanel(new GridLayout(2, 2, 10, 10));
                JLabel idLabel = new JLabel("ID de l'employé :");
                JTextField idField = new JTextField();
                JButton searchButton = createStyledButton("Rechercher");
                searchPanel.add(idLabel);
                searchPanel.add(idField);
                searchPanel.add(searchButton);

                // Panel pour les champs modifiables
                JPanel editPanel = new JPanel(new GridLayout(5, 2, 10, 10));
                editPanel.setVisible(false);

                JTextField nomField = new JTextField();
                JTextField prenomField = new JTextField();
                JTextField posteField = new JTextField();
                JTextField salaireField = new JTextField();

                editPanel.add(new JLabel("Nom :"));
                editPanel.add(nomField);
                editPanel.add(new JLabel("Prénom :"));
                editPanel.add(prenomField);
                editPanel.add(new JLabel("Poste :"));
                editPanel.add(posteField);
                editPanel.add(new JLabel("Salaire :"));
                editPanel.add(salaireField);

                JButton saveButton = createStyledButton("Enregistrer");
                editPanel.add(saveButton);

                searchButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection connection = new Connection_DB().getConnection();
                            String id = idField.getText();
                            String sql = "select * from employe where id_emp = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setInt(1, Integer.parseInt(id));
                            ResultSet resultSet = preparedStatement.executeQuery();
                            if (resultSet.next()){
                                nomField.setText(resultSet.getString("nom_emp"));
                                prenomField.setText(resultSet.getString("prenom_emp"));
                                posteField.setText(resultSet.getString("departement"));
                                salaireField.setText(String.valueOf(resultSet.getFloat("salaire")));
                                editPanel.setVisible(true);

                            }

                        } catch (SQLException e3) {
                            throw new RuntimeException(e3);
                        }




                    }
                });

                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        try {
                            Connection connection = new Connection_DB().getConnection();
                            String id = idField.getText();
                            String sql = "update employe set nom_emp= ?,prenom_emp=?,departement=?,salaire=? where id_emp = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setString(1, nomField.getText());
                            preparedStatement.setString(2, prenomField.getText());
                            preparedStatement.setString(3, posteField.getText());
                            preparedStatement.setString(4, salaireField.getText());
                            preparedStatement.setString(5, id);
                            preparedStatement.executeUpdate();
                            JOptionPane.showMessageDialog(frame1,
                                    "Modifications enregistrées",
                                    "Succès",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }catch (SQLException eee){
                            throw new RuntimeException(eee);
                        }

                    }
                });

                mainPanel.add(searchPanel, BorderLayout.NORTH);
                mainPanel.add(editPanel, BorderLayout.CENTER);
                frame1.add(mainPanel);
                frame1.setVisible(true);
            }
        });
        BtnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame1 = new JFrame("Supprimer Employé");
                frame1.setSize(400, 250);
                frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame1.setLocationRelativeTo(null);

                JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
                mainPanel.setBackground(new Color(240, 240, 240));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JPanel formPanel = new JPanel();
                formPanel.setLayout(new GridLayout(4, 1, 10, 10));
                formPanel.setBackground(new Color(240, 240, 240));

                addFormField(formPanel, "Entrer l'ID de l'employe :", new JTextField());
                JTextField idField = (JTextField) formPanel.getComponent(1);
                addFormField(formPanel, "Entrer le nom de l'employe :", new JTextField());
                JTextField nomField = (JTextField) formPanel.getComponent(3);

                JButton BtnSave = createStyledButton("Supprimer");
                BtnSave.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection connection = new Connection_DB().getConnection();
                            String requete = "delete from employe where id_emp = ? and nom_emp = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement(requete);
                            int id = Integer.parseInt(idField.getText());
                            preparedStatement.setInt(1, id);
                            preparedStatement.setString(2, nomField.getText());
                            preparedStatement.executeUpdate();
                            JOptionPane.showMessageDialog(frame1, "Employé supprime avec succès !");
                            frame1.dispose();
                        } catch (SQLException ee) {
                            JOptionPane.showMessageDialog(frame1, "Erreur : " + ee.getMessage());
                        }
                    }
                });

                mainPanel.add(formPanel, BorderLayout.CENTER);
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.setBackground(new Color(240, 240, 240));
                buttonPanel.add(BtnSave);
                mainPanel.add(buttonPanel, BorderLayout.SOUTH);

                frame1.add(mainPanel);
                frame1.setVisible(true);
            }
        });


        frame.setVisible(true);
        BtnConge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Congé");
                frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
                mainPanel.setBackground(new Color(240, 240, 240));

                JButton refuse = new JButton("Refuser");
                JButton accept = new JButton("Accepter");
                mainPanel.add(refuse, BorderLayout.EAST);
                mainPanel.add(accept, BorderLayout.WEST);

                frame.add(mainPanel, BorderLayout.SOUTH);

                String[] columnNames = {"ID", "Nom", "Prénom", "Congé demandé", "Date début", "Date fin", "Congé restant", "État"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(model);

                table.setFont(new Font("Arial", Font.PLAIN, 12));
                table.setRowHeight(25);
                table.setShowGrid(true);
                table.setGridColor(Color.LIGHT_GRAY);
                table.setSelectionBackground(new Color(0, 120, 215));
                table.setSelectionForeground(Color.BLACK);
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.setVisible(true);

                try (Connection connection = new Connection_DB().getConnection()) {
                    String sql = "SELECT employe.id_emp, nom_emp, prenom_emp, conge, date_deb, date_fin, conge_rest, etat " +
                            "FROM employe, congee WHERE employe.id_emp = congee.id_emp";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                         ResultSet resultSet = preparedStatement.executeQuery()) {

                        while (resultSet.next()) {
                            Object[] row = {
                                    resultSet.getInt("id_emp"),
                                    resultSet.getString("nom_emp"),
                                    resultSet.getString("prenom_emp"),
                                    resultSet.getInt("conge"),
                                    resultSet.getString("date_deb"),
                                    resultSet.getString("date_fin"),
                                    resultSet.getInt("conge_rest"),
                                    Etat_con(resultSet.getInt("etat")),
                            };
                            model.addRow(row);
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
                }

                final int[] selectedId = {-1}; // Variable pour stocker l'ID sélectionné

                table.getSelectionModel().addListSelectionListener(event -> {
                    if (!event.getValueIsAdjusting()) {
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow != -1) {
                            selectedId[0] = (int) table.getValueAt(selectedRow, 0);
                        }
                    }
                });

                accept.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (selectedId[0] == -1) {
                            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une demande de congé !");
                            return;
                        }

                        try (Connection connection = new Connection_DB().getConnection()) {
                            String updateStatusSQL = "UPDATE congee SET etat = ? WHERE id_emp = ? AND (etat = 0 OR etat = -1)";
                            try (PreparedStatement ps = connection.prepareStatement(updateStatusSQL)) {
                                ps.setInt(1, 1);
                                ps.setInt(2, selectedId[0]);
                                ps.executeUpdate();
                            }

                            String getCongeSQL = "SELECT conge_rest, conge , etat FROM employe, congee WHERE employe.id_emp = congee.id_emp AND employe.id_emp = ?";
                            try (PreparedStatement ps = connection.prepareStatement(getCongeSQL)) {
                                ps.setInt(1, selectedId[0]);
                                try (ResultSet rs = ps.executeQuery()) {
                                    if (rs.next()) {
                                        int congeRestant = rs.getInt("conge_rest") - (rs.getInt("conge") );
                                        if (congeRestant < 0) {
                                            JOptionPane.showMessageDialog(frame, "Erreur : Congé restant négatif !");
                                        } else {
                                            String updateCongeSQL = "UPDATE employe SET conge_rest = ? WHERE id_emp = ?";
                                            try (PreparedStatement psUpdate = connection.prepareStatement(updateCongeSQL)) {
                                                psUpdate.setInt(1, congeRestant);
                                                psUpdate.setInt(2, selectedId[0]);
                                                psUpdate.executeUpdate();
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                refuse.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (selectedId[0] == -1) {
                            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une demande de congé !");
                            return;
                        }

                        try (Connection connection = new Connection_DB().getConnection()) {
                            String refuseSQL = "UPDATE congee SET etat = -1 WHERE id_emp = ?";
                            try (PreparedStatement ps = connection.prepareStatement(refuseSQL)) {
                                ps.setInt(1, selectedId[0]);
                                ps.executeUpdate();
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }

            public String Etat_con(int etat) {
                switch (etat) {
                    case 1: return "Accepté";
                    case -1: return "Refusé";
                    default: return "En attente";
                }
            }
        });


        Btnlister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame tableframe = new JFrame("Liste des employés");
                tableframe.setSize(1000, 600); // Taille augmentée pour mieux afficher les données
                tableframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tableframe.setLayout(new BorderLayout());


                String[] columnNames = {"ID", "Nom", "Prénom", "Mail", "Téléphone", "Département", "Salaire", "Congé Restant", "Absence", "Heures Supp", "Mot de Passe"};


                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(model);
                table.setFont(new Font("Arial", Font.PLAIN, 12));
                table.setRowHeight(25);
                table.setShowGrid(true);
                table.setGridColor(Color.LIGHT_GRAY);
                table.setSelectionBackground(new Color(0, 120, 215));
                table.setSelectionForeground(Color.BLACK);


                JTableHeader header = table.getTableHeader();
                header.setFont(new Font("Arial", Font.BOLD, 14));
                header.setBackground(new Color(0, 120, 215));
                header.setForeground(Color.black);

                try {
                    Connection connection = new Connection_DB().getConnection();
                    String sql = "select * from employe";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        Object[] row = {
                                resultSet.getInt("id_emp"),
                                resultSet.getString("nom_emp"),
                                resultSet.getString("prenom_emp"),
                                resultSet.getString("mail"),
                                resultSet.getInt("num_tel"),
                                resultSet.getString("departement"),
                                resultSet.getFloat("salaire"),
                                resultSet.getString("conge_rest"),
                                resultSet.getInt("abs"),
                                resultSet.getString("heure_supp"),
                                resultSet.getString("password_emp")
                        };
                        model.addRow(row);
                    }

                } catch (SQLException e3) {
                    throw new RuntimeException(e3);
                }

                JScrollPane scrollPane = new JScrollPane(table);
                tableframe.add(scrollPane, BorderLayout.CENTER);
                tableframe.setVisible(true);
            }

        });

    }



    private void addFormField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));
        panel.add(label);
        panel.add(field);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(70, 130, 180), 0, getHeight(), new Color(100, 149, 237));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }
    public String Etat_con(int a){
        if(a == 1){
            return "Accepté";
        } else if (a == 0) {
            return "En attente";
        }
        else if(a == -1){
            return "Rejeté";
        }
        else
            return "Null";
    }



    public static void main(String[] args) throws SQLException {


    }
}