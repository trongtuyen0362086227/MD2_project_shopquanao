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
        System.out.println("Nhập vào trạng thái kích cỡ");
        System.out.println("1. Hoạt động");
        System.out.println("2. Không hoạt động");
        System.out.println("Lựa chọn của bạn là");
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
                        System.err.println("Vui lòng chọn 1 hoặc 2");
                    }
                } else {
                    System.err.println("Vui lỏng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng lựa chọn");
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
