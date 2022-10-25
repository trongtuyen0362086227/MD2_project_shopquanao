package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Color;
import ra.bussiness.entity.Product;
import ra.bussiness.entity.Size;
import ra.bussiness.imple.CatalogImp;
import ra.bussiness.imple.ColorImp;
import ra.bussiness.imple.ProductImp;
import ra.bussiness.imple.SizeImp;
import ra.config.ShopMessage;
import ra.config.ShopValidate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProductMenu {
    private static ProductImp proImp = new ProductImp();

    public static void displayProductMenu(Scanner sc) {
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
                if (ShopValidate.checkempty(str)) {
                    if (ShopValidate.checkInteger(str)) {
                        choice = Integer.parseInt(str);
                        break;
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không dược để trống vui lòng nhập dữ liệu vào");
                }
            } while (true);
            switch (choice) {
                case 1:
                    ProductMenu.displayProductByCatalog();
                    break;
                case 2:
                    ProductMenu.inputListProduct(sc);
                    break;
                case 3:
                    ProductMenu.updateListProduct(sc);
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

    public static void inputListProduct(Scanner sc) {
        List<Product> productList = proImp.readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        System.out.println("Nhập số lượng sản phẩm mới thêm vào");
        int num = 0;
        do {
            String strNum = sc.nextLine();
            if (ShopValidate.checkempty(strNum)) {
                if (ShopValidate.checkInteger(strNum)) {
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
            System.out.println("Nhập dữ liệu cho sản phẩm: " + (i + 1));
            ProductImp proImp = new ProductImp();
            Product pro = proImp.inputData(sc);
            productList.add(pro);
            proImp.writeToFile(productList);
        }
    }

    public static void updateListProduct(Scanner sc) {
        Product product = new Product();
        ProductImp proImp = new ProductImp();
        List<Color> colorList1 = product.getProductColorList();
        List<Product> productList = proImp.readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        List<Size> sizeList1 = product.getProductSizeList();
        if (sizeList1==null){
            sizeList1 = new ArrayList<>();
        }
        System.out.println("Nhập Id sản phẩm bạn muốn cập nhật vào");
        String proId;
        do {
            proId = sc.nextLine();
            if (ShopValidate.checkempty(proId)) {
                break;
            } else {
                System.out.println("Không được để trống ");
            }
        } while (true);
        boolean check = true;
        int index = 0;
        for (Product pro : productList) {
            if (pro.getPruductId().equals(proId)) {
                index = productList.indexOf(pro);
                check = false;
            }
        } if (check){
            System.err.println("Không tìm thấy sản phẩm");
        } else {
            System.out.println("Nhập tên sản phẩm vào muốn cập nhật vào");
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
                            productList.get(index).setProductName(productName);
                            break;
                        } else {
                            System.err.println(ShopMessage.PRODUCTNAME_MESSAGE_EXIST);
                        }
                    } else {
                        System.err.println(ShopMessage.PRODUCTNAME_MESSAGE_LENGHT);
                    }
                } else {
                    break;
                }
            } while (true);
            System.out.println("Nhập giá sản phẩm vào");
            do {
                String strPrice = sc.nextLine();
                float price = Float.parseFloat(strPrice);
                if (ShopValidate.checkempty(strPrice)) {
                    if (ShopValidate.checkfloat(strPrice)) {
                        if (price > 0) {
                           productList.get(index).setPrice(price);
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
                            productList.get(index).setDiscount(discount);
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
            System.out.println("Nhập tiêu đề sản phẩm vào muốn cập nhật vào");
            do {
                String title = sc.nextLine();
                if (ShopValidate.checkempty(title)) {
                    if (ShopValidate.checklenght(title, 10, 50)) {
                        productList.get(index).setTitle(title);
                        break;
                    } else {
                        System.err.println(ShopMessage.PRODUCTTITLE_MESSAGE_LENGHT);
                    }
                } else {
                break;
                }
            } while (true);
            System.out.println("Nhập mô tả sản phẩm vào");
            do {
                String content = sc.nextLine();
                if (ShopValidate.checkempty(content)) {
                    productList.get(index).setContent(content);
                    break;
                } else {
                   break;
                }
            } while (true);
            System.out.println("Vui lòng chọn các màu sắc của sản phẩm");
            do {
                ColorImp colorImp = new ColorImp();
                List<Color> colorList = colorImp.readFromfile();
                if (colorList.size() == 0) {
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
                if (choice3 > 0 && choice3 < sizeList.size()) {

                    boolean check1 = false;
                    for (Size sizeExist :sizeList1 ) {
                        if (sizeExist.getSizeId() == sizeList.get(choice3 - 1).getSizeId()) {
                            check1 = true;
                        }
                    }
                    if (!check1) {
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
                if (ProductImp.checkCatalogNotChild(cat, catalogList)) {
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
                        if (choice5 > 0 && choice5 < listCatalogChild.size()) {
                            productList.get(index).setCatalog(listCatalogChild.get(choice5 - 1));
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
                            productList.get(index).setProductStatus(true);
                            break;
                        } else if (choice == 2) {
                            productList.get(index).setProductStatus(false);
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
            Date date = new Date();
            productList.get(index).setDate(date);
        }
        ProductImp productImp = new ProductImp();
        productImp.writeToFile(productList);
    }

    public static void deleteProduct(Scanner sc) {
        List<Product> productList = proImp.readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        System.out.println("Nhập mã sản phẩm bạn muốn xóa vào");
        String proId = "";
        do {
            proId = sc.nextLine();
            if (ShopValidate.checkempty(proId)) {
                break;
            } else {
                System.err.println("Không được để trống");
            }
        } while (true);
        boolean result = proImp.deleteProduct(proId);
        if (result) {
            System.out.println("Xóa sản phẩm thành công");
        } else {
            System.out.println("Xóa sản phẩm thất bại");
        }

    }

    public static void displayListCatalogAndProduct(Catalog root, List<Catalog> list, int cnt) {
        ProductImp proImp = new ProductImp();
        List<Product> productList = proImp.readFromfile();
        if (productList==null){
            productList = new ArrayList<>();
        }
        for (int i = 0; i < cnt; i++) {
            System.out.print("\t");
        }
        CatalogImp catalogImp = new CatalogImp();
        String status = "Không hoạt động";
        if (root.isCatalogStatus()) {
            status = "Hoạt động";
        }
        System.out.printf("%d. %s - %s\n", root.getCatalogId(), root.getCatalogName(), status);
        List<Catalog> listchild = new ArrayList<>();
        for (Catalog cat : list) {
            if (cat.getCatalog() != null && cat.getCatalog().getCatalogId() == root.getCatalogId()) {
                listchild.add(cat);
            }
        }
        if (listchild.size() != 0) {
            cnt++;
        }
        for (Catalog cat : listchild) {
            displayListCatalogAndProduct(cat, list, cnt);
            for (Product pro:productList) {
                if (pro.getCatalog().getCatalogId()==cat.getCatalogId()&&ProductImp.checkCatalogNotChild(cat,list)){
                    proImp.displayData(pro);
                }
            }
        }
    }
    public static void displayProductByCatalog(){
        CatalogImp catImp = new CatalogImp();
        List<Catalog> catalogList = catImp.readFromfile();
        if (catalogList == null){
            catalogList = new ArrayList<>();
        }
        for (Catalog cat:catalogList) {
            if (cat.getCatalog()==null){
                displayListCatalogAndProduct(cat,catalogList,0);
            }
        }
    }
}
