package prado.com.rews.rest;

import java.util.List;

import prado.com.rews.model.Noticia;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Gabriel on 20/10/2016.
 */

public interface ApiInterface {

       @GET("/getWorldnews")
       Call <List<Noticia>> getNoticias();
}
