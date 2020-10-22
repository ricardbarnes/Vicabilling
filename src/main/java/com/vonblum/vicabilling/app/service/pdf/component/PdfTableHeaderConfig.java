package com.vonblum.vicabilling.app.service.pdf.component;

import com.vonblum.vicabilling.app.Context;

import java.util.ResourceBundle;

public class PdfTableHeaderConfig {

    private static final ResourceBundle RESOURCE_BUNDLE = Context.getResourceBundle();

    public static final PdfTableHeaderConfig REFERENCE_HEADER =
            new PdfTableHeaderConfig(
                    RESOURCE_BUNDLE.getString("table_Reference"),
                    14
            );

    public static final PdfTableHeaderConfig DESCRIPTION_HEADER =
            new PdfTableHeaderConfig(
                    RESOURCE_BUNDLE.getString("table_Description"),
                    36
            );

    public static final PdfTableHeaderConfig QUANTITY_HEADER =
            new PdfTableHeaderConfig(
                    RESOURCE_BUNDLE.getString("table_Quantity"),
                    8
            );

    public static final PdfTableHeaderConfig UNIT_SYMBOL_HEADER =
            new PdfTableHeaderConfig(
                    RESOURCE_BUNDLE.getString("table_Unit_symbol"),
                    6
            );

    public static final PdfTableHeaderConfig U_PRICE_HEADER =
            new PdfTableHeaderConfig(
                    RESOURCE_BUNDLE.getString("table_Unit_price"),
                    6
            );

    public static final PdfTableHeaderConfig DISCOUNT_HEADER =
            new PdfTableHeaderConfig(
                    RESOURCE_BUNDLE.getString("table_Discount_per"),
                    6
            );

    public static final PdfTableHeaderConfig PRICE_HEADER =
            new PdfTableHeaderConfig(
                    RESOURCE_BUNDLE.getString("table_Price"),
                    12
            );

    public static final PdfTableHeaderConfig[] TABLE_HEADERS = {
            REFERENCE_HEADER,
            DESCRIPTION_HEADER,
            QUANTITY_HEADER,
            UNIT_SYMBOL_HEADER,
            U_PRICE_HEADER,
            DISCOUNT_HEADER,
            PRICE_HEADER
    };

    private int maxHeaderChars;
    private String displayName;

    public PdfTableHeaderConfig(String displayName, int maxHeaderChars) {
        this.maxHeaderChars = maxHeaderChars;
        this.displayName = displayName;
    }

    public int getMaxHeaderChars() {
        return maxHeaderChars;
    }

    public void setMaxHeaderChars(int maxHeaderChars) {
        this.maxHeaderChars = maxHeaderChars;
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
