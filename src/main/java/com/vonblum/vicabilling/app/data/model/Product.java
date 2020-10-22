package com.vonblum.vicabilling.app.data.model;

import com.vonblum.vicabilling.app.service.MathTool;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {

    public enum State {
        Full,
        Half,
        Out
    }

    public static Product getEmptyProduct() {
        Product product = new Product();
        product.setReference("");
        product.setDescription("");
        product.setUnitSymbol("");
        product.setQuantity(0d);
        product.setUnitPrice(0d);
        product.setTotalPrice(0d);
        product.setDiscountPercentage(0d);
        return product;
    }

    private State state;
    private String reference;
    private String description;
    private double quantity;
    private double unitPrice;
    private String unitSymbol;
    private double discountPercentage;
    private double totalPrice;
    private double discCoefficient;

    private boolean isEmptyRef;
    private boolean isEmptyDesc;

    public Product() {
        this.state = State.Out;
    }

    public void applyCoefficientToPrice(double coefficient) {
        unitPrice = MathTool.applyCoefficientToDouble(unitPrice, coefficient);
        totalPrice = MathTool.applyCoefficientToDouble(totalPrice, coefficient);
    }

    public void trimFields() {
        if (!this.isEmpty()) {
            reference = reference.trim();
            unitSymbol = unitSymbol.trim();
            description = description.trim();
        }
    }

    public boolean isEmpty() {
        boolean isEmpty = true;

        if (!reference.isEmpty()) {
            isEmpty = false;
        }

        if (!description.isEmpty()) {
            isEmpty = false;
        }

        if (!unitSymbol.isEmpty()) {
            isEmpty = false;
        }

        if (quantity != 0) {
            isEmpty = false;
        }

        if (unitPrice != 0) {
            isEmpty = false;
        }

        if (discountPercentage != 0) {
            isEmpty = false;
        }

        if (totalPrice != 0) {
            isEmpty = false;
        }

        return isEmpty;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof Product) {
            Product product = (Product) obj;

            if (!reference.equals(product.reference)) {
                isEqual = false;
            }

            if (!description.equals(product.description)) {
                isEqual = false;
            }

            if (quantity != product.quantity) {
                isEqual = false;
            }

            if (!unitSymbol.equals(product.unitSymbol)) {
                isEqual = false;
            }

            if (discountPercentage != product.discountPercentage) {
                isEqual = false;
            }

            if (totalPrice != product.totalPrice) {
                isEqual = false;
            }

        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                state,
                reference,
                description,
                quantity,
                unitPrice,
                unitSymbol,
                discountPercentage,
                totalPrice,
                discCoefficient,
                isEmptyRef,
                isEmptyDesc
        );
    }

    @Override
    public String toString() {
        return "Product{" +
                "state=" + state +
                ", reference='" + reference + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", unitSymbol='" + unitSymbol + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", totalPrice=" + totalPrice +
                ", isEmptyRef=" + isEmptyRef +
                ", isEmptyDesc=" + isEmptyDesc +
                '}';
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
        this.isEmptyRef = reference.isEmpty();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.isEmptyDesc = description.isEmpty();
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public void setUnitSymbol(String unitSymbol) {
        this.unitSymbol = unitSymbol;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
        this.discCoefficient = discountPercentage / 100;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static class Math {

        public static double calculateTotalPrice(Product product) {
            return product.quantity * (product.unitPrice - (product.unitPrice * product.discCoefficient));
        }

        public static double calculateUnitPrice(Product product) {
            double math;
            if (product.quantity != 0 && product.totalPrice != 0) {
                math = product.totalPrice / (product.quantity - (product.quantity * product.discCoefficient));
            } else {
                math = 0;
            }
            return math;
        }

        public static double calculateQuantity(Product product) {
            double math;
            if (product.unitPrice != 0 && product.totalPrice != 0) {
                math = product.totalPrice / (product.unitPrice - (product.unitPrice * product.discCoefficient));
            } else {
                math = 0;
            }
            return math;
        }

        public static double calculateDiscount(Product product) {
            double math;
            if (product.quantity != 0 && product.unitPrice != 0 && product.totalPrice != 0) {
                math = ((product.quantity * product.unitPrice) - product.totalPrice) /
                        (product.quantity * product.unitPrice);
                math *= 100; // x100 because it is a percentage
            } else {
                math = 0d;
            }
            return math;
        }

    }

}