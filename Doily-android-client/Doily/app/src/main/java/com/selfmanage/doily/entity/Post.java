package com.selfmanage.doily.entity;

import java.util.Date;

public class Post {
    private Integer postId; // 帖子id
    private String txt; // 帖子文字内容
    private String img; // 帖子图片内容
    private int imgtag;
    private String audio;//音频文件
    private int audiotag;
    private Integer userId; // 所属用户id
    private Date time; // 发布时间

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public int getImgtag() {
        return imgtag;
    }

    public int getAudiotag() {
        return audiotag;
    }
}
