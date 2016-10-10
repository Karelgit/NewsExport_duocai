package cn.com.cloudpioneer;

import cn.com.cloudpioneer.timer.NewsTimer;

import java.text.ParseException;

/**
 * Created by Tijun on 2016/10/10.
 * @author TijunWang
 */
public class Main {
    public static void main(String []args){
        NewsTimer newsTimer=new NewsTimer();
        try {
            newsTimer.start();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
