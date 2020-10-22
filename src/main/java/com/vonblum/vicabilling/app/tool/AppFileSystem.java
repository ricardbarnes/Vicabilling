package com.vonblum.vicabilling.app.tool;

import com.vonblum.vicabilling.app.config.AppConstant;
import com.vonblum.vicabilling.app.controller.helper.LogoHandler;
import com.vonblum.vicabilling.app.data.model.Company;
import com.vonblum.vicabilling.app.data.model.Logo;
import com.vonblum.vicabilling.app.data.model.Workspace;
import com.vonblum.vicabilling.app.service.FileChecker;
import com.vonblum.vicabilling.app.service.FileHandler;
import com.vonblum.vicabilling.app.service.ObjectSerializer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AppFileSystem {

    public static final String[] SYSTEM_FOLDERS = {
            Company.FOLDER_NAME,
            Workspace.FOLDER_NAME,
            Logo.FOLDER_NAME,
    };

    public void checkAppFileSystem() {
        FileChecker fileChecker = new FileChecker();
        boolean areAllSystemFilesExisting = fileChecker.areFilesExisting(getAppSystemFoldersPaths());

        if (!areAllSystemFilesExisting) {
            createFolderStructure();
            createBasicFiles();
            setDefaultLogoFile();
        }
    }

    public void createFolderStructure() {
        FileHandler fileHandler = new FileHandler();
        boolean areSystemFoldersCreated = fileHandler.makeFolders(getAppSystemFoldersPaths());
        boolean isPdfFolderCreated =
                fileHandler.makeFolder(AppConstant.getPdfFolder());

        if (areSystemFoldersCreated && isPdfFolderCreated) {
            System.out.println("Folder structure created.");
        }
    }

    public void createBasicFiles() {
        ObjectSerializer serializer = new ObjectSerializer();

        boolean isCompanyCreated =
                serializer.serializeObject(Company.getDefaultCompany(), Company.getFilepath());
        if (isCompanyCreated) {
            System.out.println("Company file created.");
        }

    }

    public void setDefaultLogoFile() {
        try {
            URL resourceUrl = getClass().getResource(Logo.DEF_LOGO_RES_PATH);
            File dest =
                    new File(AppConstant.getFolderSystemBase() + Logo.FOLDER_NAME + File.separator + Logo.FILENAME);
            FileUtils.copyURLToFile(resourceUrl, dest); // Take the resource out of the jar
            LogoHandler.resizeAndSaveImage(dest, LogoHandler.WIDTH, LogoHandler.HEIGHT, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getAppSystemFoldersPaths() {
        int nFolder = SYSTEM_FOLDERS.length;
        String[] appFoldersPath = new String[nFolder];

        for (int i = 0; i < nFolder; i++) {
            appFoldersPath[i] = AppConstant.getFolderSystemBase() + SYSTEM_FOLDERS[i];
        }

        return appFoldersPath;
    }

}
