package NewsPusherModule.timer;

import NewsPusherModule.service.NewsPusher;

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
