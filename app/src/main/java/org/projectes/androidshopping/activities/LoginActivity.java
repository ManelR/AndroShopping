package org.projectes.androidshopping.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.Constants.MD5;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.MyApplication;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_Modify;
import org.projectes.androidshopping.Task.DBTask_Base_SelectId;
import org.projectes.androidshopping.Task.DBTask_Usuari_Select_Email;

/**
 * Created by alloveras on 12/05/15.
 */

public class LoginActivity extends BaseActivity {

    private TextView link = null;
    private Button btnLogin = null;
    private EditText txtEmail = null;
    private EditText txtPass = null;
    private CheckBox chckRecordar = null;
    private TextView lblTermsAndCondition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        associateControls();
    }

    private void associateControls() {
        this.link = (TextView) findViewById(R.id.activity_login_lblRegister);
        this.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        this.txtEmail = (EditText)findViewById(R.id.activity_login_txtEmail);
        this.txtPass = (EditText)findViewById(R.id.activity_login_txtContraseña);

        this.chckRecordar = (CheckBox)findViewById(R.id.activity_login_chckRemember);

        this.btnLogin = (Button)findViewById(R.id.activity_login_btnEntrar);

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBTask_Usuari_Select_Email BBDD_Select = new DBTask_Usuari_Select_Email();
                BBDD_Select.setResultListener(new IResult<Usuari>() {
                    @Override
                    public void onSuccess(Usuari IRresult) {
                        String errorMessage;
                        String email;
                        String hash_pass;
                        DBTask_Base_Modify<DAOUsuaris, Usuari> Task_update = new DBTask_Base_Modify<DAOUsuaris, Usuari>();
                        DAOUsuaris BBDD_Update = new DAOUsuaris(LoginActivity.this);
                        Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);

                        if(IRresult != null){
                            //Comprovar si les dades són correctes:
                            email = txtEmail.getText().toString();
                            hash_pass = MD5.md5(txtPass.getText().toString());
                            if (email.equals(IRresult.getEmail()) && hash_pass.equals(IRresult.getHash_pass())){
                                LoginActivity.this.app.setUserLog(IRresult);
                                if (chckRecordar.isChecked()){
                                    //Escriure Login a la BD
                                    Log.d("Checked:", "is checked " + Integer.toString(IRresult.getId()));
                                    IRresult.setLogged_in(1);
                                    Task_update.setResultListener(new IResult<Boolean>() {
                                        @Override
                                        public void onSuccess(Boolean IRresult) {
                                            Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
                                            startActivity(i);
                                            finish();
                                        }

                                        @Override
                                        public void onFail(String missatgeError) {
                                            Toast.makeText(LoginActivity.this, missatgeError, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    Task_update.execute(LoginActivity.this, Constants.BBDD_EDIT, BBDD_Update, IRresult);
                                }else{
                                    startActivity(i);
                                    finish();
                                }
                            }else{
                                errorMessage = LoginActivity.this.getString(R.string.error_login_fields);
                                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }else{
                            errorMessage = LoginActivity.this.getString(R.string.error_login_fields);
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFail(String missatgeError) {

                    }
                });
                BBDD_Select.execute(LoginActivity.this, txtEmail.getText().toString());
            }
        });
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


}
