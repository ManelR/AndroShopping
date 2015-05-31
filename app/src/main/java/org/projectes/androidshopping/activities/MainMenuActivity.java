package org.projectes.androidshopping.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.MyApplication;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_Modify;
import org.projectes.androidshopping.adapters.MainMenuAdapter;

public class MainMenuActivity extends BaseActivity {

    private GridView gridView = null;
    private MainMenuAdapter adapter = null;
    private boolean isAdmin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_layout);
        Usuari userLogin = this.app.getUserLog();
        this.isAdmin = userLogin.getRol() == 1;
        associateControls();
        aBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(isAdmin){
            getMenuInflater().inflate(R.menu.menu_main_menu_admin,menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_main_menu_user, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent i = null;
        DAOUsuaris BBDD_User = new DAOUsuaris(this);
        DBTask_Base_Modify<DAOUsuaris, Usuari> Task_User = new DBTask_Base_Modify<DAOUsuaris, Usuari>();
        Usuari user;

        switch (id){

            //User Role Menu Items
            case R.id.menu_main_menu_itemPerfil:
                break;
            case R.id.menu_main_menu_itemBuy:
                break;
            case R.id.menu_main_menu_itemPurchaseHistory:
                break;
            case R.id.menu_main_menu_itemExit:
                user = this.app.getUserLog();
                user.setLogged_in(0);
                Task_User.setResultListener(new IResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean IRresult) {
                        Intent i = new Intent(MainMenuActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFail(String missatgeError) {
                        Toast.makeText(MainMenuActivity.this, "Error al sortir", Toast.LENGTH_LONG).show();
                    }
                });
                Task_User.execute(MainMenuActivity.this, Constants.BBDD_EDIT, BBDD_User, user);
                break;


            //Admin Role Menu Items
            case R.id.menu_main_menu_itemManageProducts:
                i = new Intent(getApplicationContext(),ProductsManagerActivity.class);
                startActivity(i);
                break;
            case R.id.menu_main_menu_itemManageUsers:
                i = new Intent(getApplicationContext(),ManageUsersActivity.class);
                startActivity(i);
                break;
            case R.id.menu_main_menu_itemSellList:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void associateControls(){

        this.adapter = new MainMenuAdapter(this,isAdmin);
        this.gridView = (GridView) findViewById(R.id.activity_main_menu_gridView);
        if(gridView != null && adapter != null){
            gridView.setAdapter(adapter);
        }

    }

}
