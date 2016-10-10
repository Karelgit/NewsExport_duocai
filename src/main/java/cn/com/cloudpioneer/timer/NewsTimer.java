package cn.com.cloudpioneer.timer;

import cn.com.cloudpioneer.service.NewsPusher;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tianjinjin on 2016/10/9.
 */
public class NewsTimer{
    Timer timer;


    public NewsTimer() {
        timer = new Timer();
    }
    public  void start() throws ParseException, InterruptedException {
        System.out.println("program run......");
        Date myDate = new Date();

        timer.schedule(new NewsPushTask(), myDate,24*60*60 * 1000);
    }

    public void stop(){
        timer.cancel();
    }
}


