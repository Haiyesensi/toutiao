package com.amber.util;

public class FileUtil {

    public static String[] IMAGE_FILE_EXT = new String[]{"png","jpg","bmp","jpeg"};
    public static String IMAGE_SAVE_DIR = "D:\\doc\\toutiao_pic\\";
    public static String TOUTIAO_DOMAIN = "127.0.0.1:8100/";

    public static boolean isImage(String fileExtName){
        for(String ext : IMAGE_FILE_EXT){
            if(ext.equals(fileExtName)){
                return true;
            }
        }
        return false;
    }
}
