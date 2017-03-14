package laktionov.lifetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import laktionov.lifetracker.R;
import laktionov.lifetracker.controller.ItemActionController;
import laktionov.lifetracker.interfaces.OnItemAddedListener;
import laktionov.lifetracker.interfaces.OnItemDeletedListener;
import laktionov.lifetracker.model.Item;


public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementHolder> {

    private List<Item> items;
    private ItemActionController itemActionController;
    private OnItemDeletedListener onItemDeletedListener;

    public AchievementAdapter(List<Item> items, Context context) {
        this.items = items;
        itemActionController = ItemActionController.getInstance(context);
    }

    public void setOnItemDeletedListener(OnItemDeletedListener onItemDeletedListener) {
        this.onItemDeletedListener = onItemDeletedListener;
    }

    @Override
    public AchievementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_achievements, parent, false);
        return new AchievementHolder(v);
    }

    @Override
    public void onBindViewHolder(AchievementHolder holder, final int position) {
        View v = holder.card;
        TextView tv_achievement = (TextView) v.findViewById(R.id.tv_achievement);
        ImageButton ib_delete = (ImageButton) v.findViewById(R.id.ib_delete);
        tv_achievement.setText(items.get(position).getEntry());
        ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemActionController.deleteItemFromDB(items, position, ItemActionController.FROM_ACHIEVEMENT);
                onItemDeletedListener.onItemDeleted();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class AchievementHolder extends RecyclerView.ViewHolder {

        private View card;

        AchievementHolder(View v) {
            super(v);
            card = v;
        }
    }
}
