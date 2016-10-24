package prado.com.rews.rest;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Gabriel on 23/10/2016.
 */

public abstract class ApiClient {

    public static Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://35.160.130.147:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
