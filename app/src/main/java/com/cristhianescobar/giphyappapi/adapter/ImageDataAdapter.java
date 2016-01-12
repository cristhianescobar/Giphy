package com.cristhianescobar.giphyappapi.adapter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cristhianescobar.giphyappapi.R;
import com.cristhianescobar.giphyappapi.activities.GiphyActivity;
import com.cristhianescobar.giphyappapi.activities.MainActivity;
import com.cristhianescobar.giphyappapi.utils.DataUnit;
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
    private MainActivity mContext;

    public ImageDataAdapter(MainActivity context, List<DataUnit> sourceData){
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
        String url = current.title;
        if(current.title == null || current.title.isEmpty()){
            url = "http://luckylab.com/wp-content/uploads/2014/07/soccer-ball.jpg";
        }
        Picasso.with(mContext).load(url)
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
            if (title == null || title.getText().toString().isEmpty()) {
                Toast.makeText(mContext, "Error Loarding the Giphy", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(mContext, GiphyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(GiphyActivity.URL, data.get(getPosition()).title);
            View sharedView = image;
            String transitionName = mContext.getString(R.string.share_element);
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(mContext, sharedView, transitionName);
            mContext.startActivity(i, transitionActivityOptions.toBundle());
        }
    }
}
