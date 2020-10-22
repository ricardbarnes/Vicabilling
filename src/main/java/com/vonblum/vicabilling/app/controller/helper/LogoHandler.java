package com.vonblum.vicabilling.app.controller.helper;

import com.vonblum.vicabilling.app.Context;
import com.vonblum.vicabilling.app.controller.CompanyController;
import com.vonblum.vicabilling.app.data.model.Logo;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class LogoHandler {

    public static final int WIDTH = 120; // pixels
    public static final int HEIGHT = 120; // pixels
    public static final String LOGO_EXT = "png";

    public static final String[] IMAGE_EXT = {
            "*.jpg",
            "*.jpeg",
            "*.png"
    };

    public static void resizeAndSaveImage(File sourcePath, int width, int height, File destFile) {
        try {
            Thumbnails.of(sourcePath)
                    .size(width, height)
                    .outputFormat(LOGO_EXT)
                    .toFile(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final CompanyController controller;
    private final ResourceBundle resourceBundle;

    public LogoHandler(CompanyController companyController) {
        this.controller = companyController;
        this.resourceBundle = companyController.getResourceBundle();
    }

    private FileChooser.ExtensionFilter[] getExtensionFilters() {
        String allFilesDesc = resourceBundle.getString("All_files");
        String imageDescription = resourceBundle.getString("Image_files");
        String allFilesRepr = "*";
        FileChooser.ExtensionFilter allFilesExtFtr = new FileChooser.ExtensionFilter(allFilesDesc, allFilesRepr);
        FileChooser.ExtensionFilter imageFileExtFtr = new FileChooser.ExtensionFilter(imageDescription, IMAGE_EXT);
        FileChooser.ExtensionFilter[] extensionFilters = new FileChooser.ExtensionFilter[2];
        extensionFilters[0] = imageFileExtFtr;
        extensionFilters[1] = allFilesExtFtr;
        return extensionFilters;
    }

    public File loadImageFile() {
        String initialDir = System.getProperty("user.home");
        File file = new File(initialDir);

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(getExtensionFilters());
        chooser.setInitialDirectory(file);

        Stage stage = Context.getPrimaryStage();
        return chooser.showOpenDialog(stage);
    }

    public void handleLogoChoosing() {
        File logoFile = loadImageFile();
        if (logoFile != null) {
            resizeAndSaveImage(logoFile, WIDTH, HEIGHT, new File(Logo.getFilepath()));
            controller.showCompanyLogo(Logo.getFilepath());
        }
    }

}
