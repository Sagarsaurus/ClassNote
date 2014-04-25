package com.hellabreakfast.classnote.ui;

import android.app.Application;

/**
 * This class subclasses the android Application class and makes it into a singleton class so we can access it from anywhere in our code.
 */

public class ClassNoteApp extends Application {

    private static ClassNoteApp instance;

    public ClassNoteApp() {
        instance = this;
    }

    public static ClassNoteApp getApplication() {
        return instance;
    }
}
