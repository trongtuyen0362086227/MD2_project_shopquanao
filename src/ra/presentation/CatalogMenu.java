package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;
import ra.bussiness.imple.CatalogImp;
import ra.bussiness.imple.ColorImp;
import ra.bussiness.imple.ProductImp;
import ra.config.ShopMessage;
import ra.config.ShopValidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogMenu {
    private static CatalogImp catImp = new CatalogImp();

    public void displayCatalogMenu(Scanner sc) {
        boolean exit = true;
        do {
            System.out.println("**********QUẢN LÝ DANH MỤC**********");
            System.out.println("1.Hiển thị danh sách danh mục theo cây danh mục");
            System.out.println("2. Tạo mới danh mục");
            System.out.println("3. Cập nhật danh mục");
            System.out.println("4. Xóa danh mục");
            System.out.println("5. Tìm kiếm sản phẩm theo tên");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn: \n");
            int choice = 0;
            do {
                String strChoice = sc.nextLine();
                if (ShopValidate.checkempty(strChoice)) {
                    if (ShopValidate.checkInteger(strChoice)) {
                        choice = Integer.parseInt(strChoice);
                        break;
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không được để trống, vui lòng nhập lại");
                }
            } while (true);
            switch (choice) {
                case 1:
                    CatalogMenu.displayListCatalog();
                    break;
                case 2:
                    CatalogMenu.inputDataListCatalog(sc);
                    break;
                case 3:
                    CatalogMenu.updateListCatalog(sc);
                    break;
                case 4:
                    CatalogMenu.deleteCataloginlist(sc);
                    break;
                case 5:
                    CatalogMenu.searchCatalogByName(sc);
                    break;
                case 6:
                    exit = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-5");
            }
        } while (exit);
    }

    public static void displayListCatalog() {
        List<Catalog> catalogList = catImp.readFromfile();
        if (catalogList==null){
            catalogList = new ArrayList<>();
        }
        for (Catalog cat : catalogList) {
            if (cat.getCatalog() == null) {
                catImp.displayListCatalogData(cat, catalogList, 0);
            }
        }
    }

    public static void inputDataListCatalog(Scanner sc) {
        List<Catalog> catalogList = catImp.readFromfile();
        if (catalogList==null){
            catalogList = new ArrayList<>();
        }
        System.out.println("Nhập số lượng danh mục cần thêm mới");
        int number = 0;
        do {
            String strchoice = sc.nextLine();
            if (ShopValidate.checkempty(strchoice)) {
                if (ShopValidate.checkInteger(strchoice)) {
                     number = Integer.parseInt(strchoice);
                    break;
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập dữ liệu");
            }
        } while (true);
        for (int i = 0; i < number; i++) {
            System.out.println("Nhập thông tin cho danh mục thứ" + (i + 1));
            CatalogImp catalogImp = new CatalogImp();
            Catalog cat = catalogImp.inputData(sc);
            catalogList.add(cat);
            catImp.writeToFile(catalogList);
        }
    }

    public static void updateListCatalog(Scanner sc) {
        ProductImp proImp = new ProductImp();
        List<Product> productList = proImp.readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        List<Catalog> list = catImp.readFromfile();
        if (list==null){
            list = new ArrayList<>();
        }
        System.out.println("Nhập ID danh mục cần cập nhật vào");
        int catId = 0;
        do {
           String str = sc.nextLine();
           if (ShopValidate.checkempty(str)){
               if (ShopValidate.checkInteger(str)){
                   catId = Integer.parseInt(str);
                   break;
               } else {
                   System.out.println("Vui lòng nhập vào 1 số nguyên");
               }
           } else {
               System.out.println("Không được để trống nhập ID vào");
           }
        } while (true);
        for (Catalog cat:list) {
            if (cat.getCatalogId()==catId){
                System.out.println("Nhập vào tên danh mục muốn cập nhât");
                do {
                    String catalogName = sc.nextLine();
                    if (ShopValidate.checkempty(catalogName)) {
                        if (ShopValidate.checklenght(catalogName, 6, 30)) {
                            boolean check = true;
                            for (Catalog cat1 :list) {
                                if (cat1.getCatalogName().equals(catalogName)) {
                                    check = false;
                                }
                            }
                            if (check) {
                                list.get(catId).setCatalogName(catalogName);
                                break;
                            } else {
                                System.err.println(ShopMessage.CATALOGMESSAGE_EXIST);
                            }
                        } else {
                            System.err.println(ShopMessage.CATALOGMESSAGE_LENGHT);
                        }
                    } else {
                        break;
                    }
                } while (true);
                System.out.println("Nhập vào trạng thái danh mục");
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
                                list.get(catId).setCatalogStatus(true);
                                break;
                            } else if (choice == 2) {
                                list.get(catId).setCatalogStatus(false);
                                break;
                            } else {
                                System.err.println("Vui lòng chọn 1 hoặc 2");
                            }
                        } else {
                            System.err.println("Vui lỏng nhập vào 1 số nguyên");
                        }
                    } else {
                       break;
                    }
                } while (true);
                List<Catalog> listchild = new ArrayList<>();
                System.out.println("0. Danh mục gốc");
                for (Catalog cat2 : list) {
                    if (cat2.getCatalog() == null&&cat.isCatalogStatus()) {
                        boolean check = true;
                        for (Product pro: productList) {
                            if (pro.getCatalog().getCatalogId()==cat.getCatalogId()){
                                listchild.add(cat);
                                check = false;
                            }
                        }
                        if (check) {
                            CatalogImp catalogList = new CatalogImp();
                           catalogList.displayListCatalogData(cat, list, 0);
                        }
                    }
                }
                System.out.println("lựa chọn danh mục theo Id");
                int catalogId = -1;
                do {
                    String str = sc.nextLine();
                    catalogId = Integer.parseInt(str);
                    if (ShopValidate.checkempty(str)) {
                        if (ShopValidate.checkInteger(str)) {
                            boolean check = false;
                            for (Catalog cat4:listchild) {
                                if (cat4.getCatalogId()==catalogId||catalogId==0){
                                    check = true;
                                }
                            } if (check){
                                break;
                            } else {
                                System.err.println("vui lòng chọn danh mục trong số các danh mục trên");
                            }

                        } else {
                            System.err.println("Vui lòng nhập số nguyên vào");
                        }
                    } else {
                        break;
                    }
                } while (true);
                if (catalogId == 0){
                    list.get(catalogId).setCatalog(null);
                } else {
                    list.get(catalogId).setCatalog(listchild.get(catalogId));
                }
            } else {
                System.out.println("Không tìm thấy danh mục cần cập nhật");
            }
        }
    }

    public static void deleteCataloginlist(Scanner sc) {
        System.out.println("Nhập id danh mục bạn muốn xóa vào");
        int catalogId  = 0;
        do {
           String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    catalogId = Integer.parseInt(str);
                    break;
                } else {
                    System.err.println("Vui lòng nhạp vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống");
            }
        } while (true);

        boolean result =  catImp.delete(catalogId);
        if (result){
            System.out.println("Xóa thành công");
        } else {
            System.out.println("Đã xảy ra lỗi xóa thất bại");
        }
    }
    public static void searchCatalogByName(Scanner sc){
        List<Catalog> catalogList = catImp.readFromfile();
        if (catalogList==null){
            catalogList = new ArrayList<>();
        }
        System.out.println("Nhập tên danh mục muốn tìm kiêm vào");
        do {
            String catName = sc.nextLine();
            if (ShopValidate.checkempty(catName)){
                boolean check = true;
                for (Catalog cat:catalogList) {
                    if (cat.getCatalogName().equals(catName)){
                        System.out.printf("%-10s %-30s  %-20s\n","Mã danh mục","Tên danh mục","Trạng thái");
                        catImp.displayData(cat);
                        check=false;
                        break;
                    }
                } if (check){
                    System.out.println("Không tím thấy thư mục");
                    break;
                }
            } else {
                System.out.println("Không đươc để trống vui lòng nhập tên vào");
            }
        } while (true);
    }
}




