package org.projectes.androidshopping.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOCompras;
import org.projectes.androidshopping.DAObject.Compra;
import org.projectes.androidshopping.Listeners.IResultList;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_SelectAll;
import org.projectes.androidshopping.adapters.PurchaseHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryActivity extends BaseActivity {

    private ListView listViewCompras = null;
    private PurchaseHistoryAdapter adapter = null;
    private DAOCompras daoCompres = null;
    private DBTask_Base_SelectAll<DAOCompras,Compra> taskPurchaseHistory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        setTitle(getString(R.string.activity_purchaseHistory_activityName));
        associateControls();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_purchase_history, menu);
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

    private void associateControls(){
        listViewCompras = (ListView) findViewById(R.id.activity_purchaseHistory_listView);
        daoCompres = new DAOCompras(this);

        if(daoCompres != null && listViewCompras != null){
            taskPurchaseHistory = new DBTask_Base_SelectAll<DAOCompras,Compra>();
            taskPurchaseHistory.setResultListener(new IResultList<Compra>() {
                @Override
                public void onSuccess(ArrayList<Compra> obj) {
                    adapter = new PurchaseHistoryAdapter(PurchaseHistoryActivity.this,obj);
                }

                @Override
                public void onFail(String missatgeError) {
                    Toast.makeText(PurchaseHistoryActivity.this,missatgeError,Toast.LENGTH_LONG).show();
                }
            });
            taskPurchaseHistory.execute(this,Constants.BBDD_SELECT_ID,daoCompres);
        }


    }

}
