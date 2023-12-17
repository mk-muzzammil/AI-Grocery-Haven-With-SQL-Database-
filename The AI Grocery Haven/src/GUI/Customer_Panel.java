package GUI;

import java.sql.Timestamp;
import java.util.Date;

import DatabaseConnection.Connection_DB;
import ProductCataloague.Food.Food;
import ProductCataloague.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ProductCatalogueManagement.ProductCatalogueManagement;
import Stores.MyStore;
import Users.*;
import GUI.*;


public class Customer_Panel extends JFrame implements ActionListener {
    ImageIcon icon;

    JLabel categoryFilter, grandTotalLabel;
    JButton buyFromCart, placeOrder, confirmCartButton, shopOnline, viewAccount, cart, logOut, food, houseHold, personalHygiene, perishabale, nonPersishable, buy, addToCart, removeFromCart, searchFood;
    JPanel grandTotalPanel, foodFilter, customerMainBUttonPanel, customerNavBar, customerLeftBar, customerFooter;
    JFrame customerPanel;
    JPanel foodCenterPanel;

    JTable foodTable;
    JScrollPane foodScrollPane;
    JTextField productIDTextField, quantityTextField;
    private Customer customer;
    private ArrayList<MyStore> stores;
    private MyStore store;
    private HashMap<Product, Boolean> products;
    ArrayList<Product> cartProduct;
    JFrame cartFrame;
    private String quantity;
    private double totalprice;
    JPanel imageContainer;

    DefaultTableModel model;
    String currentLoggedInCustomerLocation;
    int customerID;


