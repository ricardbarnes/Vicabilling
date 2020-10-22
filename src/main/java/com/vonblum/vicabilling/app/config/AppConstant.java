package com.vonblum.vicabilling.app.config;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class AppConstant {

    private static final String FOLDER_BASE = "/Vicabilling/";
    private static final String FOLDER_SYSTEM_BASE = ".system/";

    public static final String CSS_RES_PATH = "/css/style.css";
    public static final String LANG_BASE = "lang/lang";
    public static final String VIEW_BASE = "/view/";
    public static final String VIEW_APPEND = "View.fxml";
    public static final String PDF_FOLDER = "PDFs";
    public static final String BAR_ICON_RES = "/image/icon.png";

    public static String getFolderBase() {
        File defFolder = FileSystemView.getFileSystemView().getDefaultDirectory();

        if (!defFolder.exists()) {
            // TODO throw some exception
        }

        return defFolder.getPath() + FOLDER_BASE;
    }

    public static String getFolderSystemBase() {
        return getFolderBase() + FOLDER_SYSTEM_BASE;
    }

    public static String getPdfFolder() {
        return getFolderBase() + File.separator + PDF_FOLDER;
    }
}
