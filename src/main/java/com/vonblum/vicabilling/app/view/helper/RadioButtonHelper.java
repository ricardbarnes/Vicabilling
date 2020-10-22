package com.vonblum.vicabilling.app.view.helper;

import javafx.scene.control.RadioButton;

public class RadioButtonHelper {

    public static void switchRadioButtonsState(RadioButton firstButton, RadioButton secondButton) {
        boolean firstButtonState = firstButton.isSelected();
        boolean secondButtonState = secondButton.isSelected();

        firstButton.setSelected(!firstButtonState);
        secondButton.setSelected(!secondButtonState);
    }

}
