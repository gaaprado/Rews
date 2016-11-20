package prado.com.rews.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import net.dean.jraw.RedditClient;

import prado.com.rews.R;
import prado.com.rews.helper.RedditAccount;
import prado.com.rews.helper.RedditAccountResult;
import prado.com.rews.helper.TransferData;

/**
 * Created by Gabriel on 19/11/2016.
 */

public class FragmentAccount extends Fragment implements TransferData {

    private TransferData callback;
    private RedditClient redditClient;
    private SharedPreferences sharedPreferences;

    public FragmentAccount() {}

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

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

        if (redditClient == null) {
            Button loginButton = (Button) view.findViewById(R.id.button_login);
            loginButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    EditText input_username = (EditText) view.findViewById(R.id.input_username);
                    EditText input_password = (EditText) view.findViewById(R.id.input_password);

                    String username = input_username.getText().toString();
                    String password = input_password.getText().toString();

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
                                setRedditClient(redditClient);
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
                    }
                }
            });
        } else {
            view.findViewById(R.id.linear_layout_login).setVisibility(View.GONE);
            view.findViewById(R.id.relative_layout_recycler).setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            callback = (TransferData) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public RedditClient getRedditClient() {
        return redditClient;
    }

    @Override
    public void setRedditClient(final RedditClient redditClient) {
        this.redditClient = redditClient;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (redditClient != null) {
                Log.d("RedditClient", "Meu nome é: " + redditClient.me());
            } else {
                System.out.println("Não funciona");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonClient = new Gson().toJson(redditClient);
        editor.putString("redditClient", jsonClient);
        editor.apply();
    }
}