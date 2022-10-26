package ra.bussiness.imple;

import ra.bussiness.design.Icatalog;
import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
        boolean returnData = false;
        for (int i = 0; i < catalogList.size(); i++) {
            if (catalogList.get(i).getCatalogId() == integer) {
                catalogList.get(i).setCatalogStatus(false);
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
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<Catalog> list = null;
        try {
            file = new File(ShopConstanst.URL_CATALOG_FILE);
            if (file.exists()) {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                list = (List<Catalog>) ois.readObject();
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

    @Override
    public boolean writeToFile(List<Catalog> list) {
        FileImp fileImp = new FileImp();
        return fileImp.writeToFile(list, ShopConstanst.URL_CATALOG_FILE);
    }

    @Override
    public Catalog inputData(Scanner sc) {
        ProductImp proImp = new ProductImp();
        List<Product> productList = proImp.readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
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
                System.err.println(ShopMessage.CHECK_EMPTY);
            }
        } while (true);
        System.out.println("                                         |---------------------------------------------------------------------------------------|");
        System.out.println("                                         |                              Nhập vào trạng thái danh mục                             |");
        System.out.println("                                         |---------------------------------------------------------------------------------------|");
        System.out.println("                                         |            1. Hoạt động                |       2. Không hoạt động                     |");
        System.out.println("                                         |---------------------------------------------------------------------------------------|");
        System.out.println("                                         |                                 Lựa chọn của bạn là                                   |");
        System.out.println("                                         |---------------------------------------------------------------------------------------|");

        int choice = 0;
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    choice = Integer.parseInt(str);
                    if (choice == 1) {
                        catalogNew.setCatalogStatus(true);
                        break;
                    } else if (choice == 2) {
                        catalogNew.setCatalogStatus(false);
                        break;
                    } else {
                        System.err.println("                                                Vui lòng chọn 1 hoặc 2");
                    }
                } else {
                    System.err.println(ShopMessage.CHECK_INTERGER);
                }
            } else {
                System.err.println(ShopMessage.CHECK_EMPTY);
            }
        } while (true);
        List<Catalog> listchild = new ArrayList<>();
        int cnt = 1;
        System.out.println("0. Danh mục gốc");
        for (Catalog cat : catalogList) {
            if (cat.isCatalogStatus()) {
                boolean check = true;
                for (Product pro : productList) {
                    if (pro.getCatalog().getCatalogId() == cat.getCatalogId()) {
                        check = false;
                    }
                }
                if (check) {
                    listchild.add(cat);
                    System.out.printf("%d. %s\n", cnt, cat.getCatalogName());
                    cnt++;
                }
            }
        }
        System.out.println("lựa chọn danh mục theo Id");
        int catalogId = 0;
        do {
           String str = sc.nextLine();
           catalogId =Integer.parseInt(str);
           if (ShopValidate.checkempty(str)){
               if (ShopValidate.checkInteger(str)){
                   if (catalogId==0){
                       break;
                   } else {
                       boolean check = true;
                       for (Catalog cat:listchild) {
                           if (catalogId<=listchild.size()&&catalogId>0){
                               check = false;
                           }
                       }
                       if (!check){
                           break;
                       } else {
                           System.err.println("Vui lòng chọn các danh mục ở trên");
                       }
                   }
               }else {
                   System.err.println(ShopMessage.CHECK_INTERGER);
               }
           } else {
               System.err.println(ShopMessage.CHECK_EMPTY);
           }
        } while (true);
        if (catalogId == 0) {
            catalogNew.setCatalog(null);
        } else {
            catalogNew.setCatalog(listchild.get(catalogId - 1));
        }
        return catalogNew;
    }

    @Override
    public void displayData(Catalog catalog) {
        String status = "Không hoạt động";
        if (catalog.isCatalogStatus()) {
            status = "Hoạt động";
        }
        System.out.printf("%-10d %-30s  %-20s\n", catalog.getCatalogId(), catalog.getCatalogName(), status);
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
            System.out.print("\t");
        }
        CatalogImp catalogImp = new CatalogImp();
        String status = "Không hoạt động";
        if (root.isCatalogStatus()) {
            status = "Hoạt động";
        }
        System.out.printf("%d. %s - %s\n", root.getCatalogId(), root.getCatalogName(), status);
        List<Catalog> listchild = new ArrayList<>();
        for (Catalog cat : list) {
            if (cat.getCatalog() != null && cat.getCatalog().getCatalogId() == root.getCatalogId()) {
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

    public List<Catalog> searchCatalogChild(Catalog root) {
        List<Catalog> catalogList = readFromfile();
        if (catalogList.size() == 0) {
            catalogList = new ArrayList<>();
        }
        List<Catalog> listChild = new ArrayList<>();
        for (Catalog cat : catalogList) {
            if (cat.getCatalog().getCatalogId() == root.getCatalogId()) {
                listChild.add(cat);
                searchCatalogChild(cat);
            }
        }
        return listChild;
    }
}
