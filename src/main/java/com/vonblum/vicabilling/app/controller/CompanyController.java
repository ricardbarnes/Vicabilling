package com.vonblum.vicabilling.app.controller;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.controller.helper.LogoHandler;
import com.vonblum.vicabilling.app.data.exception.ProgramException;
import com.vonblum.vicabilling.app.data.model.Company;
import com.vonblum.vicabilling.app.data.model.Logo;
import com.vonblum.vicabilling.app.service.ViewPathBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompanyController extends AnchorPane implements Initializable {

    private ResourceBundle resourceBundle;

    @FXML
    private AnchorPane basePanel;

    @FXML
    private Button logoButton;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label nameLabel;

    @FXML
    private Label websiteLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label footerLabel;

    @FXML
    private Pane logoPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField websiteField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextArea addressArea;

    @FXML
    private TextArea footerArea;

    public CompanyController() {
        try {
            resourceBundle = Context.getResourceBundle();

            String entitySimpleName = Company.class.getSimpleName();
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

    @FXML
    protected void onChangeLogoAction() {
        LogoHandler logoHandler = new LogoHandler(this);
        logoHandler.handleLogoChoosing();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context.setCompanyController(this);
        showCompany(Context.getCompany());
        showCompanyLogo(Logo.getFilepath());
    }

    public void showCompanyLogo(String logoPath) {
        File file = new File(logoPath);
        Image image = new Image(file.toURI().toString());
        logoImage.setImage(image);
    }

    public void showCompany(Company company) {
        nameField.setText(company.getName());
        websiteField.setText(company.getWebsite());
        emailField.setText(company.getEmail());
        phoneField.setText(company.getPhone());
        addressArea.setText(company.getAddress());
        footerArea.setText(company.getFooter());
    }

    public Company getCompany() {
        String name = nameField.getText().trim();
        String website = websiteField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressArea.getText().trim();
        String footer = footerArea.getText().trim();

        Company company = new Company();
        company.setName(name);
        company.setWebsite(website);
        company.setPhone(phone);
        company.setEmail(email);
        company.setAddress(address);
        company.setFooter(footer);

        return company;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

}
