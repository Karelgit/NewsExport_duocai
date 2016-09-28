package cn.com.cloudpioneer.downloader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.UrlUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 *This is a downloader for dynamic web page,and it needs phantomjs driver to support.<br/>
 * @author TijunWang
 *         Date: 2016-9-19 <br>
 *         Time: afternoon 1:37 <br>
 */
public class SeleniumDownloader implements Downloader, Closeable {

    static {
        /**
         * The phantomjs driver installed path are indicated in /webdriver.properties
         */
        InputStream is= System.class.getResourceAsStream("/webdriver.properties");
        Properties properties=new Properties();
        try
        {
            properties.load(is);
            /**
             * init an environment for phantomjs
             */
             String phantomJsPath=properties.getProperty("phantomjs.binary.path");
            if (phantomJsPath==null||phantomJsPath.equals("")){
                throw new Exception("must have phantomjs webdriver path be indicated in /webdriver.properties");
            }

            System.setProperty("phantomjs.binary.path",phantomJsPath);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private int sleepTime = 0;

    private int poolSize = 1;

    private WebDriver webDriver;

    //cache webdriver
    private Map<String,WebDriver> webDriverMap= Collections.synchronizedMap(new HashMap<String, WebDriver>());

    /**
     * use domain to gain a webdriver from webDriverMap,if webDriverMap has no webDriver for current domain,we create
     * a webDriver for it ,and put the  driver into webDrvierMap to cache
     * @param task
     * @return
     */
    private synchronized WebDriver getWebDriver(Task task){
        String domain=task.getSite().getDomain();
        WebDriver driver=  webDriverMap.get(domain);
        if (driver==null){
            webDriver = create();

            webDriverMap.put(domain, webDriver);
            driver = webDriver;
        }

        return driver;
    }

    /**
     * Constructor without any filed.
     *
     * @author
     */
    public SeleniumDownloader() {
    }

    /**
     * set sleep time to wait until load success
     *
     * @param sleepTime sleepTime
     * @return this
     */
    public SeleniumDownloader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    @Override
    public Page download(Request request, Task task) {
        webDriver= this.getWebDriver(task);

        webDriver.get(request.getUrl());
        this.sleep();
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        Page page = new Page();
        page.setRawText(content);
        page.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(content, request.getUrl())));
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        return page;
    }

    /**
     * sleep sleepTime  to wait page loading
     */
    private void sleep(){
        try
        {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void setThread(int thread) {
        this.poolSize = thread;
    }

    @Override
    public void close() throws IOException {
        this.closeAll();
    }

    /**
     *   create a webDriver and return it
     */
    private WebDriver create(){
        WebDriver webDriver=new PhantomJSDriver();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return webDriver;
    }

    /**
     * when finished all tasks，close all webdrivers and kill all webdriver processes
     */
    public void  closeAll(){
        for (String domain:webDriverMap.keySet()){
            WebDriver driver=webDriverMap.remove(domain);
            if(driver!=null){
                //exit browser
                driver.close();
                //kill browser process
                driver.quit();
                driver=null;
            }
        }
    }
}