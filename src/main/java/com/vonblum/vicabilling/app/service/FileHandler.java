package com.vonblum.vicabilling.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Basic file handling methods.
 *
 * @author Ricard P. Barnes
 */
public class FileHandler {

    /**
     * Makes the passed path folder.
     *
     * @param path The path of the folder to be created.
     * @return true if folder has been created, false if not.
     */
    public boolean makeFolder(String path) {
        File file = new File(path);
        return file.mkdirs();
    }

    /**
     * Makes the passed paths folders if they do not exist yet.
     *
     * @param paths A String array containing the folder paths.
     * @return true if all folders have been created, false if not.
     */
    public boolean makeFolders(String[] paths) {
        boolean areAllCreated = true;

        for (String path : paths) {
            if (!makeFolder(path)) {
                areAllCreated = false;
            }
        }

        return areAllCreated;
    }

    public boolean replaceFile(File file, String path) {
        boolean isSuccessful = false;

        File vFile = new File(path);

        Path source = file.toPath();
        Path destination = vFile.toPath();

        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            isSuccessful = true;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return isSuccessful;
    }

    public boolean copyFile(File file, String path) {
        return replaceFile(file, path);
    }

}
