package com.vonblum.vicabilling.app.controller;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.controller.helper.ProductHandler;
import com.vonblum.vicabilling.app.controller.rule.ProductControllerRule;
import com.vonblum.vicabilling.app.data.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductEditController extends AnchorPane implements Initializable, ProductControllerRule {

    private ProductHandler helper;
    private ResourceBundle resourceBundle;
    private boolean isSystemWorking; // Enables or disables the spinner listeners in order to avoid eternal recursions
    private ToggleGroup queryGroup;

    @FXML
    private Label referenceLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label unitPriceLabel;

    @FXML
    private Label unitSymbolLabel;

    @FXML
    private Label discountLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private Pane inputsPanel;

    @FXML
    private Pane buttonPanel;

    @FXML
    private RadioButton quantityRadio;

    @FXML
    private RadioButton discountRadio;

    @FXML
    private RadioButton unitPriceRadio;

    @FXML
    private RadioButton totalPriceRadio;

    @FXML
    private Spinner<Double> quantitySpinner;

    @FXML
    private Spinner<Double> unitPriceSpinner;

    @FXML
    private Spinner<Double> discountSpinner;

    @FXML
    private Spinner<Double> totalPriceSpinner;

    @FXML
    private TextField referenceField;

    @FXML
    private TextField unitSymbolField;

    @FXML
    private TextArea descriptionArea;

    private void disableUnitPriceField(boolean disable) {
        helper.setUnitPriceActive(!disable);
        unitPriceSpinner.setDisable(disable);
        unitPriceSpinner.getValueFactory().setValue(0d);
    }

    private void disableTotalPriceField(boolean disable) {
        helper.setTotalPriceActive(!disable);
        totalPriceSpinner.setDisable(disable);
        totalPriceSpinner.getValueFactory().setValue(0d);
    }

    private void disableQuantityField(boolean disable) {
        helper.setQuantityActive(!disable);
        quantitySpinner.setDisable(disable);
        quantitySpinner.getValueFactory().setValue(0d);
    }

    private void disableDiscountField(boolean disable) {
        helper.setDiscountActive(!disable);
        discountSpinner.setDisable(disable);
    }

    private void onProductChange() {
        isSystemWorking = true;
        Product product = getProduct();
        showProduct(product);
        isSystemWorking = false;
    }

    private void setBoxDefaults() {
        quantityRadio.setSelected(false);
        discountRadio.setSelected(false);
        unitPriceRadio.setSelected(false);
        totalPriceRadio.setSelected(true);
        disableTotalPriceField(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
        helper = new ProductHandler(this);
        Context.setProductEditController(this);

        referenceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                onProductChange();
            }
        });
        unitSymbolField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                onProductChange();
            }
        });
        descriptionArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                onProductChange();
            }
        });
        // Those listeners make sure that the last values of the spinners are immediately collected,
        // and prevent bad inputs.
        quantitySpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!isSystemWorking) {
                    try {
                        double value = Double.parseDouble(newValue);
                        quantitySpinner.getValueFactory().setValue(value);
                        onProductChange();
                    } catch (NumberFormatException exception) {
                        quantitySpinner.getEditor().setText(""); // Cannot set a 0d value, for some unknown reason
                    }
                }
            }
        });
        discountSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!isSystemWorking) {
                    try {
                        double value = Double.parseDouble(newValue);
                        discountSpinner.getValueFactory().setValue(value);
                        onProductChange();
                    } catch (NumberFormatException exception) {
                        discountSpinner.getEditor().setText(""); // idem
                    }
                }
            }
        });
        unitPriceSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!isSystemWorking) {
                    try {
                        double value = Double.parseDouble(newValue);
                        unitPriceSpinner.getValueFactory().setValue(value);
                        onProductChange();
                    } catch (NumberFormatException exception) {
                        unitPriceSpinner.getEditor().setText(""); // idem
                    }
                }
            }
        });
        totalPriceSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!isSystemWorking) {
                    try {
                        double value = Double.parseDouble(newValue);
                        totalPriceSpinner.getValueFactory().setValue(value);
                        onProductChange();
                    } catch (NumberFormatException exception) {
                        totalPriceSpinner.getEditor().setText(""); // idem
                    }
                }
            }
        });

        queryGroup = new ToggleGroup();
        quantityRadio.setToggleGroup(queryGroup);
        discountRadio.setToggleGroup(queryGroup);
        unitPriceRadio.setToggleGroup(queryGroup);
        totalPriceRadio.setToggleGroup(queryGroup);

        setBoxDefaults();

        quantityRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            disableQuantityField(newValue);
            onProductChange();
        });
        discountRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            disableDiscountField(newValue);
            onProductChange();
        });
        unitPriceRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            disableUnitPriceField(newValue);
            onProductChange();
        });
        totalPriceRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            disableTotalPriceField(newValue);
            onProductChange();
        });

        disableUnitPriceField(false);
        disableQuantityField(false);
        disableDiscountField(false);
        onProductChange();
    }

    @Override
    public void showProduct(Product product) {
        referenceField.setText(product.getReference());
        unitSymbolField.setText(product.getUnitSymbol());
        descriptionArea.setText(product.getDescription());
        quantitySpinner.getValueFactory().setValue(product.getQuantity());
        unitPriceSpinner.getValueFactory().setValue(product.getUnitPrice());
        discountSpinner.getValueFactory().setValue(product.getDiscountPercentage());
        totalPriceSpinner.getValueFactory().setValue(product.getTotalPrice());
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    @Override
    public RadioButton getQuantityRadio() {
        return quantityRadio;
    }

    @Override
    public RadioButton getDiscountRadio() {
        return discountRadio;
    }

    @Override
    public RadioButton getUnitPriceRadio() {
        return unitPriceRadio;
    }

    @Override
    public RadioButton getTotalPriceRadio() {
        return totalPriceRadio;
    }

    @Override
    public ToggleGroup getQueryGroup() {
        return queryGroup;
    }

    @Override
    public Label getInfoLabel() {
        return infoLabel;
    }

    @Override
    public Product getProduct() {
        String reference = referenceField.getText();
        String unitSymbol = unitSymbolField.getText();
        String description = descriptionArea.getText();
        double quantity = quantitySpinner.getValue();
        double unitPrice = unitPriceSpinner.getValue();
        double discount = discountSpinner.getValue();
        double totalPrice = totalPriceSpinner.getValue();

        Product product = new Product();
        product.setReference(reference);
        product.setUnitSymbol(unitSymbol);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setUnitPrice(unitPrice);
        product.setDiscountPercentage(discount);
        product.setTotalPrice(totalPrice);

        helper.handleProduct(product);

        return product;
    }

    @Override
    public boolean isSystemWorking() {
        return isSystemWorking;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

}
