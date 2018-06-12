package com.amber;

import com.AmberApplication;
import com.amber.dao.TicketDao;
import com.amber.model.Ticket;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AmberApplication.class)
public class AmberApplicationTests {

	@Autowired
	TicketDao ticketDao;

	@Test
	public void contextLoads() {
		for(int i = 0;i < 11;i++){
//			Ticket ticket = new Ticket();
//			ticket.setStatus(0);
//			Date date = new Date();
//			date.setTime(date.getTime()+1000*3600*24);
//			ticket.setExpired(date);
//			ticket.setUserId(i+1);
//			ticket.setTicket(String.format("Ticket%d",i));
//			ticketDao.addTicket(ticket);
			ticketDao.updateStatus(2,String.format("Ticket%d",i));
		}
		Assert.assertEquals(4,ticketDao.selectByTicket("Ticket3").getId());
	}

}
