package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;
import ra.bussiness.imple.CatalogImp;
import ra.bussiness.imple.ProductImp;
import ra.config.ShopValidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductMenu {
    private static ProductImp proImp = new ProductImp();
    public static void displayProductMenu(Scanner sc){
        boolean checkexit = true;
        do {
            System.out.println("************ QUẢN LÝ SẢN PHẨM *************");
            System.out.println("1. Danh sách sản phẩm được sắp xếp theo danh mục sản phẩm");
            System.out.println("2. Thêm mới sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Thoát");

            System.out.println("Lựa chọn cửa bạn là");
            int choice = 0;
            do {
                String str = sc.nextLine();
                if (ShopValidate.checkempty(str)){
                    if (ShopValidate.checkInteger(str)){
                         choice = Integer.parseInt(str);
                         break;
                    }else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không dược để trống vui lòng nhập dữ liệu vào");
                }
            } while (true);
            switch (choice){
                case 1:
                    break;
                case 2:
                    ProductMenu.inputListProduct(sc);
                    break;
                case 3:
                    break;
                case 4:
                    ProductMenu.deleteProduct(sc);
                    break;
                case 5:
                    checkexit = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1 - 5");
            }
        } while (checkexit);
    }
    public static void inputListProduct(Scanner sc){
        List<Product> productList = proImp.readFromfile();
        System.out.println("Nhập số lượng sản phẩm mới thêm vào");
        int num = 0;
        do {
            String strNum = sc.nextLine();
            if (ShopValidate.checkempty(strNum)){
                if (ShopValidate.checkInteger(strNum)){
                    num = Integer.parseInt(strNum);
                    break;
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng lựa chọn");
            }
        } while (true);
        for (int i = 0; i < num; i++) {
            System.out.println("Nhập dữ liệu cho sản phẩm: "+(i+1));
            ProductImp proImp = new ProductImp();
            Product pro = proImp.inputData(sc);
            productList.add(pro);
            proImp.create(pro);
        }
    }
    public static void updateListProduct(Scanner sc){
        List<Product> productList = proImp.readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        System.out.println("Nhập Id sản phẩm bạn muốn cập nhật vào");
        int proId = 0;
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)){
                if (ShopValidate.checkInteger(str)){
                    proId = Integer.parseInt(str);
                    break;
                } else {
                    System.out.println("Vui lòng nhập vào một số nguyên");
                }
            } else {
                System.out.println("Không được để trống ");
            }
        } while (true);
        for (Product pro : productList) {
            if (pro.getPruductId().equals(proId)){

            } else {
                System.err.println("Không tìm thấy id sản phẩm");
            }
        }
    }
    public static void deleteProduct(Scanner sc){
        List<Product> productList = proImp.readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        System.out.println("Nhập mã sản phẩm bạn muốn xóa vào");
        String str = sc.nextLine();
        for (Product pro:productList) {
            if (pro.getPruductId().equals(str)&&pro.isProductStatus()){
                 pro.setProductStatus(false);
            } else {
                System.out.println("Không tìm thấy sản phẩm bạn muốn xóa");
            }
        }
       boolean sresult =  proImp.writeToFile(productList);
        if (sresult){
            System.out.println("xóa thành công");
        }  else {
            System.err.println("Đã xảy ra lỗi");
        }
    }
}
