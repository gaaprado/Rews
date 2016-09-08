package prado.com.rews.interfaces;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;

import java.util.ArrayList;

import prado.com.rews.model.ImageDownloaded;

/**
 * Created by Prado on 14/08/2016.
 */

public interface AsyncResponseResult {
    void processFinish(Listing <Submission> output, /*List<String> urls*/ ArrayList<ImageDownloaded> images);

}
