package com.example.practice_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.practice_app.R;
import com.example.practice_app.model.country;
import com.example.practice_app.util.image_fetcher;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<country> countryList;

    public RecyclerViewAdapter(Context context, List<country> countryList) {
        this.context = context;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        return new ViewHolder(view,context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        country country = countryList.get(position);
        holder.name.setText(country.getName());
        holder.capital.setText("Capital : "+country.getCapital());
        holder.region.setText("Region : "+country.getName());
        holder.subregion.setText("Subregion : "+country.getName());
        holder.borders.setText("Borders : "+country.getBorders());
        holder.languages.setText("Languages : "+country.getLanguages());
        String imageurl = country.getImage();
        image_fetcher.fetchSvg(context,imageurl,holder.imageView);
//        Picasso.get()
//                .load(imageurl)
//                .placeholder(R.drawable.mapwalpaper)
//                .fit()
//                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView name,capital,region,subregion,borders,languages,population;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;
            name = itemView.findViewById(R.id.name);
            capital = itemView.findViewById(R.id.capital);
            region = itemView.findViewById(R.id.region);
            subregion = itemView.findViewById(R.id.subregion);
            borders = itemView.findViewById(R.id.border);
            languages = itemView.findViewById(R.id.language);
            imageView = itemView.findViewById(R.id.image_view);

        }


}
}
