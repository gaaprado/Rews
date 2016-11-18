package prado.com.rews.view.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prado.com.rews.R;

/**
 * Created by Prado on 24/08/2016.
 */

public class FragmentDialog extends DialogFragment {

    public static FragmentDialog newInstance() {

        FragmentDialog frag = new FragmentDialog();
        frag.setStyle(STYLE_NO_TITLE, 0);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(getResources().getDimensionPixelSize(R.dimen.dialogWid),
                        getResources().getDimensionPixelSize(R.dimen.dialogHei));
    }
}
