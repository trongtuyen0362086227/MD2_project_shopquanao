package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;
import ra.bussiness.entity.User;
import ra.bussiness.imple.CatalogImp;
import ra.bussiness.imple.ProductImp;
import ra.config.ShopValidate;

import java.util.*;

public class UserDisplayMenu {
    public static void displayUserMenu(Scanner sc) {
        boolean checkExist = true;
        do {
            System.out.println("********** KÍNH CHÀO QUÝ KHÁCH HÀNG **********");
            System.out.println("1 XEM DANH SÁCH SẢN PHẨM");
            System.out.println("2 TÌM KIẾM SẢN PHẨM");
            System.out.println("3 ĐỔI MẬT KHẨU");
            System.out.println("4 THOÁT");
            System.out.println("Lựa chọn của bạn là");
            int choice = 0;
            do {
                String strChoice = sc.nextLine();
                if (ShopValidate.checkInteger(strChoice)) {
                    if (ShopValidate.checkempty(strChoice)) {
                        choice = Integer.parseInt(strChoice);
                        break;
                    } else {
                        System.err.println("Không được để trống, vui lòng lựa chọn");
                    }
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } while (true);
            switch (choice) {
                case 1:
                    UserDisplayMenu.displayPruductMenu(sc);
                    break;
                case 2:
                    UserDisplayMenu.searchMenu(sc);
                    break;
                case 3:
                    break;
                case 4:
                    checkExist = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-4");
            }
        } while (checkExist);
    }

    public static void displayPruductMenu(Scanner sc) {
        boolean checkExistMenu1 = true;
        do {
            System.out.println("********* XEM DANH SÁCH SẢN PHẨM ***********");
            System.out.println("1. Xem danh sách sản phẩm theo cây thư mục");
            System.out.println("2. Xem danh sách sản phẩm mới");
            System.out.println("3. Xem danh sách sản phẩm giảm giá");
            System.out.println("4. Thoát");
            System.out.println("Lựa chọn của bạn là");
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
                    System.err.println("Không được để trống vui lòng chọn 1-4");
                }
            } while (true);
            switch (choice){
                case 1:
                    break;
                case 2:
                    UserDisplayMenu.displayProductByDate();
                    break;
                case 3:
                    UserDisplayMenu.displayProductByDiscount();
                    break;
                case 4:
                    checkExistMenu1 = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-4");
            }
        } while (checkExistMenu1);

    }

    public static void searchMenu(Scanner sc) {
        boolean checkExistMenu2 = true;
        do {
            System.out.println("*********** TÌM KIẾM SẢN PHẨM **************");
            System.out.println("1. Tìm kiếm theo tên sản phẩm");
            System.out.println("2. Tìm kiếm sản phẩm theo tên danh mục sản phẩm");
            System.out.println("3. Tìm kiếm sản phẩm theo khoảng giá bán sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm theo khoảng giảm giá của sản phẩm");
            System.out.println("5. Thoát");
            System.out.println("Lựa chọn của bạn là");
            int choice = 0;
            do {
                String strChoice = sc.nextLine();
                if (ShopValidate.checkempty(strChoice)) {
                    if (ShopValidate.checkInteger(strChoice)) {
                        choice = Integer.parseInt(strChoice);
                        break;
                    } else {
                        System.err.println("Vui lòng nhạp vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không được để trống vui lòng lựa chọn");
                }
            } while (true);
            switch (choice) {
                case 1:
                    UserDisplayMenu.searchPruductByName(sc);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    checkExistMenu2 = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-5");
            }

        } while (checkExistMenu2);
    }

    public static void changePassword(Scanner sc) {
        System.out.println("Nhập mật khẩu cũ");
        String password = sc.nextLine();
        System.out.println("Nhập mật khẩu mới");
        System.out.println("Xác nhận mật khẩu mới");

    }

    public static void displayProductByCatalog() {
        ProductImp proImp = new ProductImp();
        List<Product> productList = proImp.readFromfile();
    }

    public static void displayProductByDate() {
        ProductImp proImp = new ProductImp();
        List<Product> productList = proImp.readFromfile();
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        for (Product pro : productList) {
            proImp.displayData(pro);
        }
    }

    public static void displayProductByDiscount() {
        ProductImp proImp = new ProductImp();
        List<Product> productList = proImp.readFromfile();
        Collections.sort(productList);
        for (Product pro : productList) {
            proImp.displayData(pro);
        }
    }

    public static void searchPruductByName(Scanner sc) {
        ProductImp proImp = new ProductImp();
        List<Product> productList = proImp.readFromfile();
        List<Product> listSearch = new ArrayList<>();
        System.out.println("Nhập tên sản phẩm vào");
        do {
            String searchName = sc.nextLine();
            if (ShopValidate.checkempty(searchName)) {
                for (Product pro : productList) {
                    if (pro.getProductName().startsWith(searchName)) {
                        listSearch.add(pro);
                    }
                }
                for (Product pro : listSearch) {
                    proImp.displayData(pro);
                }
                break;
            } else {
                System.err.println("Vui lòng nhập tên sản phẩm muốn tìm kiếm vào");
            }
        } while (true);

    }

    public static void searchPruductByCatalog(Scanner sc) {
        ProductImp proImp = new ProductImp();
        List<Product> productList = proImp.readFromfile();
        CatalogImp catImp = new CatalogImp();
        List<Catalog> catalogList = catImp.readFromfile();
        System.out.println("Nhập tên thư mục bạn muốn xem sản phẩm vào");
        String catalogName = "";
        do {
            catalogName = sc.nextLine();
            if (ShopValidate.checkempty(catalogName)) {
                break;
            } else {
                System.err.println("Vui lòng nhập tên danh mục vào");
            }
        } while (true);
        for (Catalog cat : catalogList) {
            if (cat.getCatalogName().equals(catalogName)) {

            } else {
                System.err.println("Không tìm thấy tên thư mục");
            }
        }
    }
}
