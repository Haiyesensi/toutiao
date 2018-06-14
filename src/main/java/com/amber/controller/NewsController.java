package com.amber.controller;

import com.amber.model.Comment;
import com.amber.model.EntityType;
import com.amber.model.News;
import com.amber.model.ViewObject;
import com.amber.service.CommentService;
import com.amber.service.NewsService;
import com.amber.service.QiniuService;
import com.amber.service.UserService;
import com.amber.util.FileUtil;
import com.amber.util.JsonUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(NewsController.class);

    @RequestMapping(path = "/user/addNews/", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("title") String title,
                          @RequestParam("link") String link,
                          @RequestParam("image") String image) throws IOException {
        try {
            News news = newsService.addNews(title, link, image);
            if (news == null) {
                logger.error("share error!");
                return JsonUtil.getJSONString(1, "share error");
            }
        } catch (Exception e) {
            logger.error("share error!" + e.getMessage());
            return JsonUtil.getJSONString(1, "share error");
        }
        return JsonUtil.getJSONString(0, "share successful");
    }

    @RequestMapping(path = "/news/{newsId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String newsDetail(@PathVariable("newsId") int newsId, Model model) {
        News news = newsService.getNews(newsId);
        if (news != null) {
            List<Comment> comments = commentService.getCommentByEntity(news.getId(), EntityType.ENTITY_NEWS);
            List<ViewObject> commentVo = new ArrayList<>();
            for (Comment comment : comments) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                vo.set("user", userService.getUserById(comment.getUserId()));
                commentVo.add(vo);
            }
            model.addAttribute("news", news);
            model.addAttribute("owner", userService.getUserById(news.getUserId()));
            model.addAttribute("comments", commentVo);
        }
        return "detail";
    }

    @RequestMapping(path = "/uploadImage/", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            //String imageUrl = newsService.saveImage(file);
            String imageUrl = qiniuService.uploadImage(file);
            if (imageUrl == null) {
                return JsonUtil.getJSONString(1, "image上传失败");
            }
            return JsonUtil.getJSONString(0, imageUrl);
        } catch (Exception e) {
            logger.info("image upload failure" + e.getMessage());
            return JsonUtil.getJSONString(1, "image上传失败");
        }
    }

    @RequestMapping(path = "/image", method = RequestMethod.GET)
    @ResponseBody
    public void getImages(@RequestParam("name") String imageName,
                          HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("image/jpg");
        try {
            StreamUtils.copy(new FileInputStream(new File(FileUtil.IMAGE_SAVE_DIR + imageName)), httpServletResponse.getOutputStream());
        } catch (Exception e) {
            logger.error("image load failure: " + e.getMessage());
        }
    }
}
