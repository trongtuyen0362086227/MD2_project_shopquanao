package ra.data;

import java.io.*;
import java.util.List;

public class FileImp<T> {
    public List<T> readFromFile(String path){
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<T> list = null;
        try {
            file = new File(path);
            if (file.exists()){
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                list = (List<T>) ois.readObject();
            }

        } catch (Exception ex1){
            ex1.printStackTrace();
        } finally {
            try {
                if (ois!=null){
                    ois.close();
                }
                if (fis!=null){
                    fis.close();
                }
            } catch (IOException ex2){
                ex2.printStackTrace();
            }
        }
        return list;
    }
    public boolean writeToFile(List<T> list,String path) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(path);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (Exception ex1){
            returnData = false;
            ex1.printStackTrace();
        } finally {
            try {
                if (oos!=null){
                    oos.close();
                }
                if (fos!=null){
                    fos.close();
                }
            } catch (IOException ex2){
                ex2.printStackTrace();
            }
        }
        return returnData;
    }
}
