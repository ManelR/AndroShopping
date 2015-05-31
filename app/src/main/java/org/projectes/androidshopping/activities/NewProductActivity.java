package org.projectes.androidshopping.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import org.projectes.androidshopping.R;
import org.projectes.androidshopping.fragments.NewProductFragment1;
import org.projectes.androidshopping.fragments.NewProductFragment2;
import org.projectes.androidshopping.fragments.NewProductFragment3;

public class NewProductActivity extends BaseActivity {
    private Button btnAnterior;
    private Button btnSeguent;
    private NewProductFragment1 fragment1;
    private NewProductFragment2 fragment2;
    private NewProductFragment3 fragment3;
    private int paso;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        this.fragment1 = new NewProductFragment1();
        this.fragment2 = new NewProductFragment2();
        this.fragment3 = new NewProductFragment3();
        changeFragment(R.id.activity_newProduct_fragment, fragment1);
        paso = 1;
        associateControls();
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
                        changeFragment(R.id.activity_newProduct_fragment, fragment1);
                        btnAnterior.setEnabled(false);
                        btnSeguent.setEnabled(true);
                        break;
                    case 2:
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
                switch (paso){
                    case 2:
                        changeFragment(R.id.activity_newProduct_fragment, fragment2);
                        btnSeguent.setEnabled(true);
                        btnAnterior.setEnabled(true);
                        break;
                    case 3:
                        changeFragment(R.id.activity_newProduct_fragment, fragment3);
                        btnSeguent.setEnabled(true);
                        btnAnterior.setEnabled(true);
                        break;
                    case 4:
                        //TODO validar els camps de tot el registre
                        break;
                }
            }
        });
        this.btnAnterior.setEnabled(false);
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
}
