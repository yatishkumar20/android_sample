package learning.com.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yatish on 15/6/17.
 */

public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.MyViewHolder> {

    private ArrayList<Apps> mDataset;
    private Context mContext;

    public MyAppAdapter(@NonNull Context context, ArrayList<Apps> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView label;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            label = (TextView) view.findViewById(R.id.label);
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        LayoutInflater inflater = LayoutInflater.from(mContext);

                view = inflater.inflate(R.layout.installed_app, parent, false);
                return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Apps movie = mDataset.get(position);
        holder.label.setText(movie.getAppName());
        holder.icon.setImageDrawable(movie.getAppIcon());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
