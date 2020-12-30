package com.coloredlanguageapp;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

public class BaseCardAdapter extends com.huxq17.swipecardsview.BaseCardAdapter {
    private Animation animaScale;
    private List<ItemModel> items;
    private Context context;

    public BaseCardAdapter(List<ItemModel> items, Context context) {

        //animaScale = AnimationUtils.loadAnimation(context,R.anim.scale);
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.item_card;
    }

    @Override
    public void onBindData(int position, View cardview) {

        //cardview.startAnimation(animaScale);

        if(items == null || items.size() == 0){
            return;
        }

        HtmlTextView emoji, english, turkish, mean;
        emoji= cardview.findViewById(R.id.item_emoji);
        english= cardview.findViewById(R.id.item_english);
        turkish= cardview.findViewById(R.id.item_turkish);
        mean= cardview.findViewById(R.id.item_mean);

        ItemModel model = items.get(position);

        emoji.setHtml(model.getEmoji());
        mean.setHtml(model.getMean());
        english.setHtml(model.getEnglish());
        turkish.setHtml(model.getTurkish());

    }
}
