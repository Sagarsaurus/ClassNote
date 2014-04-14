package com.hellabreakfast.classnote.ui;

import android.app.Application;

public class ClassNoteApp extends Application {

    private static ClassNoteApp instance;

    public ClassNoteApp() {
        instance = this;
    }

    public static ClassNoteApp getApplication() {
        return instance;
    }

}
