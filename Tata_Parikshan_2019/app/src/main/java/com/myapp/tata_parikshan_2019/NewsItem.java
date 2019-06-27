package com.myapp.tata_parikshan_2019;

public class NewsItem {

    String Title,Content,Date;
    int userphoto;

    public NewsItem() {
    }


    public NewsItem(String title, String content, String date, int userphoto) {
        Title = title;
        Content = content;
        Date = date;
        this.userphoto = userphoto;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(int userphoto) {
        this.userphoto = userphoto;
    }
}
