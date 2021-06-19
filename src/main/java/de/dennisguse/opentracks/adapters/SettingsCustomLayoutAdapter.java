package de.dennisguse.opentracks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import de.dennisguse.opentracks.R;

public class SettingsCustomLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> titleList;
    private final Context context;

    public SettingsCustomLayoutAdapter(Context context, List<String> values) {
        this.context = context;
        titleList = values;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_stats_item, parent, false);
        return new SettingsCustomLayoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SettingsCustomLayoutAdapter.ViewHolder viewHolder = (SettingsCustomLayoutAdapter.ViewHolder) holder;
        String item = titleList.get(position);
        viewHolder.setData(item);
    }

    @Override
    public int getItemCount() {
        if (titleList == null) {
            return 0;
        } else {
            return titleList.size();
        }
    }


    public List<String> swapData(List<String> data) {
        if (titleList == data) {
            return null;
        }

        titleList = data;

        if (data != null) {
            this.notifyDataSetChanged();
        }

        return data;
    }

    public List<String> swapData(int fromPosition, int toPosition) {
        Collections.swap(titleList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return titleList;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView label;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.stats_custom_title);
        }

        public void setData(String data) {
            this.label.setText(data);
        }
    }
}
