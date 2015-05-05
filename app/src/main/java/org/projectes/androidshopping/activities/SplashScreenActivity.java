package org.projectes.androidshopping.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.projectes.androidshopping.R;


public class SplashScreenActivity extends BaseActivity {

    private ImageView imgLogo = null;
    private TextView lblSplash = null;
    private Animation fadeIn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        associateControls();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void associateControls(){
        imgLogo = (ImageView) findViewById(R.id.splash_screen_activity_imgApp);
        lblSplash = (TextView) findViewById(R.id.splash_screen_activity_lblAppName);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);

        if(lblSplash != null){
            lblSplash.setTypeface(boldFont);
            if(fadeIn != null) {
                lblSplash.startAnimation(fadeIn);
            }
        }
        if(imgLogo != null && fadeIn != null){
            imgLogo.startAnimation(fadeIn);
        }

    }

}
