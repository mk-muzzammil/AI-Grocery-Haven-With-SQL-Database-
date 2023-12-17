package GUI;

import DatabaseConnection.Connection_DB;
import Login_SignUp_Validation.credentialsVerification;
import Stores.MyStore;
import Users.Manager;
import Users.UserWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import ProductCatalogueManagement.*;

public class Manage_Manager extends JFrame implements ActionListener {
    JFrame managerFrame;
    JLabel first_name_label, last_name_label, emailLabel, salaryLabel, storeLocationLabel, usernameLabel, passwordLabel, genderLabel, cnicLabel, contact_noLabel, addressLabel;
    JTextField first_name_TextField, last_name_TextField, genderTextField, cnicTextField, contactNoTextField, addressTextFIeld, emailTextField, salaryTextField, storeLocationTextField, usernameTextField;
    JPasswordField passwordField;
    JButton ADD, updateManager;
    String ID;

    public Manage_Manager() {

    }

    public Manage_Manager(String ID) {
        this.ID = ID;
    }

    public void addManagerFrame() {

        managerFrame = new JFrame("Store Creation");
        managerFrame.setSize(400, 340);
        managerFrame.setLayout(null);
        managerFrame.setLocationRelativeTo(null);
        managerFrame.getContentPane().setBackground(Color.WHITE);
        managerFrame.setResizable(false);

        first_name_label = new JLabel("First Name: ");
        last_name_label = new JLabel("Last Name: ");
        genderLabel = new JLabel("Gender: ");
        cnicLabel = new JLabel("CNIC: ");
        contact_noLabel = new JLabel("Contact Number: ");
        addressLabel = new JLabel("Address: ");
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");

        emailLabel = new JLabel("Email: ");
        salaryLabel = new JLabel("Salary: ");
        storeLocationLabel = new JLabel("Store Location: ");

        first_name_label.setBounds(60, 10, 80, 20);
        last_name_label.setBounds(220, 10, 80, 20);

        genderLabel.setBounds(60, 60, 80, 20);
        cnicLabel.setBounds(220, 60, 80, 20);


        contact_noLabel.setBounds(60, 110, 120, 20);
        emailLabel.setBounds(220, 110, 55, 20);

        salaryLabel.setBounds(60, 160, 50, 20);
        storeLocationLabel.setBounds(220, 160, 100, 20);

        addressLabel.setBounds(60, 210, 60, 20);
        usernameLabel.setBounds(220, 210, 100, 20);

        passwordLabel.setBounds(60, 260, 100, 20);


        first_name_TextField = new JTextField();
        last_name_TextField = new JTextField();
        emailTextField = new JTextField();
        salaryTextField = new JTextField();
        storeLocationTextField = new JTextField();
        usernameTextField = new JTextField();
        passwordField = new JPasswordField();

        genderTextField = new JTextField();
        cnicTextField = new JTextField();
        contactNoTextField = new JTextField();
        addressTextFIeld = new JTextField();


        first_name_TextField.setBounds(60, 30, 120, 20);
        last_name_TextField.setBounds(220, 30, 120, 20);

        genderTextField.setBounds(60, 80, 120, 20);
        cnicTextField.setBounds(220, 80, 120, 20);

        contactNoTextField.setBounds(60, 130, 120, 20);
        emailTextField.setBounds(220, 130, 120, 20);

        salaryTextField.setBounds(60, 180, 120, 20);
        storeLocationTextField.setBounds(220, 180, 120, 20);

        addressTextFIeld.setBounds(60, 230, 120, 20);
        usernameTextField.setBounds(220, 230, 120, 20);

        passwordField.setBounds(60, 280, 120, 20);


        JPanel ADDPanel = new JPanel();
        ADDPanel.setBackground(Color.BLACK);
        ADD = new customButton("Create");

        ADDPanel.add(ADD);
        ADDPanel.setBounds(0, 310, managerFrame.getWidth(), 30);
        ADD.addActionListener(this);

        managerFrame.add(first_name_label);
        managerFrame.add(last_name_label);
        managerFrame.add(genderLabel);
        managerFrame.add(cnicLabel);
        managerFrame.add(contact_noLabel);

        managerFrame.add(usernameLabel);
        managerFrame.add(passwordLabel);
        managerFrame.add(emailLabel);
        managerFrame.add(salaryLabel);
        managerFrame.add(storeLocationLabel);
        managerFrame.add(addressLabel);

        managerFrame.add(first_name_TextField);
        managerFrame.add(last_name_TextField);
        managerFrame.add(genderTextField);
        managerFrame.add(cnicTextField);
        managerFrame.add(contactNoTextField);
        managerFrame.add(addressTextFIeld);

        managerFrame.add(usernameTextField);
        managerFrame.add(passwordField);
        managerFrame.add(emailTextField);
        managerFrame.add(salaryTextField);
        managerFrame.add(storeLocationTextField);
        managerFrame.add(ADDPanel);
        managerFrame.setVisible(true);

    }

//    public void updateManagerFrame() {
//
//        managerFrame = new JFrame("Update Manager");
//        managerFrame.setSize(300, 270);
//        managerFrame.setLayout(null);
//        managerFrame.setLocationRelativeTo(null);
//        managerFrame.getContentPane().setBackground(Color.WHITE);
//        managerFrame.setResizable(false);
//
//        first_name_label = new JLabel("Name: ");
//        usernameLabel = new JLabel("Username: ");
//        passwordLabel = new JLabel("Password: ");
//
//        emailLabel = new JLabel("Email: ");
//        salaryLabel = new JLabel("Salary: ");
//        storeLocationLabel = new JLabel("Store: ");
//
//        nameLabel.setBounds(16, 35, 50, 20);
//        usernameLabel.setBounds(10, 60, 80, 20);
//        passwordLabel.setBounds(16, 85, 80, 20);
//        emailLabel.setBounds(16, 110, 50, 20);
//        salaryLabel.setBounds(10, 135, 55, 20);
//        storeLocationLabel.setBounds(16, 160, 50, 20);
//
//        nameTextField = new JTextField();
//        emailTextField = new JTextField();
//        salaryTextField = new JTextField();
//        storeLocationTextField = new JTextField();
//        usernameTextField = new JTextField();
//        passwordField = new JTextField();
//
//
//        nameTextField.setBounds(100, 35, 120, 20);
//        usernameTextField.setBounds(100, 60, 120, 20);
//        passwordField.setBounds(100, 85, 120, 20);
//
//        emailTextField.setBounds(100, 110, 120, 20);
//        salaryTextField.setBounds(100, 135, 120, 20);
//        storeLocationTextField.setBounds(100, 160, 120, 20);
//
//        JPanel ADDPanel = new JPanel();
//        ADDPanel.setBackground(Color.BLACK);
//        updateManager = new customButton("Update");
//
//        ADDPanel.add(updateManager);
//        ADDPanel.setBounds(0, 230, managerFrame.getWidth(), 30);
//        updateManager.addActionListener(this);
//
//        managerFrame.add(nameLabel);
//        managerFrame.add(usernameLabel);
//        managerFrame.add(passwordLabel);
//        managerFrame.add(emailLabel);
//        managerFrame.add(salaryLabel);
//        managerFrame.add(storeLocationLabel);
//
//        managerFrame.add(nameTextField);
//        managerFrame.add(usernameTextField);
//        managerFrame.add(passwordField);
//        managerFrame.add(emailTextField);
//        managerFrame.add(salaryTextField);
//        managerFrame.add(storeLocationTextField);
//        managerFrame.add(ADDPanel);
//        managerFrame.setVisible(true);
//
//    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == ADD) {

