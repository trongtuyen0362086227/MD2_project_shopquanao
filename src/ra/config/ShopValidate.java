package ra.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopValidate {
    public static boolean checklenght(String str,int min, int max){
            if (str.trim().length()>=min&&str.trim().length()<=max){
                return true;
            }
        return false;
    }
    public static boolean checkInteger(String str ){
        try {
            int number = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }
    public static boolean checkfloat(String str){
        try {
            float number = Float.parseFloat(str);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }
    public static boolean checkempty(String str){
        if (str.trim().length()!=0&&str.trim()!=""){
            return true;
        }
        return false;
    }
    public static boolean checkMailFormat(String str){
      Pattern pattern = Pattern.compile(ShopConstanst.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    public static boolean checkPhoneFormat(String str){
        Pattern pattern = Pattern.compile(ShopConstanst.PHONE_REGEX);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    public static boolean checkUserNameFormat(String str){
        Pattern pattern = Pattern.compile(ShopConstanst.USERNAME_REGEX);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
