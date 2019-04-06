package com.ye.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 中国图书网 model 类
 */
public class ChinaBookModel extends BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 书的图片地址
     */
    private String imageUrl;

    /**
     * 书名
     */
    private String bookName;
    /**
     * 书的详细链接
     */
    private String detailUrl;
    /**
     * 原始链接
     */
    private String originUrl;
    /**
     * 作者信息
     */
    private String authorInfo;
    /**
     * 出版时间
     */
    private String publishTime;
    /**
     * 出版社
     */
    private String publisher;
    /**
     * 出售价格
     */
    private Double sellPrice;

    /**
     * 折扣
     */
    private Double discount;
    /**
     * 定价
     */
    private Double priceTit;

    /**
     * 摘要
     */
    private String recoLagu;

    /**
     * 类别1
     */
    private String category1;

    /**
     * 类别2
     */
    private String category2;

    private Date insertTime;
    private Date updateTime;

    public String getRecoLagu() {
        return recoLagu;
    }

    public void setRecoLagu(String recoLagu) {
        this.recoLagu = recoLagu;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(String authorInfo) {
        this.authorInfo = authorInfo;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPriceTit() {
        return priceTit;
    }

    public void setPriceTit(Double priceTit) {
        this.priceTit = priceTit;
    }

    public String getRecolagu() {
        return recoLagu;
    }

    public void setRecolagu(String recoLagu) {
        this.recoLagu = recoLagu;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private ChinaBookModel() {

    }

    /**
     * 使用 Builder 构建对象
     * 
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        /**
         * 书的图片地址
         */
        private String imageUrl;

        /**
         * 书名
         */
        private String bookName;
        /**
         * 书的详细链接
         */
        private String detailUrl;
        /**
         * 作者信息
         */
        private String authorInfo;
        /**
         * 出版时间
         */
        private String publishTime;
        /**
         * 出版社
         */
        private String publisher;
        /**
         * 出售价格
         */
        private Double sellPrice;

        /**
         * 折扣
         */
        private Double discount;
        /**
         * 定价
         */
        private Double priceTit;

        /**
         * 摘要
         */
        private String recolagu;

        /**
         * 类别1
         */
        private String category1;

        /**
         * 类别2
         */
        private String category2;

        /**
         * 原始链接
         */
        private String originUrl;

        private Date insertTime;
        private Date updateTime;

        public Builder originUrl(String originUrl) {
            this.originUrl = originUrl;
            return this;
        }

        public Builder category1(String category1) {
            this.category1 = category1;
            return this;
        }

        public Builder category2(String category2) {
            this.category2 = category2;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder detailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
            return this;
        }

        public Builder authorInfo(String authorInfo) {
            this.authorInfo = authorInfo;
            return this;
        }

        public Builder publishTime(String publishTime) {
            this.publishTime = publishTime;
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder sellPrice(Double sellPrice) {
            this.sellPrice = sellPrice;
            return this;
        }

        public Builder discount(Double discount) {
            this.discount = discount;
            return this;
        }

        public Builder priceTit(Double priceTit) {
            this.priceTit = priceTit;
            return this;
        }

        public Builder recolagu(String recolagu) {
            this.recolagu = recolagu;
            return this;
        }

        public Builder insertTime(Date insertTime) {
            this.insertTime = insertTime;
            return this;
        }

        public Builder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public ChinaBookModel build() {
            ChinaBookModel model = new ChinaBookModel();
            model.setAuthorInfo(authorInfo);
            model.setBookName(bookName);
            model.setDetailUrl(detailUrl);
            model.setDiscount(discount);
            model.setImageUrl(imageUrl);
            model.setPriceTit(priceTit);
            model.setPublisher(publisher);
            model.setPublishTime(publishTime);
            model.setRecolagu(recolagu);
            model.setSellPrice(sellPrice);
            model.setCategory1(category1);
            model.setCategory2(category2);
            model.setInsertTime(new Date());
            model.setUpdateTime(new Date());
            model.setOriginUrl(originUrl);
            return model;
        }
    }

    @Override
    public String toString() {
        return "ChinaBookModel [imageUrl=" + imageUrl + ", bookName=" + bookName + ", detailUrl=" + detailUrl
                + ", authorInfo=" + authorInfo + ", publishTime=" + publishTime + ", publisher=" + publisher
                + ", sellPrice=" + sellPrice + ", discount=" + discount + ", priceTit=" + priceTit + ", recoLagu="
                + recoLagu + ", category1=" + category1 + ", category2=" + category2 + ", insertTime=" + insertTime
                + ", updateTime=" + updateTime + "]";
    }

}
