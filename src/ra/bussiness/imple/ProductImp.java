package ra.bussiness.imple;

import ra.bussiness.design.IProduct;
import ra.bussiness.design.IShop;
import ra.bussiness.entity.Color;
import ra.bussiness.entity.Product;
import ra.bussiness.entity.User;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductImp implements IProduct<Product,Integer> {
    @Override
    public boolean create(Product product) {
        List<Product> productList = readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        productList.add(product);
        boolean result = writeToFile(productList);
        return result;
    }

    @Override
    public boolean update(Product product) {
        List<Product> productList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getPruductId() == product.getPruductId()) {
                productList.set(i, product);
                returnData = true;
                break;
            }
        }
        return returnData;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<Product> readFromfile() {
        FileImp fileImp = new FileImp();
        return fileImp.readFromFile(ShopConstanst.URL_PRODUCT_FILE);
    }

    @Override
    public boolean writeToFile(List<Product> list) {
        FileImp fileImp = new FileImp();
        return fileImp.writeToFile(list,ShopConstanst.URL_PRODUCT_FILE);
    }

    @Override
    public Product inputData(Scanner sc) {
        List<Product> productList = readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        Product productNew = new Product();
        System.out.println("Nhập mã sản phẩm vào");
        do {
            String productId = sc.nextLine();
            if (ShopValidate.checkempty(productId)){
                if (ShopValidate.checklenght(productId,5,5)){
                    boolean checkexist = true;
                    for (Product pro:productList) {
                        if (pro.getPruductId().equals(productId)){
                            checkexist = false;
                        }
                    }
                    if (checkexist){
                        productNew.setPruductId(productId);
                        break;
                    } else {
                        System.err.println(ShopMessage.PRODUCTID_MESSAGE_EXIST);
                    }
                } else {
                    System.err.println(ShopMessage.PRODUCTID_MESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.PRODUCTID_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nhập tên sản phẩm vào");
        do {
            String productName = sc.nextLine();
            if (ShopValidate.checkempty(productName)){
                if (ShopValidate.checklenght(productName,6,30)){
                    boolean checkNameExist = true;
                    for (Product pro:productList) {
                        if (pro.getProductName().equals(productName)){
                            checkNameExist = false;
                        }
                    }
                    if (checkNameExist){
                        productNew.setProductName(productName);
                        break;
                    } else {
                        System.err.println(ShopMessage.PRODUCTNAME_MESSAGE_EXIST);
                    }
                } else {
                    System.err.println(ShopMessage.PRODUCTNAME_MESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.PRODUCTNAME_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nhập giá sản phẩm vào");
        do {
            String strPrice = sc.nextLine();
            if (ShopValidate.checkempty(strPrice)){
                if (ShopValidate.checkfloat(strPrice)){
                    if (Float.parseFloat(strPrice)>0){
                        productNew.setPrice(Float.parseFloat(strPrice));
                        break;
                    } else {
                        System.err.println("Vui lòng nhập giá sản phẩm lớn hơn 0");
                    }
                } else {
                    System.err.println("Vui lòng nhập vào một số thực");
                }
            } else {
                System.err.println(ShopMessage.PRODUCTPRICE_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nhập phần trăm giảm giá vào");
        do {
            String strDiscount = sc.nextLine();
            float discount = Float.parseFloat(strDiscount);
            if (ShopValidate.checkempty(strDiscount)){
                if (ShopValidate.checkfloat(strDiscount)){
                    if (discount>=0&&discount<=100){
                        productNew.setDiscount(discount);
                        break;
                    } else {
                        System.err.println(ShopMessage.PRODUCTDISCOUNT_MESSAGE_LENGHT);
                    }
                } else {
                    System.err.println("Vui lòng nhập vào một số thực");

                }
            } else {
                System.err.println(ShopMessage.PRODUCTDISCOUNT_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nhập tiêu đề sản phẩm vào");
        do {
            String title = sc.nextLine();
            if (ShopValidate.checkempty(title)){
                if (ShopValidate.checklenght(title,10,50)){
                    productNew.setTitle(title);
                    break;
                } else {
                    System.err.println(ShopMessage.PRODUCTTITLE_MESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.PRODUCTTITLE_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nhập mô tả sản phẩm vào");
        do {
            String content = sc.nextLine();
            if (ShopValidate.checkempty(content)){
                productNew.setContent(content);
                break;
            } else {
                System.err.println(ShopMessage.PRODUCTCONTENT_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Vui lòng chọn các màu sắc của sản phẩm");
        ColorImp colorImp = new ColorImp();
        List<Color> colorList = colorImp.readFromfile();
        if (colorList.size()==0){
            colorList = new ArrayList<>();
        }
        for (Color color:colorList) {

        }
        return productNew;
    }

    @Override
    public void displayData(Product product) {

    }

    @Override
    public boolean deleteProduct(String str) {
        List<Product> productList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getPruductId().equals(str)) {
                productList.get(i).setProductStatus(!productList.get(i).isProductStatus());
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(productList);
        if (result && returnData) {
            return true;
        }
        return false;
    }
}
