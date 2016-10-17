package com.jamescoggan.teatimer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jamescoggan.teatimer.R;
import com.jamescoggan.teatimer.models.Timer;

import io.realm.RealmResults;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ViewHolder> {

    private RealmResults<Timer> itemList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_timer_name);
        }
    }

    public TimerAdapter(RealmResults<Timer> itemList) {
        this.itemList = itemList;
    }

    public void setItems(RealmResults<Timer> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public TimerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_timer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TimerAdapter.ViewHolder holder, int position) {
        final Timer item = itemList.get(position);
        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}