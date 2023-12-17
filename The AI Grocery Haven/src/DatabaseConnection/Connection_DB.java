package DatabaseConnection;

import java.security.spec.ECField;
import java.sql.*;

public class Connection_DB {
    public static final String url = "jdbc:mysql://localhost:3306/GroceryManagementSystem";
    public static final String username = "root";
    public static final String password = "Program@123";
    public static Connection connection;
    public static Statement statement;
    public static String tableName;
    public static String holdManagerLocation;
    public static String holdCustomerLocation;
    public static int holdCustomerID;



    public Connection_DB() {
        databaseConnection();

    }

    public static void databaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static boolean verifyLogin(String username, String password, String type) {
        if (type.equalsIgnoreCase("admin")) {
            System.out.println("Flag 1");
            try {
                ResultSet result = statement.executeQuery("Select * From Admin");
                String dbUserName = null;
                String dbPassword = null;
                while (result.next()) {
                    dbUserName = result.getString("username");
                    dbPassword = result.getString("password");
                }
                if (username.equalsIgnoreCase(dbUserName) && password.equalsIgnoreCase(dbPassword)) {
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.getMessage();
            }
        } else if (type.equalsIgnoreCase("manager")) {
            try {
                String query = "SELECT * FROM Manager WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);

                ResultSet result = preparedStatement.executeQuery();

                String dbUserName = null;
                String dbPassword = null;

                while (result.next()) {
                    dbUserName = result.getString("username");
                    dbPassword = result.getString("password");
                    holdManagerLocation = result.getString("manager_at").toLowerCase();
                }

                return username.equalsIgnoreCase(dbUserName) && password.equalsIgnoreCase(dbPassword);

            } catch (Exception e) {
                e.printStackTrace(); // Print the stack trace for debugging
            }
        } else {
            try {
                String query = "SELECT * FROM Customer WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);

                ResultSet result = preparedStatement.executeQuery();

                String dbUserName = null;
                String dbPassword = null;

                while (result.next()) {
                    dbUserName = result.getString("username");
                    dbPassword = result.getString("password");
                    holdCustomerLocation = result.getString("address").toLowerCase();
                    holdCustomerID = result.getInt("customer_id");
                }

                return username.equalsIgnoreCase(dbUserName) && password.equalsIgnoreCase(dbPassword);

            } catch (Exception e) {
                e.printStackTrace(); // Print the stack trace for debugging
            }
        }
        return false;
    }

    public static String getManagerLocation() {
        return holdManagerLocation;
    }
    public static String getCustomerLocation() {
        return holdCustomerLocation;
    }
    public static int getCustomerID() {
        return holdCustomerID;
    }



    public static int addUser(String getFirstName, String getLastName, String getUsername, String getPassword, String gender, String getCNIC, String getMobileNumber, String getEmail, String getLocation, int store_id) {
        try {

            // Your SQL insertion statement
            String query = "INSERT INTO customer (first_name,last_name,username,password,gender,cnic,contact_no,email,address,Store_store_id) VALUES (?,?,?,?,?,?,?,?,?,?)";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set values for placeholders in the SQL statement
            preparedStatement.setString(1, getFirstName);
            preparedStatement.setString(2, getLastName);
            preparedStatement.setString(3, getUsername);
            preparedStatement.setString(4, getPassword);
            preparedStatement.setString(5, gender);

            preparedStatement.setString(6, getCNIC);
            preparedStatement.setString(7, getMobileNumber);
            preparedStatement.setString(8, getEmail);
            preparedStatement.setString(9, getLocation);
            preparedStatement.setInt(10, store_id);


            // ...

            // Execute the SQL statement
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return 1;

            } else {
                return 2;

            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle unique constraint violation
            String violatedConstraint = e.getMessage().toLowerCase();

            if (violatedConstraint.contains("username")) {
                return 3;

            } else if (violatedConstraint.contains("email")) {
                return 4;

            } else if (violatedConstraint.contains("cnic")) {
                return 5;

            } else {
                return 6;

            }
            // You can perform specific actions or logging here
        } catch (SQLException e) {
            // Handle other SQL exceptions
            e.printStackTrace();
        }
        return 7;
    }


    public static int createStore(String getFirstName, String getLastName, String getUsername, String getPassword, String gender, String getCNIC, String getMobileNumber, String getEmail, String getManagerLocation, String manager_at, int salary) {
        int managerID = 0;
        try {

            // Your SQL insertion statement
            String query = "INSERT INTO Manager (first_name,last_name,gender,cnic,contact_no,email,address,username,password,salary,manager_at,Admin_admin_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set values for placeholders in the SQL statement
            preparedStatement.setString(1, getFirstName);
            preparedStatement.setString(2, getLastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, getCNIC);
            preparedStatement.setString(5, getMobileNumber);

            preparedStatement.setString(6, getEmail);
            preparedStatement.setString(7, getManagerLocation);
            preparedStatement.setString(8, getUsername);
            preparedStatement.setString(9, getPassword);
            preparedStatement.setInt(10, salary);
            preparedStatement.setString(11, manager_at);
            preparedStatement.setInt(12, 1);


            // ...

            // Execute the SQL statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                try {
                    String getManagerIDQuery = "SELECT * FROM Manager WHERE username = ?";
                    preparedStatement = connection.prepareStatement(getManagerIDQuery);
                    preparedStatement.setString(1, getUsername);

                    ResultSet result = preparedStatement.executeQuery();

                    while (result.next()) {
                        managerID = result.getInt("manager_id");

                    }

                } catch (Exception e) {
                    e.printStackTrace(); // Print the stack trace for debugging
                }

                String query_02 = "INSERT INTO Store (location,Admin_admin_id,Manager_manager_id) VALUES (?,?,?)";
                preparedStatement = connection.prepareStatement(query_02);

                // Set values for placeholders in the SQL statement
                preparedStatement.setString(1, manager_at);
                preparedStatement.setInt(2, 1);
                preparedStatement.setInt(3, managerID);

                int rowsAffected_02 = preparedStatement.executeUpdate();
                if (rowsAffected_02 > 0) {


                    //creating store for manager
                    String tableNamePrefix = manager_at.toLowerCase();
                    tableName = tableNamePrefix + "Store";
                    String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                            + "product_id INT PRIMARY KEY UNIQUE, "
                            + "name VARCHAR(50) NOT NULL, "
                            + "brand VARCHAR(50) NOT NULL, "
                            + "price INT, "
                            + "Weight_units VARCHAR(10) NOT NULL, "
                            + "quantity INT, "
                            + "category VARCHAR(30), "
                            + "type VARCHAR(30))";


                    try (
                            // Establish the database connection
                            Connection connection = DriverManager.getConnection(url, username, password);

                            // Create a statement object
                            Statement statement = connection.createStatement()
                    ) {
                        // Execute the query to create the table
                        statement.executeUpdate(createTableQuery);

                        System.out.println("Table created successfully!");
                        if (!insertProductIntoNewStore()) {
                            throw new RuntimeException("This is a custom error message.");
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("Store not updated");

                }
                return 1;
            } else {
                return 2;

            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle unique constraint violation
            String violatedConstraint = e.getMessage().toLowerCase();

            if (violatedConstraint.contains("username")) {
                return 3;

            } else if (violatedConstraint.contains("email")) {
                return 4;

            } else if (violatedConstraint.contains("cnic")) {
                return 5;

            } else {
                return 6;

            }
            // You can perform specific actions or logging here
        } catch (SQLException e) {
            // Handle other SQL exceptions
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return 7;
    }

    public static boolean insertProductIntoNewStore() {

        String query = "INSERT INTO " + tableName + " (product_id, name, brand,price, Weight_units,quantity,category,type) SELECT product_id, name, brand,price,units,quantity,category,type FROM Products WHERE status = ?";

        try {
            Boolean status = true;
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, status);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
            return false;
        }
    }


    public static String[] getStoreLocations() {
        String[] locations;
        int i = 0;

        try {

            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = statement.executeQuery("SELECT location FROM Store");
            resultSet.last();

            int rows = resultSet.getRow();

            locations = new String[rows];
            resultSet.beforeFirst();

            while (resultSet.next()) {
                locations[i] = resultSet.getString("location");
                i++;
            }

            return locations;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        return new String[0];
    }


    public static void main(String[] args) {
Connection_DB obj = new Connection_DB();
        System.out.println(obj.insertProductIntoNewStore());
    }
}
