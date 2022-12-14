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
                colorList.get(i).setColorStatus(false);
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
        System.out.println("Nh???p t??n m??u s???c v??o");
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
        System.out.println("Nh???p v??o tr???ng th??i m??u s???c");
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
                        colornew.setColorStatus(true);
                        break;
                    } else if (choice == 2) {
                        colornew.setColorStatus(false);
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
        return colornew;
    }

    @Override
    public void displayData(Color color) {
        String status = "Kh??ng ho???t ?????ng";
        if (color.isColorStatus()) {
            status = "Ho???t ?????ng";
        }
        System.out.printf("%-20d %-30s  %-20s\n", color.getColorId(), color.getColorName(), status);
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

