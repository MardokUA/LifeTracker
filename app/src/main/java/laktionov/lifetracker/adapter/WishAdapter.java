package laktionov.lifetracker.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laktionov.lifetracker.R;
import laktionov.lifetracker.controller.ItemActionController;
import laktionov.lifetracker.data.DBOpenHelper;
import laktionov.lifetracker.interfaces.OnItemDeletedListener;
import laktionov.lifetracker.model.Item;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.WishHolder> {

    private List<Item> items;
    private OnItemDeletedListener onItemDeletedListener;
    private ItemActionController itemActionController;

    public void setOnItemDeletedListener(OnItemDeletedListener onItemDeletedListener) {
        this.onItemDeletedListener = onItemDeletedListener;
    }

    public WishAdapter(List<Item> items, Context context) {
        this.items = items;
        itemActionController = ItemActionController.getInstance(context);
    }

    @Override
    public WishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_wishes, parent, false);
        return new WishHolder(v);
    }

    @Override
    public void onBindViewHolder(final WishHolder holder, final int position) {
        View v = holder.card;

        TextView tv_wish = (TextView) v.findViewById(R.id.tv_wish);
        tv_wish.setText(items.get(position).getEntry());
        ImageButton ib_delete = (ImageButton) v.findViewById(R.id.ib_delete);
        ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemActionController.deleteItemFromDB(items, position, ItemActionController.FROM_WISH);
                onItemDeletedListener.onItemDeleted();
            }
        });
        CardView cardView = (CardView) v.findViewById(R.id.card_wishes);
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemActionController.itemSelect(v, items, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class WishHolder extends RecyclerView.ViewHolder {
        private View card;

        private WishHolder(View v) {
            super(v);
            card = v;
        }
    }
}
