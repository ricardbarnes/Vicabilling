package com.vonblum.vicabilling.app.controller;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.data.model.Bill;
import com.vonblum.vicabilling.app.data.model.BillSettings;
import com.vonblum.vicabilling.app.data.model.Product;
import com.vonblum.vicabilling.app.data.model.Workspace;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkspaceController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane basePanel;

    @FXML
    private MenuController menuController;

    @FXML
    private ProductController productController;

    @FXML
    private Separator separator;

    @FXML
    private TableController tableController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context.setWorkspaceController(this);
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public ProductController getProductController() {
        return productController;
    }

    public TableController getTableController() {
        return tableController;
    }

    public void showWorkspace(Workspace workspace) {
        Bill bill = workspace.getBill();
        BillSettings billSettings = BillSettings.extractBillSettings(bill);

        productController.showProduct(workspace.getCurrentProduct());
        tableController.showProducts(bill.getProducts());

        Context.getSettingsController().setBillSettings(billSettings);
        Context.getPrimaryStage().setTitle(getWorkspace().getTitleName(Context.getResourceBundle()));
    }

    public Workspace getWorkspace() {
        Product currentProduct = productController.getProduct();
        BillSettings billSettings = Context
                .getSettingsController()
                .getBillSettingsController()
                .getBillSettings();
        Bill currentBill = new Bill();
        BillSettings.applySettingsToBill(billSettings, currentBill);
        List<Product> tableProducts = tableController.getProducts();
        currentBill.setProducts(tableProducts);

        Workspace workspace = new Workspace();
        workspace.setBill(currentBill);
        workspace.setCurrentProduct(currentProduct);
        return workspace;
    }

    public void clearWorkspace() {
        productController.clearController();
        tableController.clearTable();
        Context.getSettingsController().getBillSettingsController().clear();
        Context.getSettingsController().getCustomerController().clear();
        Context.getPrimaryStage().setTitle(getWorkspace().getTitleName(Context.getResourceBundle()));
    }

}
