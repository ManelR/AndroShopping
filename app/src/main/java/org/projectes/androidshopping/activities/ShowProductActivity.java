package org.projectes.androidshopping.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOProductes;
import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_SelectId;

public class ShowProductActivity extends BaseActivity {

    private TextView nomProducte;
    private TextView descProducte;
    private TextView precioProducte;
    private TextView stockProducte;
    private ImageView imageProduct;

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

        if(this.producte != null){
            this.nomProducte.setText(this.producte.getNombre());
            this.descProducte.setText(this.producte.getDescripcion());
            this.precioProducte.setText(this.producte.getPrecio() + "€");
            this.stockProducte.setText(this.producte.getStock() > 1 ? this.producte.getStock() + " unidades" : this.producte.getStock() + " unidad");
            Picasso.with(this).load(this.producte.getImage()).resize(128, 128).into(this.imageProduct);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
