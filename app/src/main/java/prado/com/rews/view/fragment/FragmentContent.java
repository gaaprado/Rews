package prado.com.rews.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import prado.com.rews.R;
import prado.com.rews.adapter.HistoryAdapter;
import prado.com.rews.adapter.RecyclerAdapter;
import prado.com.rews.helper.EndlessRecyclerViewScrollListener;
import prado.com.rews.model.Noticia;
import prado.com.rews.rest.ApiClient;
import prado.com.rews.rest.ApiInterface;
import prado.com.rews.view.ArticleActivity;
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
        linearLayoutManager = new LinearLayoutManager(getContext());
        if (type.equals("News")) {
            view = inflater.inflate(R.layout.fragment_content, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            array = new ArrayList<>();
            LoadNewSubmissions();
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
            recyclerView.setLayoutManager(linearLayoutManager);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    LoadNewSubmissions();
                }
            });
        } else {
            view = inflater.inflate(R.layout.fragment_profile, container, false);

            boolean isLogged = true;

            if (isLogged) {
                List<Noticia> noticiaList = new ArrayList<>();
                Noticia noticia = new Noticia() {{
                    setTitle("Titulo da noticia 1");
                    setFavorited(true);
                    setUrl("www.tibia.com");
                }};
                Noticia noticia2 = new Noticia() {{
                    setTitle("Titulo da noticia 2");
                    setFavorited(true);
                    setUrl("www.xvideos.com");
                }};
                Noticia noticia3 = new Noticia() {{
                    setTitle("Titulo da noticia 3");
                    setFavorited(true);
                    setUrl("www.google.com");
                }};
                Noticia noticia4 = new Noticia() {{
                    setTitle("Titulo da noticia 4");
                    setFavorited(true);
                }};
                Noticia noticia5 = new Noticia() {{
                    setTitle("Titulo da noticia 5");
                    setFavorited(true);
                }};
                Noticia noticia6 = new Noticia() {{
                    setTitle("Titulo da noticia 6");
                    setFavorited(true);
                }};
                Noticia noticia7 = new Noticia() {{
                    setTitle("Titulo da noticia 7");
                    setFavorited(true);
                }};

                noticiaList.add(noticia);
                noticiaList.add(noticia2);
                noticiaList.add(noticia3);
                noticiaList.add(noticia4);
                noticiaList.add(noticia5);
                noticiaList.add(noticia6);
                noticiaList.add(noticia7);

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_history);
                recyclerView.setHasFixedSize(true);
                HistoryAdapter historyAdapter = new HistoryAdapter(noticiaList, getContext());
                recyclerView.setAdapter(historyAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            } else {
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
                relativeLayout.setVisibility(View.VISIBLE);
                Button logIn = (Button) view.findViewById(R.id.button_login);
                logIn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        Intent intent = new Intent(getActivity(), ArticleActivity.class);
                        startActivityForResult(intent, 2);
                    }
                });
            }
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

    private class ClickListener implements View.OnClickListener {

        private String type;

        ClickListener(String type) {
            this.type = type;
        }

        @Override
        public void onClick(final View view) {
            // FragmentTransaction ft = getFragmentManager().beginTransaction();
            //   ft.replace(R.id.frame_layout_buttons, new FragmentHistory(type));
            //     ft.addToBackStack(null);
            //       ft.commit();
        }
    }
}
