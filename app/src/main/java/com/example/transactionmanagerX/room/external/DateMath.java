package com.example.transactionmanagerX.room.external;

import java.util.Calendar;

public class DateMath {


    public Long getNow(){
        Calendar calendar=Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
    public Long getSomeDaysAgo(int days_ago){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,days_ago * -1);
        return calendar.getTimeInMillis();
    }
}
