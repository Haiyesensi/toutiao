package com.amber.controller;

import com.amber.model.News;
import com.amber.service.NewsService;
import com.amber.util.FileUtil;
import com.amber.util.JsonUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(NewsController.class);

    @RequestMapping(path = "/user/addNews/",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("title") String title,
                          @RequestParam("link") String link,
                          @RequestParam("image") MultipartFile file) throws IOException {
        String imgUrl = newsService.saveImage(file);
        News news = newsService.addNews(title,link,imgUrl);
        if(news == null){
            return JsonUtil.getJSONString(1, "share error");
        }
        return JsonUtil.getJSONString(0,"share successful");
    }

    @RequestMapping(path = "/uploadImage/",method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file){
        try{
            String imageUrl = newsService.saveImage(file);
            if(imageUrl == null){
                return JsonUtil.getJSONString(1,"image上传失败");
            }
            return JsonUtil.getJSONString(0,imageUrl);
        }catch (Exception e){
            logger.info("image upload failure" + e.getMessage());
            return JsonUtil.getJSONString(1,"image上传失败");
        }
    }

    @RequestMapping(path = "/image",method = RequestMethod.GET)
    @ResponseBody
    public void getImages(@RequestParam("name") String imageName,
                         HttpServletResponse httpServletResponse){
        httpServletResponse.setContentType("image/jpg");
        try{
            StreamUtils.copy(new FileInputStream(new File(FileUtil.IMAGE_SAVE_DIR + imageName)), httpServletResponse.getOutputStream());
        }catch (Exception e){
            logger.error("image load failure: "+e.getMessage());
        }
    }
}
