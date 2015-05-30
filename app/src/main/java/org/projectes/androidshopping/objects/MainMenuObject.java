package org.projectes.androidshopping.objects;

import android.app.Activity;

/**
 * Created by alloveras on 26/05/15.
 */

public class MainMenuObject{

    private String sText;
    private int nImage;
    private Class<?> activityClass;
    private boolean isExit;

    public MainMenuObject(String sText,int nImage,Class<?> activityClass){
        this.sText = sText;
        this.nImage = nImage;
        this.activityClass = activityClass;
        this.isExit = false;
    }

    public void setIsExit(boolean value){
        this.isExit = value;
    }

    public boolean getIsExit(){
        return this.isExit;
    }

    public void setImatge(int nId){
        this.nImage = nId;
    }

    public void setText(String sText){
        this.sText = sText;
    }

    public int getImatge(){
        return this.nImage;
    }

    public String getText(){
        return this.sText;
    }

    public Class<?> getActivityClass(){
        return activityClass;
    }


}