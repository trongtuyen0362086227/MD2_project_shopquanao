package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;
import ra.bussiness.imple.CatalogImp;
import ra.bussiness.imple.ColorImp;
import ra.config.ShopMessage;
import ra.config.ShopValidate;

import java.util.List;
import java.util.Scanner;

public class CatalogMenu {
    private static CatalogImp catImp = new CatalogImp();

    public static void displayCatalogMenu(Scanner sc) {
        do {
            System.out.println("**********QUẢN LÝ DANH MỤC**********");
            System.out.println("1.Hiển thị danh sách danh mục theo cây danh mục");
            System.out.println("2. Tạo mới danh mục");
            System.out.println("3. Cập nhật danh mục");
            System.out.println("4. Xóa danh mục");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn: \n");
            boolean exit = true;
            do {
                String strChoice = sc.nextLine();
                if (ShopValidate.checkempty(strChoice)) {
                    if (ShopValidate.checkInteger(strChoice)) {
                        int choice = Integer.parseInt(strChoice);
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
                                exit = false;
                                break;
                            default:
                                System.err.println("Vui lòng chọn từ 1-5");
                        }
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không được để trống, vui lòng nhập lại");
                }
            } while (true);
        } while (true);
    }

    public static void displayListCatalog() {
        List<Catalog> catalogList = catImp.readFromfile();
        for (Catalog cat : catalogList) {
            if (cat.getCatalog() == null) {
                catImp.displayListCatalogData(cat, catalogList, 0);
            }
        }
    }

    public static void inputDataListCatalog(Scanner sc) {
        List<Catalog> catalogList = catImp.readFromfile();
        System.out.println("Nhập số lượng danh mục cần thêm mới");
        do {
            String strchoice = sc.nextLine();
            if (ShopValidate.checkempty(strchoice)) {
                if (ShopValidate.checkInteger(strchoice)) {
                    int number = Integer.parseInt(strchoice);
                    for (int i = 0; i < number; i++) {
                        System.out.println("Nhập thông tin cho danh mục thứ" + (i + 1));
                        CatalogImp catalogImp = new CatalogImp();
                        Catalog cat = catalogImp.inputData(sc);
                        catalogList.add(cat);
                        catalogImp.create(cat);
                    }
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập dữ liệu");
            }
        } while (true);
    }

    public static void updateListCatalog(Scanner sc) {
        List<Catalog> list = catImp.readFromfile();
        System.out.println("Nhập ID danh mục cần cập nhật vào");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    int catalogId = Integer.parseInt(str);
                    if (catImp.searchByID(catalogId)) {
                        System.out.println("Nhập vào tên danh mục muốn cập nhật");
                        do {
                            String catalogName = sc.nextLine();
                            if (ShopValidate.checkempty(catalogName)) {
                                if (ShopValidate.checklenght(catalogName, 6, 30)) {
                                    boolean check = true;
                                    for (Catalog cat : list) {
                                        if (cat.getCatalogName().equals(catalogName)) {
                                            check = false;
                                        }
                                    }
                                    if (check) {
                                        list.get(catalogId).setCatalogName(catalogName);
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
                        System.out.println("Bạn co muốn cập nhật trạng thái danh mục");
                        System.out.println("1. Có");
                        System.out.println("2. Không ");
                        System.out.println("Lựa chọn của bạn là");
                        try {
                            int choice = Integer.parseInt(sc.nextLine());
                            if (choice == 1) {
                                list.get(catalogId).setCatalogStatus(!list.get(catalogId).isCatalogStatus());
                            } else {
                                break;
                            }
                        } catch (NumberFormatException ex1) {
                            System.err.println("Vui lòng nhập vào một số nguyên");
                        }
                        System.out.println("0. Danh mục gốc");
                        List<Catalog> catalogListonl = null;
                        for (Catalog cat : list) {
                            if (cat.getCatalog() == null && cat.isCatalogStatus()) {
                                catImp.displayListCatalogData(cat, list, 0);
                                catalogListonl.add(cat);
                            }
                        }
                        System.out.println("lựa chọn danh mục theo Id");
                        do {
                            if (ShopValidate.checkInteger(sc.nextLine())) {
                                int choice2 = Integer.parseInt(sc.nextLine());
                                if (choice2 == 0) {
                                    list.get(catalogId).setCatalog(null);
                                    break;
                                } else {
                                    for (Catalog cat : catalogListonl) {
                                        if (cat.getCatalogId() == choice2) {
                                            list.get(catalogId).setCatalog(list.get(choice2 - 1));
                                            break;
                                        } else {
                                            System.err.println(ShopMessage.CATALOGMESSAGE_DISPLAY);
                                        }
                                    }
                                }
                            }
                        } while (true);
                    }
                } else {
                    System.out.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.out.println("Không được để trống vui lòng nhập ID vào");
            }
        } while (true);
    }

    public static void deleteCataloginlist(Scanner sc) {
        System.out.println("Nhập id danh mục bạn muốn xóa vào");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    catImp.delete(Integer.parseInt(str));
                } else {
                    System.err.println("Không được để trống");
                }
            } else {
                System.err.println("Vui lòng nhạp vào 1 số nguyên");
            }
        } while (true);
    }
}




