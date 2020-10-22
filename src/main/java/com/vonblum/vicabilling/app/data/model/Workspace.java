package com.vonblum.vicabilling.app.data.model;

import com.vonblum.vicabilling.app.config.AppConstant;

import java.io.File;
import java.io.Serializable;
import java.util.ResourceBundle;

public class Workspace implements Serializable {

    public static final String FOLDER_NAME = "saved";
    public static final String FILE_EXT = ".vbp";

    public static String getBaseFilepath() {
        return AppConstant.getFolderSystemBase() + FOLDER_NAME + File.separator;
    }

    private File file;
    private Product currentProduct;
    private Bill bill;
    private double currentIncrement;
    private boolean isBillClosed;

    public String getTitleName(ResourceBundle bundle) {
        String billTypeName = bundle.getString(bill.getType().name());
        String numberStr = String.valueOf(bill.getNumber());
        String customerName = bill.getCustomer().getName();
        String customerNameFmt = customerName;

        if (customerName.isEmpty()) {
            customerNameFmt = bundle.getString(Customer.DEF_NAME);
        }

        return billTypeName +
                " " +
                numberStr +
                " - " +
                customerNameFmt;
    }

    public String getSaveFilename(ResourceBundle bundle) {
        String customerNameFmt;
        String billTypeName = bundle.getString(bill.getType().name());
        String numberStr = String.valueOf(bill.getNumber());
        String customerName = bill.getCustomer().getName();

        if (customerName.isEmpty()) {
            customerNameFmt = bundle.getString(Customer.DEF_NAME);
        } else {
            customerNameFmt = customerName
                    .replaceAll(" ", "_")
                    .replaceAll("\\.", "")
                    .replaceAll(",", "");
        }

        return billTypeName +
                "-" +
                numberStr +
                "-" +
                customerNameFmt +
                FILE_EXT;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public double getCurrentIncrement() {
        return currentIncrement;
    }

    public void setCurrentIncrement(double currentIncrement) {
        this.currentIncrement = currentIncrement;
    }

    public boolean isBillClosed() {
        return isBillClosed;
    }

    public void setBillClosed(boolean billClosed) {
        isBillClosed = billClosed;
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "file=" + file +
                ", currentProduct=" + currentProduct +
                ", bill=" + bill +
                ", currentIncrement=" + currentIncrement +
                ", isBillClosed=" + isBillClosed +
                '}';
    }
}
