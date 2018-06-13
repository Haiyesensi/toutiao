package com.amber.service;
import com.alibaba.fastjson.JSONObject;
import com.amber.controller.NewsController;
import com.amber.util.FileUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class QiniuService{

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(QiniuService.class);

    private static final String ACCESS_KEY = "cKEtXn9HNcDi-KGRHJabOkmhDDyiPQF_qC5wMfuK";
    private static final String SECRET_KEY = "U2_vvYSj4KM4wXTvj7TEpizX87qCgDmCtanzyNbA";
    private static final String BUCKET_NAME = "toutiao";
    private static final String QINIU_DOMAIN = "http://pa8nntg2r.bkt.clouddn.com/";
    private static final Auth AUTH = Auth.create(ACCESS_KEY, SECRET_KEY);
    private static final UploadManager uploadImage = new UploadManager(new Configuration(Zone.zone0()));



    private String getUpToken(){

        return AUTH.uploadToken(BUCKET_NAME);
    }


    public String uploadImage(MultipartFile file)throws IOException{
        try{
            String fileName = FileUtil.getImageName(file);
            if(fileName == null){
                logger.error("not a image");
                return null;
            }
            Response res = uploadImage.put(file.getBytes(), fileName, getUpToken());
            String URL = res.bodyString();
            if (res.isOK() && res.isJson()) {
                return QINIU_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                logger.error("七牛异常:" + res.bodyString());
                return null;
            }
        }catch (QiniuException e){
            logger.error("qiniu error");
            return null;
        }

    }




}