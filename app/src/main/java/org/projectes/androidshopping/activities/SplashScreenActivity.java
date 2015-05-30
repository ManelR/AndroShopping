package org.projectes.androidshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.MyApplication;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.WSTask;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreenActivity extends BaseActivity {

    private ImageView imgLogo = null;
    private TextView lblSplash = null;
    private Animation fadeIn = null;
    private int boolIntent1 = 0;
    private int boolIntent2 = 0;

    private WSTask productsTask = null;
    private Timer timer;

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

        this.productsTask = new WSTask();
        this.productsTask.setResultListener(new IResult<Message>() {
            @Override
            public void onSuccess(Message IRresult) {
                synchronized (MyApplication.mutexSplash){
                    boolIntent2 = 1;
                    if (boolIntent1 == 1){
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }

            @Override
            public void onFail(String missatgeError) {
                Toast.makeText(SplashScreenActivity.this, missatgeError, Toast.LENGTH_LONG).show();
                synchronized (MyApplication.mutexSplash){
                    boolIntent2 = 1;
                    if (boolIntent1 == 1){
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (MyApplication.mutexSplash){
                    boolIntent1 = 1;
                    if (boolIntent2 == 1){
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        }, 2000);

        this.productsTask.execute(this, Constants.URL_PRODUCTES);

    }

}
