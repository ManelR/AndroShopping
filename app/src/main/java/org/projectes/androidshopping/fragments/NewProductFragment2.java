package org.projectes.androidshopping.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.projectes.androidshopping.R;


public class NewProductFragment2 extends Fragment {
    private View fragmentView;
    private Button btnImage;
    private ImageView imgImage;
    private EditText txtPreu;
    private EditText txtStock;
    private CheckBox chckActive;
    private static String picturePath;

    public NewProductFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_new_product_fragment2, container, false);
        this.btnImage = (Button)fragmentView.findViewById(R.id.fragment_newProduct2_btnSelectFoto);
        this.btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 0);
            }
        });
        this.imgImage = (ImageView)fragmentView.findViewById(R.id.fragment_newProduct2_imgImage);
        if (this.picturePath != null){
            Picasso.with(fragmentView.getContext()).load(this.picturePath).resize(128, 128).into(this.imgImage);
        }
        this.txtPreu = (EditText)fragmentView.findViewById(R.id.fragment_newProduct2_txtPrecio);
        this.txtStock = (EditText)fragmentView.findViewById(R.id.fragment_newProduct2_txtStock);
        this.chckActive = (CheckBox)fragmentView.findViewById(R.id.fragment_newProduct2_chckActivo);
        return this.fragmentView;
    }

    public void setImage(String picturePath) {
        this.picturePath = "file://" + picturePath;
        //this.imgImage.setImageBitmap(BitmapFactory.decodeFile(this.picturePath));
        Picasso.with(fragmentView.getContext()).load(this.picturePath).resize(128, 128).into(this.imgImage);
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public String getPreu(){
        return this.txtPreu.getText().toString();
    }

    public String getStock(){
        return this.txtStock.getText().toString();
    }

    public Boolean isActive(){
        return this.chckActive.isChecked();
    }
}
