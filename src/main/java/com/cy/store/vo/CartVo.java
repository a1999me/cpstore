package com.cy.store.vo;

import java.io.Serializable;
import java.util.Objects;

/**
 * 购物车数据的VO类（VAlue Object）
 */
public class CartVo  implements Serializable {

   private Integer cid;
   private  Integer uid;
   private  Integer pid;
   private Long price;
   private Integer num;
   private  String title;
   private String image;
   private Long realPrice;

    @Override
    public String toString() {
        return "CartVo{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", price=" + price +
                ", num=" + num +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", realPrice=" + realPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartVo)) return false;
        CartVo cartVo = (CartVo) o;
        return Objects.equals(getCid(), cartVo.getCid()) &&
                Objects.equals(uid, cartVo.uid) &&
                Objects.equals(pid, cartVo.pid) &&
                Objects.equals(price, cartVo.price) &&
                Objects.equals(num, cartVo.num) &&
                Objects.equals(title, cartVo.title) &&
                Objects.equals(image, cartVo.image) &&
                Objects.equals(realPrice, cartVo.realPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCid(), uid, pid, price, num, title, image, realPrice);
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {


        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Long realPrice) {
        this.realPrice = realPrice;
    }
}
