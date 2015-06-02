package org.projectes.androidshopping.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOProductes;
import org.projectes.androidshopping.DAObject.Compra;
import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.Listeners.IResultList;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_SelectAll;
import org.projectes.androidshopping.adapters.BuyAdapter;
import org.projectes.androidshopping.fragments.BuyActivityNormalFragment;
import org.projectes.androidshopping.fragments.BuyActivitySearchFragment;

import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends BaseActivity {

    private ArrayList<Producte> compraUsuari = null;
    private static BuyActivityNormalFragment fragmentNormal = new BuyActivityNormalFragment();
    private static BuyActivitySearchFragment fragmentSearch = new BuyActivitySearchFragment();
    private ListView compraListView = null;
    private BuyAdapter adapter = null;
    private Spinner spinQuantitat;
    private DAOProductes daoProductes = null;
    private DBTask_Base_SelectAll<DAOProductes, Producte> taskProduct;
    private int fragmentId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        changeFragment(R.id.activity_buy_fragmentContainer,fragmentNormal);
        fragmentId = 0;
        associateControls();
    }

    public void onResume(){
        super.onResume();
        switch (fragmentId){
            case 0:
                changeFragment(R.id.activity_buy_fragmentContainer,fragmentNormal);
                break;
            case 1:
                changeFragment(R.id.activity_buy_fragmentContainer,fragmentSearch);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.activity_buy_buyMenuItem:
                break;
            case R.id.activity_buy_searchMenuItem:
                if(fragmentId == 0){
                    changeFragment(R.id.activity_buy_fragmentContainer,fragmentSearch);
                    fragmentId = 1;
                }else{
                    changeFragment(R.id.activity_buy_fragmentContainer,fragmentNormal);
                    fragmentId = 0;
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void associateControls(){
        compraUsuari = new ArrayList<Producte>();
        compraListView = (ListView) findViewById(R.id.activity_buy_listView);
        daoProductes = new DAOProductes(this);
        this.spinQuantitat = (Spinner) findViewById(R.id.item_buy_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpin = ArrayAdapter.createFromResource(this,
                R.array.spinner_quantity, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.spinQuantitat.setAdapter(adapterSpin);

        taskProduct = new DBTask_Base_SelectAll<DAOProductes, Producte>();
        taskProduct.setResultListener(new IResultList<Producte>() {
            @Override
            public void onSuccess(ArrayList<Producte> obj) {
                if (obj != null){
                    compraUsuari = obj;
                    adapter = new BuyAdapter(BuyActivity.this, compraUsuari);
                    compraListView.setAdapter(adapter);
                }else {
                    Toast.makeText(BuyActivity.this, "Error recuperando productos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFail(String missatgeError) {
                Toast.makeText(BuyActivity.this, missatgeError, Toast.LENGTH_LONG).show();
            }
        });
        taskProduct.execute(this, Constants.BBDD_SELECT_ID, daoProductes);
    }

}
