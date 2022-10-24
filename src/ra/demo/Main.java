package ra.demo;

import ra.bussiness.entity.Catalog;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Catalogdemo cat1 = new Catalogdemo(1, "Quan Ao", true, null);
        Catalogdemo cat2_1 = new Catalogdemo(2, "Quan Ao Nam", true, cat1);
        Catalogdemo cat2_2 = new Catalogdemo(3, "Quan Ao Nu", true, cat1);
        Catalogdemo cat3_1 = new Catalogdemo(4, "Ao so mi", true, cat2_1);
        Catalogdemo cat3_2 = new Catalogdemo(5, "Quan Au", true, cat2_1);
        Catalogdemo cat2 = new Catalogdemo(6, "Giay dep", true, null);
        Catalogdemo cat2_3 = new Catalogdemo(7, "Giay dep nam", true, cat2);
        Catalogdemo cat2_4 = new Catalogdemo(8, "Giay dep nu", true, cat2);
        List<Catalogdemo> listCat = new ArrayList<>();
        listCat.add(cat1);
        listCat.add(cat2_1);
        listCat.add(cat2_2);
        listCat.add(cat3_1);
        listCat.add(cat3_2);
        listCat.add(cat2);
        listCat.add(cat2_3);
        listCat.add(cat2_4);
        //In danh muc theo cay
//        List<Catalogdemo> listchild = searchCatalogChild(cat1,listCat);
        for (Catalogdemo cat:listCat) {
            if (cat.getCatalogdemo().getCatalogName().equals(cat.getCatalogName())){
                System.out.println(cat.getCatalogId());
            }
        }
    }

    public static List<Catalogdemo> searchCatalogChild(Catalogdemo root, List<Catalogdemo> list) {
        List<Catalogdemo> listChild = new ArrayList<>();
        for (Catalogdemo cat : list) {
            if (cat.getCatalogdemo().getCatalogId() == root.getCatalogId()) {
                listChild.add(cat);
//                searchCatalogChildnotChild(cat,listChild);
            }
        }
        return listChild;
    }
}

