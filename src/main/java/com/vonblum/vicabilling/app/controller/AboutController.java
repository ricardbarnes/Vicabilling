package com.vonblum.vicabilling.app.controller;

import com.vonblum.vicabilling.app.Context;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController extends AnchorPane implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context.setAboutController(this);
    }

}
