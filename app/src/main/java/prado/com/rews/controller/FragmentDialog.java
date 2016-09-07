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

    static FragmentDialog newInstance(){

        FragmentDialog frag = new FragmentDialog();
        frag.setStyle(STYLE_NO_TITLE, 0);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
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

