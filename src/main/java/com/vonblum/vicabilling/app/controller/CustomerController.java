package com.vonblum.vicabilling.app.controller;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.data.exception.ProgramException;
import com.vonblum.vicabilling.app.data.model.Customer;
import com.vonblum.vicabilling.app.service.ViewPathBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController extends AnchorPane implements Initializable {

    private ResourceBundle resourceBundle;

    @FXML
    private Label nameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label webpageLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextArea addressArea;

    @FXML
    private TextField nameField;

    @FXML
    private TextField webpageField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    public CustomerController() {
        try {
            resourceBundle = Context.getResourceBundle();

            String entitySimpleName = Customer.class.getSimpleName();
            URL fxmlView = ViewPathBuilder.getViewURLFromEntity(entitySimpleName, this);

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(fxmlView);
            fxmlLoader.setResources(resourceBundle);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            fxmlLoader.load();
        } catch (IOException e) {
            ProgramException.showDefaultError();
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context.setCustomerController(this);
        showCustomer(Context.getBillSettings().getCustomer());
    }

    public void showCustomer(Customer customer) {
        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
        phoneField.setText(customer.getPhone());
        webpageField.setText(customer.getWebpage());
        addressArea.setText(customer.getAddress());
    }

    public Customer getCustomer() {
        String name = nameField.getText().trim();
        String address = addressArea.getText().trim();
        String webpage = webpageField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setWebpage(webpage);
        customer.setPhone(phone);
        customer.setEmail(email);
        return customer;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void clear() {
        Customer customer = Customer.getStandardCustomer();
        nameField.setText(customer.getName());
        addressArea.setText(customer.getAddress());
        webpageField.setText(customer.getWebpage());
        phoneField.setText(customer.getPhone());
        emailField.setText(customer.getEmail());
    }
}
