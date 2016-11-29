package cn.com.cloudpioneer;

import cn.com.cloudpioneer.timer.NewsTimer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.text.ParseException;

/**
 * <ApplicationNewsExport,启动Springboot提供新闻推送服务>
 * Created by Tijun on 2016/10/10.
 * @author TijunWang
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class ApplicationNewsExport {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationNewsExport.class);
    }
}
