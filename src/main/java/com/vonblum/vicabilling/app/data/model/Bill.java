package com.vonblum.vicabilling.app.data.model;

import com.vonblum.vicabilling.app.service.MathTool;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;

public class Bill implements Serializable {

    public enum Type {
        BUDGET,
        INVOICE
    }

    private int number;
    private Date date;
    private Type type;
    private List<Product> products;
    private Company company;
    private Customer customer;
    private Currency currency;
    private Tax tax;
    private String conditions;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public double getUntaxedTotal() {
        DecimalFormat decFmt = new DecimalFormat("0.00");

        double untaxedTotal = 0;

        if (products != null) {
            for (Product product : products) {
                untaxedTotal += product.getTotalPrice();
            }
        }

        return untaxedTotal;
    }

    public double getTaxedTotal() {
        return MathTool.applyCoefficientToDouble(getUntaxedTotal(), tax.getCoefficient());
    }

    public double getTaxAmount() {
        return getTaxedTotal() - getUntaxedTotal();
    }

    @Override
    public String toString() {
        return "Bill{" +
                "number=" + number +
                ", date=" + date +
                ", type=" + type +
                ", products=" + products +
                ", company=" + company +
                ", customer=" + customer +
                ", currency=" + currency +
                ", tax=" + tax +
                ", conditions='" + conditions + '\'' +
                '}';
    }

}
