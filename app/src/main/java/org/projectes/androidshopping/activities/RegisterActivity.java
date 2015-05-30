package org.projectes.androidshopping.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.projectes.androidshopping.R;

public class RegisterActivity extends BaseActivity {
    private String sGender;
    private Spinner spinGender;
    private Button btnRegister;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtRepeatPass;
    private EditText txtName;
    private CheckBox chckTerm;
    private String errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        associateControls();
    }

    private void associateControls() {
        this.spinGender = (Spinner) findViewById(R.id.activity_register_spinAge);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_age, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.spinGender.setAdapter(adapter);

        this.txtEmail = (EditText)findViewById(R.id.activity_register_txtEmail);
        this.txtPassword = (EditText)findViewById(R.id.activity_register_txtPassword);
        this.txtRepeatPass = (EditText)findViewById(R.id.activity_register_txtRepeatPassword);
        this.txtName = (EditText)findViewById(R.id.activity_register_txtName);

        this.chckTerm = (CheckBox)findViewById(R.id.activity_register_chckTerms);

        this.btnRegister = (Button)findViewById(R.id.activity_register_btnRegister);
        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = validarCampsRegistre();
                if (valid){
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validarCampsRegistre() {
        boolean result = true;

        return result;
    }

    public void onRadioButtonClicked(View view){
        switch(view.getId()){
            case R.id.activity_register_rdioMale:
                this.sGender = "Hombre";
                break;
            case R.id.activity_newUser_rdioFemale:
                this.sGender = "Mujer";
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
}
