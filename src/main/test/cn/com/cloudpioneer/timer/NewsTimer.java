package cn.com.cloudpioneer.timer;

import cn.com.cloudpioneer.service.NewsPusher;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


class MyTask extends TimerTask {
    public void run() {
        System.out.println("Time's up!");
        NewsPusher newsPusher= new NewsPusher();
        try {
            newsPusher.pushNews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //this.cancel();
    }
}
