package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.User;
import ra.bussiness.imple.*;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
            System.out.println("                                     ****************************** CHÀO MỪNG ĐẾN VỚI TORANO SHOP ***************************************");
            System.out.println("                                     ========================++++++ THƯƠNG HIỆU THỜI TRANG CAO CẤP ++++++================================");
            System.out.println("                                +----------------------------------------------------------------------------------------------------------+\n" +
                    "                                |            ****************************       TORANO SHOP       ****************************             |\n" +
                    "                                |----------------------------------------------------------------------------------------------------------|\n" +
                    "                                |         1.ĐĂNG NHÂP.     |                2. ĐĂNG KÝ.                  |        3. THOÁT.                |\n" +
                    "                                +----------------------------------------------------------------------------------------------------------+");

            System.out.print("                                                                           lỰA CHỌN CỦA BẠN: ");
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
                                System.err.println("                                                      VUI LÒNG CHỌN TỪ 1 - 3");
                        }
                        break;
                    } else {
                        System.err.println(ShopMessage.CHECK_INTERGER);
                    }
                } else {
                    System.err.println(ShopMessage.CHECK_EMPTY);
                }
            } while (true);
        } while (true);
    }

    public static void login(Scanner sc) {
        do {
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                             NHẬP TÊN ĐĂNG NHẬP VÀO                                    |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.print("                                                                           ");  String userName = sc.nextLine();
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                                                       NHẬP MẬT KHẨU ");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.print("                                                                            ");  String password = sc.nextLine();
            User user = userImp.checkLogin(userName, password);
            if (user != null) {
                if (user.isPermission()) {
                    adminMenu.displayAdminMenu(sc);
                    break;
                } else {
                    List<User> list = new ArrayList<>();
                    list.add(user);
                    writeToFile(list, ShopConstanst.URL_LOGIN_FILE);
                    UserDisplayMenu.displayUserMenu(sc);
                    break;
                }
            } else {
                System.out.println("                                         |---------------------------------------------------------------------------------------|");
                System.err.println("                                         |                               Tài khoản hoặc mật khẩu bị sai                          |");
                System.out.println("                                         |---------------------------------------------------------------------------------------|");
                System.out.println("                                         | 1 .  Đăng nhập lại |        2. Đăng ký tài khoản mới   |     3. Thoát                 | ");
                System.out.println("                                         |---------------------------------------------------------------------------------------|");
                System.out.println("                                         |                                  Lựa chọn của bạn:                                    |");
                System.out.println("                                         |---------------------------------------------------------------------------------------|");

                do {
                   System.out.print("                                                                    "); String str = sc.nextLine();
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
                            System.err.println("                                                                !!!!!!!VUI LÒNG NHẬP VÀO MỘT SỐ NGUYÊN!!!!!!!!!!");
                        }
                    } else {
                        System.err.println("                                                               !!! Không được để trống vui lòng chọn 1-3 !!!");
                    }
                } while (true);
            }
        } while (true);
    }

    public static void register(Scanner sc) {
        User user = userImp.inputData(sc);
        boolean result = userImp.create(user);
        if (result) {
            System.out.println("                                                           Đăng kí thành công");

            login(sc);
        } else {
            System.err.println("                                                    !!! Đã xảy ra lỗi trong quá trình đăng kí.!!!");
            register(sc);
        }
    }

    public static boolean writeToFile(List<User> list, String path) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(path);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (Exception ex1) {
            returnData = false;
            ex1.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        return returnData;
    }
}
