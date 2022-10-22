package ra.bussiness.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Product implements Serializable {
    private String pruductId;
    private String productName;
    private float price;
    private float discount;
    private float exportPrice;
    private String title;
    private String content;
    private List<Color> productColorList;
    private List<Size> productSizeList;
    private Catalog catalog;
    private boolean productStatus;
    private Date date;

    public Product() {
    }

    public Product(String pruductId, String productName, float price, float discount, float exportPrice, String title, String content, List<Color> productColorList, List<Size> productSizeList, Catalog catalog, boolean productStatus, Date date) {
        this.pruductId = pruductId;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.exportPrice = exportPrice;
        this.title = title;
        this.content = content;
        this.productColorList = productColorList;
        this.productSizeList = productSizeList;
        this.catalog = catalog;
        this.productStatus = productStatus;
        this.date = date;
    }

    public String getPruductId() {
        return pruductId;
    }

    public void setPruductId(String pruductId) {
        this.pruductId = pruductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(float exportPrice) {
        this.exportPrice = exportPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Color> getProductColorList() {
        return productColorList;
    }

    public void setProductColorList(List<Color> productColorList) {
        this.productColorList = productColorList;
    }

    public List<Size> getProductSizeList() {
        return productSizeList;
    }

    public void setProductSizeList(List<Size> productSizeList) {
        this.productSizeList = productSizeList;
    }

    public Catalog getCatalog() {
        return this.catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

