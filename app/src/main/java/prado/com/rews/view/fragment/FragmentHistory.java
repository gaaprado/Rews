package prado.com.rews.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_history);

        //RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_history);
        //recyclerView.setHasFixedSize(true);

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        //recyclerView.setLayoutManager(layoutManager);

        //RelativeLayout downVoteLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_downvotes);
        //RelativeLayout favoriteLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_favorites);

        /*
        switch (type) {
            case "upvote":
                upVoteLayout.setVisibility(View.VISIBLE);
                downVoteLayout.setVisibility(View.GONE);
                favoriteLayout.setVisibility(View.GONE);
                break;
            case "downvote":
                upVoteLayout.setVisibility(View.GONE);
                downVoteLayout.setVisibility(View.VISIBLE);
                favoriteLayout.setVisibility(View.GONE);
                break;
            case "favorite":
                upVoteLayout.setVisibility(View.GONE);
                downVoteLayout.setVisibility(View.GONE);
                favoriteLayout.setVisibility(View.VISIBLE);
                break;
        }*/

        return view;
    }
}
