package ra.presentation;

import ra.bussiness.entity.Color;
import ra.bussiness.entity.User;
import ra.bussiness.imple.ColorImp;
import ra.bussiness.imple.UserImp;
import ra.config.ShopMessage;
import ra.config.ShopValidate;

import java.util.*;

public class UserMenu {
    private static UserImp userImp = new UserImp();

    public void displayMenuUser(Scanner sc) {
        boolean exit = true;
        do {
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |       ******************************QUẢN LÝ TÀI KHOẢN ***************************     |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        1  . Hiển thị danh sách tài khoản theo ngày tạo                                |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        2  . Thêm tài khoản quản trị                                                   |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        3  . Cập nhật tài khoản quản trị                                               |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        4  . Cập nhật trạng thái tài khoản khác hàng                                    |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        5  . Tìm kiếm tài khoản khách hàng theo tên đăng nhập hoặc tên chủ tài khoản   |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |        6. Thoát                                                                       |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");
            System.out.println("                                         |                                Lựa chọn của bạn:                                      |");
            System.out.println("                                         |---------------------------------------------------------------------------------------|");

            int choice = 0;
            do {
                String strChoice = sc.nextLine();
                choice = Integer.parseInt(strChoice);
                if (ShopValidate.checkempty(strChoice)) {
                    if (ShopValidate.checkInteger(strChoice)) {
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
                    UserMenu.displayListUserByDate();
                    break;
                case 2:
                    UserMenu.creatAdmimToListUser(sc);
                    break;
                case 3:
                    UserMenu.updateAdmin(sc);
                    break;
                case 4:
                    UserMenu.deleteUser(sc);
                    break;
                case 5:
                    UserMenu.searchByUserNameorUserFullName(sc);
                    break;
                case 6:
                    exit = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-6");
            }
        } while (exit);
    }

    public static void displayListUserByDate() {
        List<User> userList = userImp.readFromfile();
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        for (User user:userList) {
            userImp.displayData(user);
        }
    }
    public static void creatAdmimToListUser(Scanner sc){
        List<User> userList = userImp.readFromfile();
        System.out.println("Nhập số lượng tài khoản quản trị muốn thêm vào");
        int num = 0;
        do {
            String str = sc.nextLine();
            num = Integer.parseInt(str);
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
        for (int i = 0; i < num; i++) {
            System.out.println("Nhập dữ liệu cho tài khoản: " + (i + 1));
            UserImp userImp1 = new UserImp();
            User user = userImp1.inputDataAdmin(sc);
            userList.add(user);
            userImp.writeToFile(userList);
        }
        if (userImp.writeToFile(userList)){
            System.out.println("Thêm mới tài khoản quản trị thành công");
        } else {
            System.out.println("Đã xảy ra lỗi");
        }
    }
    public static void updateAdmin(Scanner sc){
        List<User> userList = userImp.readFromfile();
        List<User> adminList = new ArrayList<>();
        for (User user: userList) {
            if (user.isPermission()){
                adminList.add(user);
            }
        }
        System.out.println("Nhập ID tài khoản quản trị muốn cập nhật");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)){
                if (ShopValidate.checkInteger(str)){
                    int number = Integer.parseInt(str);
                    boolean check = true;
                    for (User user:adminList) {
                        if (user.getUserId()==number){
                            check = false;
                            break;
                        }
                    } if (check){
                        System.err.println("Không tìm thấy tài khoản");
                        break;
                    } else {
                        int userId = Integer.parseInt(str);
                        //cap nhat tai khoan
                        System.out.println("Nhập tên tài khoản mới");
                        do {
                            String strUserName = sc.nextLine();
                            if (ShopValidate.checkempty(strUserName)){
                                if (ShopValidate.checkUserNameFormat(strUserName)){
                                    boolean checkexist = true;
                                    for (User user : userList) {
                                        if (user.getUserName().equals(str)){
                                            check = false;
                                        }
                                    }
                                    if (checkexist){
                                        adminList.get(userId).setUserName(strUserName);
                                        break;
                                    } else {
                                        System.err.println(ShopMessage.USERMESSAGE_EXIST);
                                    }
                                } else {
                                    System.err.println("Tên mật khẩu gồm ít nhất 6 ký tự có ký tự viết thường viết hoa và số");
                                }
                            } else {
                                break;
                            }
                        } while (true);
                        System.out.println("Vui lòng nhập tên chủ tài khoản mới vào");
                        String fullName = sc.nextLine();
                        if (ShopValidate.checkempty(fullName)){
                            adminList.get(userId).setFullName(fullName);
                        }
                        System.out.println("Bạn có muốn cập nhật trạng thái tài khoản");
                        System.out.println("1. Có");
                        System.out.println("2. Không ");
                        System.out.println("Lựa chọn của bạn là");
                        try {
                            int choice = Integer.parseInt(sc.nextLine());
                            if (choice == 1) {
                                adminList.get(userId).setUserStatus(!adminList.get(userId).isUserStatus());
                            } else {
                                break;
                            }
                        } catch (NumberFormatException ex1) {
                            System.err.println("Vui lòng nhập vào một số nguyên");
                        }
                        System.out.println("Nhập email bạn muốn cập nhật");
                        do {
                            String email = sc.nextLine();
                            if (ShopValidate.checkempty(email)){
                                if (ShopValidate.checkMailFormat(email)){
                                    adminList.get(userId).setUserEmail(email);
                                    break;
                                } else {
                                    System.err.println("Sai định dạng vui lòng nhập lại mail");
                                }
                            } else {
                                break;
                            }
                        } while (true);
                        System.out.println("Nhập số điện thoại bạn muốn cập nhật");
                        do {
                            String phone = sc.nextLine();
                            if (ShopValidate.checkempty(phone)){
                                if (ShopValidate.checkPhoneFormat(phone)){
                                    adminList.get(userId).setUserEmail(phone);
                                    break;
                                } else {
                                    System.err.println("Sai định dạng vui lòng nhập lại mail");
                                }
                            } else {
                                break;
                            }
                        } while (true);
                    }

                } else {
                    System.err.println("Vui lòng nhập vào 1 số nguyên");
                }
            } else {
                System.err.println("Không được để trống, vui lòng nhập dữ liệu");
            }
        } while (true);
    }
    public static void deleteUser(Scanner sc){
        List<User> userList = userImp.readFromfile();
        System.out.println("Nhập id tài khoản khách hàng bạn muốn cập nhật trạng thái vào");
        do {
            String strUserId = sc.nextLine();
            if (ShopValidate.checkempty(strUserId)){
                if (ShopValidate.checkInteger(strUserId)){
                    boolean checkexist = true;
                    int userId = Integer.parseInt(strUserId);
                    for (User user:userList) {
                        if (user.getUserId()==userId&&!user.isUserStatus()){
                            checkexist = false;
                        }
                    }
                    if (checkexist){
                        System.out.println("Không tìm thấy tài khoản khách hàng có id này");
                    } else {
                        userList.get(userId).setUserStatus(! userList.get(userId).isUserStatus());
                    }
                } else {
                    System.out.println("");
                }
            } else {
                System.out.println("");
            }
        } while (true);
    }
    public static void searchByUserNameorUserFullName(Scanner sc){
        List<User> userList = userImp.readFromfile();
        System.out.println("Nhập tên tài khoản cần tìm kiếm vào");
        do {
            String searchName = sc.nextLine();
            boolean check = true;
            for (User user : userList) {
                if (!user.isPermission()&&user.getUserName().equals(searchName)||!user.isPermission()&&user.getFullName().equals(searchName)){
                    userImp.displayData(user);
                    check = false;
                    break;
                }
            }
            if (check){
                System.out.println("Không tìm thấy tài khoản khách hàng");
            }
        } while (true);
    }
}