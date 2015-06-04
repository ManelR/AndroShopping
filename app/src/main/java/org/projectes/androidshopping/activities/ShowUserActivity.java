package org.projectes.androidshopping.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_SelectId;

public class ShowUserActivity extends BaseActivity {

    public static final String ID_USER_KEY = "ID_USER_KEY";

    private DBTask_Base_SelectId<DAOUsuaris,Usuari> taskUsuari = null;
    private DAOUsuaris daoUsuaris = null;
    private TextView txtEmail = null;
    private RadioGroup radioGenere = null;
    private TextView txtAge = null;
    private TextView txtTotalAmount = null;
    private int userId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);
        setTitle(getString(R.string.activity_showUser_title));
        associateControls();
        if(getIntent() != null) {
            userId = getIntent().getIntExtra(ID_USER_KEY,-1);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        //Check if the userId is valid
        if(userId != -1){
            Usuari auxUser = new Usuari();
            auxUser.setId(userId);
            taskUsuari = new DBTask_Base_SelectId<DAOUsuaris,Usuari>();
            taskUsuari.setResultListener(new IResult<Usuari>() {
                @Override
                public void onSuccess(Usuari IRresult) {
                    txtEmail.setText(IRresult.getEmail());
                    txtAge.setText(IRresult.getEdat() + " años");
                    txtTotalAmount.setText(String.format("%.2f", IRresult.getTotalAmount()) + " €");
                    radioGenere.check(IRresult.getGenere() == 2 ? R.id.activity_showUser_radioFemale : R.id.activity_showUser_radioMale);
                }

                @Override
                public void onFail(String missatgeError) {
                    Toast.makeText(getApplicationContext(),null,Toast.LENGTH_LONG).show();
                }
            });
            taskUsuari.execute(this, Constants.BBDD_SELECT_ID,daoUsuaris,auxUser);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_user, menu);
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
        txtAge = (TextView) findViewById(R.id.activity_showUser_txtAge);
        txtEmail = (TextView) findViewById(R.id.activity_showUser_txtEmail);
        txtTotalAmount = (TextView) findViewById(R.id.activity_showUser_txtTotalAmount);
        radioGenere = (RadioGroup) findViewById(R.id.activity_showUser_genereContainer);
        daoUsuaris = new DAOUsuaris(this);
    }
}
