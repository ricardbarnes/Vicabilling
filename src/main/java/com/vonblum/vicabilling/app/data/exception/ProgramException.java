package com.vonblum.vicabilling.app.data.exception;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ProgramException extends Exception {

    public static final String DEFAULT_ERROR_MSG = "Sorry, some error just happened.";

    public ProgramException(String message) {
        super(message);
    }

    public static void showDefaultError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, DEFAULT_ERROR_MSG, ButtonType.OK);
        alert.showAndWait();
    }

}
