package prado.com.rews.controller;

/**
 * Created by Prado on 07/09/2016.
 */

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prado.com.rews.R;


/**
 * Created by Prado on 24/08/2016.
 */

public class FragmentDialog extends DialogFragment{

    private static String type;

    public FragmentDialog(String type){
        this.type = type;
    }

    static FragmentDialog newInstance(String type){

        FragmentDialog frag = new FragmentDialog(type);
        frag.setStyle(STYLE_NO_TITLE, 0);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        if(type.equals("sort")){
            view = inflater.inflate(R.layout.fragment_dialog, container, false);
        } else if(type.equals("share")) {
            view = inflater.inflate(R.layout.dialog_share, container, false);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        int width = getResources().getDimensionPixelSize(R.dimen.dialogWid);
        int height = getResources().getDimensionPixelSize(R.dimen.dialogHei);
        this.getDialog().getWindow().setLayout(width, height);
    }

}

