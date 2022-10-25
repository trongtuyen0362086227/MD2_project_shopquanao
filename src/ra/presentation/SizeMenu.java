package ra.presentation;

import ra.bussiness.entity.Color;
import ra.bussiness.entity.Size;
import ra.bussiness.imple.ColorImp;
import ra.bussiness.imple.SizeImp;
import ra.config.ShopMessage;
import ra.config.ShopValidate;

import java.util.*;

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
        if (sizeList==null){
            sizeList = new ArrayList<>();
        }
        Collections.sort(sizeList, new Comparator<Size>() {
            @Override
            public int compare(Size o1, Size o2) {
                return o1.getSizeName().compareTo(o2.getSizeName());
            }
        });
        System.out.printf("%-20s %-30s %-30s\n", "Mã kich cỡ","Tên kích cỡ","Trạng thái");
        for (Size size : sizeList) {
            sizeImp.displayData(size);
        }
    }

    public static void inputListSize(Scanner sc) {
        List<Size> sizeList = sizeImp.readFromfile();
        if (sizeList==null){
            sizeList = new ArrayList<>();
        }
        System.out.println("Nhập số lượng kích cỡ muốn thêm vào");
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
            System.out.println("Nhập dữ liệu cho kích cỡ: " + (i + 1));
            SizeImp sizeImp1 = new SizeImp();
            Size size = sizeImp1.inputData(sc);
           sizeList.add(size);
            sizeImp1.writeToFile(sizeList);
        }
    }

    public static void updateListSize(Scanner sc) {
        List<Size> sizeList = sizeImp.readFromfile();
        if (sizeList == null) {
            sizeList = new ArrayList<>();
        }
        System.out.println("Nhập ID kích cỡ cần cập nhật vào");
        int sizeId = 0;
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    sizeId = Integer.parseInt(str);
                    break;
                } else {
                    System.out.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.out.println("Không được để trống nhập ID vào");
            }
        } while (true);
        boolean check = true;
        for (Size size : sizeList) {
            if (size.getSizeId() == sizeId) {
                check = false;
                break;
            }
        }
        if (check) {
            System.out.println("Không tìm thấy kích cỡ");
        } else {
            System.out.println("Nhập vào tên kích muốn cập nhật");
            do {
                String sizeName = sc.nextLine();
                if (ShopValidate.checkempty(sizeName)) {
                    if (ShopValidate.checklenght(sizeName, 4, 30)) {
                        boolean checkExist = true;
                        for (Size size : sizeList) {
                            if (size.getSizeName().equals(sizeName)) {
                                checkExist = false;
                            }
                        }
                        if (checkExist) {
                            sizeList.get(sizeId - 1).setSizeName(sizeName);
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
                sizeList.get(sizeId-1).setSizeStatus(!sizeList.get(sizeId-1).isSizeStatus());
            }
            boolean result =  sizeImp.writeToFile(sizeList);
            if (result){
                System.out.println("Cập nhật thành công");
            } else {
                System.out.println("Cập nhật thất bại");
            }
        }
    }

    public static void deleteSize(Scanner sc) {
        List<Size> sizeList = sizeImp.readFromfile();
        if (sizeList == null) {
            sizeList = new ArrayList<>();
        }
        System.out.println("Nhập id kích cỡ bạn muốn xóa vào");
        int sizeId = 0;
        do {
            String str = sc.nextLine();
            sizeId = Integer.parseInt(str);
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkInteger(str)) {
                    break;
                } else {
                    System.err.println("Không được để trống");
                }
            } else {
                System.err.println("Vui lòng nhập vào 1 số nguyên");
            }
        } while (true);
        boolean result = sizeImp.delete(sizeId);
        if (result) {
            System.out.println("Xóa kích cỡ thành công");
        } else {
            System.out.println("Xóa kích cỡ thất bại");
        }

    }
}
