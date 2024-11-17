package com.tarjanyicsanad.data.settings;

import java.util.prefs.Preferences;

public class UserSettings {
    private UserSettings() {}

    private static final Preferences preferences = Preferences.userNodeForPackage(UserSettings.class);

    private static final String STORAGE_MODE_KEY = "storageMode";

    public static StorageMode getStorageMode() {
        return StorageMode.valueOf(preferences.get(STORAGE_MODE_KEY, StorageMode.IN_MEMORY.name()));
    }

    public static void setStorageMode(StorageMode value) {
        preferences.put(STORAGE_MODE_KEY, String.valueOf(value));
    }

    public enum StorageMode {
        IN_MEMORY, DATABASE
    }
}
