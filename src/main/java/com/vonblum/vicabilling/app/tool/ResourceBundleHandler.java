package com.vonblum.vicabilling.app.tool;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.config.AppConstant;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleHandler {

    /**
     * Creates and stores the default application resource bundle.
     */
    public void setAppResourceBundle() {
        Locale locale = new Locale("ca", "CAT");
        ResourceBundle resourceBundle = ResourceBundle.getBundle(AppConstant.LANG_BASE, locale);
        Context.setResourceBundle(resourceBundle);
    }

}
