package org.projectes.androidshopping.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAO.DAOVenta;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.DAObject.Venta;
import org.projectes.androidshopping.Listeners.IResultList;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_SelectAll;
import org.projectes.androidshopping.Task.DBTask_Base_SelectAllNotDeleted;
import org.projectes.androidshopping.adapters.SellAdapter;

import java.util.ArrayList;

public class SellListActivity extends BaseActivity {
    private ArrayList<Venta> aVentes;
    private SellAdapter adapter;
    private DAOVenta daoVenta;
    private ListView listViewVentes;
    private DBTask_Base_SelectAll<DAOVenta, Venta> DBTask_Select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_list);
        setTitle(getString(R.string.activity_sell_list_activityName));
        associateControls();
    }

    private void associateControls() {
        this.aVentes = new ArrayList<Venta>();
        this.daoVenta = new DAOVenta(this);
        this.adapter = new SellAdapter(this, this.aVentes);
        this.listViewVentes = (ListView) findViewById(R.id.activity_sellList_listView);
        if(adapter != null){
            this.listViewVentes.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sell_list, menu);
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

    @Override
    public void onResume(){
        super.onResume();
        DBTask_Select = new DBTask_Base_SelectAll<DAOVenta, Venta>();
        if(DBTask_Select != null){
            DBTask_Select.setResultListener(new IResultList<Venta>() {
                @Override
                public void onSuccess(ArrayList<Venta> obj) {
                    if(aVentes != null){
                        aVentes.clear();
                        for(Venta v : obj){
                            aVentes.add(v);
                        }
                        if(adapter != null){
                            adapter.updateInformation();
                        }
                    }
                }

                @Override
                public void onFail(String missatgeError) {
                    if(aVentes != null){
                        aVentes.clear();
                        if(adapter != null) {
                            adapter.updateInformation();
                        }
                    }
                }
            });

            DBTask_Select.execute(this, Constants.BBDD_SELECT_ID, daoVenta);
        }
    }
}
