package com.amber.service;

import com.amber.dao.NewsDao;
import com.amber.model.HostHolder;
import com.amber.model.News;
import com.amber.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {

    @Autowired
    private NewsDao newsDao;
    @Autowired
    private HostHolder hostHolder;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }


    public News addNews(String title, String link, String img){
        if(title == null || link == null || img == null){
            return null;
        }else {
            News news = new News();
            news.setUserId(hostHolder.getUser().getId());
            news.setTitle(title);
            news.setCreatedDate(new Date());
            news.setImage(img);
            news.setLikeCount(0);
            news.setLink(link);
            news.setCommentCount(0);
            newsDao.addNews(news);
            return news;
        }
    }


    public String saveImage(MultipartFile file) throws IOException {
        String fileName = FileUtil.getImageName(file);
        Files.copy(file.getInputStream(),new File(FileUtil.IMAGE_SAVE_DIR + fileName).toPath(),StandardCopyOption.REPLACE_EXISTING);

        return FileUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }

}
