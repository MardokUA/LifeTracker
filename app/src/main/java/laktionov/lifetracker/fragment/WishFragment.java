package laktionov.lifetracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import laktionov.lifetracker.R;
import laktionov.lifetracker.adapter.WishAdapter;
import laktionov.lifetracker.interfaces.OnItemDeletedListener;
import laktionov.lifetracker.loader.AsyncEntryLoader;
import laktionov.lifetracker.model.Item;

public class WishFragment extends Fragment implements OnItemDeletedListener {

    private WishAdapter adapter;
    private List<Item> listItem;
    private Unbinder unbinder;

    @BindView(R.id.rv_wish_list)
    RecyclerView rv_wish_list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wish, container, false);
        unbinder = ButterKnife.bind(this, root);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv_wish_list.setHasFixedSize(true);
        rv_wish_list.setLayoutManager(manager);

        listItem = new ArrayList<>();
        adapter = new WishAdapter(listItem, getActivity());
        adapter.setOnItemDeletedListener(this);
        rv_wish_list.setAdapter(adapter);

        new AsyncEntryLoader(getActivity(), listItem, adapter, AsyncEntryLoader.FROM_WISH_FRAGMENT).execute();

        return root;
    }

    @Override
    public void onItemDeleted() {
        new AsyncEntryLoader(getActivity(), listItem, adapter, AsyncEntryLoader.FROM_WISH_FRAGMENT).execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
