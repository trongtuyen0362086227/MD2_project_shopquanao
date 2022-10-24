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
            System.out.println("********** QUẢN LÝ CỬA HÀNG***********");
            System.out.println("1 - QUẢN LÝ DANH MỤC");
            System.out.println("2 - QUẢN LÝ SẢN PHẨM");
            System.out.println("3 - QUẢN LÝ MÀU SẮC");
            System.out.println("4 - QUẢN LÝ KÍCH CỠ");
            System.out.println("5 - QUẢN LÝ TÀI KHOẢN");
            System.out.println("Lựa chọn của bạn là");
            do {
                String strChoice = sc.nextLine();
                if (ShopValidate.checkempty(strChoice)) {
                    if (ShopValidate.checkInteger(strChoice)) {
                        int choice = Integer.parseInt(strChoice);
                        switch (choice) {
                            case 1:
                                catMenu.displayCatalogMenu(sc);
                                break;
                            case 2:
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
                            default:
                        }
                        break;
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không được để trống Vui lòng nhập lựa chọn");
                }
            } while (true);
        } while (checkExit);
    }
}
