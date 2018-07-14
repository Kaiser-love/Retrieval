package com.example.lenovo.client.Database;

public class DocumentBean {
    private String author;  //论文作者
    private String title;   //论文题目
    private String journal; //论文期刊
    private String year;    //论文年份
    private String url;     //论文地址
    private String content; //文档内容
    private StringBuffer contentAboutquery;//与查询有关的内容
    private String date;    //查询时间
    private String query;   //查询词
    public DocumentBean()
    {
        contentAboutquery=new StringBuffer();
    }
    public void setContent(String content)
    {
       this.content=content;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }public void setURL(String url)
    {
        this.url=url;
    }
    public void setDate(String date)
    {
        this.date=date;
    }

    public void update(String str,String query)
    {
        if(str.contains(query))  contentAboutquery.append("……"+str+"……");
        StringBuffer sb=new StringBuffer();
        String string=str.substring(str.indexOf("{")+1, str.lastIndexOf("}"));
        for(String temp:string.split("\\s+"))
            sb.append(temp+" ");
        if(str.contains("author"))
            author=sb.toString();
        else if(str.contains("title"))
            title=sb.toString();
        else if(str.contains("journal")  && sb.length()<10)
            journal=sb.toString();
        else if(str.contains("year"))
            year=sb.toString();
        else if(str.contains("url")  &&  !str.contains("bib"))
            url=sb.toString();
    }
    public String getContnetAboutquery()
    {
        if(contentAboutquery.length()==0)
            return null;
        return contentAboutquery.toString();
    }
    public String getAllContent()
    {
        return content;
    }
    public String getDate()
    {
        return date;
    }
    public String getAuthor()
    {
        return author;
    }
    public String getTitle()
    {
        return title;
    }
    public String getJournal()
    {
        return journal;
    }
    public String getYear()
    {
        return year;
    }
    public String getUrl()
    {
        return url;
    }
    public void setQuery(String query)
    {
        this.query=query;
    }
    public String getQuery()
    {
        return query;
    }
    public void print()
    {
        System.out.println("author="+author);
        System.out.println("title="+title);
        System.out.println("journal="+journal);
        System.out.println("year="+year);
        System.out.println("url="+url);
    }
}
