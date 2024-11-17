package com.tarjanyicsanad.data.settings;

import java.util.prefs.Preferences;

public class UserSettings {
    private UserSettings() {}

    private static final Preferences preferences = Preferences.userNodeForPackage(UserSettings.class);

    private static final String STORAGE_MODE_KEY = "storageMode";
    private static final String SAVE_TO_FILE_KEY = "saveToFile";
    private static final String LOAD_FROM_FILE_KEY = "loadFromFile";

    public static StorageMode getStorageMode() {
        return StorageMode.valueOf(preferences.get(STORAGE_MODE_KEY, StorageMode.IN_MEMORY.name()));
    }

    public static void setStorageMode(StorageMode value) {
        preferences.put(STORAGE_MODE_KEY, String.valueOf(value));
    }

    public static boolean getSaveToFile() {
        return preferences.getBoolean(SAVE_TO_FILE_KEY, true);
    }

    public static void setSaveToFile(boolean value) {
        preferences.putBoolean(SAVE_TO_FILE_KEY, value);
    }

    public static boolean getLoadFromFile() {
        return preferences.getBoolean(LOAD_FROM_FILE_KEY, false);
    }

    public static void setLoadFromFile(boolean value) {
        preferences.putBoolean(LOAD_FROM_FILE_KEY, value);
    }

    public enum StorageMode {
        IN_MEMORY, DATABASE
    }
}
