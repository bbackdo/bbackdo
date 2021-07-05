package com.eos.caffeinproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.caffeinproject.R;
import com.eos.caffeinproject.data.CafeData;

import java.util.List;

public class RecyclerAdpater extends RecyclerView.Adapter<RecyclerViewHolder> {


    private List<CafeData> myList;


    public RecyclerAdpater(List<CafeData> myList) {

        this.myList = myList;

    }


    @Override

    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cafe, parent, false);

        return new RecyclerViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        CafeData my = myList.get(position);

        holder.cafeNameText.setText(String.valueOf(position + 1));

        holder.timeText.setText(my.toString());

    }



    @Override

    public int getItemCount() {

        return myList.size();

    }

}
class RecyclerViewHolder extends RecyclerView.ViewHolder {


    public final View mView;

    public final TextView cafeNameText;

    public final TextView timeText;



    public RecyclerViewHolder(View view) {

        super(view);

        mView = view;


        cafeNameText = (TextView) view.findViewById(R.id.cafe_name);

        timeText = (TextView) view.findViewById(R.id.time);

    }

}
