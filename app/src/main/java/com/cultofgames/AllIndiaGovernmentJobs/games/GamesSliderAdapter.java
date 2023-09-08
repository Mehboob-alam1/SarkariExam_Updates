package com.cultofgames.AllIndiaGovernmentJobs.games;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.cultofgames.AllIndiaGovernmentJobs.R;
import com.cultofgames.AllIndiaGovernmentJobs.SarkariModal.Slider;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class GamesSliderAdapter extends SliderViewAdapter<GamesSliderAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Slider> list;


    public GamesSliderAdapter(Context context, ArrayList<Slider> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {


        View view = LayoutInflater.from(context).inflate(R.layout.games_slider_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Slider items = list.get(position);

        // viewHolder.imageView.setImageResource(items.getImage());
        Glide.with(context)
                .load(items.getImageUrl())
                .fitCenter()
                .into(viewHolder.imageView)
        ;
        viewHolder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(items.getImageLink()));
            context.startActivity(i);
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class MyViewHolder extends ViewHolder {
        View itemView;
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_auto_image_slider);

        }
    }
}
