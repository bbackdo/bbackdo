package com.eos.caffeinproject.data;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;

public class CafeData {
    public String cafeName;
    public int status;
    public String time;
    public ImageView logo;

    public CafeData(String cafeName, int status, String time){
        this.cafeName = cafeName;
        this.status = status;
        this.time = time;
    }



}
