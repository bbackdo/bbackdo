package com.eos.caffeinproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.caffeinproject.data.CafeData;

import java.util.ArrayList;


public class ZzimActivity extends Fragment {


    private RecyclerAdpater mAdapter;
    private ArrayList<CafeData> myList = new ArrayList<>();


    public ZzimActivity(){}

    private void initData(){
        myList.clear();
        myList.add(new CafeData("탐앤탐스", 2, "운영시간 00:00~3:00", R.drawable.tomntoms));
        myList.add(new CafeData("스타벅스", 4, "운영안해~!", R.drawable.starbucks));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_zzim, container, false);

        initData();

        Context context = v.getContext();
        RecyclerView recyclerView = v.findViewById(R.id.zzim_recy);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        RecyclerAdpater adapter = new RecyclerAdpater(myList, context);
        recyclerView.setAdapter(adapter);

        return v;
    }

}
