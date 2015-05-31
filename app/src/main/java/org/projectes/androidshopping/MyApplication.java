package org.projectes.androidshopping;


import android.app.Application;

import org.projectes.androidshopping.DAObject.Usuari;

/**
 * Created by alloveras on 29/04/15.
 */
public class MyApplication  extends Application {

    public static Object mutexSplash = new Object();

    private Usuari userLog;

    public MyApplication() {
        this.userLog = new Usuari();
    }

    public Usuari getUserLog() {
        return userLog;
    }

    public void setUserLog(Usuari userLog) {
        this.userLog = userLog;
    }
}
