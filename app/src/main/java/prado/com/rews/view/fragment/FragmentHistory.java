package prado.com.rews.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prado.com.rews.R;

/**
 * Created by Gabriel on 15/11/2016.
 */

public class FragmentHistory extends Fragment {

    private String type;

    public FragmentHistory(String type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);



        return view;
    }
}
