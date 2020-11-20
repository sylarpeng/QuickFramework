package com.zz.quickframework.test;


import java.util.List;

/**
 * create   : by ChenSH
 * 开发时间  : 2020/10/20
 * 描  述  ：每一个模块的播放内容
 */

public class LayoutAttributes {

    private String viewType;//每一屏 的文件类型
    private int width;
    private int height;
    private int marginLeft;
    private int marginTop;

    private String resource;//播放文件资源


    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

//    private List<String> resource;
//
//    public List<String> getResource() {
//        return resource;
//    }
//
//    public void setResource(List<String> resource) {
//        this.resource = resource;
//    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }


}
