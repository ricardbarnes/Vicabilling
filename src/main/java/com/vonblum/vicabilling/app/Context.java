package com.vonblum.vicabilling.app;

import com.vonblum.vicabilling.app.controller.*;
import com.vonblum.vicabilling.app.controller.rule.ProductControllerRule;
import com.vonblum.vicabilling.app.data.model.BillSettings;
import com.vonblum.vicabilling.app.data.model.Company;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Application global context.
 *
 * @author Ricard P. Barnes
 */
public class Context {

    private static ResourceBundle resourceBundle;

    private static AboutController aboutController;
    private static BillSettingsController billSettingsController;
    private static CompanyController companyController;
    private static CustomerController customerController;
    private static MenuController menuController;
    private static ProductControllerRule productController;
    private static ProductControllerRule productEditController;
    private static SettingsController settingsController;
    private static TableController tableController;
    private static WorkspaceController workspaceController;

    private static Company company;
    private static BillSettings billSettings;
    private static Stage primaryStage;
    private static String css;


    /**
     * Sets the resource bundle that will be available for the whole application.
     *
     * @param bundle the resource bundle to be used by the application context.
     */
    public static void setResourceBundle(ResourceBundle bundle) {
        Context.resourceBundle = bundle;
    }

    /**
     * Gets the resource bundle that is currently available for the whole application.
     *
     * @return The current application resource bundle, if any.
     */
    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static Company getCompany() {
        return company;
    }

    public static void setCompany(Company company) {
        Context.company = company;
    }

    public static BillSettings getBillSettings() {
        return billSettings;
    }

    public static void setBillSettings(BillSettings billSettings) {
        Context.billSettings = billSettings;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Context.primaryStage = primaryStage;
    }

    public static String getCss() {
        return css;
    }

    public static void setCss(String css) {
        Context.css = css;
    }

    public static AboutController getAboutController() {
        return aboutController;
    }

    public static void setAboutController(AboutController aboutController) {
        Context.aboutController = aboutController;
    }

    public static BillSettingsController getBillSettingsController() {
        return billSettingsController;
    }

    public static void setBillSettingsController(BillSettingsController billSettingsController) {
        Context.billSettingsController = billSettingsController;
    }

    public static CompanyController getCompanyController() {
        return companyController;
    }

    public static void setCompanyController(CompanyController companyController) {
        Context.companyController = companyController;
    }

    public static CustomerController getCustomerController() {
        return customerController;
    }

    public static void setCustomerController(CustomerController customerController) {
        Context.customerController = customerController;
    }

    public static MenuController getMenuController() {
        return menuController;
    }

    public static void setMenuController(MenuController menuController) {
        Context.menuController = menuController;
    }

    public static ProductControllerRule getProductController() {
        return productController;
    }

    public static void setProductController(ProductControllerRule productController) {
        Context.productController = productController;
    }

    public static ProductControllerRule getProductEditController() {
        return productEditController;
    }

    public static void setProductEditController(ProductControllerRule productEditController) {
        Context.productEditController = productEditController;
    }

    public static SettingsController getSettingsController() {
        return settingsController;
    }

    public static void setSettingsController(SettingsController settingsController) {
        Context.settingsController = settingsController;
    }

    public static TableController getTableController() {
        return tableController;
    }

    public static void setTableController(TableController tableController) {
        Context.tableController = tableController;
    }

    public static WorkspaceController getWorkspaceController() {
        return workspaceController;
    }

    public static void setWorkspaceController(WorkspaceController workspaceController) {
        Context.workspaceController = workspaceController;
    }
}
