package com.zz.quickframework.test;



import java.util.ArrayList;
import java.util.List;


/**
 * create   : by ChenSH
 * 开发时间  : 2020/10/20
 * 描  述  ：玩法内容信息
 */
public class LayoutData {

    private Long id;
    private String model; //玩法类型
    private String layoutType;//横竖屏类型
    private String layoutId;
    private LayoutAttributes centers[];//播放的内容
//    private List<LayoutAttributes> centers;


//    public List<LayoutAttributes> getCenters() {
//        return centers;
//    }
//
//    public void setCenters(List<LayoutAttributes> centers) {
//        this.centers = centers;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

//    public LayoutAttributes[] getCenters() {
//        return centers;
//    }
//
//    public void setCenters(LayoutAttributes[] centers) {
//        this.centers = centers;
//    }
}
