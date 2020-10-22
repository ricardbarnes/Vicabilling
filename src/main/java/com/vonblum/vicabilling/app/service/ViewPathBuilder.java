package com.vonblum.vicabilling.app.service;

import com.vonblum.vicabilling.app.config.AppConstant;

import java.net.URL;

public class ViewPathBuilder {

    public static String getViewPathFromEntity(String entityName) {
        return AppConstant.VIEW_BASE + entityName + AppConstant.VIEW_APPEND;
    }

    public static URL getViewURLFromEntity(String entityName, Object callerObject) {
        String fullPath = getViewPathFromEntity(entityName);
        return callerObject.getClass().getResource(fullPath);
    }

}
