package com.amber;


import com.amber.dao.UserDao;
import com.amber.model.News;
import com.amber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

@SpringBootApplication
public class ToutiaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToutiaoApplication.class, args);
	}
}
