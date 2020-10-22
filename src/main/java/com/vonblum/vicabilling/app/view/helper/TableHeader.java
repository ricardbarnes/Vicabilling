package com.vonblum.vicabilling.app.view.helper;

import com.vonblum.vicabilling.app.Context;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

public class TableHeader {

    private static final ResourceBundle RESOURCE_BUNDLE = Context.getResourceBundle();

    public static final TableHeader REFERENCE_HEADER = new TableHeader(
            "reference",
            RESOURCE_BUNDLE.getString("Reference")
    );

    public static final TableHeader DESCRIPTION_HEADER = new TableHeader(
            "description",
            RESOURCE_BUNDLE.getString("Description")
    );

    public static final TableHeader QUANTITY_HEADER = new TableHeader(
            "quantity",
            RESOURCE_BUNDLE.getString("Quantity")
    );

    public static final TableHeader UNIT_HEADER = new TableHeader(
            "unitSymbol",
            RESOURCE_BUNDLE.getString("Unit_symbol")
    );

    public static final TableHeader U_PRICE_HEADER = new TableHeader(
            "unitPrice",
            RESOURCE_BUNDLE.getString("Unit_price")
    );

    public static final TableHeader DISCOUNT_HEADER = new TableHeader(
            "discountPercentage",
            RESOURCE_BUNDLE.getString("Discount_per")
    );

    public static final TableHeader PRICE_HEADER = new TableHeader(
            "totalPrice",
            RESOURCE_BUNDLE.getString("Price")
    );

    public static final TableHeader[] TABLE_HEADERS = {
            REFERENCE_HEADER,
            DESCRIPTION_HEADER,
            QUANTITY_HEADER,
            UNIT_HEADER,
            U_PRICE_HEADER,
            DISCOUNT_HEADER,
            PRICE_HEADER
    };

    @Contract(value = " -> new", pure = true)
    public static ComboItem @NotNull [] getSortableColumnSelectors() {
        return new ComboItem[]{
                new ComboItem(REFERENCE_HEADER.key, REFERENCE_HEADER.displayName),
                new ComboItem(DESCRIPTION_HEADER.key, DESCRIPTION_HEADER.displayName),
                new ComboItem(QUANTITY_HEADER.key, QUANTITY_HEADER.displayName),
                new ComboItem(U_PRICE_HEADER.key, U_PRICE_HEADER.displayName),
                new ComboItem(DISCOUNT_HEADER.key, DISCOUNT_HEADER.displayName),
                new ComboItem(QUANTITY_HEADER.key, QUANTITY_HEADER.displayName),
                new ComboItem(PRICE_HEADER.key, PRICE_HEADER.displayName),
                new ComboItem("original", "*Original*"),
        };
    }

    @Contract(value = " -> new", pure = true)
    public static ComboItem @NotNull [] getOperableColumnSelectors() {
        return new ComboItem[]{
                new ComboItem(U_PRICE_HEADER.key, U_PRICE_HEADER.displayName),
                new ComboItem(DISCOUNT_HEADER.key, DISCOUNT_HEADER.displayName),
                new ComboItem(QUANTITY_HEADER.key, QUANTITY_HEADER.displayName)
        };
    }

    private int index;
    private String key;
    private String displayName;

    public TableHeader(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
