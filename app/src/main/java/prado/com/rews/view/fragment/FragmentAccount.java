package prado.com.rews.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.dean.jraw.RedditClient;

import prado.com.rews.R;
import prado.com.rews.helper.FragmentListener;
import prado.com.rews.helper.RedditAccount;
import prado.com.rews.helper.RedditAccountResult;
import prado.com.rews.view.MainActivity;

/**
 * Created by Gabriel on 19/11/2016.
 */

public class FragmentAccount extends Fragment implements FragmentListener {

    private RedditClient redditClient;
    private SharedPreferences sharedPreferences;
    private View view;

    public FragmentAccount() {}

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jsonClient = sharedPreferences.getString("redditClient", "invalid");

        if (!jsonClient.equals("invalid")) {
            System.out.println("pegou salvo");
            redditClient = new Gson().fromJson(jsonClient, RedditClient.class);
        }

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

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonClient = new Gson().toJson(redditClient);
        editor.putString("redditClient", jsonClient);
        editor.apply();
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
        if (redditClient == null) {
            view.findViewById(R.id.linear_layout_login).setVisibility(View.VISIBLE);
            view.findViewById(R.id.relative_layout_recycler).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.linear_layout_login).setVisibility(View.GONE);
            view.findViewById(R.id.relative_layout_recycler).setVisibility(View.VISIBLE);
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