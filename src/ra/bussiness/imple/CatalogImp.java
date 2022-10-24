package ra.bussiness.imple;

import ra.bussiness.design.Icatalog;
import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogImp implements Icatalog<Catalog, Integer> {
    @Override
    public boolean create(Catalog catalog) {
        List<Catalog> catalogList = readFromfile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        catalogList.add(catalog);
        boolean result = writeToFile(catalogList);
        return result;
    }

    @Override
    public boolean update(Catalog catalog) {
        List<Catalog> catalogList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < catalogList.size(); i++) {
            if (catalogList.get(i).getCatalogId() == catalog.getCatalogId()) {
                catalogList.set(i, catalog);
                returnData = true;
                break;
            }
        }
        return returnData;
    }

    @Override
    public boolean delete(Integer integer) {
        List<Catalog> catalogList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < catalogList.size(); i++) {
            if (catalogList.get(i).getCatalogId() == integer) {
                catalogList.get(i).setCatalogStatus(!catalogList.get(i).isCatalogStatus());
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(catalogList);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<Catalog> readFromfile() {
        FileImp fileImp = new FileImp();
        return fileImp.readFromFile(ShopConstanst.URL_CATALOG_FILE);
    }

    @Override
    public boolean writeToFile(List<Catalog> list) {
        FileImp fileImp = new FileImp();
        return fileImp.writeToFile(list, ShopConstanst.URL_CATALOG_FILE);
    }

    @Override
    public Catalog inputData(Scanner sc) {
        List<Catalog> catalogList = readFromfile();
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        Catalog catalogNew = new Catalog();
        if (catalogList.size() == 0) {
            catalogNew.setCatalogId(1);
        } else {
            int max = 0;
            for (Catalog cat : catalogList) {
                if (max < cat.getCatalogId()) {
                    max = cat.getCatalogId();
                }
            }
            catalogNew.setCatalogId(max + 1);
        }
        System.out.println("Nhập vào tên danh mục");
        do {
            String catalogName = sc.nextLine();
            if (ShopValidate.checkempty(catalogName)) {
                if (ShopValidate.checklenght(catalogName, 6, 30)) {
                    boolean check = true;
                    for (Catalog cat : catalogList) {
                        if (cat.getCatalogName().equals(catalogName)) {
                            check = false;
                        }
                    }
                    if (check) {
                        catalogNew.setCatalogName(catalogName);
                        break;
                    } else {
                        System.err.println(ShopMessage.CATALOGMESSAGE_EXIST);
                    }
                } else {
                    System.err.println(ShopMessage.CATALOGMESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.CATALOGMESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nhập vào trạng thái danh mục");
        System.out.println("1. Hoạt động");
        System.out.println("2. Không hoạt động");
        System.out.println("Lựa chọn của bạn là");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                catalogNew.setCatalogStatus(true);
            } else if (choice == 2) {
                catalogNew.setCatalogStatus(false);
            } else {
                System.err.println("Vui lòng chọn 1 hoặc 2");
            }
        } catch (NumberFormatException ex1) {
            System.err.println("Vui lòng nhập vào một số nguyên");
        }
        ProductImp proImp = new ProductImp();
        System.out.println("0. Danh mục gốc");
        List<Catalog> catalogListonl = null;
        for (Catalog cat : catalogList) {
            if (cat.getCatalog() == null && cat.isCatalogStatus()) {
                for (Product pro:proImp.readFromfile()) {
                    if (pro.getCatalog().getCatalogId()!=cat.getCatalogId()){
                        displayListCatalogData(cat, catalogList, 0);
                        catalogListonl.add(cat);
                    }
                }

            }
        }
        System.out.println("lựa chọn danh mục theo Id");
        do {
            if (ShopValidate.checkInteger(sc.nextLine())) {
                int choice2 = Integer.parseInt(sc.nextLine());
                if (choice2 == 0) {
                    catalogNew.setCatalog(null);
                    break;
                } else {
                    for (Catalog cat : catalogListonl) {
                        if (cat.getCatalogId() == choice2) {
                            catalogNew.setCatalog(catalogList.get(choice2 - 1));
                            break;
                        } else {
                            System.err.println(ShopMessage.CATALOGMESSAGE_DISPLAY);
                        }
                    }
                }
            }
        } while (true);
        return catalogNew;
    }

    @Override
    public void displayData(Catalog catalog) {
        String status = "Không hoạt động";
        if (catalog.isCatalogStatus()) {
            status = "Hoạt động";
        }
        System.out.printf("%-10d. %-30s - %-20s\n", catalog.getCatalogId(), catalog.getCatalogName(), status);
    }

    @Override
    public boolean searchByID(int catalogId) {
        List<Catalog> list = readFromfile();
        boolean check = false;
                    for (Catalog cat : list) {
                        if (cat.getCatalogId() == catalogId) {
                            check = true;
                            break;
                        }
                    }
        return check;
    }

    public void displayListCatalogData(Catalog root, List<Catalog> list, int cnt) {
        for (int i = 0; i < cnt; i++) {
            System.out.println("\t");
        }
        CatalogImp catalogImp = new CatalogImp();
        catalogImp.displayData(root);
        List<Catalog> listchild = new ArrayList<>();
        for (Catalog cat : list) {
            if (cat.getCatalog() != null && cat.getCatalogId() == root.getCatalogId()) {
                listchild.add(cat);
            }
        }
        if (listchild.size() != 0) {
            cnt++;
        }
        for (Catalog cat : listchild) {
            displayListCatalogData(cat, list, cnt);
        }
    }
}
