package com.eos.caffeinproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.caffeinproject.data.CafeData;

import java.util.ArrayList;


public class ZzimActivity extends Fragment {
    View v;
    private RecyclerView recyclerView;

    private RecyclerAdpater mAdapter;

    private ArrayList<CafeData> list = new ArrayList<>();

    private RecyclerView.LayoutManager mLayoutManager;

    public ZzimActivity(){}


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.activity_zzim, container, false);

        recyclerView = v.findViewById(R.id.collection_list);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.scrollToPosition(0);

        for(int i = 0; i<20; i++){
            list.add(new CafeData("탐앤탐스", 2, "운영시간 00:00~3:00"));
            list.add(new CafeData("흥신소", 4, "운영안해~!"));
        }

        mAdapter = new RecyclerAdpater(list);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        return v;
    }

}
