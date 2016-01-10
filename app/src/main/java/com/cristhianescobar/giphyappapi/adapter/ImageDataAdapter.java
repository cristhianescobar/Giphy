package com.cristhianescobar.giphyappapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristhianescobar.giphyappapi.DataUnit;
import com.cristhianescobar.giphyappapi.R;
import com.cristhianescobar.giphyappapi.activities.GiphyActivity;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cristhian.escobar on 1/9/16.
 */
public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.DataUnitViewHolder> {

    private List<DataUnit> data = Collections.emptyList();
    private LayoutInflater inflator;
    private Context mContext;

    public ImageDataAdapter(Context context, List<DataUnit> sourceData){
        inflator = LayoutInflater.from(context);
        mContext = context;
        data = sourceData;
    }

    public void setNewData(List newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public DataUnitViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View root = inflator.inflate(R.layout.item_giphy, parent , false);
        Log.d("ImageDataAdapter", "On CreateViewHolder called!");
        DataUnitViewHolder dataUnitViewHolder =  new DataUnitViewHolder(root);

        return dataUnitViewHolder;
    }

    @Override
    public void onBindViewHolder(DataUnitViewHolder holder, int position) {
        DataUnit current = data.get(position) ;
        Log.d("ImageDataAdapter", "On onBindViewHolder called " + position);
        holder.title.setText(current.title);
        Picasso.with(mContext).load(current.title)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DataUnitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.list_text)
        TextView title;
        @Bind(R.id.list_icon)
        ImageView image;

        public DataUnitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, GiphyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("url", data.get(getPosition()).title);
            mContext.startActivity(intent);
        }
    }
}
