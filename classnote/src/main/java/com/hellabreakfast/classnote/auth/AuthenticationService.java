package com.hellabreakfast.classnote.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * This class is necessary for the AccountManager integration
 */
public class AuthenticationService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new GatechAccountAuthenticator(this).getIBinder();
    }

}