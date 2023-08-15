package com.example.ecorecicla.Utils;

import com.example.ecorecicla.Constants.DateKeysConstants;

import java.util.HashMap;


public class DateStringFormat{

    public String yearValue,monthValue,dayValue;

    public HashMap<String,String> dateTime;

    public DateStringFormat(String year,String month,String day) {
        this.yearValue = year;
        this.monthValue = month;
        this.dayValue = day;
    }


    public HashMap<String,String> getDateTime(){
        dateTime = new HashMap<>();
        dateTime.put(DateKeysConstants.YEAR.toString(),yearValue);
        dateTime.put(DateKeysConstants.MONTH.toString(),monthValue);
        dateTime.put(DateKeysConstants.DAY.toString(),dayValue);

        return dateTime;
    }

}
