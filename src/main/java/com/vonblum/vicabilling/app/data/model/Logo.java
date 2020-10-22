package com.vonblum.vicabilling.app.data.model;

import com.vonblum.vicabilling.app.config.AppConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Logo {

    public static final String FOLDER_NAME = "logo";
    public static final String FILENAME = "logo.png";
    public static final String DEF_LOGO_RES_PATH = "/image/default_logo.png";

    @Contract(pure = true)
    public static @NotNull String getFilepath() {
        return getBaseFilepath() + FILENAME;
    }

    public static String getBaseFilepath() {
        return AppConstant.getFolderSystemBase() + FOLDER_NAME + File.separator;
    }

}
