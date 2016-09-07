package prado.com.rews.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;

import prado.com.rews.R;
import prado.com.rews.adapter.RecyclerAdapter;
import prado.com.rews.helper.ListItemTouchHelper;
import prado.com.rews.helper.LoadSubmissions;
import prado.com.rews.interfaces.AsyncResponseResult;

public class FragmentContent extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Listing <Submission> myDataset;
    private FragmentTransaction ft;

    public FragmentContent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                final View view                         = inflater.inflate(R.layout.content, container, false);
                recyclerView                      = (RecyclerView) view.findViewById(R.id.recyclerView);
                linearLayoutManager               = new LinearLayoutManager(getContext());
                LoadSubmissions info              = (LoadSubmissions) new LoadSubmissions(new AsyncResponseResult() {

            @Override
            public void processFinish(Listing <Submission> output) {

                myDataset                         =  output;
                ft                                = getFragmentManager().beginTransaction();
                recyclerAdapter                   = new RecyclerAdapter(myDataset, ft);
                ItemTouchHelper.Callback callback = new ListItemTouchHelper(recyclerAdapter);
                ItemTouchHelper itemTouchHelper   = new ItemTouchHelper(callback);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                itemTouchHelper.attachToRecyclerView(recyclerView);
                recyclerView.setAdapter(recyclerAdapter);

            }
        },view).execute();


        return view;
    }

}
