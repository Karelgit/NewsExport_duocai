package NewsPusherModule.timer;

import NewsPusherModule.service.NewsPusher;

import java.util.TimerTask;

/**
 * Created by Administrator on 2016/10/10.
 */
public class NewsPushTask extends TimerTask {

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
