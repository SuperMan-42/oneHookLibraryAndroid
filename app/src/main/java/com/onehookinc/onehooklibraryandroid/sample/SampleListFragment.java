package com.onehookinc.onehooklibraryandroid.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onehookinc.onehooklibraryandroid.R;
import com.onehookinc.onehooklibraryandroid.sample.common.BaseListFragment;

import java.util.ArrayList;

public class SampleListFragment extends BaseListFragment implements View.OnClickListener {

    private static final String ARG_PARENT = "argParent";
    private static final String ARG_HAS_PARENT = "argHasParent";

    public static SampleListFragment newInstance(final SampleItem itemParent, final boolean hasParent) {
        final SampleListFragment fragment = new SampleListFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARG_PARENT, itemParent);
        args.putBoolean(ARG_HAS_PARENT, hasParent);
        fragment.setArguments(args);
        return fragment;
    }

    private SampleItem mParent;

    @Override
    public RecyclerView.Adapter createAdapter() {
        mParent = getArguments().getParcelable(ARG_PARENT);
        return new SampleListAdapter(mParent.getSubItems());
    }

    @Override
    public void onToolbarReady(Toolbar toolbar) {
        super.onToolbarReady(toolbar);
        mParent = getArguments().getParcelable(ARG_PARENT);
        getBaseAcivity().getSupportActionBar().setTitle(mParent.getTitle());
        final boolean hasParent = getArguments().getBoolean(ARG_HAS_PARENT, false);
        getBaseAcivity().getSupportActionBar().setDisplayHomeAsUpEnabled(hasParent);
    }

    @Override
    public void onClick(View view) {
        final SampleItem item = (SampleItem) view.getTag(R.id.cell_view_tag_key);
        if (item != null) {
            final MainActivity activity = (MainActivity) getActivity();
            activity.onItemClicked(item);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;

        public ItemViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sample_item,
                    parent, false));
            mTitleTextView = itemView.findViewById(R.id.cell_sample_item_title_textview);
            itemView.setOnClickListener(SampleListFragment.this);
        }

        private void bind(final int position, final SampleItem item) {
            itemView.setTag(R.id.cell_view_position_key, Integer.valueOf(position));
            itemView.setTag(R.id.cell_view_tag_key, item);
            mTitleTextView.setText(item.getTitle());
        }
    }

    private class SampleListAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        @NonNull
        private ArrayList<SampleItem> mItems;

        public SampleListAdapter(final ArrayList<SampleItem> items) {
            mItems = items;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            final SampleItem item = mItems.get(position);
            holder.bind(position, item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

}
