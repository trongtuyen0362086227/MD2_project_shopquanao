package ra.bussiness.imple;

import ra.bussiness.design.IUser;
import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.Product;
import ra.bussiness.entity.User;
import ra.config.ShopConstanst;
import ra.config.ShopMessage;
import ra.config.ShopValidate;
import ra.data.FileImp;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserImp implements IUser<User, Integer> {
    @Override
    public boolean create(User user) {
        List<User> userList = readFromfile();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        userList.add(user);
        boolean result = writeToFile(userList);
        return result;
    }

    @Override
    public boolean update(User user) {
        List<User> userList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == user.getUserId()) {
                userList.set(i, user);
                returnData = true;
                break;
            }
        }
        return returnData;
    }

    @Override
    public boolean delete(Integer integer) {
        List<User> userList = readFromfile();
        boolean returnData = false;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == integer) {
                userList.get(i).setUserStatus(!userList.get(i).isUserStatus());
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(userList);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> readFromfile() {
        FileImp fileImp = new FileImp();
        return fileImp.readFromFile(ShopConstanst.URL_USER_FILE);
    }

    @Override
    public boolean writeToFile(List<User> list) {
        FileImp fileImp = new FileImp();
        return fileImp.writeToFile(list, ShopConstanst.URL_USER_FILE);
    }

    @Override
    public User inputData(Scanner sc) {
        List<User> userList = readFromfile();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        User userNew = new User();
        if (userList.size() == 0) {
            userNew.setUserId(1);
        } else {
            int max = 0;
            for (User user : userList) {
                if (max < user.getUserId()) {
                    max = user.getUserId();
                }
            }
            userNew.setUserId(max + 1);
        }
        System.out.println("Nh???p t??n ????ng nh???p v??o");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkUserNameFormat(str)) {
                    boolean check = true;
                    for (User user : userList) {
                        if (user.getUserName().equals(str)) {
                            check = false;
                        }
                    }
                    if (check) {
                        userNew.setUserName(str);
                        break;
                    } else {
                        System.err.println(ShopMessage.USERMESSAGE_EXIST);
                    }
                } else {
                    System.err.println("T??n ????ng nh???p g???m ??t nh???t 6 k?? t??? c?? k?? t??? vi???t th?????ng vi???t hoa v?? s???");
                }
            } else {
                System.err.println(ShopMessage.USERMESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Vui l??ng nh???p m???t kh???u v??o");
        do {
            String password = sc.nextLine();
            if (ShopValidate.checkempty(password)) {
                if (password.trim().length() >= 6) {
                    userNew.setPassword(password);
                    break;
                } else {
                    System.err.println(ShopMessage.USERPASWORDMESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.USERPASSWORDMESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Vui l??ng nh???p x??c nh???n m???t kh???u v??o");
        do {
            String confirmPassword = sc.nextLine();
            if (confirmPassword.equals(userNew.getPassword())) {
                userNew.setConfirmPassword(confirmPassword);
                break;
            } else {
                System.err.println("X??c nh???n m???t kh???u sai vui l??ng nh???p l???i");
            }
        } while (true);
        System.out.println("Vui l??ng nh???p t??n ch??? t??i kho???n");

        userNew.setFullName(sc.nextLine());
        Date date = new Date();
        userNew.setDate(date);
        System.out.println("Nh???p v??o tr???ng th??i c???a t??i kho???n");
        System.out.println("1. Ho???t ?????ng");
        System.out.println("2. B??? kh??a");
        System.out.println("L???a ch???n c???a b???n l??");
        do {
            String str = sc.nextLine();
            int choice = Integer.parseInt(str);
            if (ShopValidate.checkInteger(str)) {
                if (choice == 1) {
                    userNew.setUserStatus(true);
                    break;
                } else if (choice == 2) {
                    userNew.setUserStatus(false);
                    break;
                } else {
                    System.err.println("Vui l??ng ch???n 1 ho???c 2");
                }
            } else {
                System.err.println("Vui l??ng nh???p v??o m???t s??? nguy??n");
            }
            ;
        } while (true);
        System.out.println("Nh???p email v??o");
        do {
            String email = sc.nextLine();
            if (ShopValidate.checkMailFormat(email)) {
                userNew.setUserEmail(email);
                break;
            } else {
                System.err.println(ShopMessage.USEREMAILMESSAGE_FORMAT);
            }
        } while (true);
        System.out.println("Nh???p s??? ??i???n tho???i v??o");
        do {
            String phoneNumber = sc.nextLine();
            if (ShopValidate.checkPhoneFormat(phoneNumber)) {
                userNew.setUserPhoneNumber(phoneNumber);
                break;
            } else {
                System.out.println("??inh d???ng s??? ??i???n tho???i sai vui l??ng nh???p l???i");
            }
        } while (true);

        return userNew;
    }

    @Override
    public void displayData(User user) {
        String status = "Kh??ng ho???t ?????ng";
        if (user.isUserStatus()) {
            status = "Ho???t ?????ng";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(user.getDate());

        System.out.printf("M?? t??i kho???n: %-10d | T??n ????ng nh???p: %-20s | T??n ch??? t??i kho???n %-20s\n", user.getUserId(), user.getUserName(), user.getFullName());
        System.out.printf("Ng??y t???o tk: %-20s | Email: %-30s | ST??: %-15s\n", strDate, user.getUserEmail(), user.getUserPhoneNumber());
        System.out.printf("Tr???ng th??i: %-20s  \n", status);
    }

    @Override
    public void displayProductbyCatalog(List<Catalog> list) {

    }

    @Override
    public void displayProductbyDate(List<Product> list) {

    }

    @Override
    public void displayProductbyDiscount(List<Product> list) {

    }

    @Override
    public Product searchProductbyProductName(Scanner sc) {
        return null;
    }

    @Override
    public Product searchProductbyCatalogName(Scanner sc) {
        return null;
    }

    @Override
    public List<Product> searchProductbyExporPrice(float min, float max) {
        return null;
    }

    @Override
    public List<Product> searchProductbyDiscount(float min, float max) {
        return null;
    }

    public User checkLogin(String userName, String password) {
        List<User> userList = readFromfile();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        for (User user : userList) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User inputDataAdmin(Scanner sc) {
        List<User> userList = readFromfile();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        User userNew = new User();
        if (userList.size() == 0) {
            userNew.setUserId(1);
        } else {
            int max = 0;
            for (User user : userList) {
                if (max < user.getUserId()) {
                    max = user.getUserId();
                }
            }
            userNew.setUserId(max + 1);
        }
        System.out.println("Nh???p t??n ????ng nh???p v??o");
        do {
            String str = sc.nextLine();
            if (ShopValidate.checkempty(str)) {
                if (ShopValidate.checkUserNameFormat(str)) {
                    boolean check = true;
                    for (User user : userList) {
                        if (user.getUserName().equals(str)) {
                            check = false;
                        }
                    }
                    if (check) {
                        userNew.setUserName(str);
                        break;
                    } else {
                        System.err.println(ShopMessage.USERMESSAGE_EXIST);
                    }
                } else {
                    System.err.println("T??n ????ng nh???p g???m ??t nh???t 6 k?? t??? c?? k?? t??? vi???t th?????ng vi???t hoa v?? s???");
                }
            } else {
                System.err.println(ShopMessage.USERMESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Vui l??ng nh???p m???t kh???u v??o");
        do {
            String password = sc.nextLine();
            if (ShopValidate.checkempty(password)) {
                if (password.trim().length() >= 6) {
                    userNew.setPassword(password);
                    break;
                } else {
                    System.err.println(ShopMessage.USERPASWORDMESSAGE_LENGHT);
                }
            } else {
                System.err.println(ShopMessage.USERPASSWORDMESSAGE_EMPTY);
            }
        } while (true);
        System.out.println("Vui l??ng nh???p x??c nh???n m???t kh???u v??o");
        do {
            String confirmPassword = sc.nextLine();
            if (confirmPassword.equals(userNew.getPassword())) {
                userNew.setConfirmPassword(confirmPassword);
                break;
            } else {
                System.err.println("X??c nh???n m???t kh???u sai vui l??ng nh???p l???i");
            }
        } while (true);
        System.out.println("Vui l??ng nh???p t??n ch??? t??i kho???n");

        userNew.setFullName(sc.nextLine());
        Date date = new Date();
        userNew.setDate(date);
        System.out.println("Nh???p v??o tr???ng th??i c???a t??i kho???n");
        System.out.println("1. Ho???t ?????ng");
        System.out.println("2. B??? kh??a");
        System.out.println("L???a ch???n c???a b???n l??");
        do {
            String str = sc.nextLine();
            int choice = Integer.parseInt(str);
            if (ShopValidate.checkInteger(str)) {
                if (choice == 1) {
                    userNew.setUserStatus(true);
                    break;
                } else if (choice == 2) {
                    userNew.setUserStatus(false);
                    break;
                } else {
                    System.err.println("Vui l??ng ch???n 1 ho???c 2");
                }
            } else {
                System.err.println("Vui l??ng nh???p v??o m???t s??? nguy??n");
            }
            ;
        } while (true);
        System.out.println("Nh???p email v??o");
        do {
            String email = sc.nextLine();
            if (ShopValidate.checkMailFormat(email)) {
                userNew.setUserEmail(email);
                break;
            } else {
                System.err.println(ShopMessage.USEREMAILMESSAGE_FORMAT);
            }
        } while (true);
        System.out.println("Nh???p s??? ??i???n tho???i v??o");
        do {
            String phoneNumber = sc.nextLine();
            if (ShopValidate.checkPhoneFormat(phoneNumber)) {
                userNew.setUserPhoneNumber(phoneNumber);
                break;
            } else {
                System.out.println("??inh d???ng s??? ??i???n tho???i sai vui l??ng nh???p l???i");
            }
        } while (true);
        userNew.setPermission(true);

        return userNew;
    }
}


