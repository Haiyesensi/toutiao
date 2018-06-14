package com.amber.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileUtil {

    public static String[] IMAGE_FILE_EXT = new String[]{"png", "jpg", "bmp", "jpeg"};
    public static String IMAGE_SAVE_DIR = "D:\\doc\\toutiao_pic\\";
    public static String TOUTIAO_DOMAIN = "127.0.0.1:8100/";

    public static boolean isImage(String fileExtName) {
        for (String ext : IMAGE_FILE_EXT) {
            if (ext.equals(fileExtName)) {
                return true;
            }
        }
        return false;
    }

    public static String getImageName(MultipartFile file) {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!FileUtil.isImage(fileExt)) {
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        return fileName;
    }
}
