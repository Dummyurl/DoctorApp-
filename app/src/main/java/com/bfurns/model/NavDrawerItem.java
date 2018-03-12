package com.bfurns.model;


public class NavDrawerItem {
    private boolean showNotify;
    private String title;



    private int imageUrl;


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int imageUrl) {
        this.showNotify = showNotify;
        this.title = title;
        this.imageUrl=imageUrl;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
