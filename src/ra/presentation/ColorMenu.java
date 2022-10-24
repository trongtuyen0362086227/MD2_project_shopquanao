package ra.presentation;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Color;
import ra.bussiness.imple.ColorImp;
import ra.config.ShopMessage;
import ra.config.ShopValidate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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

            do {
                String strChoice = sc.nextLine();
                if (ShopValidate.checkempty(strChoice)) {
                    if (ShopValidate.checkInteger(strChoice)) {
                        int choice = Integer.parseInt(strChoice);
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
                    } else {
                        System.err.println("Vui lòng nhập vào 1 số nguyên");
                    }
                } else {
                    System.err.println("Không được để trống, vui lòng nhập lại");
                }
            } while (true);
        } while (exit);
    }

    public static void displayListColor() {
        List<Color> colorList = colorImp.readFromfile();
        Collections.sort(colorList, new Comparator<Color>() {
            @Override
            public int compare(Color o1, Color o2) {
                return o1.getColorName().compareTo(o2.getColorName());
            }
        });
        for (Color color : colorList) {
            colorImp.displayData(color);
        }
    }

    public static void inputListColor(Scanner sc) {
        List<Color> colorList = colorImp.readFromfile();
        System.out.println("Nhập số lượng màu sắc muốn thêm vào");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    int num = Integer.parseInt(str);
                    for (int i = 0; i < num; i++) {
                        System.out.println("Nhập dữ liệu cho màu sắc: " + (i + 1));
                        ColorImp colorImp1 = new ColorImp();
                        Color color = colorImp1.inputData(sc);
                        colorList.add(color);
                        colorImp1.create(color);
                    }
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập 1 số nguyên vào");
            }
        } while (true);
    }

    public static void updateListColor(Scanner sc) {
        List<Color> list = colorImp.readFromfile();
        System.out.println("Nhập ID màu sắc cần cập nhật vào");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    int colorId = Integer.parseInt(str);
                    if (colorImp.searchById(colorId)) {
                        System.out.println("Nhập vào tên màu sắc muốn cập nhật");
                        do {
                            String colorName = sc.nextLine();
                            if (ShopValidate.checkempty(colorName)) {
                                if (ShopValidate.checklenght(colorName, 4, 30)) {
                                    boolean check = true;
                                    for (Color color : list) {
                                        if (color.getColorName().equals(colorName)) {
                                            check = false;
                                        }
                                    }
                                    if (check) {
                                        list.get(colorId).setColorName(colorName);
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
                        System.out.println("Bạn co muốn cập nhật trạng thái màu sắc");
                        System.out.println("1. Có");
                        System.out.println("2. Không ");
                        System.out.println("Lựa chọn của bạn là");
                        try {
                            int choice = Integer.parseInt(sc.nextLine());
                            if (choice == 1) {
                                list.get(colorId).setColorStatus(!list.get(colorId).isColorStatus());
                            } else {
                                break;
                            }
                        } catch (NumberFormatException ex1) {
                            System.err.println("Vui lòng nhập vào một số nguyên");
                        }
                    }
                } else {
                    System.out.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.out.println("Không được để trống vui lòng nhập ID vào");
            }
        } while (true);
    }

    public static void deleteColor(Scanner sc) {
        System.out.println("Nhập id màu sắc bạn muốn xóa vào");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    colorImp.delete(Integer.parseInt(str));
                } else {
                    System.err.println("Không được để trống");
                }
            } else {
                System.err.println("Vui lòng nhạp vào 1 số nguyên");
            }
        } while (true);
    }
}