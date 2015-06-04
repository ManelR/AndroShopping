package org.projectes.androidshopping.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.projectes.androidshopping.R;
import org.projectes.androidshopping.activities.BuyActivity;
import org.projectes.androidshopping.adapters.BuyAdapter;

/**
 * Created by alloveras on 2/06/15.
 */
public class BuyActivitySearchFragment extends Fragment{

    private View fragmentView = null;
    private EditText txtSearch = null;
    private BuyAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_buy_search_layout, container, false);
        this.txtSearch = (EditText)fragmentView.findViewById(R.id.fragment_buy_txtSearch);
        this.adapter = ((BuyActivity)getActivity()).getAdapter();
        this.txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null){
                    if (s.toString().equals("")){
                        adapter.setSearchPattern(null);
                    }else{
                        adapter.setSearchPattern(s.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        this.txtSearch.requestFocus();
        return fragmentView;
    }

    public void setTxtSearchToNull(){
        txtSearch.setText("");
    }
}
