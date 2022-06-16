package com.abdul_Codefellowship.codefellowship;

import com.abdul_Codefellowship.codefellowship.nytimes.News;
import com.abdul_Codefellowship.codefellowship.nytimes.NewsWriter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

class CodefellowshipApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testNews() throws IOException {
		NewsWriter sut = new NewsWriter();
		News x = sut.newsReport();
		return;
	}

}
