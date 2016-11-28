package cn.com.cloudpioneer.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Tijun on 2016/9/21.
 */
@Component
public class CrawlerDataEntity
{
    private int seqeueID;

    private String tid;

    private String url;

    private int  statusCode;

    private String pass;

    private String type;

    private String rootUrl;

    private String fromUrl;
    //content
    private String text;
    private String html;
    private String startTime;
    private Date crawlTime;
    //
    private Date publishTime;
    private int depthfromSeed;
    private String title;
    private int  count;
    private int tag;
    private int fetched;
    private String sourceName;
    private String author;

    public String getParsedData() {
        return parsedData;
    }

    public void setParsedData(String parsedData) {
        this.parsedData = parsedData;
    }

    private String parsedData;



    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSeqeueID()
    {
        return seqeueID;
    }

    public void setSeqeueID(int seqeueID)
    {
        this.seqeueID = seqeueID;
    }

    public String getTid()
    {
        return tid;
    }

    public void setTid(String tid)
    {
        this.tid = tid;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass(String pass)
    {
        this.pass = pass;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getRootUrl()
    {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl)
    {
        this.rootUrl = rootUrl;
    }

    public String getFromUrl()
    {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl)
    {
        this.fromUrl = fromUrl;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public Date getCrawlTime()
    {
        return crawlTime;
    }

    public void setCrawlTime(Date crawlTime)
    {
        this.crawlTime = crawlTime;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public int getDepthfromSeed()
    {
        return depthfromSeed;
    }

    public void setDepthfromSeed(int depthfromSeed)
    {
        this.depthfromSeed = depthfromSeed;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getTag()
    {
        return tag;
    }

    public void setTag(int tag)
    {
        this.tag = tag;
    }

    public int getFetched()
    {
        return fetched;
    }

    public void setFetched(int fetched)
    {
        this.fetched = fetched;
    }
}
