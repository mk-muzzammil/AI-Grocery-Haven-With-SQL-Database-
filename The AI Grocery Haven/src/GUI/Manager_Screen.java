package GUI;

import ProductCataloague.Product;
import ProductCatalogueManagement.ProductCatalogueManagement;
import Stores.MyStore;
import Users.Manager;
import Users.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.io.*;

import Login_SignUp_Validation.credentialsVerification;


public class Manager_Screen extends JFrame implements ActionListener {
    JFrame frame;
    JPanel leftpanel;
    JPanel footerPanel, mainCenterPanel, storeCenterPanel;
    JButton managerStoreButton, viewManagerButton, viewCustomerButton, logOutButton;
    JButton setQuantityButton, setPriceButton, removeCustomer, removeProduct, seachCustomer, orderButton;
    Manager manager;

    private ArrayList<Person> customerList;
    private ArrayList<MyStore> stores;
    private HashMap<Product, Boolean> storeInventory;
    MyStore store;
    String currentLoggedInManagerLocation;

    DefaultTableModel model;


    public Manager_Screen(String location) {
        this.currentLoggedInManagerLocation = location;


        frame = new JFrame();
        frame.setLayout(new BorderLayout(0, 0));
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setTitle("The AI Grocery Haven-Manager Panel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        leftpanel = new JPanel();
        leftpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftpanel.setPreferredSize(new Dimension(130, frame.getHeight()));

        leftpanel.setBackground(Color.LIGHT_GRAY);

        managerStoreButton = new customButton("Store");
        viewManagerButton = new customButton("Account");
        viewCustomerButton = new customButton("Customers");
        logOutButton = new customButton("Log Out");
        seachCustomer = new customButton("Search");
        orderButton = new customButton("Orders");

        managerStoreButton.setPreferredSize(new Dimension(120, 40));
        viewManagerButton.setPreferredSize(new Dimension(120, 40));
        viewCustomerButton.setPreferredSize(new Dimension(120, 40));
        logOutButton.setPreferredSize(new Dimension(120, 40));
        seachCustomer.setPreferredSize(new Dimension(120, 40));
        orderButton.setPreferredSize(new Dimension(120, 40));


        managerStoreButton.addActionListener(this);
        viewManagerButton.addActionListener(this);
        viewCustomerButton.addActionListener(this);
        logOutButton.addActionListener(this);
        seachCustomer.addActionListener(this);

        orderButton.addActionListener(this);


        leftpanel.add(managerStoreButton);
        leftpanel.add(viewManagerButton);
        leftpanel.add(viewCustomerButton);
        leftpanel.add(orderButton);
        leftpanel.add(logOutButton);


        footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        footerPanel.setBackground(Color.LIGHT_GRAY);

        setQuantityButton = new customButton("Set Quantity");
        setPriceButton = new customButton("Set Price");
        removeProduct = new customButton("Remove Product");
        removeCustomer = new customButton("Remove Customer");
        setQuantityButton.setPreferredSize(new Dimension(120, 40));
        setPriceButton.setPreferredSize(new Dimension(120, 40));
        removeCustomer.setPreferredSize(new Dimension(150, 40));
        removeProduct.setPreferredSize(new Dimension(120, 40));

        setQuantityButton.addActionListener(this);
        setPriceButton.addActionListener(this);
        removeProduct.addActionListener(this);
        removeCustomer.addActionListener(this);


        mainCenterPanel = new JPanel();
        mainCenterPanel.setBackground(Color.WHITE);
        mainCenterPanel.setLayout(new BorderLayout());


        model = new DefaultTableModel();

        mainCenterPanel.setVisible(false);
        footerPanel.setVisible(false);

        frame.add(leftpanel, BorderLayout.WEST);
        frame.add(footerPanel, BorderLayout.SOUTH);
        frame.setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        footerPanel.setVisible(false);

        footerPanel.remove(setPriceButton);
        footerPanel.remove(setQuantityButton);
        footerPanel.remove(removeCustomer);
        footerPanel.remove(removeProduct);
        mainCenterPanel.removeAll();

        if (e.getSource() == managerStoreButton) {

            footerPanel.add(setQuantityButton);
            footerPanel.add(setPriceButton);
            footerPanel.add(removeProduct);

            mainCenterPanel.setVisible(true);
            footerPanel.setVisible(true);

            try {
                String query = "Select * from " + currentLoggedInManagerLocation + "Store";

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                // Create a TableModel with the data
                model = buildTableModel(resultSet);

                // Close resources
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }


            JTable store = new JTable(model);
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            store.getTableHeader().setDefaultRenderer(headerRenderer);

            JScrollPane customerScrollPane = new JScrollPane(store);

            mainCenterPanel.add(customerScrollPane);

            frame.add(mainCenterPanel, BorderLayout.CENTER);
            frame.revalidate();

        } else if (e.getSource() == setQuantityButton) {
            footerPanel.add(setQuantityButton);
            footerPanel.add(setPriceButton);
            footerPanel.add(removeProduct);

            mainCenterPanel.setVisible(true);
            footerPanel.setVisible(true);

            int product_id = Integer.parseInt((JOptionPane.showInputDialog(null, "Enter Product ID: ")));

            if (product_id > 0 && product_id < 31) {

                int product_quantity = Integer.parseInt((JOptionPane.showInputDialog(null, "Enter Quantity(collective): ")));

                if (product_quantity > -1) {

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                        Statement statement = connection.createStatement();
                        String query = "Update " + currentLoggedInManagerLocation + "Store SET quantity = ? where product_id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, product_quantity);

                        preparedStatement.setInt(2, product_id);


                        int rowAffected = preparedStatement.executeUpdate();

                        if (rowAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Updated");

                        } else {
                            JOptionPane.showMessageDialog(null, "Update failed");

                        }

                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Update failed\n" + e1.getMessage());

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Quantity");

                }


            } else {
                JOptionPane.showMessageDialog(null, "Invalid entry");
            }

        } else if (e.getSource() == setPriceButton) {
            footerPanel.add(setQuantityButton);
            footerPanel.add(setPriceButton);
            footerPanel.add(removeProduct);

            mainCenterPanel.setVisible(true);
            footerPanel.setVisible(true);

            int product_id = Integer.parseInt((JOptionPane.showInputDialog(null, "Enter Product ID: ")));

            if (product_id > 0 && product_id < 31) {

                int product_price = Integer.parseInt((JOptionPane.showInputDialog(null, "Enter new price: ")));

                if (product_price > -1) {

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                        Statement statement = connection.createStatement();
                        String query = "Update " + currentLoggedInManagerLocation + "Store SET price = ? where product_id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, product_price);

                        preparedStatement.setInt(2, product_id);


                        int rowAffected = preparedStatement.executeUpdate();

                        if (rowAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Updated");

                        } else {
                            JOptionPane.showMessageDialog(null, "Update failed");

                        }

                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Update failed\n" + e1.getMessage());

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Price");

                }


            } else {
                JOptionPane.showMessageDialog(null, "Invalid entry");
            }

        } else if (e.getSource() == removeProduct) {
            int product_id=0;
            footerPanel.add(setQuantityButton);
            footerPanel.add(setPriceButton);
            footerPanel.add(removeProduct);

            mainCenterPanel.setVisible(true);
            footerPanel.setVisible(true);


         try{
              product_id = Integer.parseInt((JOptionPane.showInputDialog(null, "Enter Product ID: ")));
         }catch (NumberFormatException e1){
             JOptionPane.showMessageDialog(null, "Invalid entry");
             return;
         }

            if (product_id > 0 && product_id < 31) {


                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                    Statement statement = connection.createStatement();
                    String query = "Delete from " + currentLoggedInManagerLocation + "Store where product_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, product_id);

                    int rowAffected = preparedStatement.executeUpdate();

                    if (rowAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Changes have been applied");

                    } else {
                        JOptionPane.showMessageDialog(null, "Operation failed");

                    }

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Update failed\n" + e1.getMessage());

                }

            } else {
                JOptionPane.showMessageDialog(null, "Invalid Product ID");
            }
        } else if (e.getSource() == viewManagerButton) {

            String first_name = null;
            String last_name = null;
            String email = null;
            String contactNo = null;
            String address = null;
            String username = null;
            String password = null;
            String salary = null;


            mainCenterPanel.setVisible(false);

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");

                PreparedStatement preparedStatement = connection.prepareStatement("Select * from Manager where manager_at = ?");
                preparedStatement.setString(1, currentLoggedInManagerLocation);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    first_name = resultSet.getString("first_name");
                    last_name = resultSet.getString("last_name");
                    email = resultSet.getString("email");
                    contactNo = resultSet.getString("contact_no");
                    address = resultSet.getString("address");
                    username = resultSet.getString("username");
                    password = resultSet.getString("password");

                    salary = resultSet.getString("salary");

                }

            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }

