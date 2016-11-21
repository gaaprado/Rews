package prado.com.rews.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Contribution;
import net.dean.jraw.models.Listing;

import prado.com.rews.R;
import prado.com.rews.adapter.HistoryAdapter;
import prado.com.rews.helper.FragmentListener;
import prado.com.rews.helper.LoadSubmissionsResult;
import prado.com.rews.helper.RedditAccount;
import prado.com.rews.helper.RedditAccountResult;
import prado.com.rews.helper.SendSubmission;
import prado.com.rews.model.ImageDownloaded;
import prado.com.rews.view.MainActivity;

/**
 * Created by Gabriel on 19/11/2016.
 */

public class FragmentAccount extends Fragment implements FragmentListener {

    private View view;

    public FragmentAccount() {}

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView createAccount = (TextView) view.findViewById(R.id.text_view_account);
        createAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.reddit.com/login"));
                startActivity(browserIntent);
            }
        });

        Button loginButton = (Button) view.findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                EditText input_username = (EditText) view.findViewById(R.id.input_username);
                EditText input_password = (EditText) view.findViewById(R.id.input_password);

                String username = input_username.getText().toString();
                String password = input_password.getText().toString();

                doLogin(username, password);
            }
        });

        onResumeFragment();

        return view;
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
        try {
            if (((MainActivity) getActivity()).getRedditClient() == null) {
                view.findViewById(R.id.linear_layout_login).setVisibility(View.VISIBLE);
                view.findViewById(R.id.relative_layout_recycler).setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.linear_layout_login).setVisibility(View.GONE);
                view.findViewById(R.id.relative_layout_recycler).setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doLogin(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Authenticating...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            RedditAccount redditAccount = new RedditAccount(new RedditAccountResult() {

                @Override
                public void onSuccess(final RedditClient redditClient) {
                    progressDialog.hide();
                    view.findViewById(R.id.linear_layout_login).setVisibility(View.GONE);
                    view.findViewById(R.id.relative_layout_recycler).setVisibility(View.VISIBLE);
                    ((MainActivity) getActivity()).setRedditClient(redditClient);
                    FloatingActionButton loggoutButton =
                            (FloatingActionButton) getActivity().findViewById(R.id.floating_button_loggout);
                    loggoutButton.setOnClickListener(new onLoggoutListener());
                    loggoutButton.setVisibility(View.VISIBLE);

                    final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_history);
                    recyclerView.setHasFixedSize(true);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);

                    try {
                        if (redditClient != null) {
                            SendSubmission sendSubmission =
                                    new SendSubmission(redditClient, new LoadSubmissionsResult() {

                                        @Override
                                        public void onSuccess(final ImageDownloaded imageDownloaded) {
                                        }

                                        @Override
                                        public void onSuccess(final Listing<Contribution> listing) {
                                            recyclerView.setAdapter(new HistoryAdapter(listing, getActivity()));
                                        }
                                    }, "saved");
                            sendSubmission.execute();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed() {
                    progressDialog.setMessage("Username/Password invalid.");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            progressDialog.hide();
                        }
                    }, 3000);
                }
            }, username, password);
            redditAccount.execute();
        } else {
            Toast.makeText(getActivity(), "Insert your username and password.", Toast.LENGTH_SHORT).show();
        }
    }

    private class onLoggoutListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            ((MainActivity) getActivity()).setRedditClient(null);
            view.findViewById(R.id.linear_layout_login).setVisibility(View.VISIBLE);
            view.findViewById(R.id.relative_layout_recycler).setVisibility(View.GONE);
            getActivity().findViewById(R.id.floating_button_loggout).setVisibility(View.GONE);
        }
    }
}