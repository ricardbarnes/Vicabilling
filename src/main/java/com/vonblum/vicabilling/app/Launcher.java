package com.vonblum.vicabilling.app;

import javafx.application.Application;

/**
 * app.controller.Launcher class required in order to get the JavaFx application working properly.
 * The app may probably not run without this.
 *
 * @author Ricard P. Barnes
 */
class Launcher {

    /**
     * This main method redirects the program execution to the JavaFx application.
     *
     * @param args The program execution arguments.
     */
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

}