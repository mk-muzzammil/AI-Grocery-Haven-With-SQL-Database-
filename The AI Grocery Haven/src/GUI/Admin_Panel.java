package GUI;


import Login_SignUp_Validation.credentialsVerification;
import ProductCataloague.Product;
import ProductCatalogueManagement.ProductCatalogueManagement;
import Stores.MyStore;
import Users.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin_Panel extends JFrame implements ActionListener {

    //main panel components
    JFrame adminFrame;
    JPanel leftPanel;
    JButton manageManagers, manageCatalouge, viewAccount, managestores, logOut;


    JPanel adminFooter;
    private JTable maangerTable;


    JPanel storeNavBar;
    JPanel mainCenter;
    JButton food, houseHold, personalHygiene;

    JButton setStatus;
    private JTable foodTable, houseHoldTable, personalHygieneTable;

    JScrollPane scrollPane;
    JTable viewTable;
    JTable adminTable;
    JButton addStore;
    JTable storeTable;


    ImageIcon icon;
    public ArrayList<Manager> managerList;
    public ArrayList<MyStore> storeList;

    public ArrayList<Person> adminList;

    public HashMap<Product, Boolean> products;

    public HashMap<Product, Boolean> foodCategory;

    public HashMap<Product, Boolean> houseHoldCategory;
    public HashMap<Product, Boolean> personalHygieneCategory;

    DefaultTableModel model;

    public Admin_Panel() {

        model = new DefaultTableModel();


        storeList = UserWriter.LoadStoresList(storeList);


        icon = new ImageIcon("images/Company_Logo.png");
        adminFrame = new JFrame("The AI Grocery Haven-Admin Panel");
        adminFrame.setSize(800, 800);
        adminFrame.setIconImage(icon.getImage());
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setLayout(new BorderLayout(0, 0));
        adminFrame.getContentPane().setBackground(Color.WHITE);
        adminFrame.setResizable(true);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Setting necessary buttons for admin
        manageManagers = new customButton("Manager");
        manageCatalouge = new customButton("Catalogue");
        viewAccount = new customButton("Account");
        logOut = new customButton("Log out");
        managestores = new customButton("Stores");
        addStore = new customButton("ADD Store");


        manageManagers.setPreferredSize(new Dimension(150, 30));
        manageCatalouge.setPreferredSize(new Dimension(150, 30));
        managestores.setPreferredSize(new Dimension(150, 30));

        viewAccount.setPreferredSize(new Dimension(150, 30));
        logOut.setPreferredSize(new Dimension(150, 30));


        // Creating footer panel for holding buttons to manage Managers

        adminFooter = new JPanel();
        adminFooter.setBackground(Color.BLACK);
        adminFooter.setPreferredSize(new Dimension(adminFrame.getWidth(), 30));


        //creating navBar for store
        storeNavBar = new JPanel();
        storeNavBar.setBackground(Color.BLACK);
        storeNavBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        storeNavBar.setPreferredSize(new Dimension(adminFrame.getWidth(), 30));


        //creating center panel for managing stores
        mainCenter = new JPanel();
        mainCenter.setLayout(new BorderLayout());
        mainCenter.setBackground(Color.WHITE);


        // Creating store Nav Bar menu for managing Stores
        food = new customButton("Food");
        houseHold = new customButton("House Holding Cleaning");
        personalHygiene = new customButton("Personal Hygiene");
        setStatus = new customButton("Set Status");


        //creating table

        maangerTable = new JTable();
        foodTable = new JTable();
        houseHoldTable = new JTable();
        personalHygieneTable = new JTable();
        viewTable = new JTable();
        storeTable = new JTable();


        adminFooter.add(setStatus);
        adminFooter.add(addStore);


        //Adding buttons to store NavBar
        storeNavBar.add(food);
        storeNavBar.add(houseHold);
        storeNavBar.add(personalHygiene);


        // Setting left panel of Frame
        leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(124, 190, 213, 151));
        leftPanel.setPreferredSize(new Dimension(160, adminFrame.getHeight()));
        leftPanel.add(manageManagers);
        leftPanel.add(manageCatalouge);
        leftPanel.add(managestores);
        leftPanel.add(viewAccount);
        leftPanel.add(logOut);


        //initially setting footer panel, manager center, store Nav Bar and store Center visibility false
        adminFooter.setVisible(false);
        storeNavBar.setVisible(false);


        //Adding Action Listener to Admin panel buttons
        manageManagers.addActionListener(this);
        manageCatalouge.addActionListener(this);
        managestores.addActionListener(this);
        food.addActionListener(this);
        personalHygiene.addActionListener(this);
        houseHold.addActionListener(this);
        setStatus.addActionListener(this);
        addStore.addActionListener(this);
        viewAccount.addActionListener(this);
        logOut.addActionListener(this);


        // Adding components to Main Frame
        adminFrame.add(leftPanel, BorderLayout.WEST);
        adminFrame.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        adminFooter.setVisible(false);
        adminFooter.remove(addStore);
        adminFooter.remove(setStatus);

        mainCenter.removeAll(); // Clear the main center panel

        if (e.getSource() == manageManagers) {
            mainCenter.setVisible(true);
            storeNavBar.setVisible(false);

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Manager");

                // Create a TableModel with the data
                model = buildTableModel(resultSet);
                JTable managerTable = new JTable(model);

                DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
                headerRenderer.setBackground(Color.BLUE);
                headerRenderer.setForeground(Color.WHITE);
                headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                managerTable.getTableHeader().setDefaultRenderer(headerRenderer);

                // Add the table to the scroll pane
                scrollPane = new JScrollPane(managerTable);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                // Close resources
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }


            mainCenter.add(scrollPane);
            adminFrame.add(mainCenter, BorderLayout.CENTER);
            adminFrame.revalidate();

        } else if (e.getSource() == managestores) {
            mainCenter.setVisible(true);
            adminFooter.setVisible(true);
            adminFooter.add(addStore);

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Store");

                // Create a TableModel with the data
                model = buildTableModel(resultSet);
                storeTable.setModel(model); // Set model to existing table
                scrollPane = new JScrollPane(storeTable);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                // Close resources
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            storeTable.getTableHeader().setDefaultRenderer(headerRenderer);

            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane
            mainCenter.add(scrollPane);

            adminFrame.add(mainCenter, BorderLayout.CENTER);
            adminFrame.add(adminFooter, BorderLayout.SOUTH);
            adminFrame.revalidate();
        } else if (e.getSource() == manageCatalouge) {
            mainCenter.setVisible(true);
            storeNavBar.setVisible(true);
            adminFooter.setVisible(true);
            adminFooter.add(setStatus);


            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Products");

                // Create a TableModel with the data
                model = buildTableModel(resultSet);
                foodTable = new JTable(model);


                // Add the table to the scroll pane
                scrollPane = new JScrollPane(foodTable);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                // Close resources
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            foodTable.getTableHeader().setDefaultRenderer(headerRenderer);

            scrollPane = new JScrollPane(foodTable);

            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane


            mainCenter.add(scrollPane);


            adminFrame.add(storeNavBar, BorderLayout.NORTH);
            adminFrame.add(mainCenter, BorderLayout.CENTER);
            adminFrame.add(adminFooter, BorderLayout.SOUTH);


            adminFrame.revalidate();

        } else if (e.getSource() == food) {

            adminFooter.setVisible(true);
            mainCenter.setVisible(true);
            adminFooter.add(setStatus);

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Products where category='food'");

                // Create a TableModel with the data
                model = buildTableModel(resultSet);
                foodTable = new JTable(model);


                // Add the table to the scroll pane
                scrollPane = new JScrollPane(foodTable);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                // Close resources
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }


            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            foodTable.getTableHeader().setDefaultRenderer(headerRenderer);

            scrollPane = new JScrollPane(foodTable);

            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane


            mainCenter.add(scrollPane);

            adminFrame.add(storeNavBar, BorderLayout.NORTH);
            adminFrame.add(mainCenter, BorderLayout.CENTER);

            adminFrame.revalidate();

        } else if (e.getSource() == houseHold) {
            adminFooter.setVisible(true);
            adminFooter.add(setStatus);

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Products where category='household cleaning'");

                // Create a TableModel with the data
                model = buildTableModel(resultSet);
                houseHoldTable = new JTable(model);


                // Add the table to the scroll pane
                scrollPane = new JScrollPane(foodTable);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                // Close resources
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }


            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            houseHoldTable.getTableHeader().setDefaultRenderer(headerRenderer);

            scrollPane = new JScrollPane(houseHoldTable);

            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane
            mainCenter.add(scrollPane);


            mainCenter.setVisible(true);

            adminFrame.add(storeNavBar, BorderLayout.NORTH);
            adminFrame.add(mainCenter, BorderLayout.CENTER);
            adminFrame.add(adminFooter, BorderLayout.SOUTH);
            adminFrame.revalidate();

        } else if (e.getSource() == personalHygiene) {
            adminFooter.setVisible(true);
            adminFooter.add(setStatus);

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Products where category='personal hygiene'");

                // Create a TableModel with the data
                model = buildTableModel(resultSet);
                personalHygieneTable = new JTable(model);


                // Add the table to the scroll pane
                scrollPane = new JScrollPane(foodTable);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                // Close resources
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }


            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            personalHygieneTable.getTableHeader().setDefaultRenderer(headerRenderer);


            scrollPane = new JScrollPane(personalHygieneTable);

            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane
            mainCenter.add(scrollPane);


            mainCenter.setVisible(true);


            adminFrame.add(storeNavBar, BorderLayout.NORTH);
            adminFrame.add(mainCenter, BorderLayout.CENTER);
            adminFrame.add(adminFooter, BorderLayout.SOUTH);
            adminFrame.revalidate();

        } else if (e.getSource() == setStatus) {
            adminFooter.setVisible(true);
            adminFooter.add(setStatus);

            String pID = JOptionPane.showInputDialog(null, "Enter product ID:");
            Boolean statusUpdate;
            Object[] options = {"Set True", "Set False"};

            // Show the JOptionPane with custom buttons
            int result = JOptionPane.showOptionDialog(null,
                    "Choose status:", "Setting Product Status",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);

            if (result == 0) {
                statusUpdate = true;
            } else if (result == 1) {
                statusUpdate = false;
            }
            else {
                JOptionPane.showMessageDialog(null, "No option was selected");
                return;
            }
            if (pID != null) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                    Statement statement = connection.createStatement();
                    String query = "Update  Products SET status = ? where product_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setBoolean(1, statusUpdate);

                    preparedStatement.setInt(2, Integer.parseInt(pID));


                    int rowAffected = preparedStatement.executeUpdate();

                    if (rowAffected > 0) {
                        updatingStores(statusUpdate,pID);
                        JOptionPane.showMessageDialog(null, "Updated");

                    } else {
                        JOptionPane.showMessageDialog(null, "Update failed");

                    }

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Update failed\n" + e1.getMessage());

                }
            } else {
                JOptionPane.showMessageDialog(null, "Input field is empty");
            }


        } else if (e.getSource() == addStore) {
            adminFooter.setVisible(true);
            adminFooter.add(addStore);
            Manage_Manager manager = new Manage_Manager();
            manager.addManagerFrame();

        }
        else if (e.getSource() == viewAccount) {

            mainCenter.setVisible(true);
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Admin");

                // Create a TableModel with the data
                model = buildTableModel(resultSet);
                 adminTable = new JTable(model);


                // Add the table to the scroll pane
                scrollPane = new JScrollPane(adminTable);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());

                // Close resources
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }


            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLACK);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            adminTable.getTableHeader().setDefaultRenderer(headerRenderer);

            scrollPane = new JScrollPane(adminTable);

            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane
            mainCenter.add(scrollPane);


            adminFrame.add(mainCenter, BorderLayout.CENTER);
            adminFrame.revalidate();


        } else if (e.getSource() == logOut) {
            adminFrame.dispose();
        }

    }

    public static void updatingStores(Boolean status,String pid){
        if(!status){
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                String query = "Select * from Store";
                Statement statement = connection.createStatement();
                ResultSet resultSet=statement.executeQuery(query);
                String location=null;
                while(resultSet.next()){
                    location=resultSet.getString("location").toLowerCase();
                    try {

                        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                        String query_01 = "DELETE FROM " + location + "Store WHERE product_id = ?";

                        PreparedStatement preparedStatement = connection.prepareStatement(query_01);
                        preparedStatement.setInt(1, Integer.parseInt(pid));
                        preparedStatement.executeUpdate();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Update failed\n" + e1.getMessage());

            }

        }
        else{
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                String query = "Select * from Store";
                Statement statement = connection.createStatement();
                ResultSet resultSet=statement.executeQuery(query);
                String location=null;
                while(resultSet.next()){
                    location=resultSet.getString("location").toLowerCase();
                    try {

                        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                        String query_01 = "INSERT INTO "+location+"Store"+" (product_id, name, brand,price, Weight_units,quantity) SELECT product_id, name, brand,price,units,quantity FROM Products WHERE product_id = ?";

                        PreparedStatement preparedStatement= connection.prepareStatement(query_01);
                        preparedStatement.setInt(1,Integer.parseInt(pid));
                        int rowAffected = preparedStatement.executeUpdate();

                        if(rowAffected > 0){
                            JOptionPane.showMessageDialog(null, "Update Successfully");
                        }else{
                            JOptionPane.showMessageDialog(null, "Update failed");

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Update failed\n" + e1.getMessage());

            }

        }

    }
    // Helper method to convert a ResultSet to a TableModel
    private static DefaultTableModel buildTableModel(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();

            // Get column names
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            // Get data rows
            Object[][] data = new Object[100][columnCount];
            int rowCount = 0;
            while (resultSet.next() && rowCount < 100) {
                for (int i = 1; i <= columnCount; i++) {
                    data[rowCount][i - 1] = resultSet.getObject(i);
                }
                rowCount++;
            }

            // Create a DefaultTableModel
            return new DefaultTableModel(data, columnNames);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        Admin_Panel ad = new Admin_Panel();
//        for (MyStore s: ad.storeList){
//            System.out.println(s.getLocation());
//        }

    }


}

