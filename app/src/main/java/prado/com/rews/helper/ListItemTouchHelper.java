package prado.com.rews.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import prado.com.rews.adapter.RecyclerAdapter;

/**
 * Created by Prado on 07/09/2016.
 */

public class ListItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerAdapter recyclerAdapter;

    public ListItemTouchHelper(RecyclerAdapter recyclerAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.recyclerAdapter = recyclerAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        recyclerAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        recyclerAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}
