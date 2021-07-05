package com.eos.caffeinproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.caffeinproject.data.CafeData;

import java.util.List;

public class RecyclerAdpater extends RecyclerView.Adapter<RecyclerAdpater.Holder> {


    private List<CafeData> myList;
    private Context mContext;

    public RecyclerAdpater(List<CafeData> myList, Context mContext) {

        this.mContext = mContext;
        this.myList = myList;

    }

    public static class Holder extends RecyclerView.ViewHolder{
       protected ImageView caffelogo;
       protected TextView cafe_name;
       protected TextView time;
       protected ImageView star1;
       protected ImageView star2;
       protected ImageView star3;
       protected ImageView star4;


        public Holder(View view){
            super(view);
            this.cafe_name = view.findViewById(R.id.cafe_name);
            this.caffelogo = view.findViewById(R.id.caffe_logo);
            this.time = view.findViewById(R.id.time);
            this.star1 = view.findViewById(R.id.star1);
            this.star2 = view.findViewById(R.id.star4);
            this.star3 = view.findViewById(R.id.star2);
            this.star4 = view.findViewById(R.id.star3);
        }
    }

    @Override

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.item_cafe, parent, false);
        Holder viewHolder = new Holder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.cafe_name.setText(myList.get(position).cafeName);
        holder.caffelogo.setImageResource(myList.get(position).logo);
        holder.time.setText(myList.get(position).time);
        if(myList.get(position).status == 1){
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.INVISIBLE);
            holder.star3.setVisibility(View.INVISIBLE);
            holder.star4.setVisibility(View.INVISIBLE);
        }
        else if(myList.get(position).status == 2){
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.INVISIBLE);
            holder.star4.setVisibility(View.INVISIBLE);
        }
        else if(myList.get(position).status == 3){
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
            holder.star4.setVisibility(View.INVISIBLE);
        }
        else{
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
            holder.star4.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {

        return myList.size();

    }

}

