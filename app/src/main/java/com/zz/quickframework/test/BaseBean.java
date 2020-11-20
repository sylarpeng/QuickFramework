package com.zz.quickframework.test;

import java.io.Serializable;

/**
 * @author：PengJunShan.

 * 时间：On 2019-05-06.

 * 描述：根据实际接口文档创建一个BaseBean 用泛型接收不同的实体类
 */

public class BaseBean implements Serializable{

  private int code = 0;//接口访问成功的errorCode
  private String message;
  private LayoutData data;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LayoutData getData() {
    return data;
  }

  public void setData(LayoutData data) {
    this.data = data;
  }
}
