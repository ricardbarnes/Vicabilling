package com.vonblum.vicabilling.app.service;

import java.io.File;

/**
 * Basic file checking operations class.
 *
 * @author Ricard P. Barnes
 */
public class FileChecker {

    private static final String EXT_SEPARATOR = ".";

    /**
     * Checks if a given path corresponds to an actual file.
     *
     * @param path The path of the file to be checked.
     * @return true if and only if the given path corresponds to an actual file.
     */
    public boolean isActualFilePath(String path) {
        boolean isFile = false;
        File file = new File(path);

        if (file.exists()) {
            isFile = true;
        }

        return isFile;
    }

    /**
     * Checks if a given set of paths are actual file paths.
     *
     * @param folderPaths The array containing the paths to be checked.
     * @return True if and only if every passed path is an actual file path.
     */
    public boolean areFilesExisting(String[] folderPaths) {
        boolean araAllFiles = true;
        FileChecker fileChecker = new FileChecker();

        for (String path : folderPaths) {
            if (!fileChecker.isActualFilePath(path)) {
                araAllFiles = false;
                break;
            }
        }

        return araAllFiles;
    }

    public boolean hasFileGivenExtension(File file, String extension) {
        boolean hasExtension = false;

        String filename = file.getName();
        int filenameLength = filename.length();
        int extLength = EXT_SEPARATOR.length() + extension.length();

        if (filenameLength > extLength) {
            int initialIdx = filenameLength - extLength;
            String actualExtension = filename.substring(initialIdx, filenameLength);
            String expectedExt = EXT_SEPARATOR + actualExtension;

            if (extension.equals(expectedExt)) {
                hasExtension = true;
            }
        }

        return hasExtension;
    }

}
