package ra.bussiness.design;

public interface IProduct<T,E> extends IShop<T,E> {
    boolean deleteProduct(String str);
}
