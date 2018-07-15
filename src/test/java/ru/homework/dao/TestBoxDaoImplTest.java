package ru.homework.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import ru.homework.common.TestUnit;
import ru.homework.configuration.TestSettings;
import ru.homework.reader.CSVReader;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
	InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
	ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})	
public class TestBoxDaoImplTest {
	
	@Autowired
	private TestBoxDaoImpl testBoxDao;
	
	@MockBean 
	private CSVReader csvReader;
	
	@MockBean 
	private TestSettings settings;	
	
	private List<List<String>> testList;

	@Before
	public void init() {
		String[] row1 = {"question1", "answer11", "0", "answer12", "0", "answer13", "1", "answer14", "0"};
		String[] row2 = {"question2", "answer21", "1", "answer22", "0", "answer23", "1", "answer24", "0", "answer25", "0"};
		testList = new ArrayList<List<String>>();
		testList.add(Arrays.asList(row1));
		testList.add(Arrays.asList(row2));		
	}
	
	@Test
	public void test() {
		given(this.settings.getFile()).willReturn("mock");
		given(this.csvReader.parse(any(File.class))).willReturn(testList);
		testBoxDao.open();	
		assertTrue(testBoxDao.count() == 2);
		assertFalse(testBoxDao.isEOF());
		TestUnit tu = testBoxDao.getTest();
		assertEquals(tu.getQuestion(), "question1"); 
		assertEquals(tu.getAnswers().size(), 4); 
		assertFalse(tu.isMultiChoice());
		tu = testBoxDao.nextTest();
		assertEquals(tu.getQuestion(), "question2"); 
		assertEquals(tu.getAnswers().size(), 5); 	
		assertTrue(tu.isMultiChoice()); 
		assertTrue(testBoxDao.isEOF());
		testBoxDao.reset();
		assertFalse(testBoxDao.isEOF());
	}

}
