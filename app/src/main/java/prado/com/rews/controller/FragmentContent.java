package prado.com.rews.controller;

import android.app.DialogFragment;
import android.content.Intent;
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
import android.widget.ImageView;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;

import java.util.ArrayList;

import prado.com.rews.R;
import prado.com.rews.adapter.RecyclerAdapter;
import prado.com.rews.helper.ListItemTouchHelper;
import prado.com.rews.helper.LoadSubmissions;
import prado.com.rews.interfaces.AsyncResponseResult;
import prado.com.rews.model.ImageDownloaded;

public class FragmentContent extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Listing <Submission> myDataset;
    private FragmentTransaction ft;
    private View view;
    private FloatingActionButton floatingActionButton;
    private ArrayList<Submission> array;

    public FragmentContent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view                                      = inflater.inflate(R.layout.content, container, false);
        recyclerView                              = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager                       = new LinearLayoutManager(getContext());
        floatingActionButton                      = (FloatingActionButton) view.findViewById(R.id.floatingButton);

        LoadSubmissions info                      = (LoadSubmissions) new LoadSubmissions(new AsyncResponseResult() {

            @Override
            public void processFinish(Listing <Submission> output, ArrayList<ImageDownloaded> images) {

                myDataset                         =  output;
                ft                                = getFragmentManager().beginTransaction();
                recyclerAdapter                   = new RecyclerAdapter(myDataset, ft, images);
                ItemTouchHelper.Callback callback = new ListItemTouchHelper(recyclerAdapter);
                ItemTouchHelper itemTouchHelper   = new ItemTouchHelper(callback);
                itemTouchHelper.attachToRecyclerView(recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(recyclerAdapter);

                array = new ArrayList<Submission>();
                for(int i =0; i<myDataset.size(); i++){
                    array.add(myDataset.get(i));
                }

            }
        },view, getContext()).execute();

        recyclerView.setLayoutManager(linearLayoutManager);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, new FragmentWeb("https://www.reddit.com/login"));
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(floatingActionButton != null){
                    //System.out.println("apareceu");
                } else {
                    //System.out.println("não apareceu");
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Bundle bundle = new Bundle();
        //bundle.putParcelableArray(array);
        //outState.pu
    }

}
