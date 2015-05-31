package org.projectes.androidshopping.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.adapters.InsertTagsAdapter;
import org.projectes.androidshopping.fragments.NewProductFragment1;
import org.projectes.androidshopping.fragments.NewProductFragment2;
import org.projectes.androidshopping.fragments.NewProductFragment3;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewProductActivity extends BaseActivity {
    private Button btnAnterior;
    private Button btnSeguent;
    private static LinkedList<String> lTags = new LinkedList<String>();
    private static NewProductFragment1 fragment1 = new NewProductFragment1();
    private static NewProductFragment2 fragment2 = new NewProductFragment2();
    private static NewProductFragment3 fragment3 = new NewProductFragment3();

    private static int paso = 1;
    private String PREU_PATTERN = "^[0-9]*$";
    private String STOCK_PATTERN = "^[0-9]*$";

    private String missatgeError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        associateControls();
        switch (paso){
            case 1:
                aBar.setSubtitle("Paso 1/3");
                changeFragment(R.id.activity_newProduct_fragment, this.fragment1);
                btnAnterior.setEnabled(false);
                btnSeguent.setEnabled(true);
                break;
            case 2:
                aBar.setSubtitle("Paso 2/3");
                changeFragment(R.id.activity_newProduct_fragment, this.fragment2);
                btnSeguent.setEnabled(true);
                btnAnterior.setEnabled(true);
                break;
            case 3:
                aBar.setSubtitle("Paso 3/3");
                changeFragment(R.id.activity_newProduct_fragment, this.fragment3);
                btnSeguent.setEnabled(true);
                btnAnterior.setEnabled(true);
                fragment3.setTagList(lTags);
                break;
        }
    }

    private void associateControls() {
        this.btnAnterior = (Button)findViewById(R.id.activity_newProduct_btnAnterior);
        this.btnSeguent = (Button)findViewById(R.id.activity_newProduct_btnSeguent);

        this.btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paso--;
                switch (paso){
                    case 1:
                        aBar.setSubtitle("Paso 1/3");
                        changeFragment(R.id.activity_newProduct_fragment, fragment1);
                        btnAnterior.setEnabled(false);
                        btnSeguent.setEnabled(true);
                        break;
                    case 2:
                        aBar.setSubtitle("Paso 2/3");
                        changeFragment(R.id.activity_newProduct_fragment, fragment2);
                        btnSeguent.setEnabled(true);
                        btnAnterior.setEnabled(true);
                        break;
                }
            }
        });
        this.btnSeguent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paso++;
                switch (paso) {
                    case 2:
                        aBar.setSubtitle("Paso 2/3");
                        changeFragment(R.id.activity_newProduct_fragment, fragment2);
                        btnSeguent.setEnabled(true);
                        btnAnterior.setEnabled(true);
                        break;
                    case 3:
                        aBar.setSubtitle("Paso 3/3");
                        fragment3.setTagList(lTags);
                        changeFragment(R.id.activity_newProduct_fragment, fragment3);
                        btnSeguent.setEnabled(true);
                        btnAnterior.setEnabled(true);
                        break;
                    case 4:
                        //TODO validar els camps de tot el registre
                        if(validarCampsProducte()){
                            Producte newProduct = new Producte();
                            newProduct.setDeleted(0);
                            newProduct.setId_remot(-1);
                            newProduct.setNombre(fragment1.getTxtName());
                            newProduct.setDescripcion(fragment1.getTxtDesc());
                            newProduct.setImage(fragment2.getPicturePath());
                            newProduct.setPrecio(Integer.valueOf(fragment2.getPreu()));
                            newProduct.setStock(Integer.valueOf(fragment2.getStock()));
                        }else{
                            Toast.makeText(NewProductActivity.this, missatgeError, Toast.LENGTH_LONG).show();
                            paso--;
                        }
                        break;
                }
            }
        });
    }

    private boolean validarCampsProducte(){
        boolean result = true;
        Pattern preuPattern;
        Matcher preuMatcher;
        Pattern stockPattern;
        Matcher stockMatcher;

        if (result && fragment1.getTxtName().equals("")){
            result = false;
            this.missatgeError = getString(R.string.error_product_name_empty);
        }
        if (result && fragment1.getTxtDesc().equals("")){
            result = false;
            this.missatgeError = getString(R.string.error_product_desc_empty);
        }
        if (result && fragment2.getPicturePath() == null){
            result = false;
            this.missatgeError = getString(R.string.error_product_img_empty);
        }
        if (result && fragment2.getPicturePath() != null){
            if (fragment2.getPicturePath().equals("")){
                result = false;
                this.missatgeError = getString(R.string.error_product_img_empty);
            }
        }
        if (result && fragment2.getPreu().equals("")){
            result = false;
            this.missatgeError = getString(R.string.error_product_preu_empty);
        }
        if (result && !fragment2.getPreu().equals("")){
            //Comprovar rex preu
            preuPattern = Pattern.compile(PREU_PATTERN);
            preuMatcher = preuPattern.matcher(fragment2.getPreu());
            if (!preuMatcher.matches()){
                result = false;
                this.missatgeError = getString(R.string.error_product_preu_incorrect);
            }
        }
        if (result && fragment2.getStock().equals("")){
            result = false;
            this.missatgeError = getString(R.string.error_product_stock_empty);
        }
        if (result && !fragment2.getPreu().equals("")){
            //Comprovar rex preu
            stockPattern = Pattern.compile(STOCK_PATTERN);
            stockMatcher = stockPattern.matcher(fragment2.getStock());
            if (!stockMatcher.matches()){
                result = false;
                this.missatgeError = getString(R.string.error_product_stock_incorrect);
            }
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_product, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("IMAGE:", "Ha entrat a la resposta");
        if (data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            fragment2.setImage(picturePath);

        }


    }
}
