package ra.bussiness.imple;

import ra.bussiness.design.IShop;
import ra.bussiness.entity.Color;
import ra.bussiness.entity.Size;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SizeImp implements IShop<Size, Integer> {
    @Override
    public boolean create(Size size) {
        List<Size> sizeList = readFromfile();
        if (sizeList == null) {
            sizeList = new ArrayList<>();
        }
        sizeList.add(size);
        boolean result = writeToFile(sizeList);
        return result;
    }

    @Override
    public boolean update(Size size) {
        List<Size> sizeList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < sizeList.size(); i++) {
            if (sizeList.get(i).getSizeId() == size.getSizeId()) {
                sizeList.set(i, size);
                returnData = true;
                break;
            }
        }
        return returnData;
    }

    @Override
    public boolean delete(Integer integer) {
        List<Size> sizeList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < sizeList.size(); i++) {
            if (sizeList.get(i).getSizeId() == integer) {
                sizeList.get(i).setSizeStatus(false);

                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(sizeList);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<Size> readFromfile() {
        FileImp fileImp = new FileImp();
       return fileImp.readFromFile(ShopConstanst.URL_SIZE_FILE);
    }

    @Override
    public boolean writeToFile(List<Size> list) {
        FileImp fileImp = new FileImp();
        return fileImp.writeToFile(list,ShopConstanst.URL_SIZE_FILE);
    }

    @Override
    public Size inputData(Scanner sc) {
        List<Size> sizeList = readFromfile();
        if (sizeList == null) {
            sizeList = new ArrayList<>();
        }
        Size sizenew = new Size();
        if (sizeList.size() == 0) {
            sizenew.setSizeId(1);
        } else {
            int max = 0;
            for (Size size : sizeList) {
                if (max < size.getSizeId()) {
                    max = size.getSizeId();
                }
            }
            sizenew.setSizeId(max + 1);
        }
        System.out.println("Nh???p t??n k??ch c??? v??o");
        do {
            String sizeName = sc.nextLine();
            if (ShopValidate.checkempty(sizeName)){
                if (ShopValidate.checklenght(sizeName,1,10)){
                    boolean check = true;
                    for (Size size : sizeList) {
                        if (size.getSizeName().equals(sizeName)){
                            check = false;
                        }
                    }
                    if (check){
                        sizenew.setSizeName(sizeName);
                        break;
                    } else {
                        System.err.println(ShopMessage.SIZEMESSAGE_EXIST);
                    }
                } else {
                    System.out.println(ShopMessage.SIZEMESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.SIZEMESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nh???p v??o tr???ng th??i k??ch c???");
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
                         sizenew.setSizeStatus(true);
                        break;
                    } else if (choice == 2) {
                        sizenew.setSizeStatus(false);
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
        return sizenew;
    }

    @Override
    public void displayData(Size size) {
        String status = "Kh??ng ho???t ?????ng";
        if (size.isSizeStatus()) {
            status = "Ho???t ?????ng";
        }
        System.out.printf("%-20d %-30s %-30s\n", size.getSizeId(),size.getSizeName(), status);
    }
    public boolean searchById(int sizeId){
        List<Size> list = readFromfile();
        boolean check = false;
        for (Size size : list) {
            if (size.getSizeId() == sizeId) {
                check = true;
                break;
            }
        }
        return check;
    }
}