    public Customer_Panel(String location, int ID) {
        this.currentLoggedInCustomerLocation = location;
        this.customerID = ID;
        ImageIcon customerScreen = new ImageIcon("images/customer_screen.png");

        JLabel label = new JLabel();
        label.setIcon(customerScreen);

        imageContainer = new JPanel();
        imageContainer.add(label);


        icon = new ImageIcon("images/Company_Logo.png");
        customerPanel = new JFrame("The AI Grocery Haven");
        customerPanel.setSize(1000, 800);
        customerPanel.setIconImage(icon.getImage());
        customerPanel.setLocationRelativeTo(null);
        customerPanel.setLayout(new BorderLayout(0, 1));
        customerPanel.getContentPane().setBackground(Color.WHITE);
        customerPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        shopOnline = new customButton("Shop");
        cart = new customButton("Cart");
        viewAccount = new customButton("Account");
        logOut = new customButton("Log Out");
        food = new customButton("Food");
        personalHygiene = new customButton("Hygienics");
        houseHold = new customButton("Household");
        searchFood = new customButton("Search Item");
        perishabale = new customButton("Perishable");
        perishabale.setBackground(Color.green);

        nonPersishable = new customButton("Non Perishable");
        nonPersishable.setBackground(Color.green);

        confirmCartButton = new customButton("Confirm");
        placeOrder = new customButton("Place Order");
        buyFromCart = new customButton("Check Out");

        categoryFilter = new JLabel();
        categoryFilter.setText("Filter");
        categoryFilter.setBackground(Color.BLACK);
        categoryFilter.setForeground(Color.WHITE);

        grandTotalLabel = new JLabel();
        grandTotalLabel.setForeground(Color.WHITE);
        grandTotalLabel.setBackground(Color.BLACK);


        buy = new customButton("Buy");
        addToCart = new customButton("Add to Cart");
        removeFromCart = new customButton("Remove Product");


        shopOnline.setPreferredSize(new Dimension(80, 20));
        cart.setPreferredSize(new Dimension(80, 20));
        viewAccount.setPreferredSize(new Dimension(80, 20));
        logOut.setPreferredSize(new Dimension(100, 20));
        food.setPreferredSize(new Dimension(130, 30));
        personalHygiene.setPreferredSize(new Dimension(130, 30));
        houseHold.setPreferredSize(new Dimension(130, 30));
        perishabale.setPreferredSize(new Dimension(150, 20));
        nonPersishable.setPreferredSize(new Dimension(150, 20));
        searchFood.setPreferredSize(new Dimension(150, 20));


        foodFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        foodFilter.setBackground(Color.BLACK);
        foodFilter.add(categoryFilter);
        foodFilter.add(perishabale);
        foodFilter.add(nonPersishable);


        grandTotalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
        grandTotalPanel.add(grandTotalLabel);
        grandTotalPanel.setBackground(Color.BLACK);


        customerMainBUttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        customerMainBUttonPanel.setBackground(Color.BLACK);
        customerMainBUttonPanel.add(shopOnline);
        customerMainBUttonPanel.add(cart);
        customerMainBUttonPanel.add(viewAccount);
        customerMainBUttonPanel.add(logOut);
        customerMainBUttonPanel.add(searchFood);

        customerNavBar = new JPanel(new BorderLayout());
        customerNavBar.setPreferredSize(new Dimension(customerPanel.getWidth(), 60));
        customerNavBar.setBackground(Color.BLACK);

        customerNavBar.add(customerMainBUttonPanel, BorderLayout.WEST);
        customerNavBar.add(foodFilter, BorderLayout.EAST);
        customerNavBar.add(grandTotalPanel, BorderLayout.CENTER);


        customerLeftBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 17));
        customerLeftBar.setPreferredSize(new Dimension(160, customerPanel.getHeight()));
        customerLeftBar.setBackground(Color.ORANGE);

        customerLeftBar.add(food);
        customerLeftBar.add(houseHold);
        customerLeftBar.add(personalHygiene);

        foodCenterPanel = new JPanel(new BorderLayout());
        foodCenterPanel.setBackground(Color.WHITE);


        model = new DefaultTableModel();


        customerFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        customerFooter.setPreferredSize(new Dimension(customerPanel.getWidth(), 40));
        customerFooter.setBackground(Color.BLACK);
        customerFooter.add(buy);
        customerFooter.add(addToCart);
        customerFooter.add(removeFromCart);
        customerFooter.add(buyFromCart);

        model = new DefaultTableModel();


        shopOnline.addActionListener(this);
        viewAccount.addActionListener(this);
        cart.addActionListener(this);
        addToCart.addActionListener(this);
        removeFromCart.addActionListener(this);
        buy.addActionListener(this);
        logOut.addActionListener(this);
        food.addActionListener(this);
        personalHygiene.addActionListener(this);
        houseHold.addActionListener(this);
        perishabale.addActionListener(this);
        nonPersishable.addActionListener(this);
        confirmCartButton.addActionListener(this);
        placeOrder.addActionListener(this);
        buyFromCart.addActionListener(this);
        searchFood.addActionListener(this);

        customerLeftBar.setVisible(false);


        foodCenterPanel.setVisible(false);
        customerFooter.setVisible(false);
        foodFilter.setVisible(false);
        grandTotalPanel.setVisible(false);


        customerPanel.add(customerLeftBar, BorderLayout.WEST);
        customerPanel.add(customerNavBar, BorderLayout.NORTH);
        customerPanel.add(imageContainer, BorderLayout.CENTER);
        customerPanel.setVisible(true);

    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void actionPerformed(ActionEvent e) {
        imageContainer.setVisible(false);
        customerFooter.remove(buy);
        customerFooter.remove(addToCart);
        customerFooter.remove(buyFromCart);
        customerFooter.remove(removeFromCart);


        grandTotalPanel.setVisible(false);
        customerFooter.setVisible(false);
        foodFilter.setVisible(false);
        foodCenterPanel.removeAll();

        if (e.getSource() == shopOnline) {

            customerPanel.revalidate();

            foodCenterPanel.setVisible(false);


            customerLeftBar.setVisible(true);


            customerPanel.revalidate();


        } else if (e.getSource() == food) {

            foodFilter.setVisible(true);
            customerFooter.setVisible(true);

            customerFooter.add(buy);
            customerFooter.add(addToCart);

            customerPanel.revalidate();

            customerLeftBar.setVisible(true);
            foodCenterPanel.setVisible(true);

            try {
                String location = currentLoggedInCustomerLocation + "Store";
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                String query = "SELECT product_id, name,brand, price, Weight_units FROM " + location + " WHERE category = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "Food");
                ResultSet result = preparedStatement.executeQuery();


                model = buildTableModel(result);


            } catch (Exception e1) {

                System.out.println(e1.getMessage());
            }
            foodTable = new JTable(model);

            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            foodTable.getTableHeader().setDefaultRenderer(headerRenderer);

            foodScrollPane = new JScrollPane(foodTable);
            foodScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane
            foodCenterPanel.add(foodScrollPane);


            customerPanel.add(foodCenterPanel, BorderLayout.CENTER);
            customerPanel.add(customerFooter, BorderLayout.SOUTH);


            customerPanel.revalidate();


        } else if (e.getSource() == searchFood) {

            String productID = JOptionPane.showInputDialog(null, "Enter Product ID of item ");

            while (productID == null) {
                productID = JOptionPane.showInputDialog(null, "No input enter Again :");

            }

            try {
                String location = currentLoggedInCustomerLocation + "Store";
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                String query = "SELECT product_id, name,brand, price, Weight_units FROM " + location + " WHERE product_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(productID));

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int product_id = resultSet.getInt("product_id");
                    String name = resultSet.getString("name");
                    String brand = resultSet.getString("brand");
                    int price = resultSet.getInt("price");
                    String weight_units = resultSet.getString("Weight_units");
                    JOptionPane.showMessageDialog(null, "Item found\n +" + "Product ID:" + product_id + "\nProduct Name :" + name + "\nProduct Brand" + brand + "\nProduct Price :" + price + "\nproduct WeightUnits  :" + weight_units);

                } else {
                    JOptionPane.showMessageDialog(null, "Item not available with this Product ID");
                }


            } catch (Exception e1) {
                e1.printStackTrace();
            }


        } else if (e.getSource() == perishabale) {


            foodFilter.setVisible(true);
            customerFooter.setVisible(true);

            customerFooter.add(buy);
            customerFooter.add(addToCart);


            customerPanel.revalidate();
            // foodNavBar.setVisible(true);
            customerPanel.revalidate();

            foodCenterPanel.setVisible(true);

            customerLeftBar.setVisible(true);

            try {
                String location = currentLoggedInCustomerLocation + "Store";
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                String query = "SELECT product_id, name,brand, price, Weight_units FROM " + location + " WHERE type = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "perishable");
                ResultSet result = preparedStatement.executeQuery();


                model = buildTableModel(result);
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }

            foodTable = new JTable(model);


            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            foodTable.getTableHeader().setDefaultRenderer(headerRenderer);

            foodScrollPane = new JScrollPane(foodTable);
            foodScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane

            foodCenterPanel.add(foodScrollPane);


            customerPanel.add(foodCenterPanel, BorderLayout.CENTER);
            customerPanel.add(customerFooter, BorderLayout.SOUTH);

            customerPanel.revalidate();

        } else if (e.getSource() == nonPersishable) {


            foodFilter.setVisible(true);
            customerFooter.setVisible(true);
            customerPanel.revalidate();

            customerFooter.add(buy);
            customerFooter.add(addToCart);


            foodCenterPanel.setVisible(true);


            customerLeftBar.setVisible(true);

            try {
                String location = currentLoggedInCustomerLocation + "Store";
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                String query = "SELECT product_id, name,brand, price, Weight_units FROM " + location + " WHERE type = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "non-perishable");
                ResultSet result = preparedStatement.executeQuery();


                model = buildTableModel(result);
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }


            foodTable = new JTable(model);


            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            foodTable.getTableHeader().setDefaultRenderer(headerRenderer);

            foodScrollPane = new JScrollPane(foodTable);
            foodScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane

            foodCenterPanel.add(foodScrollPane);


            customerPanel.add(foodCenterPanel, BorderLayout.CENTER);

            customerPanel.revalidate();

        } else if (e.getSource() == personalHygiene) {


            customerFooter.setVisible(true);

            customerFooter.add(buy);
            customerFooter.add(addToCart);

            foodCenterPanel.setVisible(true);

            customerLeftBar.setVisible(true);

            try {
                String location = currentLoggedInCustomerLocation + "Store";
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                String query = "SELECT product_id,name,brand, price, Weight_units FROM " + location + " WHERE category = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "Personal Hygiene");
                ResultSet result = preparedStatement.executeQuery();


                model = buildTableModel(result);
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }


            foodTable = new JTable(model);

            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            foodTable.getTableHeader().setDefaultRenderer(headerRenderer);

            foodScrollPane = new JScrollPane(foodTable);
            foodScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane

            foodCenterPanel.add(foodScrollPane);


            customerPanel.revalidate();


            customerPanel.add(foodCenterPanel, BorderLayout.CENTER);
            customerPanel.revalidate();

        } else if (e.getSource() == houseHold) {

            model.setColumnCount(0);
            model.setRowCount(0);


            customerPanel.revalidate();
            customerFooter.setVisible(true);

            customerFooter.add(buy);
            customerFooter.add(addToCart);

            foodCenterPanel.setVisible(true);

            customerLeftBar.setVisible(true);

            try {
                String location = currentLoggedInCustomerLocation + "Store";
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                String query = "SELECT product_id,name,brand, price, Weight_units FROM " + location + " WHERE category = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "Household Cleaning");
                ResultSet result = preparedStatement.executeQuery();


                model = buildTableModel(result);
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }


            foodTable = new JTable(model);


            // Custom header renderer to change the color
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(Color.BLUE);
            headerRenderer.setForeground(Color.WHITE);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            foodTable.getTableHeader().setDefaultRenderer(headerRenderer);

            foodScrollPane = new JScrollPane(foodTable);
            foodScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane

            foodCenterPanel.add(foodScrollPane);


            customerPanel.revalidate();
            customerPanel.add(foodCenterPanel, BorderLayout.CENTER);
            customerPanel.revalidate();

        } else if (e.getSource() == viewAccount) {
            customerPanel.revalidate();
            customerFooter.setVisible(false);

            foodCenterPanel.setVisible(false);

            customerLeftBar.setVisible(false);

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                PreparedStatement preparedStatement = connection.prepareStatement("Select first_name,last_name,username,cnic,contact_no,email From Customer where customer_id = ?");
                preparedStatement.setInt(1, customerID);

                ResultSet resultSet = preparedStatement.executeQuery();
                String first_name = null;
                String last_name = null;
                String username = null;
                String cnic = null;
                String contact_no = null;
                String email = null;
                while (resultSet.next()) {
                    first_name = resultSet.getString("first_name");
                    last_name = resultSet.getString("last_name");
                    username = resultSet.getString("username");
                    cnic = resultSet.getString("cnic");
                    contact_no = resultSet.getString("contact_no");
                    email = resultSet.getString("email");

                }
                JOptionPane.showMessageDialog(null, "Full Name : " + first_name + " " + last_name + "\nUsername :" + username + "\nEmail        : " + email + "\nContact :" + contact_no + "\nCNIC  : " + cnic);


            } catch (Exception e1) {
                System.out.println("line 538\n" + e1.getMessage());
            }

            customerPanel.revalidate();


        } else if (e.getSource() == cart) {
            int grandTotal = 0;

            try {

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");

                PreparedStatement cartStatement = connection.prepareStatement("SELECT product_id,productName, ProductPrice, ProductUnits, quantity FROM Cart WHERE Customer_customer_id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                cartStatement.setInt(1, customerID);
                ResultSet cartResultSet = cartStatement.executeQuery();

                model = buildTableModel(cartResultSet);

                cartResultSet.beforeFirst();

                while (cartResultSet.next()) {
                    int price = cartResultSet.getInt("ProductPrice");
                    int quantity = cartResultSet.getInt("quantity");
                    System.out.println("Price: " + price + " Quantity: " + quantity);
                    grandTotal += quantity * price;
                    System.out.println(grandTotal);
                }


                System.out.println(grandTotal);
                grandTotalLabel.setText("Grand Total: " + grandTotal);


                grandTotalPanel.setVisible(true);

                customerPanel.revalidate();

                customerFooter.setVisible(true);

                customerFooter.add(buyFromCart);
                customerFooter.add(removeFromCart);

                customerPanel.revalidate();

                foodCenterPanel.setVisible(true);

                customerLeftBar.setVisible(false);


                foodTable = new JTable(model);


                // Custom header renderer to change the color
                DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
                headerRenderer.setBackground(Color.BLUE);
                headerRenderer.setForeground(Color.WHITE);
                headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                foodTable.getTableHeader().setDefaultRenderer(headerRenderer);

                foodScrollPane = new JScrollPane(foodTable);
                foodScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane

                foodCenterPanel.add(foodScrollPane, BorderLayout.CENTER);


                customerPanel.add(foodCenterPanel, BorderLayout.CENTER);
                customerPanel.add(customerFooter, BorderLayout.SOUTH);


                customerPanel.revalidate();


            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }


        } else if (e.getSource() == addToCart) {
            customerFooter.add(buy);
            customerFooter.add(addToCart);

            customerFooter.setVisible(true);

            cartFrame = new JFrame();
            JLabel productIDLabel = new JLabel("Product ID ");
            productIDTextField = new JTextField();
            JLabel quantityLabel = new JLabel("Product Quantity ");
            quantityTextField = new JTextField();


            cartFrame.setSize(200, 200);
            productIDLabel.setBounds(20, 20, 150, 20);
            productIDTextField.setBounds(20, 50, 150, 20);
            quantityLabel.setBounds(20, 80, 170, 20);
            quantityTextField.setBounds(20, 110, 150, 20);
            confirmCartButton.setBounds(45, 140, 100, 20);
            cartFrame.setLocationRelativeTo(null);
            cartFrame.setLayout(null);
            cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cartFrame.setVisible(true);
            cartFrame.add(productIDLabel);
            cartFrame.add(productIDTextField);
            cartFrame.add(quantityLabel);
            cartFrame.add(quantityTextField);
            cartFrame.add(confirmCartButton);


        } else if (e.getSource() == confirmCartButton) {
            customerFooter.add(buy);
            customerFooter.add(addToCart);

            customerFooter.setVisible(true);

            int product_ID = Integer.parseInt(productIDTextField.getText());
            String Quantity = quantityTextField.getText();


            if (!productIDTextField.getText().isEmpty() && !quantityTextField.getText().isEmpty()) {

                if (product_ID > 0 && product_ID < 31) {
                    if (Integer.parseInt(Quantity) > 0) {
                        try {
                            String location = currentLoggedInCustomerLocation + "Store";
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                            String selectQuery = "SELECT product_id, name, price, Weight_units FROM " + location + " WHERE product_id = ?";
                            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                            selectStatement.setInt(1, product_ID);
                            ResultSet result = selectStatement.executeQuery();

                            // Assuming you have another table named "AnotherTable" with columns name, price, and Weight_units
                            String insertQuery = "INSERT INTO Cart (productName, ProductPrice, ProductUnits,Customer_customer_id,quantity,product_id) VALUES (?, ?, ?,?,?,?)";
                            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                            // Iterate through the result set and insert each row into AnotherTable
                            while (result.next()) {
                                String name = result.getString("name");
                                int price = result.getInt("price");
                                String weightUnits = result.getString("Weight_units");
                                int product_id = result.getInt("product_id");

                                // Set values for the insert statement
                                insertStatement.setString(1, name);
                                insertStatement.setInt(2, price);
                                insertStatement.setString(3, weightUnits);
                                insertStatement.setInt(4, customerID);
                                insertStatement.setInt(5, Integer.parseInt(Quantity));
                                insertStatement.setInt(6, product_id);


                                // Execute the insert statement
                                int rowAffected = insertStatement.executeUpdate();
                                if (rowAffected > 0) {
                                    System.out.println("Inserted");
                                } else {
                                    System.out.println("Not inserted");
                                }
                            }


                            // Close resources
                            result.close();
                            selectStatement.close();
                            insertStatement.close();
                            connection.close();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            System.out.println("line 722 " + e1.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Quantity must be greater than 0");

                    }
                    cartFrame.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "Product does not exist");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Some of the fields are empty");

            }

        } else if (e.getSource() == removeFromCart) {
            customerFooter.add(buyFromCart);
            customerFooter.add(removeFromCart);

            customerFooter.setVisible(true);
            String removeProduct = JOptionPane.showInputDialog(null, "Enter Product ID:");

            if (removeProduct != null) {
                if (Integer.parseInt(removeProduct) > 0 && Integer.parseInt(removeProduct) < 31) {

                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                        String selectQuery = "Delete from Cart where product_id =  ?";
                        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                        selectStatement.setInt(1, Integer.parseInt(removeProduct));
                        int rowAffected = selectStatement.executeUpdate();

                        if (rowAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Product removed");

                        } else {
                            JOptionPane.showMessageDialog(null, "Something went wrong");

                        }
                    } catch (Exception e1) {
                        System.out.println("line 793 " + e1.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid ID");

                }
            } else {
                JOptionPane.showMessageDialog(null, "No input");
            }


        } else if (e.getSource() == buy) {
            customerFooter.add(buy);
            customerFooter.add(addToCart);

            customerFooter.setVisible(true);

            cartFrame = new JFrame();
            cartFrame.setSize(200, 200);
            cartFrame.setTitle("Place Order");
            cartFrame.setLayout(null);
            cartFrame.setLocationRelativeTo(null);
            cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


            JLabel productIDLabel = new JLabel("Product ID ");
            JLabel quantityLabel = new JLabel("Enter Quantity ");

            quantityTextField = new JTextField();
            productIDTextField = new JTextField();


            productIDLabel.setBounds(20, 20, 150, 20);
            quantityLabel.setBounds(20, 80, 170, 20);


            productIDTextField.setBounds(20, 45, 150, 20);
            quantityTextField.setBounds(20, 105, 150, 20);
            placeOrder.setBounds(25, 135, 150, 20);


            cartFrame.setVisible(true);
            cartFrame.add(productIDLabel);
            cartFrame.add(productIDTextField);
            cartFrame.add(quantityLabel);
            cartFrame.add(quantityTextField);
            cartFrame.add(placeOrder);


        } else if (e.getSource() == placeOrder) {
            customerFooter.add(buy);
            customerFooter.add(addToCart);
            customerFooter.setVisible(true);

            String productIDText = productIDTextField.getText();
            String brandNameQuantity = quantityTextField.getText();

            if (!productIDText.isEmpty() && !brandNameQuantity.isEmpty()) {
                int productID = Integer.parseInt(productIDText);
                int brandQuantity = Integer.parseInt(brandNameQuantity);

                if (productID > 0 && productID < 31 && brandQuantity > 0) {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");

                        String selectQuery = "SELECT * FROM " + currentLoggedInCustomerLocation + "Store WHERE product_id=?";
                        PreparedStatement selectStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        selectStatement.setInt(1, productID);
                        ResultSet resultSet = selectStatement.executeQuery();
                        int storeID = 0;
                        String name = null;
                        String brandName = null;
                        int total = 0;
                        int price = 0;
                        int storeQuantity = 0;
                        while (resultSet.next()) {
                            price = resultSet.getInt("price");
                            name = resultSet.getString("name");
                            brandName = resultSet.getString("brand");
                            storeQuantity = resultSet.getInt("quantity");
                            total = price * brandQuantity;
                        }

                        int choice = JOptionPane.showConfirmDialog(cartFrame, "Do you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.YES_OPTION) {
                            int updatedQuantity = storeQuantity - brandQuantity;
                            updatedQuantity = Math.max(updatedQuantity, 0);

                            String updateQuery = "UPDATE " + currentLoggedInCustomerLocation + "Store SET quantity = ? WHERE product_id = ?";
                            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                            updateStatement.setInt(1, updatedQuantity);
                            updateStatement.setInt(2, productID);
                            updateStatement.executeUpdate();

                            PreparedStatement storeStatement = connection.prepareStatement("Select store_id from Store where location = ?");
                            storeStatement.setString(1, currentLoggedInCustomerLocation);
                            ResultSet store = storeStatement.executeQuery();

                            if (store.next()) {
                                storeID = store.getInt("store_id");

                            }
                            Timestamp timestamp = new Timestamp(new Date().getTime());

                            String insertINTOOrderQuery = "INSERT INTO Orders(order_date,product_name,quantity,price,total_amount,Store_store_id,Customer_customer_id) values (?,?,?,?,?,?,?)";
                            PreparedStatement insertINTOOrder = connection.prepareStatement(insertINTOOrderQuery);
                            insertINTOOrder.setTimestamp(1, timestamp);
                            insertINTOOrder.setString(2, name);
                            insertINTOOrder.setInt(3, brandQuantity);
                            insertINTOOrder.setInt(4, price);
                            insertINTOOrder.setInt(5, total);
                            insertINTOOrder.setInt(6, storeID);
                            insertINTOOrder.setInt(7, customerID);

                            int rowAffected = insertINTOOrder.executeUpdate();
                            if (rowAffected > 0) {
                                System.out.println("order table updated");
                            } else {
                                System.out.println("order not table updated");

                            }

                            JOptionPane.showMessageDialog(cartFrame, "Product Bought Successfully\nProduct Name: " + name + "\nProduct Brand: " + brandName + "\nQuantity: " + brandQuantity + "\nTotal Bill: " + total);
                            cartFrame.dispose();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Something went wrong");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Quantity error");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Required fields are empty");
            }
        } else if (e.getSource() == buyFromCart) {
            customerFooter.add(buyFromCart);
            customerFooter.add(removeFromCart);
            customerFooter.setVisible(true);
            double bill = 0;
            int storeID = 0;
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryManagementSystem", "root", "Program@123");
                String selectQuery = "SELECT * FROM Cart WHERE Customer_customer_id=?";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                selectStatement.setInt(1, customerID);
                ResultSet resultSet = selectStatement.executeQuery();

                while (resultSet.next()) {
                    int price = resultSet.getInt("ProductPrice");
                    int quantity = resultSet.getInt("quantity");
                    String name = resultSet.getString("productName");
                    int invivdualTotal =price * quantity;

                    bill += price * quantity;

                    String query = "SELECT * FROM " + currentLoggedInCustomerLocation + "Store WHERE product_id=?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, resultSet.getInt("product_id"));
                    ResultSet resultSet1 = statement.executeQuery();

                    // Check if resultSet1 has any rows before trying to access columns
                    if (resultSet1.next()) {
                        int updatedQuantity = resultSet1.getInt("quantity") - quantity;
                        updatedQuantity = Math.max(updatedQuantity, 0);

                        String updateQuery = "UPDATE " + currentLoggedInCustomerLocation + "Store SET quantity = ? WHERE product_id = ?";
                        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                        updateStatement.setInt(1, updatedQuantity);
                        updateStatement.setInt(2, resultSet.getInt("product_id"));
                        int rowAffected = updateStatement.executeUpdate();

                        if (rowAffected > 0) {
                            System.out.println("Quantity updated");
                            updateQuery = "delete from  Cart  WHERE product_id = ?";
                            updateStatement = connection.prepareStatement(updateQuery);

                            updateStatement.setInt(1, resultSet.getInt("product_id"));
                            updateStatement.executeUpdate();

                            PreparedStatement storeStatement = connection.prepareStatement("Select store_id from Store where location = ?");
                            storeStatement.setString(1, currentLoggedInCustomerLocation);
                            ResultSet store = storeStatement.executeQuery();

                            if (store.next()) {
                                storeID = store.getInt("store_id");

                            }
                            Timestamp timestamp = new Timestamp(new Date().getTime());

                            String insertINTOOrderQuery = "INSERT INTO Orders(order_date,product_name,quantity,price,total_amount,Store_store_id,Customer_customer_id) values (?,?,?,?,?,?,?)";
                            PreparedStatement insertINTOOrder = connection.prepareStatement(insertINTOOrderQuery);
                            insertINTOOrder.setTimestamp(1, timestamp);
                            insertINTOOrder.setString(2, name);
                            insertINTOOrder.setInt(3, quantity);
                            insertINTOOrder.setInt(4, price);
                            insertINTOOrder.setInt(5, invivdualTotal);
                            insertINTOOrder.setInt(6, storeID);
                            insertINTOOrder.setInt(7, customerID);

                            int rowAffected_01 = insertINTOOrder.executeUpdate();
                            if (rowAffected_01 > 0) {
                                System.out.println("order table updated from cart");
                            } else {
                                System.out.println("order not table updated from cart");

                            }

                        } else {
                            System.out.println("Not updated");
                        }
                    }
                }

                JOptionPane.showMessageDialog(null, "Grand Bill: " + bill + "\nThanks for Shopping");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == logOut) {
            customerPanel.dispose();
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
        new Customer_Panel("lahore", 1);
    }

}
