package com.vonblum.vicabilling.app.data.model;

import com.vonblum.vicabilling.app.config.AppConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class BillSettings implements Serializable {

    public static final String FOLDER_NAME = "bill";
    public static final String FILENAME = "settings";

    public static void applySettingsToBill(BillSettings settings, Bill bill) {
        bill.setType(settings.type);
        bill.setDate(settings.date);
        bill.setNumber(settings.number);
        bill.setCurrency(settings.currency);
        bill.setTax(settings.tax);
        bill.setConditions(settings.conditions);
        bill.setCustomer(settings.customer);
        bill.setCompany(settings.company);
    }

    public static BillSettings extractBillSettings(Bill bill) {
        BillSettings billSettings = new BillSettings();
        billSettings.setDate(bill.getDate());
        billSettings.setNumber(bill.getNumber());
        billSettings.setType(bill.getType());
        billSettings.setCurrency(bill.getCurrency());
        billSettings.setTax(bill.getTax());
        billSettings.setConditions(bill.getConditions());
        billSettings.setCustomer(bill.getCustomer());
        return billSettings;
    }

    @Contract(pure = true)
    public static @NotNull String getFilepath() {
        return AppConstant.getFolderSystemBase() + FOLDER_NAME + File.separator + FILENAME;
    }

    public static @NotNull BillSettings getStandardConfig() {
        BillSettings billSettings = new BillSettings();
        billSettings.setType(Bill.Type.INVOICE);
        billSettings.setDate(new Date());
        billSettings.setCurrency(Currency.getInstance(new Locale("ca", "ES")));
        billSettings.setTax(Tax.getDefaultTax());
        billSettings.setConditions("This bill must be paid within the next 15 natural days.");
        billSettings.setCustomer(Customer.getStandardCustomer());
        return billSettings;
    }

    private Bill.Type type;
    private Date date;
    private int number;
    private Company company;
    private Customer customer;
    private Currency currency;
    private Tax tax;
    private String conditions;

    public Bill.Type getType() {
        return type;
    }

    public void setType(Bill.Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
