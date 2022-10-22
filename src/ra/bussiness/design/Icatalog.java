package ra.bussiness.design;

import java.util.Scanner;

public interface Icatalog<T, E> extends IShop<T,E>{
    T searchByname(Scanner sc);
}
