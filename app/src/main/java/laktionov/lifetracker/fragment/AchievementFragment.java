package laktionov.lifetracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import laktionov.lifetracker.R;
import laktionov.lifetracker.adapter.AchievementAdapter;
import laktionov.lifetracker.interfaces.OnItemAddedListener;
import laktionov.lifetracker.interfaces.OnItemDeletedListener;
import laktionov.lifetracker.loader.AsyncEntryLoader;
import laktionov.lifetracker.model.Item;
import laktionov.lifetracker.utils.GlobalVariables;

public class AchievementFragment extends Fragment implements OnItemDeletedListener, OnItemAddedListener {

    private AchievementAdapter adapter;
    private List<Item> listItem;
    private Unbinder unbinder;

    @BindView(R.id.rv_achievement_list)
    RecyclerView rv_achievement_list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_achievement, container, false);
        unbinder = ButterKnife.bind(this, root);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_achievement_list.hasFixedSize();
        rv_achievement_list.setLayoutManager(layoutManager);

        listItem = new ArrayList<>();
        adapter = new AchievementAdapter(listItem, getActivity());
        adapter.setOnItemDeletedListener(this);
        rv_achievement_list.setAdapter(adapter);

        new AsyncEntryLoader(getActivity(), listItem, adapter, GlobalVariables.FROM_ACHIEVEMENT_FRAGMENT).execute();

        return root;
    }

    @Override
    public void onItemDeleted() {
        new AsyncEntryLoader(getActivity(), listItem, adapter, GlobalVariables.FROM_ACHIEVEMENT_FRAGMENT).execute();
    }

    @Override
    public void onItemAdded() {
        new AsyncEntryLoader(getActivity(), listItem, adapter, GlobalVariables.FROM_ACHIEVEMENT_FRAGMENT).execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}