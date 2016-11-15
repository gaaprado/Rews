package prado.com.rews.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import prado.com.rews.R;
import prado.com.rews.adapter.RecyclerAdapter;
import prado.com.rews.helper.EndlessRecyclerViewScrollListener;
import prado.com.rews.model.Noticia;
import prado.com.rews.rest.ApiClient;
import prado.com.rews.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentContent extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerViewScrollListener recyclerViewScrollListener;
    private List<Noticia> array;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String type;
    private View view;

    public FragmentContent(String type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (type.equals("News")) {
            view = inflater.inflate(R.layout.fragment_content, container, false);

            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            array = new ArrayList<>();
            LoadNewSubmissions();
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    LoadNewSubmissions();
                }
            });
        } else {
            view = inflater.inflate(R.layout.fragment_profile, container, false);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void LoadNewSubmissions() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        Call<List<Noticia>> call = apiInterface.getNoticias();
        call.enqueue(new Callback<List<Noticia>>() {

            @Override
            public void onResponse(final Call<List<Noticia>> call, final Response<List<Noticia>> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (!response.body().isEmpty()) {
                    array.add(response.body().get(0));
                    array.add(response.body().get(1));
                    progressBar.setVisibility(View.GONE);
                }

                final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(array, getActivity());
                recyclerView.setAdapter(recyclerAdapter);

                recyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {

                    @Override
                    public void onLoadMore(final int page, final int totalItemsCount, final RecyclerView view) {
                        if (page + 1 < response.body().size()) {
                            array.add(response.body().get(page + 1));
                            recyclerAdapter.notifyDataSetChanged();
                        }
                    }
                };
                recyclerView.addOnScrollListener(recyclerViewScrollListener);
            }

            @Override
            public void onFailure(final Call<List<Noticia>> call, final Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
