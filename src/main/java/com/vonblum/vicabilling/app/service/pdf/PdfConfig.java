package com.vonblum.vicabilling.app.service.pdf;

import com.vonblum.vicabilling.app.data.model.Bill;
import com.vonblum.vicabilling.app.data.model.Customer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ResourceBundle;

public class PdfConfig {

    public static final String FOLDER_NAME = "PDF";
    public static final String PDF_EXT = "pdf";

    public static final float Y_MARGIN = 35.0f;
    public static final float X_MARGIN = 50.0f;

    @Contract("_ -> new")
    public static @NotNull String addPDFExt(@NotNull String filename) {
        return filename + "." + PDF_EXT;
    }

    public static String getSaveFilename(ResourceBundle bundle, Bill bill) {
        String customerNameFmt;
        String billTypeName = bundle.getString(bill.getType().name());
        String numberStr = String.valueOf(bill.getNumber());
        String customerName = bill.getCustomer().getName();

        if (customerName.isEmpty()) {
            customerNameFmt = bundle.getString(Customer.DEF_NAME);
        } else {
            customerNameFmt = customerName
                    .replaceAll(" ", "_")
                    .replaceAll(",", "")
                    .replaceAll("\\.", "");

        }

        return addPDFExt(
                billTypeName +
                        "-" +
                        numberStr +
                        "-" +
                        customerNameFmt
        );
    }

    public static String getPDFTitle(Bill bill, ResourceBundle bundle) {
        String billTypeName = bill.getType().name().toUpperCase();
        String numberStr = String.valueOf(bill.getNumber());
        String customerName = bill.getCustomer().getName();

        if (customerName.isEmpty()) {
            customerName = bundle.getString("UNKNOWN");
        }

        return addPDFExt(
                billTypeName +
                        " " +
                        numberStr +
                        " " +
                        customerName
        );
    }

    public static File getSavingFile(String filename) {
        return new File(
                PdfConfig.FOLDER_NAME +
                        File.separator +
                        filename
        );
    }

    public static File getSavingFile(ResourceBundle bundle, Bill bill) {
        return getSavingFile(getSaveFilename(bundle, bill));
    }

}
