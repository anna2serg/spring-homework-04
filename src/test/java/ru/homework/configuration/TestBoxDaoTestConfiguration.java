package ru.homework.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import ru.homework.dao.TestBoxDao;
import ru.homework.dao.TestBoxDaoImpl;

@Profile("test")
@Configuration
public class TestBoxDaoTestConfiguration {
	
	@Bean	
	@Primary
	public TestBoxDao testBoxDao() {
		TestBoxDaoImpl dao = new TestBoxDaoImpl(null, null);
		String[] row1 = {"question1", "answer11", "0", "answer12", "0", "answer13", "1", "answer14", "0"};
		String[] row2 = {"question2", "answer21", "1", "answer22", "0", "answer23", "1", "answer24", "0", "answer25", "0"};
		List<List<String>> testList = new ArrayList<List<String>>();
		testList.add(Arrays.asList(row1));
		testList.add(Arrays.asList(row2));			
		dao.load(testList);
		return dao;
	}
	
}
