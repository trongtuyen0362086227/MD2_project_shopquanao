package ra.presentation;

import ra.bussiness.entity.Color;
import ra.bussiness.entity.Size;
import ra.bussiness.imple.ColorImp;
import ra.bussiness.imple.SizeImp;
import ra.config.ShopMessage;
import ra.config.ShopValidate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SizeMenu {
    private static SizeImp sizeImp = new SizeImp();

    public void displaySizeMenu(Scanner sc) {
        boolean exit = true;
        do {
            System.out.println("********** QUẢN LÝ KÍCH CỠ **********");
            System.out.println("1.Hiển thị danh sách kích cỡ theo a>b");
            System.out.println("2. Tạo mới kích cỡ");
            System.out.println("3. Cập nhật kích cỡ");
            System.out.println("4. Xóa kích cỡ");
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
                    SizeMenu.displayListSize();
                    break;
                case 2:
                    SizeMenu.inputListSize(sc);
                    break;
                case 3:
                    SizeMenu.updateListSize(sc);
                    break;
                case 4:
                    SizeMenu.deleteSize(sc);
                    break;
                case 5:
                    exit = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-5");
            }
        } while (exit);
    }

    public static void displayListSize() {
        List<Size> sizeList = sizeImp.readFromfile();
        Collections.sort(sizeList, new Comparator<Size>() {
            @Override
            public int compare(Size o1, Size o2) {
                return o1.getSizeName().compareTo(o2.getSizeName());
            }
        });
        for (Size size : sizeList) {
            sizeImp.displayData(size);
        }
    }

    public static void inputListSize(Scanner sc) {
        List<Size> sizeList = sizeImp.readFromfile();
        System.out.println("Nhập số lượng kích cỡ muốn thêm vào");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    int num = Integer.parseInt(str);
                    for (int i = 0; i < num; i++) {
                        System.out.println("Nhập dữ liệu cho kích cỡ: " + (i + 1));
                        SizeImp sizeImp1 = new SizeImp();
                        Size size = sizeImp1.inputData(sc);
                        sizeList.add(size);
                        sizeImp1.create(size);
                    }
                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống vui lòng nhập 1 số nguyên vào");
            }
        } while (true);
    }

    public static void updateListSize(Scanner sc) {
        List<Size> list = sizeImp.readFromfile();
        System.out.println("Nhập ID kích cỡ cần cập nhật vào");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    int sizeId = Integer.parseInt(str);
                    if (sizeImp.searchById(sizeId)) {
                        System.out.println("Nhập vào tên kích cỡ muốn cập nhật");
                        do {
                            String sizeName = sc.nextLine();
                            if (ShopValidate.checkempty(sizeName)) {
                                if (ShopValidate.checklenght(sizeName, 1, 10)) {
                                    boolean check = true;
                                    for (Size size : list) {
                                        if (size.getSizeName().equals(sizeName)) {
                                            check = false;
                                        }
                                    }
                                    if (check) {
                                        list.get(sizeId).setSizeName(sizeName);
                                        break;
                                    } else {
                                        System.err.println(ShopMessage.SIZEMESSAGE_EXIST);
                                    }
                                } else {
                                    System.err.println(ShopMessage.SIZEMESSAGE_LENGHT);
                                }
                            } else {
                                break;
                            }
                        } while (true);
                        System.out.println("Bạn có muốn cập nhật trạng thái kích cỡ");
                        System.out.println("1. Có");
                        System.out.println("2. Không ");
                        System.out.println("Lựa chọn của bạn là");
                        try {
                            int choice = Integer.parseInt(sc.nextLine());
                            if (choice == 1) {
                                list.get(sizeId).setSizeStatus(!list.get(sizeId).isSizeStatus());
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

    public static void deleteSize(Scanner sc) {
        System.out.println("Nhập id kích cỡ bạn muốn xóa vào");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    sizeImp.delete(Integer.parseInt(str));
                } else {
                    System.err.println("Không được để trống");
                }
            } else {
                System.err.println("Vui lòng nhạp vào 1 số nguyên");
            }
        } while (true);
    }
}