            String first_name = first_name_TextField.getText();
            String last_name = last_name_TextField.getText();
            String gender = genderTextField.getText();
            String cnic = cnicTextField.getText();
            String contactNo = contactNoTextField.getText();
            String address = addressTextFIeld.getText();
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            String email = emailTextField.getText();
            String salary = salaryTextField.getText();
            String manager_at = storeLocationTextField.getText().toLowerCase();

            if (!first_name.isEmpty() && !last_name.isEmpty() && !gender.isEmpty() && !cnic.isEmpty() && !contactNo.isEmpty() && !address.isEmpty() && !username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !salary.isEmpty() && !manager_at.isEmpty()) {


                Connection_DB addManager = new Connection_DB();
                int response = addManager.createStore(first_name, last_name, username, password, gender, cnic, contactNo, email, address, manager_at, Integer.parseInt(salary));

                if (response == 2) {
                    JOptionPane.showMessageDialog(null, "Something went wrong");
                } else if (response == 3) {
                    JOptionPane.showMessageDialog(null, "Username already exists.");
                } else if (response == 4) {
                    JOptionPane.showMessageDialog(null, "email number already exists.");
                } else if (response == 5) {
                    JOptionPane.showMessageDialog(null, "cnic already exists.");
                } else if (response == 7 || response == 6) {
                    JOptionPane.showMessageDialog(null, "something went wrong");
                }
                else if (response == 3) {
                    JOptionPane.showMessageDialog(null, "Username already exists.");
                }
                else if (response == 1) {
                    JOptionPane.showMessageDialog(null, "Store has been created");

                    managerFrame.dispose();

                }
            } else {
                JOptionPane.showMessageDialog(null, "Some of the fields are empty");
            }

        } else if (e.getSource() == updateManager) {
            updateManager(ID);
            managerFrame.dispose();
        }
    }

    public void addingStore() {


        ArrayList<Manager> managerList = new ArrayList<>();
        managerList = UserWriter.LoadArrayList(managerList);

        ArrayList<MyStore> storeList = new ArrayList<>();
        storeList = UserWriter.LoadStoresList(storeList);

        if (storeList != null) {
            credentialsVerification c = new credentialsVerification();
//            if (c.isUniqueManager(ID)) {
//                if (c.isUniqueManager(first_name_label.paramString())) {
//                    if (c.isUniqueManager(email)) {
//
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Manager email already exists.");
//                        return;
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "Manager username already exists.");
//                    return;
//                }
//
//            } else {
//                JOptionPane.showMessageDialog(null, "Manager ID already exists.");
//                return;
//            }
        } else if (storeList == null) {
            storeList = new ArrayList<>();
        }

