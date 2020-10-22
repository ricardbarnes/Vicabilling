package com.vonblum.vicabilling.app.controller.helper;

import com.vonblum.vicabilling.app.controller.rule.ProductControllerRule;
import com.vonblum.vicabilling.app.data.model.Product;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

import java.util.ResourceBundle;

public class ProductHandler {

    private final ProductControllerRule controller;
    private final ResourceBundle bundle;
    private final Label infoLabel;

    private Color infoColor;
    private String infoText;
    private String infoTip;
    private boolean isQuantityActive;
    private boolean isDiscountActive;
    private boolean isUnitPriceActive;
    private boolean isTotalPriceActive;

    private void showInfo() {
        infoLabel.setText(infoText);
        infoLabel.setTextFill(infoColor);
        infoLabel.setTooltip(new Tooltip(infoTip));
    }

    private void setDefaultModeDisplay() {
        infoColor = Color.GREEN;
        infoText = bundle.getString("info_text_default");
        infoTip = bundle.getString("info_tip_default");
    }

    private void setMissedUnitPriceDisplay() {
        infoColor = Color.BLUE;
        infoText = bundle.getString("info_text_no_unit_price");
        infoTip = bundle.getString("info_tip_no_unit_price");
    }

    private void setMissedQuantityDisplay() {
        infoColor = Color.BLUE;
        infoText = bundle.getString("info_text_no_quantity");
        infoTip = bundle.getString("info_tip_no_quantity");
    }

    private void setMissedDiscountDisplay() {
        infoColor = Color.BLUE;
        infoText = bundle.getString("info_text_no_discount");
        infoTip = bundle.getString("info_tip_no_discount");
    }

    /**
     * Base formulae:
     * <p>
     * qu - qud = t
     * <p>
     * q - quantity
     * u - unit price
     * d - discount
     * t - total price
     *
     * @param product the current product on display.
     */
    private void handleCalculations(Product product) {
        // Clearing the info label
        infoLabel.setText("");
        infoLabel.setTextFill(null);
        infoLabel.setTooltip(null);

        infoColor = Color.BLACK;
        infoText = "";
        infoTip = "";

        /*
         * Getting the total price from the other fields (default)
         */
        if (!isTotalPriceActive) {
            product.setTotalPrice(Product.Math.calculateTotalPrice(product));
            setDefaultModeDisplay();

            /*
             * Getting unit price from the other fields.
             */
        } else if (!isUnitPriceActive) {
            product.setUnitPrice(Product.Math.calculateUnitPrice(product));
            setMissedUnitPriceDisplay();

            /*
             *  Getting quantity from the other fields.
             */
        } else if (!isQuantityActive) {
            product.setQuantity(Product.Math.calculateQuantity(product));
            setMissedQuantityDisplay();

            /*
             * Getting discount from the other fields
             */
        } else if (!isDiscountActive) {
            product.setDiscountPercentage(Product.Math.calculateDiscount(product));
            setMissedDiscountDisplay();

        }

        showInfo();
    }

    private void handleState(Product product) {
        Product.State state = Product.State.Out;
        if (!product.getReference().isEmpty() &&
                !product.getDescription().isEmpty() &&
                !product.getUnitSymbol().isEmpty()) {
            state = Product.State.Full;
        } else if (!product.getReference().isEmpty() || !product.getDescription().isEmpty()) {
            state = Product.State.Half;
        }

        product.setState(state);
    }

    public ProductHandler(ProductControllerRule controller) {
        this.controller = controller;
        this.bundle = controller.getResourceBundle();
        this.infoLabel = controller.getInfoLabel();
    }

    public void handleProduct(Product product) {
        handleCalculations(product);
        handleState(product);
    }

    public void setDefaultMode() {
        controller.getQuantityRadio().setSelected(true);
        controller.getDiscountRadio().setSelected(true);
        controller.getUnitPriceRadio().setSelected(true);
        controller.getTotalPriceRadio().setSelected(false);

        setDefaultModeDisplay();
        showInfo();
    }

    public boolean isQuantityActive() {
        return isQuantityActive;
    }

    public void setQuantityActive(boolean quantityActive) {
        isQuantityActive = quantityActive;
    }

    public boolean isDiscountActive() {
        return isDiscountActive;
    }

    public void setDiscountActive(boolean discountActive) {
        isDiscountActive = discountActive;
    }

    public boolean isUnitPriceActive() {
        return isUnitPriceActive;
    }

    public void setUnitPriceActive(boolean unitPriceActive) {
        isUnitPriceActive = unitPriceActive;
    }

    public boolean isTotalPriceActive() {
        return isTotalPriceActive;
    }

    public void setTotalPriceActive(boolean totalPriceActive) {
        isTotalPriceActive = totalPriceActive;
    }

    public ProductControllerRule getController() {
        return controller;
    }

    @Override
    public String toString() {
        return "ProductHandler{" +
                "isQuantity=" + isQuantityActive +
                ", isDiscount=" + isDiscountActive +
                ", isUnitPrice=" + isUnitPriceActive +
                ", isTotalPrice=" + isTotalPriceActive +
                '}';
    }
}
