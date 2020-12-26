package com.coloredlanguageapp;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private List<ItemModel> items;

    public CardStackAdapter(List<ItemModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        HtmlTextView emoji, english, turkish, mean;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            emoji= itemView.findViewById(R.id.item_emoji);
            english= itemView.findViewById(R.id.item_english);
            turkish= itemView.findViewById(R.id.item_turkish);
            mean= itemView.findViewById(R.id.item_mean);
        }

        void setData(ItemModel data) {
            emoji.setHtml(data.getEmoji());
            english.setHtml(data.getEnglish());
            turkish.setHtml(data.getTurkish());
            mean.setHtml(data.getMean());
        }
    }
}
