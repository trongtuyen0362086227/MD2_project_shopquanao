package ra.demo;

import ra.bussiness.entity.Catalog;
import ra.bussiness.entity.User;
import ra.bussiness.imple.UserImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Date date = new Date();
        User user = new User(3,"tuyendz","tuyendz","tuyendz","tuyentrong",true,date,true,"tuyen10@gmail.com","0362087227");
        UserImp userImp = new UserImp();
        userImp.create(user);
    }
}

