package ra.presentation;

import ra.config.ShopValidate;

import java.util.Scanner;

public class AdminMenu {
    private static CatalogMenu catMenu = new CatalogMenu();
    private static ColorMenu colorMenu = new ColorMenu();
    private static SizeMenu sizeMenu = new SizeMenu();
    private static UserMenu userMenu = new UserMenu();

    public void displayAdminMenu(Scanner sc) {
        boolean checkExit = true;
        do {
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |********************************* QUẢN LÝ CỬA HÀNG*************************************|");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                               1 - QUẢN LÝ DANH MỤC                                    |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                               2 - QUẢN LÝ SẢN PHẨM                                    |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                               3 - QUẢN LÝ MÀU SẮC                                     |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                               4 - QUẢN LÝ KÍCH CỠ                                     |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                               5 - QUẢN LÝ TÀI KHOẢN                                   |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                               6 - THOÁT                                               |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                              LỰA CHỌN CỦA BẠN LÀ                                      |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");

            int choice = 0;
            do {
                System.out.print("                                                                               ");String strChoice = sc.nextLine();

                if (ShopValidate.checkempty(strChoice)) {
                    if (ShopValidate.checkInteger(strChoice)) {
                         choice = Integer.parseInt(strChoice);
                        break;
                    } else {
                        System.err.println("                                                                !!!!!!!VUI LÒNG NHẬP VÀO MỘT SỐ NGUYÊN!!!!!!!!!!");
                    }
                } else {
                    System.err.println("                                                                           kHÔNG ĐƯỢC ĐỂ TRỐNG VUI LÒNG NHẬP LỰA CHỌN");
                }
            } while (true);
            switch (choice) {
                case 1:
                    catMenu.displayCatalogMenu(sc);
                    break;
                case 2:
                    ProductMenu.displayProductMenu(sc);
                    break;
                case 3:
                    colorMenu.displayColorMenu(sc);
                    break;
                case 4:
                    sizeMenu.displaySizeMenu(sc);
                    break;
                case 5:
                    userMenu.displayMenuUser(sc);
                    break;
                case 6:
                    checkExit = false;
                    break;
                default:
            }
        } while (checkExit);
    }
}
