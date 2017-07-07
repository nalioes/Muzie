package com.syncsource.org.muzie.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.databinding.ItemFilterBinding;
import com.syncsource.org.muzie.model.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nls on 6/6/17.
 */
public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Filter> chartsgenres = new ArrayList<>();
    int selected_position = 0;
    filterListener filterListener;

    public FilterAdapter(Context context) {
        this.context = context;
    }

    public void setFilterListener(filterListener filterListener) {
        this.filterListener = filterListener;
    }

    public void addItem(List<Filter> chartsgenres, filterListener filterListener, int selected_position) {
        this.chartsgenres = chartsgenres;
        this.selected_position = selected_position;
        this.filterListener = filterListener;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_filter, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ItemViewHolder) holder).getBinding().filterTitle.setText(chartsgenres.get(position).getName());

        if (selected_position == position) {
            ((ItemViewHolder) holder).getBinding().getRoot().setSelected(true);
            ((ItemViewHolder) holder).getBinding().selectorImg.setVisibility(View.VISIBLE);
            ((ItemViewHolder) holder).getBinding().selectorImg.setImageResource(R.drawable.arr_filter);
            ((ItemViewHolder) holder).getBinding().filterTitle.setTextColor(Color.parseColor("#fb496c"));
        } else {
            ((ItemViewHolder) holder).getBinding().getRoot().setSelected(false);
            ((ItemViewHolder) holder).getBinding().selectorImg.setVisibility(View.GONE);
            ((ItemViewHolder) holder).getBinding().filterTitle.setTextColor(Color.parseColor("#fdfdfd"));
        }

        ((ItemViewHolder) holder).getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_position == position) {
                    ((ItemViewHolder) holder).getBinding().selectorImg.setVisibility(View.INVISIBLE);
                    ((ItemViewHolder) holder).getBinding().filterTitle.setTextColor(Color.parseColor("#fdfdfd"));
                    notifyItemChanged(selected_position);
                } else {
                    selected_position = position;
                    ((ItemViewHolder) holder).getBinding().filterTitle.setTextColor(Color.parseColor("#fb496c"));
                    ((ItemViewHolder) holder).getBinding().selectorImg.setVisibility(View.VISIBLE);
                    if (filterListener != null) {
                        filterListener.isChecked(true, position, chartsgenres.get(position));
                    }
                    notifyItemChanged(selected_position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chartsgenres.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemFilterBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemFilterBinding getBinding() {
            return binding;
        }
    }

    public interface filterListener {
        void isChecked(Boolean check, int position, Filter filter);
    }
}
