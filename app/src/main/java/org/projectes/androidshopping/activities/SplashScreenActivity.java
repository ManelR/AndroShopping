package org.projectes.androidshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAObject.Product;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.WSTask;
import org.projectes.androidshopping.WS.JacksonJSONHelper;

import java.io.IOException;


public class SplashScreenActivity extends BaseActivity {

    private ImageView imgLogo = null;
    private TextView lblSplash = null;
    private Animation fadeIn = null;

    private WSTask productsTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        associateControls();

        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);

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
                ObjectMapper objMapper = JacksonJSONHelper.Initialize();
                try {
                    Product[] wsResult = objMapper.readValue(IRresult.obj.toString(), Product[].class);
                    Log.d("Hola", "Hola Hola");
                    Toast.makeText(SplashScreenActivity.this, (String) wsResult[0].getNombre(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String missatgeError) {
                Toast.makeText(SplashScreenActivity.this, missatgeError, Toast.LENGTH_LONG).show();
            }
        });
        this.productsTask.execute(this, Constants.URL_PRODUCTES);


    }

}
