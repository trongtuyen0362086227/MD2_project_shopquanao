package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.User;
import ra.bussiness.imple.*;
import ra.config.ShopValidate;

import java.util.Date;
import java.util.Scanner;

public class ShopManagement {
    private static CatalogImp catImp = new CatalogImp();
    private static UserImp userImp = new UserImp();
    private static ProductImp proImp = new ProductImp();
    private static ColorImp colorImp = new ColorImp();
    private static SizeImp sizeImp = new SizeImp();
    private static AdminMenu adminMenu = new AdminMenu();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("****************TORANO SHOP***************");
            System.out.println("1. ĐĂNG NHẬP");
            System.out.println("2. ĐĂNG KÝ");
            System.out.println("3. THOÁT");
            System.out.print("Su lua chon cua ban: \n");
            do {
                String strchoice = sc.nextLine();
                if (ShopValidate.checkempty(strchoice)) {
                    if (ShopValidate.checkInteger(strchoice)) {
                        int choice = Integer.parseInt(strchoice);
                        switch (choice) {
                            case 1:
                                ShopManagement.login(sc);
                                break;
                            case 2:
                                ShopManagement.register(sc);
                                break;
                            case 3:
                                System.exit(0);
                                break;
                            default:
                                System.err.println("Vui lòng chọn 1-3");
                        }
                        break;
                    } else {
                        System.err.println("vui lòng nhập 1 số nguyên");
                    }
                } else {
                    System.err.println("không được để trống, vui lòng chọn từ 1-3");
                }
            } while (true);
        } while (true);
    }

    public static void login(Scanner sc) {
        do {
            System.out.println("Tên đăng nhập");
            String userNaame = sc.nextLine();
            System.out.println("Nhập mật khẩu");
            String password = sc.nextLine();
            User user = userImp.checkLogin(userNaame, password);
            if (user != null) {
                if (user.isPermission()) {
                    adminMenu.displayAdminMenu(sc);
                    break;
                } else {
                    UserDisplayMenu.displayUserMenu(sc);
                    break;
                }
            } else {
                System.err.println("Tài khoản hoặc mật khẩu bị sai");
                System.out.println("1. Đăng nhập lại");
                System.out.println("2. Đăng ký tài khoản mới");
                System.out.println("3. Thoát");
                System.out.print("Lựa chọn của bạn: \n");
                do {
                    String str = sc.nextLine();
                    if (ShopValidate.checkempty(str)) {
                        if (ShopValidate.checkInteger(str)) {
                            int choice = Integer.parseInt(str);
                            if (choice == 2) {
                                register(sc);
                                break;
                            } else if (choice == 3) {
                                System.exit(0);
                            } else {
                                break;
                            }
                        } else {
                            System.err.println("Vui lòng nhập vào 1 số nguyên");
                        }
                    } else {
                        System.err.println("Không được để trống vui lòng chọn 1-3");
                    }
                } while (true);
            }
        } while (true);
    }

    public static void register(Scanner sc) {
        User user = userImp.inputData(sc);
        boolean result = userImp.create(user);
        if (result) {
            System.out.println("Đăng kí thành công");

            login(sc);
        } else {
            System.out.println("Đã xảy ra lỗi trong quá trình đăng kí.");
            register(sc);
        }
    }
}
