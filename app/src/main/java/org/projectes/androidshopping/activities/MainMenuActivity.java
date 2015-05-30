package org.projectes.androidshopping.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import org.projectes.androidshopping.R;
import org.projectes.androidshopping.adapters.MainMenuAdapter;

public class MainMenuActivity extends BaseActivity {

    private GridView gridView = null;
    private MainMenuAdapter adapter = null;
    private boolean isAdmin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_layout);
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

        switch (id){

            //User Role Menu Items
            case R.id.menu_main_menu_itemPerfil:
                break;
            case R.id.menu_main_menu_itemBuy:
                break;
            case R.id.menu_main_menu_itemPurchaseHistory:
                break;
            case R.id.menu_main_menu_itemExit:
                break;


            //Admin Role Menu Items
            case R.id.menu_main_menu_itemManageProducts:
                break;
            case R.id.menu_main_menu_itemManageUsers:
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
