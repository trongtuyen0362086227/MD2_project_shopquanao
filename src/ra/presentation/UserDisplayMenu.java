package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;
import ra.bussiness.entity.User;
import ra.bussiness.imple.CatalogImp;
import ra.bussiness.imple.ProductImp;
import ra.bussiness.imple.UserImp;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class UserDisplayMenu {
    private static ProductImp proImp = new ProductImp();
    private static UserImp userImp = new UserImp();

    public static void displayUserMenu(Scanner sc) {
        boolean checkExist = true;
        do {
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |   ************************** KÍNH CHÀO QUÝ KHÁCH HÀNG ****************************    |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |           1.  XEM DANH SÁCH SẢN PHẨM                                                  |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |           2.  TÌM KIẾM SẢN PHẨM                                                       |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |           3.  ĐỔI MẬT KHẨU                                                            |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |           4.  THOÁT                                                                   |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                                 Lựa chọn của bạn là                                   |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");

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
                    UserDisplayMenu.changePassword(sc);
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
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |     ************************** XEM DANH SÁCH SẢN PHẨM **************************      |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        1. Xem danh sách sản phẩm theo cây thư mục                                     |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        2. Xem danh sách sản phẩm mới                                                  |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        3. Xem danh sách sản phẩm giảm giá                                             |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        4. Thoát                                                                       |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                                Lựa chọn của bạn là                                    |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");

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
            switch (choice) {
                case 1:
                    ProductMenu.displayProductByCatalog();
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
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |            *********************       TÌM KIẾM SẢN PHẨM      ************************|");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |         1. Tìm kiếm theo tên sản phẩm                                                 |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |         2. Tìm kiếm sản phẩm theo tên danh mục sản phẩm                               |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |         3. Tìm kiếm sản phẩm theo khoảng giá bán sản phẩm                             |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |         4. Tìm kiếm sản phẩm theo khoảng giảm giá của sản phẩm                        |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |         5. Thoát                                                                      |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                               Lựa chọn của bạn là                                     |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");

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
                    UserDisplayMenu.searchPruductByCatalog(sc);
                    break;
                case 3:
                    UserDisplayMenu.searchPruductByPrice(sc);
                    break;
                case 4:
                    UserDisplayMenu.searchPruductDiscount(sc);
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
        List<User> userList = userImp.readFromfile();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        int index = getUserId();
        System.out.println("Nhập mật khẩu cũ");
        String password = sc.nextLine();
        if (userList.get(index - 1).getPassword().equals(password)) {
            System.out.println("Nhập mật khẩu mới");
            String newPass = sc.nextLine();
            do {
                if (ShopValidate.checkempty(password)) {
                    if (password.trim().length() >= 6) {
                        break;
                    } else {
                        System.err.println(ShopMessage.USERPASWORDMESSAGE_LENGHT);
                    }
                } else {
                    System.err.println(ShopMessage.USERPASSWORDMESSAGE_EMPTY);
                }
            } while (true);
            System.out.println("Vui lòng nhập xác nhận mật khẩu vào");
            String confim = sc.nextLine();
            if (newPass.equals(confim)) {
                userList.get(index - 1).setPassword(newPass);
            } else {
                System.err.println("xác nhận mật khẩu sai, vui lòng thao tác lại");
            }
            boolean result = userImp.writeToFile(userList);
            if (result) {
                System.out.println(" thay đổi mật khẩu thành công");
            } else {
                System.out.println("Đã xảy ra lỗi, vui lòng thao tác lại");
            }
        }
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
        if (productList == null) {
            productList = new ArrayList<>();
        }
        List<Product> listSearch = new ArrayList<>();
        System.out.println("Nhập tên sản phẩm vào");
        do {
            String searchName = sc.nextLine();
            boolean check = true;
            if (ShopValidate.checkempty(searchName)) {
                for (Product pro : productList) {
                    if (pro.getProductName().toLowerCase().contains(searchName.toLowerCase())) {
                        listSearch.add(pro);
                        check = false;
                    }
                }
                if (!check) {
                    for (Product pro : listSearch) {
                        proImp.displayData(pro);
                        break;
                    }
                } else {
                    System.err.println("Không tìm thấy sản phẩm bạn muốn tìm");
                    break;
                }
            }
        } while (true);

    }

    public static void searchPruductByCatalog(Scanner sc) {
        CatalogImp catImp = new CatalogImp();
        List<Catalog> catalogList = catImp.readFromfile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        List<Catalog> listChild = new ArrayList<>();
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
        boolean check = true;
        for (Catalog cat : catalogList) {
            if (cat.getCatalogName().toLowerCase().contains(catalogName.toLowerCase())) {
                listChild.add(cat);
                check = false;
            }
        }
        if (check) {
            System.err.println("Không tìm thấy tên thư mục");
        } else {
            for (Catalog cat : listChild) {
                ProductMenu.displayListCatalogAndProduct(cat, catalogList, 0);
            }
        }
    }

    public static void searchPruductByPrice(Scanner sc) {
        List<Product> productList = proImp.readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        System.out.println("Nhập giá Thấp nhất bạn muốn tìm kiếm");
        float minPrice = 0;
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkfloat(str)) {
                    minPrice = Float.parseFloat(str);
                    break;
                } else {
                    System.err.println("Vui lòng nhập 1 số thục vào");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập dữ liệu vào");
            }
        } while (true);
        System.out.println("Nhập giá cao nhât bạn muốn tìm kiếm");
        float maxPrice = 0;
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkfloat(str)) {
                    maxPrice = Float.parseFloat(str);
                    break;
                } else {
                    System.err.println("Vui lòng nhập 1 số thục vào");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập dữ liệu vào");
            }
        } while (true);
        List<Product> listNew = new ArrayList<>();
        boolean check = true;

        for (Product pro : productList) {
            proImp.calExportPrice(pro);
            if (pro.getExportPrice() >= minPrice && pro.getExportPrice() <= maxPrice) {
                listNew.add(pro);
                check = false;
            }
        }
        if (check) {
            for (Product pro : listNew) {
                proImp.displayData(pro);
            }
        } else {
            System.out.println("Không tìm thấy sản phẩm trong khoảng giá này");
        }
    }

    public static void searchPruductDiscount(Scanner sc) {
        List<Product> productList = proImp.readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        System.out.println("Nhập mã giảm giá Thấp nhất bạn muốn tìm kiếm");
        float minDiscount = 0;
        do {
            String str = sc.nextLine();
            minDiscount = Float.parseFloat(str);
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkfloat(str)) {
                    break;
                } else {
                    System.err.println("Vui lòng nhập 1 số thực vào");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập dữ liệu vào");
            }
        } while (true);
        System.out.println("Nhập mã giảm giá cao nhất bạn muốn tìm kiếm");
        float maxDiscount = 0;
        do {
            String str = sc.nextLine();
            maxDiscount = Integer.parseInt(str);
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkfloat(str)) {
                    break;
                } else {
                    System.err.println("Vui lòng nhập 1 số thực vào");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập dữ liệu vào");
            }
        } while (true);
        List<Product> listNew = new ArrayList<>();
        for (Product pro : productList) {
            if (pro.getDiscount() <= minDiscount && pro.getDiscount() <= maxDiscount) {
                listNew.add(pro);
            }
        }
        for (Product pro : listNew) {
            proImp.displayData(pro);
        }
    }

    public static int getUserId() {
        List<User> list = readFromFilelogin(ShopConstanst.URL_LOGIN_FILE);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list.get(0).getUserId();
    }

    public static List<User> readFromFilelogin(String path) {
        List<User> List = new ArrayList<>();
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<User> list = null;
        try {
            file = new File(path);
            if (file.exists()) {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                list = (List<User>) ois.readObject();
            }

        } catch (Exception ex1) {
            ex1.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        return list;
    }
}