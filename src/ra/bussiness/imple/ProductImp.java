package ra.bussiness.imple;

import ra.bussiness.design.IProduct;
import ra.bussiness.design.IShop;
import ra.bussiness.entity.*;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProductImp implements IProduct<Product, Integer> {
    @Override
    public boolean create(Product product) {
        List<Product> productList = readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        productList.add(product);
        boolean result = writeToFile(productList);
        return result;
    }

    @Override
    public boolean update(Product product) {
        List<Product> productList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getPruductId() == product.getPruductId()) {
                productList.set(i, product);
                returnData = true;
                break;
            }
        }
        return returnData;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<Product> readFromfile() {
        FileImp fileImp = new FileImp();
        return fileImp.readFromFile(ShopConstanst.URL_PRODUCT_FILE);
    }

    @Override
    public boolean writeToFile(List<Product> list) {
        FileImp fileImp = new FileImp();
        return fileImp.writeToFile(list, ShopConstanst.URL_PRODUCT_FILE);
    }

    @Override
    public Product inputData(Scanner sc) {
        Product product = new Product();
        List<Size> sizeList1 = product.getProductSizeList();
        if (sizeList1==null){
            sizeList1 = new ArrayList<>();
        }
        List<Color> colorList1 = product.getProductColorList();
        List<Product> productList = readFromfile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        Product productNew = new Product();
        System.out.println("Nh???p m?? s???n ph???m v??o");
        do {
            String productId = sc.nextLine();
            if (ShopValidate.checkempty(productId)) {
                if (ShopValidate.checklenght(productId, 5, 5)) {
                    if (productId.startsWith("P")){
                        boolean checkexist = true;
                        for (Product pro : productList) {
                            if (pro.getPruductId().equals(productId)) {
                                checkexist = false;
                            }
                        }
                        if (checkexist) {
                            productNew.setPruductId(productId);
                            break;
                        } else {
                            System.err.println(ShopMessage.PRODUCTID_MESSAGE_EXIST);
                        }
                    } else {
                        System.err.println("M?? danh m???c ph???i b???t ?????u b???ng k?? t??? P");
                    }

                } else {
                    System.err.println(ShopMessage.PRODUCTID_MESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.PRODUCTID_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nh???p t??n s???n ph???m v??o");
        do {
            String productName = sc.nextLine();
            if (ShopValidate.checkempty(productName)) {
                if (ShopValidate.checklenght(productName, 6, 30)) {
                    boolean checkNameExist = true;
                    for (Product pro : productList) {
                        if (pro.getProductName().equals(productName)) {
                            checkNameExist = false;
                        }
                    }
                    if (checkNameExist) {
                        productNew.setProductName(productName);
                        break;
                    } else {
                        System.err.println(ShopMessage.PRODUCTNAME_MESSAGE_EXIST);
                    }
                } else {
                    System.err.println(ShopMessage.PRODUCTNAME_MESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.PRODUCTNAME_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nh???p gi?? s???n ph???m v??o");
        do {
            String strPrice = sc.nextLine();
            float price = Float.parseFloat(strPrice);
            if (ShopValidate.checkempty(strPrice)) {
                if (ShopValidate.checkfloat(strPrice)) {
                    if (price > 0) {
                        productNew.setPrice(price);
                        break;
                    } else {
                        System.err.println("Vui l??ng nh???p gi?? s???n ph???m l???n h??n 0");
                    }
                } else {
                    System.err.println("Vui l??ng nh???p v??o m???t s??? th???c");
                }
            } else {
                System.err.println(ShopMessage.PRODUCTPRICE_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nh???p ph???n tr??m gi???m gi?? v??o");
        do {
            String strDiscount = sc.nextLine();
            float discount = Float.parseFloat(strDiscount);
            if (ShopValidate.checkempty(strDiscount)) {
                if (ShopValidate.checkfloat(strDiscount)) {
                    if (discount >= 0 && discount <= 100) {
                        productNew.setDiscount(discount);
                        break;
                    } else {
                        System.err.println(ShopMessage.PRODUCTDISCOUNT_MESSAGE_LENGHT);
                    }
                } else {
                    System.err.println("Vui l??ng nh???p v??o m???t s??? th???c");
                }
            } else {
                System.err.println(ShopMessage.PRODUCTDISCOUNT_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nh???p ti??u ????? s???n ph???m v??o");
        do {
            String title = sc.nextLine();
            if (ShopValidate.checkempty(title)) {
                if (ShopValidate.checklenght(title, 10, 50)) {
                    productNew.setTitle(title);
                    break;
                } else {
                    System.err.println(ShopMessage.PRODUCTTITLE_MESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.PRODUCTTITLE_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nh???p m?? t??? s???n ph???m v??o");
        do {
            String content = sc.nextLine();
            if (ShopValidate.checkempty(content)) {
                productNew.setContent(content);
                break;
            } else {
                System.err.println(ShopMessage.PRODUCTCONTENT_MESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Vui l??ng ch???n c??c m??u s???c c???a s???n ph???m");
        do {
            ColorImp colorImp = new ColorImp();
            List<Color> colorList = colorImp.readFromfile();
            if (colorList==null) {
                colorList = new ArrayList<>();
            }
            for (Color color : colorList) {
                System.out.printf("%d. %s\n", color.getColorId(), color.getColorName());
            }
            System.out.println("ch???n m??u s???c");
            int choice = 0;
            do {
                String strchoice = sc.nextLine();
                choice = Integer.parseInt(strchoice);
                if (ShopValidate.checkempty(strchoice)) {
                    if (ShopValidate.checkInteger(strchoice)) {
                        break;
                    } else {
                        System.err.println("Vui l??ng nh???p v??o 1 s??? nguy??n");
                    }
                } else {
                    System.err.println("Danh s??c m??u s???c kh??ng ???????c ????? tr???ng, vui l??ng l???a ch???n m??u s???c");
                }
            } while (true);
            if (choice > 0 && choice <= colorList.size()) {

                if (colorList1==null){
                    colorList1 = new ArrayList<>();
                }
                boolean checkColorExist = false;
                for (Color colorExist : colorList1) {
                    if (colorExist.getColorId() == colorList.get(choice - 1).getColorId()) {
                        checkColorExist = true;
                    }
                }
                if (!checkColorExist) {
                    colorList1.add(colorList.get(choice - 1));
                } else {
                    System.err.println("M??u s???c ???? t???n t???i tr???ng danh s??ch m??u s???c");
                }
                System.out.println("B???n c?? mu???n ch???n th??m m??u s???c kh??ng: ");
                System.out.println("1. C??");
                System.out.println("2. Kh??ng");
                System.out.print("L???a ch???n c???a b???n l??: ");
                int choice2 = 0;
                do {
                    String strchoice2 = sc.nextLine();
                    choice2 = Integer.parseInt(strchoice2);
                    if (ShopValidate.checkempty(strchoice2)) {
                        if (ShopValidate.checkInteger(strchoice2)) {
                            break;
                        } else {
                            System.err.println("Vui l??ng nh???p v??o 1 s??? nguy??n");
                        }
                    } else {
                        System.err.println("Kh??ng ???????c ????? tr???ng vui l??ng l???a ch???n 1 ho???c 2");
                    }
                } while (true);
                if (choice2 != 1) {
                    break;
                }
            } else {
                System.err.println("Vui l??ng ch???n m??u s???c c?? trong danh s??ch");
            }
        } while (true);
        System.out.println("Ch???n danh s??ch c??c k??ch c???");
        do {
            SizeImp sizeImp = new SizeImp();
            List<Size> sizeList = sizeImp.readFromfile();
            if (sizeImp==null){
                sizeList = new ArrayList<>();
            }
            for (Size size : sizeList) {
                System.out.printf("%d. %s\n", size.getSizeId(), size.getSizeName());
            }
            System.out.println("l???a ch???n k??ch c???");
            int choice3;
            do {
                String strchoice3 = sc.nextLine();
                if (ShopValidate.checkempty(strchoice3)) {
                    if (ShopValidate.checkInteger(strchoice3)) {
                        choice3 = Integer.parseInt(strchoice3);
                        break;
                    } else {
                        System.err.println("Vui l??ng nh???p v??o 1 s??? nguy??n");
                    }
                } else {
                    System.err.println("Kh??ng ???????c ????? tr???ng");
                }
            } while (true);
            if (choice3 > 0 && choice3 <= sizeList.size()) {

                boolean check = false;
                for (Size sizeExist :sizeList1 ) {
                    if (sizeExist.getSizeId() == sizeList.get(choice3 - 1).getSizeId()) {
                        check = true;
                    }
                }
                if (!check) {
                    sizeList1.add(sizeList.get(choice3 - 1));
                } else {
                    System.err.println("Kich c??? ???? t???n t???i");
                }
                System.out.println("B???n mu???n ch???n th??m k??ch c??? kh??ng: ");
                System.out.println("1. C??");
                System.out.println("2. Kh??ng");
                System.out.print("L???a ch???n c???a b???n: ");
                int choiceExit;
                do {
                    String strchoice4 = sc.nextLine();
                    if (ShopValidate.checkempty(strchoice4)) {
                        if (ShopValidate.checkInteger(strchoice4)) {
                            choiceExit = Integer.parseInt(strchoice4);
                            break;
                        } else {
                            System.err.println("Vui l??ng nh???p v??o m???t s??? nguy??n");
                        }
                    } else {
                        System.err.println("Kh??ng ???????c ????? tr???ng vui l??ng nh???p v??o 1 ho???c 2");
                    }
                } while (true);
                if (choiceExit != 1) {
                    break;
                }
            } else {
                System.err.println("k??ch c??? kh??ng t???n t???i vui l??ng ch???n k??ch c??? trong danh s??ch");
            }
        } while (true);
        CatalogImp catalogImp = new CatalogImp();
        List<Catalog> catalogList = catalogImp.readFromfile();
        if (catalogList==null){
            catalogList = new ArrayList<>();
        }
        List<Catalog> listCatalogChild = new ArrayList<>();
        int cnt = 1;
        for (Catalog cat : catalogList) {
            if (checkCatalogNotChild(cat, catalogList)) {
              if (cat.isCatalogStatus()){
                  listCatalogChild.add(cat);
              }
            }
        }
        for (Catalog cat : listCatalogChild) {
            System.out.printf("%d. %s \n", cnt, cat.getCatalogName());
            cnt++;
        }
        System.out.println("ch???n danh m???c s???n ph???m");
        int choice5;
        do {
            String strchoice5 = sc.nextLine();
            choice5 = Integer.parseInt(strchoice5);
            if (ShopValidate.checkempty(strchoice5)) {
                if (ShopValidate.checkInteger(strchoice5)) {
                    if (choice5 > 0 && choice5 <= listCatalogChild.size()) {
                        productNew.setCatalog(listCatalogChild.get(choice5 - 1));
                        break;
                    } else {
                        System.err.println("Danh m???c kh??ng t???n t???i vui l??ng ch???n danh m???c kh??c");
                    }
                } else {
                    System.err.println("Vui l??ng nh???p v??o 1 s??? nguy??n");
                }
            } else {
                System.err.println("Kh??ng ???????c ????? tr???ng vui l??ng l???a ch???n danh m???c");
            }
        } while (true);
        System.out.println("Nh???p v??o tr???ng th??i s???n ph???m");
        System.out.println("1. Ho???t ?????ng");
        System.out.println("2. Kh??ng ho???t ?????ng");
        System.out.println("L???a ch???n c???a b???n l??");
        int choice = 0;
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    choice = Integer.parseInt(str);
                    if (choice == 1) {
                        productNew.setProductStatus(true);
                        break;
                    } else if (choice == 2) {
                        productNew.setProductStatus(false);
                        break;
                    } else {
                        System.err.println("Vui l??ng ch???n 1 ho???c 2");
                    }
                } else {
                    System.err.println("Vui l???ng nh???p v??o 1 s??? nguy??n");
                }
            } else {
                System.err.println("Kh??ng ???????c ????? tr???ng vui l??ng l???a ch???n");
            }
        } while (true);
        productNew.setExportPrice(calExportPrice(productNew));
        Date date = new Date();
        productNew.setDate(date);
        return productNew;
    }

    @Override
    public void displayData(Product product) {
        String status = "H???t h??ng";
        if (product.isProductStatus()) {
            status = "??ang b??n";
        }
        product.setExportPrice(calExportPrice(product));
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(product.getDate());
        System.out.println("*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*");
        System.out.printf("|   %-35s%-35s%-30s%-35s%-30s\n", "M?? s???n ph???m", "T??n s???n ph???m", "Gi?? s???n ph???m", "Ph???n tr??m gi???m gi??", "Gi?? b??n s???n ph???m");
        System.out.printf("|   %-35s%-35s%-30.2f%-35f%-30.2f\n", product.getPruductId(), product.getProductName(), product.getPrice(), product.getDiscount(), product.getExportPrice());
        System.out.printf("|   %-35s%-35s%-30s%-35s%-30s\n", "Ti??u ?????", "M?? t???", "Danh m???c", "Tr???ng th??i", "Ng??y nh???p s???n ph???m");
        System.out.printf("|   %-35s%-35s%-30s%-35s%-30s\n", product.getTitle(), product.getContent(), product.getCatalog().getCatalogName(), status, strDate);
        System.out.println("|   Color: ");
        List<Color> colorList = product.getProductColorList();
        if (colorList==null){
            colorList = new ArrayList<>();
        }
        for (Color col : colorList) {
            System.out.print("|   " + col.getColorName() + "\t");
        }
        System.out.println();
        System.out.println("|   Size: ");
        List<Size> sizeList = product.getProductSizeList();
        if (sizeList==null){
            sizeList = new ArrayList<>();
        }
        for (Size size : sizeList) {
            System.out.print("|   " + size.getSizeName() + "\t");
        }
        System.out.println();
        System.out.println("*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*");
    }

    @Override
    public boolean deleteProduct(String str) {
        List<Product> productList = readFromfile();
        if (productList==null){
            productList = new ArrayList<>();
        }
        boolean returnData = false;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getPruductId().equals(str)) {
                productList.get(i).setProductStatus(false);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(productList);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public float calExportPrice(Product product) {
        float exportPrice = product.getPrice()*(100-product.getDiscount())/100;
        return exportPrice;
    }

    public static boolean checkCatalogNotChild(Catalog child, List<Catalog> list) {
        boolean check = true;
        for (Catalog cat : list) {
            if (cat.getCatalog()!=null&&child.getCatalogId() == cat.getCatalog().getCatalogId()) {
                check = false;
                break;
            }
        } if (check){
            return true;
        } else {
            return false;
        }
    }
    public static String displayListColor(Product pro){
        String str = "";
        for (Color color:pro.getProductColorList()) {
             str += color+", ";
        }
        return str;
    }
    public static String displayListSize(Product pro){
        String str = "";
        for (Size size:pro.getProductSizeList()) {
            str += size+" - ";
        }
        return str;
    }

}
