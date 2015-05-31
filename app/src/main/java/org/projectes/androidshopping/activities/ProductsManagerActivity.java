package org.projectes.androidshopping.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOProductes;
import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.Listeners.IResultList;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_SelectAllNotDeleted;
import org.projectes.androidshopping.adapters.ManageProductsAdapter;

import java.util.ArrayList;

public class ProductsManagerActivity extends BaseActivity {

    private DAOProductes daoProductes = null;
    private DBTask_Base_SelectAllNotDeleted<DAOProductes,Producte> taskProductes = null;
    private ArrayList<Producte> aProductes = null;
    private ManageProductsAdapter adapter = null;
    private ListView listViewProductes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_manager);
        setTitle(R.string.activity_manage_products_activityName);
        associateControls();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products_manager, menu);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        taskProductes = new DBTask_Base_SelectAllNotDeleted<DAOProductes,Producte>();
        //Obtenim noves dades de la base de dades local
        if(taskProductes != null){

            taskProductes.setResultListener(new IResultList<Producte>() {
                @Override
                public void onSuccess(ArrayList<Producte> obj) {
                    if(aProductes != null){
                        aProductes.clear();

                        //Afegim els productes de la base de dades a la llista
                        for(Producte p : obj){
                            aProductes.add(p);
                        }
                        adapter.updateInformation();
                    }
                }

                @Override
                public void onFail(String missatgeError) {

                    //Netegem la llista de productes perquè està desactualitzada
                    if(aProductes != null){
                        aProductes.clear();
                    }

                    Toast.makeText(ProductsManagerActivity.this, missatgeError, Toast.LENGTH_LONG).show();

                }
            });

            taskProductes.execute(this, Constants.BBDD_SELECT_ID,daoProductes);
        }
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
        aProductes = new ArrayList<Producte>();
        daoProductes = new DAOProductes(this);
        adapter = new ManageProductsAdapter(this,aProductes);
        listViewProductes = (ListView) findViewById(R.id.activity_manage_products_listView);

        //Assign the adapter
        if(listViewProductes != null && adapter != null){
            listViewProductes.setItemsCanFocus(true);
            listViewProductes.setAdapter(adapter);
        }

    }

}
