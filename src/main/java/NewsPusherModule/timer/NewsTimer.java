package NewsPusherModule.timer;

import java.text.ParseException;
import java.util.Date;
import java.util.Timer;

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


