package org.projectes.androidshopping.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.projectes.androidshopping.MyApplication;
import org.projectes.androidshopping.R;

/**
 * Created by alloveras on 29/04/15.
 */

public class BaseActivity extends AppCompatActivity {

    protected MyApplication app = null;
    protected ActionBar aBar = null;
    protected Typeface normalFont = null;
    protected Typeface boldFont = null;
    protected Typeface italicFont = null;

    protected FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allocateMemory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        finish();
    }

    private void allocateMemory(){

        //Get App Singleton
        app = (MyApplication) getApplication();

        //Get Fragment manager
        fragmentManager = getSupportFragmentManager();

        //Get the action bar
        aBar = (ActionBar) getSupportActionBar();
        if(aBar != null){
            aBar.setIcon(R.mipmap.ic_launcher);
            aBar.setDisplayShowHomeEnabled(true);
            aBar.setDisplayHomeAsUpEnabled(true);
        }

        //Import Typefaces
        normalFont = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        boldFont = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Bold.ttf");
        italicFont = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Italic.ttf");

    }

    protected void changeFragment(int containerId,Fragment newFragment){
        fragmentTransaction = fragmentManager.beginTransaction();

    }

}
