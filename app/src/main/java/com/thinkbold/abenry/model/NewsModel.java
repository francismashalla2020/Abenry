package com.thinkbold.abenry.model;


public class NewsModel {

    private int id;
    private String title;
    private String date;
    private String image;
    private String desc;
    private String category;

    public NewsModel(){
        this.id = id;
        this.title = title;
        this.date = date;
        this.image = image;
        this.desc = desc;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc(){
        return desc;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

}
