package org.projectes.androidshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.Listeners.IResultList;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_SelectAllNotDeleted;
import org.projectes.androidshopping.adapters.ManageUsersAdapter;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersActivity extends BaseActivity {

    private DBTask_Base_SelectAllNotDeleted<DAOUsuaris,Usuari> taskUsuaris = null;
    private DAOUsuaris daoUsuaris = null;
    private ListView listViewUsuaris = null;
    private ArrayList<Usuari> aUsuaris = null;
    private ManageUsersAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        setTitle(getString(R.string.activity_manageUsers_activityName));
        associateControls();
    }

    @Override
    public void onResume(){
        super.onResume();
        taskUsuaris = new DBTask_Base_SelectAllNotDeleted<DAOUsuaris, Usuari>();
        if(taskUsuaris != null){
            taskUsuaris.setResultListener(new IResultList<Usuari>() {
                @Override
                public void onSuccess(ArrayList<Usuari> obj) {
                    if(aUsuaris != null){
                        aUsuaris.clear();
                        for(Usuari u : obj){
                            aUsuaris.add(u);
                        }
                        if(adapter != null){
                            adapter.updateInformation();
                        }
                    }
                }

                @Override
                public void onFail(String missatgeError) {
                    if(aUsuaris != null){
                        aUsuaris.clear();
                        if(adapter != null) {
                            adapter.updateInformation();
                        }
                    }
                }
            });

            taskUsuaris.execute(this, Constants.BBDD_SELECT_ID,daoUsuaris);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.activity_manageUsers_menu_addItem:
                Intent i = new Intent(getApplicationContext(),NewUserActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void associateControls(){
        aUsuaris = new ArrayList<Usuari>();
        daoUsuaris = new DAOUsuaris(this);
        adapter = new ManageUsersAdapter(this,aUsuaris);
        listViewUsuaris = (ListView) findViewById(R.id.activity_manageUsers_listView);
        if(adapter != null){
            listViewUsuaris.setAdapter(adapter);
        }
    }
}
