package ra.bussiness.design;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public interface IUser extends IShop{
    void displayProductbyCatalog(List<Catalog> list);
    void displayProductbyDate(List<Product> list);
    void displayProductbyDiscount(List<Product> list);
    Product searchProductbyProductName(Scanner sc);
    Product searchProductbyCatalogName(Scanner sc);
    List<Product> searchProductbyExporPrice(float min, float max);
    List<Product> searchProductbyDiscount(float min, float max);
}
