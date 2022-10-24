package ra.bussiness.imple;

import ra.bussiness.design.IShop;
import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Color;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ColorImp implements IShop<Color, Integer> {

    @Override
    public boolean create(Color color) {
        List<Color> colorList = readFromfile();
        if (colorList == null) {
            colorList = new ArrayList<>();
        }
        colorList.add(color);
        boolean result = writeToFile(colorList);
        return result;
    }

    @Override
    public boolean update(Color color) {
        List<Color> colorList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < colorList.size(); i++) {
            if (colorList.get(i).getColorId() == color.getColorId()) {
                colorList.set(i, color);
                returnData = true;
                break;
            }
        }
        return returnData;
    }

    @Override
    public boolean delete(Integer integer) {
        List<Color> colorList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < colorList.size(); i++) {
            if (colorList.get(i).getColorId() == integer) {
                colorList.get(i).setColorStatus(!colorList.get(i).isColorStatus());
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(colorList);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<Color> readFromfile() {
        FileImp fileImp = new FileImp();
        return fileImp.readFromFile(ShopConstanst.URL_COLOR_FILE);
    }

    @Override
    public boolean writeToFile(List<Color> list) {
        FileImp fileImp = new FileImp();
        return fileImp.writeToFile(list, ShopConstanst.URL_COLOR_FILE);
    }

    @Override
    public Color inputData(Scanner sc) {
        List<Color> colorList = readFromfile();
        if (colorList == null) {
            colorList = new ArrayList<>();
        }
        Color colornew = new Color();
        if (colorList.size() == 0) {
            colornew.setColorId(1);
        } else {
            int max = 0;
            for (Color col : colorList) {
                if (max < col.getColorId()) {
                    max = col.getColorId();
                }
            }
            colornew.setColorId(max + 1);
        }
        System.out.println("Nhập tên màu sắc vào");
        do {
            String colorName = sc.nextLine();
            if (ShopValidate.checkempty(colorName)){
                if (ShopValidate.checklenght(colorName,4,30)){
                     boolean check = true;
                    for (Color col:colorList) {
                        if (col.getColorName().equals(colorName)){
                            check = false;
                        }
                    }
                    if (check){
                        colornew.setColorName(colorName);
                        break;
                    } else {
                        System.err.println(ShopMessage.COLORMESSAGE_EXIST);
                    }
                } else {
                    System.out.println(ShopMessage.COLORMESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.COLORMESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Nhập trạng thái màu sắc");
        System.out.println("1. Hoạt động ");
        System.out.println("2. Không hoạt động");
        System.out.println("Lựa chọn của bạn là");
        do {
            if (ShopValidate.checkInteger(sc.nextLine())){
                int choice = Integer.parseInt(sc.nextLine());
                if (choice==1){
                    colornew.setColorStatus(true);
                } else if (choice==2){
                    colornew.setColorStatus(false);
                } else {
                    System.err.println("Vui lòng chọn 1 hoặc 2");
                }
                break;
            } else {
                System.err.println("Vui lòng nhập vào một số nguyên");
            }
        } while (true);
        return colornew;
    }

    @Override
    public void displayData(Color color) {
        String status = "Không hoạt động";
        if (color.isColorStatus()) {
            status = "Hoạt động";
        }
        System.out.printf("%-10d. %-30s - %-20s\n", color.getColorId(), color.getColorName(), status);
    }
    public boolean searchById(int colorId){
        List<Color> list = readFromfile();
        boolean check = false;
        for (Color color : list) {
            if (color.getColorId() == colorId) {
                check = true;
                break;
            }
        }
        return check;
    }
}

