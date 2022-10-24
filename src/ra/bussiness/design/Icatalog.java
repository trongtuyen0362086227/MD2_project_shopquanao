package ra.bussiness.design;

import ra.bussiness.entity.Catalog;

import java.util.Scanner;

public interface Icatalog<T, E> extends IShop<T,E>{
    boolean searchByID(int id);
}
