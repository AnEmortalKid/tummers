package com.anemortalkid.yummers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TummersUiApplication.class)
@WebAppConfiguration
@Controller
public class TummersUiApplicationTests {

	@Test
	public void contextLoads() {
	}

	@RequestMapping("/calendar")
	public String calendar() {
		return "calendar";
	}

}
