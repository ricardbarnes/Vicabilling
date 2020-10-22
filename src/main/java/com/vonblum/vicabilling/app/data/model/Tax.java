package com.vonblum.vicabilling.app.data.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Tax implements Serializable {

    public static @NotNull Tax getDefaultTax() {
        return new Tax("iva", "IVA", "Impuesto al valor añadido", 0.21d);
    }

    private String code;
    private String symbol;
    private String displayName;
    private double coefficient;

    public Tax(String code, String symbol, String displayName, double coefficient) {
        this.code = code;
        this.symbol = symbol;
        this.displayName = displayName;
        this.coefficient = coefficient;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
