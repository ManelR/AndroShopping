package org.projectes.androidshopping.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.projectes.androidshopping.R;
import org.projectes.androidshopping.activities.NewProductActivity;
import org.projectes.androidshopping.adapters.InsertTagsAdapter;
import org.w3c.dom.Text;

import java.util.List;

public class NewProductFragment3 extends Fragment {
    private View fragmentView = null;

    private LinearLayout listViewTags = null;
    private InsertTagsAdapter adapter = null;
    private List<String> lTags = null;
    private Button btnAddTag = null;
    private EditText txtNewTag = null;

    public void setTagList(List<String> lTags){
        this.lTags = lTags;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_new_product_fragment3, container, false);

        btnAddTag = (Button) fragmentView.findViewById(R.id.fragment_newProduct3_btnAddTag);
        txtNewTag = (EditText) fragmentView.findViewById(R.id.fragment_newProduct3_txtNewTag);
        if(btnAddTag != null){
            btnAddTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(txtNewTag != null){
                        if(txtNewTag.getText().length() > 0){
                            if(!lTags.contains(txtNewTag.getText().toString().toLowerCase())){
                                lTags.add(txtNewTag.getText().toString().toLowerCase());
                                txtNewTag.setText("");
                                adapter.updateInformation();
                            }else{
                                txtNewTag.setText("");
                                Toast.makeText(getActivity(),getString(R.string.fragment_newProduct3_repeatedTagError),Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getActivity(),getString(R.string.fragment_newProduct3_emptyTagError),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }

        listViewTags = (LinearLayout) fragmentView.findViewById(R.id.fragment_newProduct3_listView);
        if(listViewTags != null && lTags != null){
            listViewTags.setScrollContainer(false);
            adapter = new InsertTagsAdapter(lTags,listViewTags,getActivity());
            listViewTags.removeAllViews();
            for(int i = 0 ; i < adapter.getCount() ; i++){
                listViewTags.addView(adapter.getView(i,null,null));
            }
        }

        return this.fragmentView;
    }

    public List<String> getTags(){
        return this.lTags;
    }

}
