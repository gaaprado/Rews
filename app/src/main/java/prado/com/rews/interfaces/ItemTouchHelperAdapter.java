package prado.com.rews.interfaces;

/**
 * Created by Prado on 07/09/2016.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}