package prado.com.rews.helper;

import net.dean.jraw.models.Contribution;
import net.dean.jraw.models.Listing;

import prado.com.rews.model.ImageDownloaded;

/**
 * Created by Gabriel on 19/11/2016.
 */
public interface LoadSubmissionsResult {

    void onSuccess(ImageDownloaded imageDownloaded);

    void onSuccess(Listing<Contribution> listing);
}
