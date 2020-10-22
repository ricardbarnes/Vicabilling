package com.vonblum.vicabilling.app.service;

public class MathTool {

    public static double applyCoefficientToDouble(double originalValue, double coefficient) {
        return originalValue + (originalValue * coefficient);
    }

    public static double getPercentFromCoefficient(double coefficient) {
        return coefficient * 100d;
    }

    public static double getCoefficientFromPercent(double percent) {
        return percent / 100;
    }

}
