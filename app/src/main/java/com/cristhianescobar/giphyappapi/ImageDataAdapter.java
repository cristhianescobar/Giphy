package com.cristhianescobar.giphyappapi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by cristhian.escobar on 1/9/16.
 */
public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.DataUnitViewHolder> {

    private Context mContext;
    private LayoutInflater inflator;

    private List<DataUnit> data = Collections.emptyList();

    public ImageDataAdapter(Context context, List<DataUnit> sourceData){
        inflator = LayoutInflater.from(context);
        mContext = context;
        data = sourceData;
    }

//    public void addItem(Reminder reminder){
//        DataUnit d = new DataUnit();
//        d.iconId = R.drawable.ic_textsms_white24dp;
//        d.title = reminder.name;
//        data.add(d);
//        notifyDataSetChanged();
//    }

    @Override
    public DataUnitViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View root = inflator.inflate(R.layout.reminder_item, parent , false);
        Log.d("DataUnitAdapter", "On CreateViewHolder called!");
        DataUnitViewHolder dataUnitViewHolder =  new DataUnitViewHolder(root);

        return dataUnitViewHolder;
    }

    @Override
    public void onBindViewHolder(DataUnitViewHolder holder, int position) {
        DataUnit current = data.get(position) ;
        Log.d("DataUnitAdapter", "On onBindViewHolder called " + position);
        holder.title.setText(current.title);
//        holder.image.setImageResource(current.iconId);
        Picasso.with(mContext).load(current.title).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class DataUnitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView image;

        public DataUnitViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_text);
            image = (ImageView) itemView.findViewById(R.id.list_icon);
            image.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "Clicked " + getPosition(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, GiphyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("url", data.get(getPosition()).title);
            mContext.startActivity(intent);
        }
    }
}
