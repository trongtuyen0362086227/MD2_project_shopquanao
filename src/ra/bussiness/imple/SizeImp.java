package ra.bussiness.imple;

import ra.bussiness.design.IShop;
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
                sizeList.get(i).setSizeStatus(!sizeList.get(i).isSizeStatus());
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
        System.out.println("Nhập tên kích cỡ vào");
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
        System.out.println("Nhập trạng thái kích cỡ");
        System.out.println("1. Hoạt động ");
        System.out.println("2. Không hoạt động");
        System.out.println("Lựa chọn của bạn là");
        do {
            if (ShopValidate.checkInteger(sc.nextLine())){
                int choice = Integer.parseInt(sc.nextLine());
                if (choice==1){
                    sizenew.setSizeStatus(true);
                } else if (choice==2){
                    sizenew.setSizeStatus(false);
                } else {
                    System.err.println("Vui lòng chọn 1 hoặc 2");
                }
                break;
            } else {
                System.err.println("Vui lòng nhập vào một số nguyên");
            }
        } while (true);
        return sizenew;
    }

    @Override
    public void displayData(Size size) {
        String status = "Không hoạt động";
        if (size.isSizeStatus()) {
            status = "Hoạt động";
        }
        System.out.printf("%-10d. %-30s - %-20s\n", size.getSizeId(),size.getSizeName(), status);
    }
}
