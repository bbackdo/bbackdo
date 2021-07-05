package com.eos.caffeinproject.data;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;

public class CafeData {
    public String cafeName;
    public int status;
    public String time;
    public int logo;


    public CafeData(String cafeName, int status, String time, int logo){
        this.cafeName = cafeName;
        this.status = status;
        this.time = time;
        this.logo = logo;
    }



}
