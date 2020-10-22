package com.vonblum.vicabilling.app.service;

public class StringTool {

    public static String putSpacesWith(String string, int nBlank) {
        StringBuilder blank = new StringBuilder();
        blank.append(" ".repeat(Math.max(0, nBlank)));
        return blank.toString() + string + blank.toString();
    }

    public static String putSpaces(String string, int maxChars) {
        int nChars = string.length();
        int charDiff = maxChars - nChars;

        if (charDiff % 2 != 0) {
            charDiff -= 1;
        }

        int halfDiff = charDiff / 2;

        return putSpacesWith(string, halfDiff);
    }

    public static String cutStringToMaxWithEtc(String string, int maxChars) {
        if (string.isEmpty() || string.length() < (maxChars)) {
            return string;
        }

        return string.substring(0, maxChars) + "...";
    }

}
