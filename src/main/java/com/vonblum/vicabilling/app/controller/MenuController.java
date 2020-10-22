package com.vonblum.vicabilling.app.controller;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.config.AppConstant;
import com.vonblum.vicabilling.app.controller.helper.PdfHandler;
import com.vonblum.vicabilling.app.controller.helper.WorkspaceHandler;
import com.vonblum.vicabilling.app.data.exception.ProgramException;
import com.vonblum.vicabilling.app.data.model.*;
import com.vonblum.vicabilling.app.service.ObjectSerializer;
import com.vonblum.vicabilling.app.service.ViewPathBuilder;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class MenuController extends AnchorPane implements Initializable {

    private ResourceBundle resourceBundle;
    private Stage settingsStage;
    private Stage aboutStage;
    private ObjectSerializer objectSerializer;

    @FXML
    private AnchorPane basePanel;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu;

    @FXML
    private Menu editMenu;

    @FXML
    private Menu billMenu;

    @FXML
    private Menu helpMenu;

    @FXML
    private Menu tableMenu;

    @FXML
    private MenuItem itemOpen;

//    @FXML
//    private MenuItem itemOpenPDFFolder;

    @FXML
    private MenuItem itemClose;

    @FXML
    private MenuItem itemSave;

    @FXML
    private MenuItem itemSettings;

    @FXML
    private MenuItem itemBuildPDF;

    @FXML
    private MenuItem itemOpenFolder;

    @FXML
    private MenuItem itemAbout;

    @FXML
    private MenuItem itemSelectAll;

    @FXML
    private MenuItem itemDeleteSelected;

    @FXML
    private MenuItem itemApplyPercentage;

    @FXML
    private SeparatorMenuItem firstFileSeparator;

    @FXML
    private SeparatorMenuItem firstTableSeparator;

    private void preloadSettingsView() {
        try {
            String entitySimpleName = Settings.class.getSimpleName();
            URL settingsView = ViewPathBuilder.getViewURLFromEntity(entitySimpleName, this);

            FXMLLoader settingsLoader = new FXMLLoader();
            settingsLoader.setLocation(settingsView);
            settingsLoader.setResources(resourceBundle);

            Parent root = settingsLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Context.getCss());

            settingsStage = new Stage();
            settingsStage.setTitle(resourceBundle.getString("Settings"));
            settingsStage.initStyle(StageStyle.UTILITY);
            settingsStage.setScene(scene);
            settingsStage.setResizable(false); // Not working! :(
            settingsStage.setOnCloseRequest(event -> {
                saveCompany();
                saveCustomer();
                saveBillSettings();
                Context.getWorkspaceController().getTableController().refreshProductList();
                Context.getPrimaryStage().setTitle(
                        Context.getWorkspaceController()
                                .getWorkspace()
                                .getTitleName(Context.getResourceBundle())
                );
            });
        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void preloadAboutView() {
        try {
            String entitySimpleName = About.class.getSimpleName();
            String fxmlPath = ViewPathBuilder.getViewPathFromEntity(entitySimpleName);
            URL aboutView = getClass().getResource(fxmlPath);

            FXMLLoader aboutLoader = new FXMLLoader();
            aboutLoader.setLocation(aboutView);
            aboutLoader.setResources(resourceBundle);

            Parent root = aboutLoader.load();
            Scene scene = new Scene(root);
            URL cssUrl = getClass().getResource(AppConstant.CSS_RES_PATH);
            scene.getStylesheets().add(cssUrl.toExternalForm());

            aboutStage = new Stage();
            aboutStage.setTitle(resourceBundle.getString("About"));
            aboutStage.initStyle(StageStyle.UTILITY);
            aboutStage.setScene(scene);
            aboutStage.setResizable(false);
        } catch (IOException e) {
            ProgramException.showDefaultError();
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void saveCompany() {
        Company company = Context.getCompanyController().getCompany();
        Context.setCompany(company);
        objectSerializer.serializeObject(company, Company.getFilepath());
    }

    private void saveCustomer() {
        Customer customer = Context.getCustomerController().getCustomer();
        Context.getBillSettings().setCustomer(customer);
    }

    private void saveBillSettings() {
        BillSettings billSettings = Context.getBillSettingsController().getBillSettings();
        Context.setBillSettings(billSettings);
        saveCustomer();
    }

    public MenuController() {
        try {
            resourceBundle = Context.getResourceBundle();
            objectSerializer = new ObjectSerializer();

            String entitySimpleName = Menu.class.getSimpleName();
            URL fxmlView = ViewPathBuilder.getViewURLFromEntity(entitySimpleName, this);

            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(fxmlView);
            menuLoader.setResources(resourceBundle);
            menuLoader.setRoot(this);
            menuLoader.setController(this);
            menuLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context.setMenuController(this);
        preloadSettingsView();
        preloadAboutView();

        itemSelectAll.setDisable(true);
        itemDeleteSelected.setDisable(true);
        itemApplyPercentage.setDisable(true);
    }

    @FXML
    protected void onItemOpenAction(ActionEvent actionEvent) {
        WorkspaceHandler workspaceHandler = new WorkspaceHandler(Context.getResourceBundle());
        Workspace workspace = workspaceHandler.openLoadingDialog();

        if (workspace != null) {
            Context.getWorkspaceController().showWorkspace(workspace);
        }
    }

    @FXML
    protected void onItemCloseAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resourceBundle.getString("Closing_the_bill"));
        alert.setContentText(resourceBundle.getString("Save_the_bill"));
        ButtonType saveButton =
                new ButtonType(resourceBundle.getString("Yes"), ButtonBar.ButtonData.YES);
        ButtonType noSaveButton =
                new ButtonType(resourceBundle.getString("No"), ButtonBar.ButtonData.NO);
        ButtonType cancelButton =
                new ButtonType(resourceBundle.getString("Cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(saveButton, noSaveButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == saveButton) {
                WorkspaceHandler workspaceHandler = new WorkspaceHandler(Context.getResourceBundle());
                Workspace workspace = Context.getWorkspaceController().getWorkspace();
                workspaceHandler.save(workspace);
                Context.getWorkspaceController().clearWorkspace();
            } else if (type == noSaveButton) {
                Context.getWorkspaceController().clearWorkspace();
            }
        });
    }

    @FXML
    protected void onItemSaveAction(ActionEvent actionEvent) {
        Workspace workspace = Context.getWorkspaceController().getWorkspace();
        WorkspaceHandler workspaceHandler = new WorkspaceHandler(Context.getResourceBundle());
        workspaceHandler.save(workspace);
    }

    @FXML
    protected void onItemSettingsAction(ActionEvent actionEvent) {
        settingsStage.show();
    }

    @FXML
    protected void onItemBuildPDFAction(ActionEvent actionEvent) {
        Workspace workspace = Context.getWorkspaceController().getWorkspace();
        Bill bill = workspace.getBill();
        PdfHandler handler = new PdfHandler(resourceBundle);

        try {
            if (handler.openSavingDialog(bill)) {
                Alert confirmAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmAlert.setTitle(resourceBundle.getString("Build_the_bill"));
                confirmAlert.setContentText(resourceBundle.getString("Bill_created"));
                confirmAlert.showAndWait();
            }
        } catch (ProgramException exception) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle(resourceBundle.getString("Build_the_bill"));
            errorAlert.setHeaderText(resourceBundle.getString("Bill_not_created"));
            errorAlert.setContentText(resourceBundle.getString("Bill_not_created_content"));
            errorAlert.show();
        }
    }

//    @FXML
//    protected void onItemOpenPDFFolder(ActionEvent actionEvent) {
//        File file = new File("PDF");
//
//        try {
//            Desktop.getDesktop().open(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    protected void onItemAboutAction(ActionEvent actionEvent) {
        aboutStage.show();
    }

    @FXML
    protected void onItemSelectAllAction(ActionEvent actionEvent) {
        TableView<Product> productTable = Context.getTableController().getTable();
        productTable.requestFocus();
        productTable.getSelectionModel().selectAll();
    }

    @FXML
    protected void onItemDeleteSelectedAction(ActionEvent actionEvent) {
        TableView<Product> productTable = Context.getTableController().getTable();
        ObservableList<Product> selectedProducts = productTable.getSelectionModel().getSelectedItems();
        productTable.getItems().removeAll(selectedProducts);
    }

    @FXML
    protected void onItemApplyPercentageAction(ActionEvent actionEvent) {
        AtomicReference<Double> result = new AtomicReference<>((double) 0);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(resourceBundle.getString("Selected_rows"));
        dialog.setHeaderText(resourceBundle.getString("Which_percentage_apply"));
        dialog.setContentText("%");

        Optional<String> input = dialog.showAndWait();
        input.ifPresent(string -> {
            try {
                result.set(Double.parseDouble(string));
                Context.getTableController().applyPercentageToSelectedProducts(result.get());
            } catch (NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("Error"));
                alert.setHeaderText(resourceBundle.getString("Entered_invalid_value"));
                alert.setContentText(resourceBundle.getString("Double_input_example"));
                alert.show();
            }
        });
    }

//    @FXML
//    protected void onEnglishUSAction(ActionEvent actionEvent) {
//        Locale locale = new Locale("en", "US");
//        ResourceBundle bundle = ResourceBundle.getBundle(AppConstant.LANG_BASE, locale);
//        Context.setResourceBundle(bundle);
//    }
//
//    @FXML
//    protected void onCatalanAction(ActionEvent actionEvent) {
//        Locale locale = new Locale("ca", "CAT");
//        ResourceBundle bundle = ResourceBundle.getBundle(AppConstant.LANG_BASE, locale);
//        Context.setResourceBundle(bundle);
//    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public MenuItem getItemSelectAll() {
        return itemSelectAll;
    }

    public MenuItem getItemDeleteSelected() {
        return itemDeleteSelected;
    }

    public MenuItem getItemApplyPercentage() {
        return itemApplyPercentage;
    }
}
