package com.rumbleworks.classnote;

import android.app.Application;
import android.content.Context;

public class ClassNoteApp extends Application {

    private static ClassNoteApp instance;

    public ClassNoteApp() {
        instance = this;
    }

    public static ClassNoteApp getApplication() {
        return instance;
    }

}
