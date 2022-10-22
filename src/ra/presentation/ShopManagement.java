package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.imple.*;
import ra.config.ShopValidate;

import java.util.Scanner;

public class ShopManagement {
    private static CatalogImp catImp = new CatalogImp();
    private static UserImp userImp = new UserImp();
    private static ProductImp proImp = new ProductImp();
    private static ColorImp colorImp = new ColorImp();
    private static SizeImp sizeImp = new SizeImp();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("************** - SHOP QUẦN ÁO TORANO - *************");
            System.out.println("1. ĐĂNG NHẬP");
            System.out.println("2. ĐĂNG KÝ");
            System.out.println("3. THOÁT");
            System.out.print("Su lua chon cua ban: ");
            int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.err.println("Vui lòng chọn từ 1-3");
                }
        } while (true);
    }
}
