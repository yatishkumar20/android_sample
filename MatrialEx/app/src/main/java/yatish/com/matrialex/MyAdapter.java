package yatish.com.matrialex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yatish on 27/11/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<ItemObject> itemList;
    private Context context;

    public MyAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView personName;
        public TextView personAddress;
        public ImageView personPhoto;
        public ViewHolder(View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.tv_name);
            personAddress = itemView.findViewById(R.id.tv_addr);
            personPhoto = itemView.findViewById(R.id.iv_img);
        }
    }



    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        holder.personName.setText(itemList.get(position).getName());
        holder.personAddress.setText(itemList.get(position).getAddress());
        holder.personPhoto.setImageResource(itemList.get(position).getPhotoId());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
