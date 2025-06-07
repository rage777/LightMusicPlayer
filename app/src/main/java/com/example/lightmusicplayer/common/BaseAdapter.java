package com.example.lightmusicplayer.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<ITEM,T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private final List<ITEM> list = new ArrayList<>();

    public final void submitList(List<ITEM> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public final ITEM getItem(int index){
        return list.get(index);
    }

    public final void updateItem(ITEM item,int index){
        list.set(index,item);
        notifyItemChanged(index);
    }


   public interface ItemOnClickListener{
        void onClick(View v,int index);
   }


   private ItemOnClickListener itemOnClickListener;
   public final void setOnItemClickListener(ItemOnClickListener listener){
        itemOnClickListener = listener;
   }

    @NonNull
    @Override
    public final T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        T vh = onCreateViewHolder(parent,viewType,parent.getContext(),LayoutInflater.from(parent.getContext()));
        vh.itemView.setOnClickListener(v -> {
            if (itemOnClickListener == null) return;
            itemOnClickListener.onClick(v,vh.getAdapterPosition());
        });
        return vh;
    }

    public abstract T onCreateViewHolder(ViewGroup parent, int viewType, Context context, LayoutInflater inflater);

    @Override
    public final void onBindViewHolder(@NonNull T holder, int position) {
        onBindViewHolder(holder,position,getItem(position));
    }

    public abstract void onBindViewHolder(@NonNull T holder, int position,ITEM item);

    @Override
    public final int getItemCount() {
        return list.size();
    }

}
