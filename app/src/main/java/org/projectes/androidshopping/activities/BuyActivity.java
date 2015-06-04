package org.projectes.androidshopping.activities;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOCompras;
import org.projectes.androidshopping.DAO.DAOProductes;
import org.projectes.androidshopping.DAObject.Compra;
import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.Listeners.IResultList;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_Modify;
import org.projectes.androidshopping.Task.DBTask_Base_SelectAll;
import org.projectes.androidshopping.adapters.BuyAdapter;
import org.projectes.androidshopping.fragments.BuyActivityNormalFragment;
import org.projectes.androidshopping.fragments.BuyActivitySearchFragment;

import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends BaseActivity {

    private static ArrayList<Producte> compraUsuari;
    private static BuyActivityNormalFragment fragmentNormal = new BuyActivityNormalFragment();
    private static BuyActivitySearchFragment fragmentSearch = new BuyActivitySearchFragment();
    private DAOCompras daoCompras;
    private DBTask_Base_Modify<DAOCompras, Compra> taskCompra;
    private ListView compraListView = null;
    private BuyAdapter adapter = null;
    private Spinner spinQuantitat;
    private static int flagOnCreate = 0;
    private DAOProductes daoProductes = null;
    private DBTask_Base_SelectAll<DAOProductes, Producte> taskProduct;
    private static int fragmentId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        if (flagOnCreate == 0){
            compraUsuari = new ArrayList<Producte>();
        }
        switch (fragmentId){
            case 0:
                changeFragment(R.id.activity_buy_fragmentContainer,fragmentNormal);
                break;
            case 1:
                changeFragment(R.id.activity_buy_fragmentContainer,fragmentSearch);
                break;
        }
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
        adapter = new BuyAdapter(BuyActivity.this, compraUsuari);
        compraListView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        fragmentId = 0;
    }

    @Override
    public void onPause(){
        super.onPause();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.activity_buy_listView).getWindowToken(), 0);
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
        Compra compra = new Compra();
        ArrayList<Producte> productesComprats = new ArrayList<Producte>();
        switch(id){
            case R.id.activity_buy_buyMenuItem:
                for (Producte p : compraUsuari){
                    if (p.isChecked()) productesComprats.add(p);
                }
                compra.setlProductes(productesComprats);
                taskCompra = new DBTask_Base_Modify<DAOCompras, Compra>();
                taskCompra.setResultListener(new IResult<Boolean>() {
                    @Override
                    public void onSuccess(Boolean IRresult) {
                        flagOnCreate = 0;
                        BuyActivity.this.finish();
                    }

                    @Override
                    public void onFail(String missatgeError) {
                        Toast.makeText(BuyActivity.this, missatgeError, Toast.LENGTH_LONG).show();
                    }
                });
                taskCompra.execute(this, Constants.BBDD_INSERT, daoCompras, compra);
                break;
            case R.id.activity_buy_searchMenuItem:
                if(fragmentId == 0){
                    changeFragment(R.id.activity_buy_fragmentContainer,fragmentSearch);
                    fragmentId = 1;
                }else{
                    adapter.setSearchPattern(null);
                    fragmentSearch.setTxtSearchToNull();
                    changeFragment(R.id.activity_buy_fragmentContainer,fragmentNormal);
                    fragmentId = 0;
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void associateControls(){
        adapter = new BuyAdapter(BuyActivity.this, compraUsuari);
        compraListView = (ListView) findViewById(R.id.activity_buy_listView);
        daoProductes = new DAOProductes(this);
        daoCompras = new DAOCompras(this);
        taskProduct = new DBTask_Base_SelectAll<DAOProductes, Producte>();
        taskProduct.setResultListener(new IResultList<Producte>() {
            @Override
            public void onSuccess(ArrayList<Producte> obj) {
                if (obj != null){
                    if(flagOnCreate == 0){
                        for(Producte p : obj){
                            compraUsuari.add(p);
                            flagOnCreate = 1;
                        }
                        adapter.setList(compraUsuari);
                        compraListView.setAdapter(adapter);
                    }

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


    public BuyAdapter getAdapter() {
        return adapter;
    }
}
