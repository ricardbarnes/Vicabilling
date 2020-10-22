package com.vonblum.vicabilling.app.service;

import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * Object serializing and deserializing class.
 *
 * @author Ricard P. Barnes
 */
public class ObjectSerializer {

    /**
     * Serializes a given object into a file within the given path.
     *
     * @param object   The object to be serialize.
     * @param filepath The path for the object containing class.
     * @return True if and only if the object has been successfully serialized. False if not.
     */
    public boolean serializeObject(Object object, String filepath) {
        boolean hasSucceed;

        try {
            FileOutputStream out = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(object);
            hasSucceed = true;
        } catch (IOException e) {
            hasSucceed = false;
            e.printStackTrace();
        }

        return hasSucceed;
    }

    /**
     * Deserializes an object serialized into a file from a given path.
     *
     * @param filepath The path of the file that stores the object.
     * @return True if and only if the object has been successfully deserialized. False if not.
     */
    public @Nullable Object deserializeObject(String filepath) {
        Object object = null;

        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();
            objectIn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return object;
    }

}