//        MyStore newStore = new MyStore(manager, location);
//        newStore.createStore();
//        storeList.add(newStore);
        UserWriter.WriteToFileStores(storeList);
    }

    public void updateManager(String ID) {
        //getting data to update
        String username = usernameTextField.getText();
        String password = passwordField.getText();
//        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String salary = salaryTextField.getText();
        String location = storeLocationTextField.getText();

        //Loading already existing manager file to update required manager
        ArrayList<Manager> managersList = new ArrayList<>();
        managersList = UserWriter.LoadArrayList(managersList);

        boolean isUpdate = false;
        int i = 0;
        for (Manager m : managersList) {
            if (m.getId().equals(ID)) {
                isUpdate = true;
                m.setUsername(username);
                m.setPassword(password);
//                m.setFirstName(name);
                m.setLastName("");
                m.setEmail(email);
                m.setSalary(salary);
                m.setCity(location);
                managersList.set(i, m);
                UserWriter.WriteToFile(managersList);
                JOptionPane.showMessageDialog(null, "Manager has been updated");
                break;
            }
            i++;

        }
        if (!isUpdate) {
            JOptionPane.showMessageDialog(null, "Manager has not been updated");
            return;
        }

    }

    public void deleteManager() {
        String id = JOptionPane.showInputDialog(null, "Enter Manager ID: ");
        if (id != null) {
            ArrayList<Manager> managersList = new ArrayList<>();
            managersList = UserWriter.LoadArrayList(managersList);
            if (managersList == null) {
                JOptionPane.showMessageDialog(null, "No Manager exists with this ID");
                return;
            }
            boolean found = false;
            for (Manager manager : managersList) {
                if (manager.getId().equalsIgnoreCase(id)) {
                    found = true;
                    managersList.remove(manager);
                    UserWriter.WriteToFile(managersList);
                    break;
                }
            }
            if (found == false) {
                JOptionPane.showMessageDialog(null, "No Manager exists with this ID");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No ID entered");
        }

    }


    public static void main(String[] args) {
        Manage_Manager m = new Manage_Manager();
        m.addManagerFrame();
    }
}