            JOptionPane.showMessageDialog(null, "Full Name : " + first_name + " " + last_name + "\nEmail        : " + email + "\nContact Number: " + contactNo + "\nUsername :" + username + "\nPassword  :" + password + "\nSalary      : " + salary + "\nLocation  : " + address);


        } else if (e.getSource() == viewCustomerButton) {


            footerPanel.add(seachCustomer);


            mainCenterPanel.setVisible(true);

            footerPanel.setVisible(true);

            footerPanel.add(removeCustomer);
            frame.revalidate();

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");

                String query = "Select first_name, last_name, contact_no,email,address from Customer where address = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, currentLoggedInManagerLocation);

                ResultSet result = preparedStatement.executeQuery();

                // Create a TableModel with the data
                model = buildTableModel(result);


                // Close resources
                result.close();
                preparedStatement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }


            JTable table = new JTable(model);

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.GREEN);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            table.getTableHeader().setDefaultRenderer(headerRenderer);

            JScrollPane customerScrollPane = new JScrollPane(table);
            customerScrollPane.setBorder(BorderFactory.createEmptyBorder());

            mainCenterPanel.add(customerScrollPane);
            frame.add(mainCenterPanel);
            frame.revalidate();

        } else if (e.getSource() == orderButton) {
            mainCenterPanel.setVisible(true);
            frame.revalidate();
            int storeID = 0;

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");

                String query = "select store_id from Store where location = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, currentLoggedInManagerLocation);

                ResultSet result = preparedStatement.executeQuery();

                if (result.next()) {
                    storeID = result.getInt("store_id");
                }

                String orderQuery = "select * from Orders where Store_store_id = ?";
                PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
                orderStatement.setInt(1, storeID);

                ResultSet orderResult = orderStatement.executeQuery();

                // Create a TableModel with the data
                model = buildTableModel(orderResult);


                // Close resources
                result.close();
                preparedStatement.close();
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }


            JTable table = new JTable(model);

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.GREEN);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            table.getTableHeader().setDefaultRenderer(headerRenderer);

            JScrollPane customerScrollPane = new JScrollPane(table);
            customerScrollPane.setBorder(BorderFactory.createEmptyBorder());

            mainCenterPanel.add(customerScrollPane);
            frame.add(mainCenterPanel);
            frame.revalidate();


        } else if (e.getSource() == seachCustomer) {
            String name = JOptionPane.showInputDialog(null, "Enter First name");

            for (Person c : customerList) {
                if (name.equals(c.getFirstName())) {
                    JOptionPane.showMessageDialog(null, name);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Not found");


        } else if (e.getSource() == removeCustomer) {
            mainCenterPanel.setVisible(true);
            footerPanel.setVisible(true);
            footerPanel.add(removeCustomer);

            frame.revalidate();
        } else if (e.getSource() == logOutButton) {

            frame.dispose();
        }
    }

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

        Manager_Screen manager_screen = new Manager_Screen("lahore");

    }
}
