package com.vonblum.vicabilling.app.controller.rule;

import com.vonblum.vicabilling.app.data.model.Product;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.ResourceBundle;

public interface ProductControllerRule {

    void showProduct(Product product);

    ResourceBundle getResourceBundle();

    RadioButton getQuantityRadio();

    RadioButton getDiscountRadio();

    RadioButton getUnitPriceRadio();

    RadioButton getTotalPriceRadio();

    ToggleGroup getQueryGroup();

    Label getInfoLabel();

    Product getProduct();

    boolean isSystemWorking();

}
