package org.projectes.androidshopping.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_Modify;
import org.projectes.androidshopping.Task.DBTask_Usuari_Select_Email;
import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewUserActivity extends BaseActivity {
    private String sGender;
    private Spinner spinGender;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtRepeatPass;
    private EditText txtName;
    private CheckBox chckTerm;
    private String errorMessage = null;
    private DAOUsuaris BBDDUsers = null;
    private TextView termsAndConditions = null;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        setTitle(getString(R.string.activity_new_user_activityName));
        associateControls();
    }

    private void associateControls() {
        BBDDUsers = new DAOUsuaris(this);

        this.spinGender = (Spinner) findViewById(R.id.activity_newUser_spinAge);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_age, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.spinGender.setAdapter(adapter);

        this.txtEmail = (EditText)findViewById(R.id.activity_newUser_txtEmail);
        this.txtPassword = (EditText)findViewById(R.id.activity_newUser_txtPassword);
        this.txtRepeatPass = (EditText)findViewById(R.id.activity_newUser_txtRepeatPassword);
        this.txtName = (EditText)findViewById(R.id.activity_newUser_txtName);
        this.termsAndConditions = (TextView) findViewById(R.id.activity_newUser_lblTermsAndConditions);
        this.chckTerm = (CheckBox)findViewById(R.id.activity_newUser_chckTerms);

        if(termsAndConditions != null){
            termsAndConditions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder confirmation = new AlertDialog.Builder(NewUserActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                    confirmation.setTitle(getString(R.string.termsAndConditions_lblTitle));
                    confirmation.setMessage(R.string.termsAndConditions_lblBody);
                    confirmation.setNeutralButton(getString(R.string.termsAndConditions_lblOK), null);
                    confirmation.show();
                }
            });
        }



        Button btnRegister = (Button) findViewById(R.id.activity_newUser_btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = validarCampsRegistre();
                if (valid) {
                    //Validar que el correu no existeixi
                    DBTask_Usuari_Select_Email DBTask_User = new DBTask_Usuari_Select_Email();
                    DBTask_User.setResultListener(new IResult<Usuari>() {
                        @Override
                        public void onSuccess(Usuari IRresult) {
                            if (IRresult != null){
                                //Existeix el correu
                                errorMessage = NewUserActivity.this.getString(R.string.error_email_repeat);
                                Toast.makeText(NewUserActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            }else{
                                //No existeix
                                //Guardar l'usuari a la base de dades
                                DBTask_Base_Modify<DAOUsuaris, Usuari> DBTask = new DBTask_Base_Modify<DAOUsuaris, Usuari>();
                                int genere = sGender.equals("Hombre") ? 1 : 2;
                                Usuari user = new Usuari(txtEmail.getText().toString(), txtPassword.getText().toString(), genere, txtName.getText().toString(), Integer.valueOf(spinGender.getSelectedItem().toString()), 2, 0);
                                DBTask.setResultListener(new IResult<Boolean>() {
                                    @Override
                                    public void onSuccess(Boolean IRresult) {
                                        finish();
                                    }

                                    @Override
                                    public void onFail(String missatgeError) {
                                        Toast.makeText(NewUserActivity.this, missatgeError, Toast.LENGTH_LONG).show();
                                    }
                                });
                                DBTask.execute(NewUserActivity.this, Constants.BBDD_INSERT, BBDDUsers, user);
                            }
                        }

                        @Override
                        public void onFail(String missatgeError) {

                        }
                    });
                    DBTask_User.execute(NewUserActivity.this, txtEmail.getText().toString());
                } else {
                    Toast.makeText(NewUserActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validarCampsRegistre() {
        boolean result = true;
        Pattern emailPattern;
        Matcher emailMatcher;

        Editable emailText = this.txtEmail.getText();
        if (!emailText.toString().matches("") && result){
            emailPattern = Pattern.compile(EMAIL_PATTERN);
            emailMatcher = emailPattern.matcher(emailText.toString());
            if (!emailMatcher.matches()){
                result = false;
                errorMessage = this.getString(R.string.error_email_message);
            }
        }else{
            result = false;
            errorMessage = this.getString(R.string.error_email_message);
        }
        Editable passText = this.txtPassword.getText();
        if (passText.toString().matches("") && result){
            errorMessage = this.getString(R.string.error_pass_empty);
            result = false;
        }
        if (passText.toString().length() < 6 && result){
            errorMessage = this.getString(R.string.error_pass_length);
            result = false;
        }
        Editable repeatPassText = this.txtRepeatPass.getText();
        if (repeatPassText.toString().matches("") && result){
            errorMessage = this.getString(R.string.error_rePass_empty);
            result = false;
        }
        if (!passText.toString().equals(repeatPassText.toString()) && result){
            errorMessage = this.getString(R.string.error_pass_nomatch);
            result = false;
        }
        if (this.sGender == null && result){
            errorMessage = this.getString(R.string.error_gender_empty);
            result = false;
        }
        Editable nameText = this.txtName.getText();
        if (nameText.toString().equals("") && result){
            errorMessage = this.getString(R.string.error_name_empty);
            result = false;
        }
        String optionSpinner = (String) this.spinGender.getSelectedItem();
        if (optionSpinner.matches("Edad") && result){
            errorMessage = this.getString(R.string.error_ageSpinner_empty);
            result = false;
        }
        if (!this.chckTerm.isChecked() && result){
            errorMessage = this.getString(R.string.error_chckTerm_notChecked);
            result = false;
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view){
        switch(view.getId()){
            case R.id.activity_newUser_rdioMale:
                this.sGender = "Hombre";
                break;
            case R.id.activity_newUser_rdioFemale:
                this.sGender = "Mujer";
                break;
        }
    }
}
