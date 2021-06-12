package com.github.scott.workouttracking.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.github.scott.workouttracking.R;
import com.github.scott.workouttracking.data.History;
import com.github.scott.workouttracking.databinding.ItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder> {
    private List<History> list = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_list, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<History> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private ItemListBinding binding;

        public Holder(@NonNull ItemListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(History history) {
            binding.setItem(history);
        }
    }
}
