package ra.bussiness.imple;

import ra.bussiness.design.IProduct;
import ra.bussiness.design.IShop;
import ra.bussiness.entity.*;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProductImp implements IProduct<Product, Integer> {
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
        return fileImp.writeToFile(list, ShopConstanst.URL_PRODUCT_FILE);
    }

    @Override
    public Product inputData(Scanner sc) {
        Product product = new Product();
        List<Size> sizeList1 = product.getProductSizeList();
        if (sizeList1==null){
            sizeList1 = new ArrayList<>();
        }
        List<Color> colorList1 = product.getProductColorList();
        List<Product> productList = readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        Product productNew = new Product();
        System.out.println("Nhập mã sản phẩm vào");
        do {
            String productId = sc.nextLine();
            if (ShopValidate.checkempty(productId)) {
                if (ShopValidate.checklenght(productId, 5, 5)) {
                    if (productId.startsWith("P")){
                        boolean checkexist = true;
                        for (Product pro : productList) {
                            if (pro.getPruductId().equals(productId)) {
                                checkexist = false;
                            }
                        }
                        if (checkexist) {
                            productNew.setPruductId(productId);
                            break;
                        } else {
                            System.err.println(ShopMessage.PRODUCTID_MESSAGE_EXIST);
                        }
                    } else {
                        System.err.println("Mã danh mục phải bắt đầu bằng ký tự P");
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
            if (ShopValidate.checkempty(productName)) {
                if (ShopValidate.checklenght(productName, 6, 30)) {
                    boolean checkNameExist = true;
                    for (Product pro : productList) {
                        if (pro.getProductName().equals(productName)) {
                            checkNameExist = false;
                        }
                    }
                    if (checkNameExist) {
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
            float price = Float.parseFloat(strPrice);
            if (ShopValidate.checkempty(strPrice)) {
                if (ShopValidate.checkfloat(strPrice)) {
                    if (price > 0) {
                        productNew.setPrice(price);
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
            if (ShopValidate.checkempty(strDiscount)) {
                if (ShopValidate.checkfloat(strDiscount)) {
                    if (discount >= 0 && discount <= 100) {
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
            if (ShopValidate.checkempty(title)) {
                if (ShopValidate.checklenght(title, 10, 50)) {
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
            if (ShopValidate.checkempty(content)) {
                productNew.setContent(content);
                break;
            } else {
                System.err.println(ShopMessage.PRODUCTCONTENT_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Vui lòng chọn các màu sắc của sản phẩm");
        do {
            ColorImp colorImp = new ColorImp();
            List<Color> colorList = colorImp.readFromfile();
            if (colorList==null) {
                colorList = new ArrayList<>();
            }
            for (Color color : colorList) {
                System.out.printf("%d. %s\n", color.getColorId(), color.getColorName());
            }
            System.out.println("chọn màu sắc");
            int choice = 0;
            do {
                String strchoice = sc.nextLine();
                choice = Integer.parseInt(strchoice);
                if (ShopValidate.checkempty(strchoice)) {
                    if (ShopValidate.checkInteger(strchoice)) {
                        break;
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Danh sác màu sắc không được để trống, vui lòng lựa chọn màu sắc");
                }
            } while (true);
            if (choice > 0 && choice <= colorList.size()) {

                if (colorList1==null){
                    colorList1 = new ArrayList<>();
                }
                boolean checkColorExist = false;
                for (Color colorExist : colorList1) {
                    if (colorExist.getColorId() == colorList.get(choice - 1).getColorId()) {
                        checkColorExist = true;
                    }
                }
                if (!checkColorExist) {
                    colorList1.add(colorList.get(choice - 1));
                } else {
                    System.err.println("Màu sắc đã tồn tại trọng danh sách màu sắc");
                }
                System.out.println("Bạn có muốn chọn thêm màu sắc không: ");
                System.out.println("1. Có");
                System.out.println("2. Không");
                System.out.print("Lựa chọn của bạn là: ");
                int choice2 = 0;
                do {
                    String strchoice2 = sc.nextLine();
                    choice2 = Integer.parseInt(strchoice2);
                    if (ShopValidate.checkempty(strchoice2)) {
                        if (ShopValidate.checkInteger(strchoice2)) {
                            break;
                        } else {
                            System.err.println("Vui lòng nhập vào 1 số nguyên");
                        }
                    } else {
                        System.err.println("Không được để trống vui lòng lựa chọn 1 hoặc 2");
                    }
                } while (true);
                if (choice2 != 1) {
                    break;
                }
            } else {
                System.err.println("Vui lòng chọn màu sắc có trong danh sách");
            }
        } while (true);
        System.out.println("Chọn danh sách các kích cỡ");
        do {
            SizeImp sizeImp = new SizeImp();
            List<Size> sizeList = sizeImp.readFromfile();
            if (sizeImp==null){
                sizeList = new ArrayList<>();
            }
            for (Size size : sizeList) {
                System.out.printf("%d. %s\n", size.getSizeId(), size.getSizeName());
            }
            System.out.println("lựa chọn kích cỡ");
            int choice3;
            do {
                String strchoice3 = sc.nextLine();
                if (ShopValidate.checkempty(strchoice3)) {
                    if (ShopValidate.checkInteger(strchoice3)) {
                        choice3 = Integer.parseInt(strchoice3);
                        break;
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không được để trống");
                }
            } while (true);
            if (choice3 > 0 && choice3 <= sizeList.size()) {

                boolean check = false;
                for (Size sizeExist :sizeList1 ) {
                    if (sizeExist.getSizeId() == sizeList.get(choice3 - 1).getSizeId()) {
                        check = true;
                    }
                }
                if (!check) {
                    sizeList1.add(sizeList.get(choice3 - 1));
                } else {
                    System.err.println("Kich cỡ đã tồn tại");
                }
                System.out.println("Bạn muốn chọn thêm kích cỡ không: ");
                System.out.println("1. Có");
                System.out.println("2. Không");
                System.out.print("Lựa chọn của bạn: ");
                int choiceExit;
                do {
                    String strchoice4 = sc.nextLine();
                    if (ShopValidate.checkempty(strchoice4)) {
                        if (ShopValidate.checkInteger(strchoice4)) {
                            choiceExit = Integer.parseInt(strchoice4);
                            break;
                        } else {
                            System.err.println("Vui lòng nhập vào một số nguyên");
                        }
                    } else {
                        System.err.println("Không được để trống vui lòng nhập vào 1 hoặc 2");
                    }
                } while (true);
                if (choiceExit != 1) {
                    break;
                }
            } else {
                System.err.println("kích cỡ không tồn tại vui lòng chọn kích cỡ trong danh sách");
            }
        } while (true);
        CatalogImp catalogImp = new CatalogImp();
        List<Catalog> catalogList = catalogImp.readFromfile();
        if (catalogList==null){
            catalogList = new ArrayList<>();
        }
        List<Catalog> listCatalogChild = new ArrayList<>();
        int cnt = 1;
        for (Catalog cat : catalogList) {
            if (checkCatalogNotChild(cat, catalogList)) {
              if (cat.isCatalogStatus()){
                  listCatalogChild.add(cat);
              }
            }
        }
        for (Catalog cat : listCatalogChild) {
            System.out.printf("%d. %s \n", cnt, cat.getCatalogName());
            cnt++;
        }
        System.out.println("chọn danh mục sản phẩm");
        int choice5;
        do {
            String strchoice5 = sc.nextLine();
            choice5 = Integer.parseInt(strchoice5);
            if (ShopValidate.checkempty(strchoice5)) {
                if (ShopValidate.checkInteger(strchoice5)) {
                    if (choice5 > 0 && choice5 <= listCatalogChild.size()) {
                        productNew.setCatalog(listCatalogChild.get(choice5 - 1));
                        break;
                    } else {
                        System.err.println("Danh mục không tồn tại vui lòng chọn danh mục khác");
                    }
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng lựa chọn danh mục");
            }
        } while (true);
        System.out.println("Nhập vào trạng thái sản phẩm");
        System.out.println("1. Hoạt động");
        System.out.println("2. Không hoạt động");
        System.out.println("Lựa chọn của bạn là");
        int choice = 0;
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    choice = Integer.parseInt(str);
                    if (choice == 1) {
                        productNew.setProductStatus(true);
                        break;
                    } else if (choice == 2) {
                        productNew.setProductStatus(false);
                        break;
                    } else {
                        System.err.println("Vui lòng chọn 1 hoặc 2");
                    }
                } else {
                    System.err.println("Vui lỏng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng lựa chọn");
            }
        } while (true);
        productNew.setExportPrice(calExportPrice(productNew.getPrice(),productNew.getDiscount()));
        Date date = new Date();
        productNew.setDate(date);
        return productNew;
    }

    @Override
    public void displayData(Product product) {
        String status = "Hết hàng";
        if (product.isProductStatus()) {
            status = "Đang bán";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(product.getDate());
       System.out.printf("%-10s - %-20s - %-10.2f - %-10f - %-30s\n",product.getPruductId(),product.getProductName(),product.getPrice(),product.getDiscount(),product.getTitle());
       System.out.printf("%-20s - %-30s - %-30s\n",product.getContent(),displayListColor(product),displayListSize(product));
       System.out.printf("%-15s - %-15s - %-15s\n",product.getCatalog(),strDate,status);
    }

    @Override
    public boolean deleteProduct(String str) {
        List<Product> productList = readFromfile();
        if (productList==null){
            productList = new ArrayList<>();
        }
        boolean returnData = false;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getPruductId().equals(str)) {
                productList.get(i).setProductStatus(false);
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

    @Override
    public float calExportPrice(float price,float discount) {
        float exportPrice = price*(100-discount)/100;
        return exportPrice;
    }

    public static boolean checkCatalogNotChild(Catalog child, List<Catalog> list) {
        boolean check = true;
        for (Catalog cat : list) {
            if (cat.getCatalog()!=null&&child.getCatalogId() == cat.getCatalog().getCatalogId()) {
                check = false;
                break;
            }
        } if (check){
            return true;
        } else {
            return false;
        }
    }
    public static String displayListColor(Product pro){
        String str = "";
        for (Color color:pro.getProductColorList()) {
             str += color+", ";
        }
        return str;
    }
    public static String displayListSize(Product pro){
        String str = "";
        for (Size size:pro.getProductSizeList()) {
            str += size+" - ";
        }
        return str;
    }

}
