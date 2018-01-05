package learning.com.learning;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yatish on 22/2/17.
 */

public class MyAdapter extends RecyclerView.Adapter {
    private String[] mDataset;
    private Context mContext;
    private static final int FIRST_ROW = 0;
    private static final int NOT_FIRST = 1;


    public MyAdapter(@NonNull Context context, String[] myDataset) {
        mDataset = myDataset;
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (viewType) {

            case FIRST_ROW:

                view = inflater.inflate(R.layout.card_view_first, parent, false);
                return new FirstItemhViewHolder(view);

            case NOT_FIRST:

                view = inflater.inflate(R.layout.card_view_item, parent, false);
                return new ItemListViewHolder(view);

        }

        return null;

    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        switch (viewType) {
            case FIRST_ROW:

                ((FirstItemhViewHolder)holder).tvFirst.setText(mDataset[position]);

                break;

            case NOT_FIRST:

                ((ItemListViewHolder)holder).tvItem.setText(mDataset[position]);

                break;
        }

    }


    @Override
    public int getItemViewType(int position) {

        if(position == FIRST_ROW){
            return FIRST_ROW;
        }else{
            return NOT_FIRST;
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    class FirstItemhViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvFirst;
        public FirstItemhViewHolder(View itemView) {
            super(itemView);

            tvFirst = (TextView) itemView.findViewById(R.id.tv_first_row);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


           /* view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {*/
                    Intent i = new Intent(mContext,SecondActivity.class);
                    mContext.startActivity(i);
             /*   }
            });*/




        }
    }

    class ItemListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvItem;
        public ItemListViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(mContext,SecondActivity.class);
            mContext.startActivity(i);
        }
    }

}
