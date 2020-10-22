package com.vonblum.vicabilling.app.controller;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.data.model.BillSettings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends AnchorPane implements Initializable {

    @FXML
    private BillSettingsController billSettingsController;

    @FXML
    private CompanyController companyController;

    @FXML
    private CustomerController customerController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context.setSettingsController(this);
    }

    public void setBillSettings(BillSettings billSettings) {
        Context.setBillSettings(billSettings);
        customerController.showCustomer(billSettings.getCustomer());
        billSettingsController.showBillSettings(billSettings);
    }

    public BillSettingsController getBillSettingsController() {
        return billSettingsController;
    }

    public CompanyController getCompanyController() {
        return companyController;
    }

    public CustomerController getCustomerController() {
        return customerController;
    }

}
