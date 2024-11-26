package com.tarjanyicsanad.data.settings;

import java.util.prefs.Preferences;

/**
 * A class for managing user settings.
 */
public class UserSettings {
    private UserSettings() {}

    private static final Preferences preferences = Preferences.userNodeForPackage(UserSettings.class);

    private static final String STORAGE_MODE_KEY = "storageMode";
    private static final String SAVE_TO_FILE_KEY = "saveToFile";
    private static final String LOAD_FROM_FILE_KEY = "loadFromFile";

    /**
     * Returns the storage mode for the application.
     * @return the storage mode for the application
     */
    public static StorageMode getStorageMode() {
        return StorageMode.valueOf(preferences.get(STORAGE_MODE_KEY, StorageMode.DATABASE.name()));
    }

    /**
     * Sets the storage mode for the application.
     *
     * @param value the storage mode to set
     */
    public static void setStorageMode(StorageMode value) {
        preferences.put(STORAGE_MODE_KEY, String.valueOf(value));
    }

    /**
     * Returns whether the application should save data to a file on shutdown.
     *
     * @return {@code true} if the application should save data to a file on shutdown, {@code false} otherwise
     */
    public static boolean getSaveToFile() {
        return preferences.getBoolean(SAVE_TO_FILE_KEY, true);
    }

    /**
     * Sets whether the application should save data to a file on shutdown.
     *
     * @param value {@code true} if the application should save data to a file on shutdown, {@code false} otherwise
     */
    public static void setSaveToFile(boolean value) {
        preferences.putBoolean(SAVE_TO_FILE_KEY, value);
    }

    /**
     * Returns whether the application should load data from a file on startup.
     *
     * @return {@code true} if the application should load data from a file on startup, {@code false} otherwise
     */
    public static boolean getLoadFromFile() {
        return preferences.getBoolean(LOAD_FROM_FILE_KEY, false);
    }

    /**
     * Sets whether the application should load data from a file on startup.
     *
     * @param value {@code true} if the application should load data from a file on startup, {@code false} otherwise
     */
    public static void setLoadFromFile(boolean value) {
        preferences.putBoolean(LOAD_FROM_FILE_KEY, value);
    }

    /**
     * An enumeration of the possible storage modes.
     */
    public enum StorageMode {
        /**
         * Store data in memory.
         */
        IN_MEMORY,

        /**
         * Store data in a database.
         */
        DATABASE
    }
}
