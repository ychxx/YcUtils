package com.yc.ycutilslibrary.widget.banner;

/**
 * 轮播图实体类
 */
public class BannerEntity {
    private String mTitle;
    private String mImg;
    private String mLink;
    public BannerEntity(){}
    public BannerEntity(String link, String title, String img) {
        this.mLink = link;
        this.mTitle = title;
        this.mImg = img;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImg() {
        return mImg;
    }

    public void setImg(String img) {
        mImg = img;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }
}
