package org.projectes.androidshopping.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOProductes;
import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_Modify;
import org.projectes.androidshopping.Task.DBTask_Base_SelectId;

public class ShowProductActivity extends BaseActivity {

    private TextView nomProducte;
    private TextView descProducte;
    private TextView precioProducte;
    private TextView stockProducte;
    private ImageView imageProduct;
    private LinearLayout listTags;
    private String[] tags;
    private Producte producte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id;
        setContentView(R.layout.activity_show_product);
        id = getIntent().getIntExtra("id_product", -1);
        if(id > 0){
            recuperarProducte(id);
        }
    }

    private void recuperarProducte(int id) {
        DAOProductes BBDDProducte = new DAOProductes(this);
        DBTask_Base_SelectId<DAOProductes, Producte> TaskDB = new DBTask_Base_SelectId<DAOProductes, Producte>();
        this.producte = new Producte();
        this.producte.setId(id);
        TaskDB.setResultListener(new IResult<Producte>() {
            @Override
            public void onSuccess(Producte IRresult) {
                if(IRresult!=null){
                    producte = IRresult;
                    associateControls();
                }
            }

            @Override
            public void onFail(String missatgeError) {
                Toast.makeText(ShowProductActivity.this, missatgeError, Toast.LENGTH_LONG).show();
            }
        });
        TaskDB.execute(this, Constants.BBDD_SELECT_ID, BBDDProducte, producte);
    }

    private void associateControls() {
        this.nomProducte = (TextView)findViewById(R.id.activity_showProduct_lblNameProduct);
        this.descProducte = (TextView)findViewById(R.id.activity_showProduct_lblDescProduct);
        this.precioProducte = (TextView)findViewById(R.id.activity_showProduct_lblPrecioResult);
        this.stockProducte = (TextView)findViewById(R.id.activity_showProduct_lblStockResult);
        this.imageProduct = (ImageView)findViewById(R.id.activity_showProduct_imgImage);
        this.listTags = (LinearLayout)findViewById(R.id.activity_showProduct_listTag);


        if(this.producte != null){
            this.nomProducte.setText(this.producte.getNombre());
            this.descProducte.setText(this.producte.getDescripcion());
            this.precioProducte.setText(this.producte.getPrecio() + "€");
            this.stockProducte.setText(this.producte.getStock() > 1 ? this.producte.getStock() + " unidades" : this.producte.getStock() + " unidad");
            Picasso.with(this).load(this.producte.getImage()).resize(128, 128).into(this.imageProduct);
            this.tags = new String[this.producte.getDB_tags().size()];
            for (int i = 0; i < this.producte.getDB_tags().size(); i++){
                this.tags[i] = new String((i+1) + ". " + this.producte.getDB_tags().get(i).getNom());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, this.tags);
            for (int i = 0; i< adapter.getCount(); i++){
                this.listTags.addView(adapter.getView(i, null, null));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.activity_showProduct_menu_delete) {
            AlertDialog.Builder confirmation = new AlertDialog.Builder(this,R.style.Theme_AppCompat_Light_Dialog_Alert);
            confirmation.setTitle(this.getString(R.string.item_manage_products_lblTitleConfirmation));
            confirmation.setIcon(R.mipmap.delete);
            confirmation.setMessage(this.getString(R.string.item_manage_products_lblBodyConfirmation) + " " + this.producte.getNombre() + " ?");
            confirmation.setPositiveButton(R.string.item_manage_users_lblYesConfirmation, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DBTask_Base_Modify<DAOProductes, Producte> taskModify;
                    DAOProductes daoProductes = new DAOProductes(ShowProductActivity.this);
                    taskModify = new DBTask_Base_Modify<DAOProductes, Producte>();
                    taskModify.execute(ShowProductActivity.this, Constants.BBDD_DELETE, daoProductes, producte);
                    ShowProductActivity.this.finish();
                }
            });
            confirmation.setNegativeButton(R.string.item_manage_products_lblNoConfirmation, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //No fem res
                }
            });

            confirmation.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
