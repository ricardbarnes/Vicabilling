package com.vonblum.vicabilling.app.controller;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.data.exception.ProgramException;
import com.vonblum.vicabilling.app.data.model.Product;
import com.vonblum.vicabilling.app.data.model.ProductEdit;
import com.vonblum.vicabilling.app.data.model.Table;
import com.vonblum.vicabilling.app.service.MathTool;
import com.vonblum.vicabilling.app.service.ViewPathBuilder;
import com.vonblum.vicabilling.app.view.helper.TableHeader;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class TableController extends AnchorPane implements Initializable {

    private ResourceBundle resourceBundle;
    private ObservableList<Product> products;
    private ObservableList<Product> selectedProducts;
    private boolean isEditOpen;

    @FXML
    private AnchorPane basePanel;

    @FXML
    private Label totalNoVatLabel;

    @FXML
    private Label totalVatLabel;

    @FXML
    private Pane tablePane;

    @FXML
    private Pane resultPane;

    @FXML
    private TableView<Product> table;

    @FXML
    private TextField totalNoVatField;

    @FXML
    private TextField totalVatField;

    private void setProductTableColumns() {
        TableHeader[] headers = TableHeader.TABLE_HEADERS;
        double columnProportion = 1.00d / (double) headers.length;

        for (TableHeader header : headers) {
            TableColumn<Product, Void> column = new TableColumn<>(header.getDisplayName());
            column.setCellValueFactory(new PropertyValueFactory<>(header.getKey()));
            column.prefWidthProperty().bind(table.widthProperty().multiply(columnProportion));
            column.setResizable(false);
            column.setReorderable(false);
            column.setSortable(false);
            table.getColumns().add(column);
        }
    }

    private void showProductEditView(TableRow<Product> row) {
        if (!isEditOpen) {
            try {
                int index = row.getIndex();
                Product product = row.getItem();

                String entitySimpleName = ProductEdit.class.getSimpleName();
                URL editView = ViewPathBuilder.getViewURLFromEntity(entitySimpleName, this);

                FXMLLoader editLoader = new FXMLLoader();
                editLoader.setLocation(editView);
                editLoader.setResources(resourceBundle);

                Parent root = editLoader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Context.getCss());

                Stage editStage = new Stage();
                editStage.setTitle(resourceBundle.getString("Edit_product_title"));
                editStage.initStyle(StageStyle.UTILITY);
                editStage.setAlwaysOnTop(true);
                editStage.setScene(scene);
                editStage.setResizable(false); // Not working! :(
                editStage.setOnCloseRequest(event -> {
                    Product editedProduct = Context.getProductEditController().getProduct();
                    int productIndex = table.getItems().indexOf(editedProduct);

                    if (editedProduct.getState() == Product.State.Out) {
                        editStage.hide();
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initOwner(Context.getPrimaryStage());
                        alert.setTitle(resourceBundle.getString("Warning"));
                        alert.setHeaderText(resourceBundle.getString("Product_cannot_save"));
                        alert.setContentText(resourceBundle.getString("Product_need_ref"));
                        alert.showAndWait();
                    } else {
                        editedProduct.trimFields();
                        table.getItems().set(index, editedProduct);
                    }

                    table.getSelectionModel().select(productIndex);

                    isEditOpen = false;
                });
                editStage.show();

                isEditOpen = true;
                Context.getProductEditController().showProduct(product);
            } catch (IOException e) {
                System.out.println("ERROR");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void refreshSelectedList(ListChangeListener.Change<? extends Product> change) {
        refreshMenuItems();
    }

    private void refreshMenuItems() {
        boolean isTableEmpty = products.size() == 0;
        boolean isSelectedProduct = selectedProducts.size() > 0;
        boolean areAllProductsSelected = products.size() == selectedProducts.size();

        MenuController menuController = Context.getMenuController();
        menuController
                .getItemSelectAll()
                .setDisable(isTableEmpty || areAllProductsSelected);
        menuController.getItemApplyPercentage().setDisable(!isSelectedProduct);
        menuController.getItemDeleteSelected().setDisable(!isSelectedProduct);
    }

    @FXML
    protected void insertRow(Product product) {
        table.getItems().add(product);
    }

    public TableController() {
        try {
            resourceBundle = Context.getResourceBundle();

            String entitySimpleName = Table.class.getSimpleName();
            URL fxmlView = ViewPathBuilder.getViewURLFromEntity(entitySimpleName, this);

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(fxmlView);
            fxmlLoader.setResources(resourceBundle);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();

            setProductTableColumns();
            products = table.getItems();
            products.addListener((ListChangeListener<Product>) this::refreshProductList);

            selectedProducts = table.getSelectionModel().getSelectedItems();
            selectedProducts.addListener(this::refreshSelectedList);

            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            table.getSelectionModel().selectedItemProperty().addListener((observer, oldSelection, newSelection)
                    -> refreshMenuItems());
            table.setRowFactory(view -> {
                TableRow<Product> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (table.getSelectionModel().getSelectedIndices().size() > 0) {
                        MenuController menuController = Context.getMenuController();
                        menuController.getItemDeleteSelected().setDisable(false);
                        menuController.getItemApplyPercentage().setDisable(false);
                    }
                    if (!row.isEmpty()) {
                        if (row.isSelected()) {
                            if (event.getButton() == MouseButton.PRIMARY) {
                                if (event.getClickCount() == 2) {
                                    showProductEditView(row);
                                }
                            } else if (event.getButton() == MouseButton.SECONDARY) {
                                if (event.getClickCount() == 1) {
                                    ContextMenu context = new ContextMenu();

                                    MenuItem itemEdit = new MenuItem(resourceBundle.getString("Edit"));
                                    itemEdit.setOnAction(itemEvent -> showProductEditView(row));

                                    MenuItem itemCopy = new MenuItem(resourceBundle.getString("Copy"));
                                    itemCopy.setOnAction(itemEvent -> {
                                        Product product = row.getItem();
                                        Context.getProductController().showProduct(product);
                                    });

                                    MenuItem itemDelete = new MenuItem(resourceBundle.getString("Delete"));
                                    itemDelete.setOnAction(itemEvent -> products.remove(row.getIndex()));

                                    context.getItems().addAll(itemEdit, itemCopy, itemDelete);
                                    row.setContextMenu(context);
                                }
                            }
                        }
                    }
                });
                refreshMenuItems();
                return row;
            });
        } catch (IOException e) {
            ProgramException.showDefaultError();
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context.setTableController(this);
    }

    public void showProducts(List<Product> products) {
        table.getItems().addAll(products);
    }

    public void clearTable() {
        table.getItems().clear();
    }

    public void refreshProductList() {
        refreshMenuItems();

        double subtotalPrice = 0.0d;

        for (Product product : products) {
            subtotalPrice += product.getTotalPrice();
        }

        Currency currency = Context.getBillSettings().getCurrency();
        DecimalFormat decFmt = new DecimalFormat("0.00");

        totalNoVatField.setText(currency.getSymbol() + " " + decFmt.format(subtotalPrice));

        double taxCoefficient = Context.getBillSettings().getTax().getCoefficient();
        double totalPrice = MathTool.applyCoefficientToDouble(subtotalPrice, taxCoefficient);

        totalVatField.setText(currency.getSymbol() + " " + decFmt.format(totalPrice));
    }

    public void refreshProductList(ListChangeListener.Change<? extends Product> change) {
        refreshProductList();
    }

    public void applyPercentageToSelectedProducts(double percentage) {
        double coefficient = percentage / 100;
        Map<Integer, Product> productMap = new HashMap<>();

        for (int i = 0; i < products.size(); i++) {
            if (table.getSelectionModel().isSelected(i)) {
                Product product = products.get(i);
                product.applyCoefficientToPrice(coefficient);
                productMap.put(i, product);
            }
        }

        for (Map.Entry<Integer, Product> productEntry : productMap.entrySet()) {
            table.getItems().set(productEntry.getKey(), productEntry.getValue());
        }
    }

    public List<Product> getProducts() {
        Product[] products = table.getItems().toArray(new Product[0]);
        return Arrays.asList(products);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public TableView<Product> getTable() {
        return table;
    }

}
