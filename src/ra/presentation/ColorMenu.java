package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Color;
import ra.bussiness.imple.ColorImp;
import ra.config.ShopMessage;
import ra.config.ShopValidate;

import java.util.*;

public class ColorMenu {
    private static ColorImp colorImp = new ColorImp();


    public void displayColorMenu(Scanner sc) {
        boolean exit = true;
        do {
            System.out.println("**********QUẢN LÝ MÀU SẮC**********");
            System.out.println("1.Hiển thị danh sách màu sắc theo a>b");
            System.out.println("2. Tạo mới màu săc");
            System.out.println("3. Cập nhật màu sắc");
            System.out.println("4. Xóa màu sắc");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn: \n");
            int choice = 0;
            do {
                String strChoice = sc.nextLine();
                if (ShopValidate.checkempty(strChoice)) {
                    if (ShopValidate.checkInteger(strChoice)) {
                        choice = Integer.parseInt(strChoice);
                        break;
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không được để trống, vui lòng nhập lại");
                }
            } while (true);
            switch (choice) {
                case 1:
                    ColorMenu.displayListColor();
                    break;
                case 2:
                    ColorMenu.inputListColor(sc);
                    break;
                case 3:
                    ColorMenu.updateListColor(sc);
                    break;
                case 4:
                    ColorMenu.deleteColor(sc);
                    break;
                case 5:
                    exit = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-5");
            }
        } while (exit);
    }

    public static void displayListColor() {
        List<Color> colorList = colorImp.readFromfile();
        if (colorList == null) {
            colorList = new ArrayList<>();
        }
        Collections.sort(colorList, new Comparator<Color>() {
            @Override
            public int compare(Color o1, Color o2) {
                return o1.getColorName().compareTo(o2.getColorName());
            }
        });
        System.out.printf("%-10s %-30s %-20s\n", "Mã màu sắc", "Tên màu sắc", "trạng thái");
        for (Color color : colorList) {
            colorImp.displayData(color);
        }
    }

    public static void inputListColor(Scanner sc) {
        List<Color> colorList = colorImp.readFromfile();
        if (colorList == null) {
            colorList = new ArrayList<>();
        }
        System.out.println("Nhập số lượng màu sắc muốn thêm vào");
        int number = 0;
        do {
            String str = sc.nextLine();
            number = Integer.parseInt(str);
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    break;
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập 1 số nguyên vào");
            }
        } while (true);
        for (int i = 0; i < number; i++) {
            System.out.println("Nhập dữ liệu cho màu sắc: " + (i + 1));
            ColorImp colorImp1 = new ColorImp();
            Color color = colorImp1.inputData(sc);
            colorList.add(color);
            colorImp1.writeToFile(colorList);
        }
    }

    public static void updateListColor(Scanner sc) {
        List<Color> colorList = colorImp.readFromfile();
        if (colorList == null) {
            colorList = new ArrayList<>();
        }
        System.out.println("Nhập ID màu sắc cần cập nhật vào");
        int colorId = 0;
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    colorId = Integer.parseInt(str);
                    break;
                } else {
                    System.out.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.out.println("Không được để trống nhập ID vào");
            }
        } while (true);
        boolean check = true;
        for (Color color : colorList) {
            if (color.getColorId() == colorId) {
                check = false;
                break;
            }
        }
        if (check) {
            System.out.println("Không tìm thấy màu sắc");
        } else {
            System.out.println("Nhập vào tên màu sắc muốn cập nhật");
            do {
                String colorName = sc.nextLine();
                if (ShopValidate.checkempty(colorName)) {
                    if (ShopValidate.checklenght(colorName, 4, 30)) {
                        boolean checkExist = true;
                        for (Color color : colorList) {
                            if (color.getColorName().equals(colorName)) {
                                checkExist = false;
                            }
                        }
                        if (checkExist) {
                            colorList.get(colorId - 1).setColorName(colorName);
                            break;
                        } else {
                            System.err.println(ShopMessage.COLORMESSAGE_EXIST);
                        }
                    } else {
                        System.err.println(ShopMessage.COLORMESSAGE_LENGHT);
                    }
                } else {
                    break;
                }
            } while (true);
            System.out.println("Bạn có muốn cập nhật trang thái không");
            System.out.println("1. Có");
            System.out.println("2. Không");
            System.out.println("Lựa chọn của bạn là");
            int choice = 0;
            do {
                String srt = sc.nextLine();
                if (ShopValidate.checkempty(srt)){
                    if (ShopValidate.checkInteger(srt)){
                        choice = Integer.parseInt(srt);
                        break;
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không được để trống vui lòng nhập dữ liệu vào");
                }
            } while (true);
            if (choice ==1){
               colorList.get(colorId-1).setColorStatus(!colorList.get(colorId-1).isColorStatus());
            }
            boolean result =  colorImp.writeToFile(colorList);
            if (result){
                System.out.println("Cập nhật thành công");
            } else {
                System.out.println("Cập nhật thất bại");
            }
        }
    }

    public static void deleteColor(Scanner sc) {
        List<Color> colorList = colorImp.readFromfile();
        if (colorList == null) {
            colorList = new ArrayList<>();
        }
        System.out.println("Nhập id màu sắc bạn muốn xóa vào");
        int colorId = 0;
        do {
            String str = sc.nextLine();
            colorId = Integer.parseInt(str);
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    break;
                } else {
                    System.err.println("Không được để trống");
                }
            } else {
                System.err.println("Vui lòng nhạp vào 1 số nguyên");
            }
        } while (true);
        boolean result = colorImp.delete(colorId);
        if (result) {
            System.out.println("Xóa màu sắc thành công");
        } else {
            System.out.println("Xóa màu sắc thất bại");
        }

    }
}