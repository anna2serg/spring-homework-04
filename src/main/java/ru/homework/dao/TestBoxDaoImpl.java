package ru.homework.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.homework.common.Answer;
import ru.homework.common.TestUnit;
import ru.homework.configuration.TestSettings;
import ru.homework.reader.CSVReader;

@Repository
public class TestBoxDaoImpl implements TestBoxDao {
	
	private List<TestUnit> testList;
	private int index = 0; 
	
	private final TestSettings settings;
	private final CSVReader csvReader;
	
	protected void testListLoad(List<List<String>> testTable) {
		reset();
		testList = new ArrayList<TestUnit>();
		for (List<String> rows : testTable) {
			TestUnit testUnit = new TestUnit();
			String var = "";
			List<Integer> rightAnswers = new ArrayList<Integer>();
			Boolean varRight = false; 
			int iCol = 0;
			int idAnswer = 1;
			for (String col : rows) {
				String s = col.trim();
				if (iCol==0) testUnit.setQuestion(s);
				else {
					if (iCol%2==0) {
						varRight = s.equals("1");
						if (varRight) rightAnswers.add(idAnswer);
						testUnit.addAnswer(new Answer(idAnswer, var));
						idAnswer += 1;
					}
					else {
						var = s;
					}
				}
				iCol++;
			}	
			testUnit.setRightAnswers(rightAnswers);
			testList.add(testUnit);
		}
	}
	
	@Autowired
    public TestBoxDaoImpl(TestSettings settings, CSVReader csvReader) {
    	 this.csvReader = csvReader;
    	 this.settings = settings;
    }
	
	public void load(List<List<String>> sources) {
		testListLoad(sources);
	}
	
	@Override
	public void open() {
		if (testList == null) {
			List<List<String>> parsedStrings = csvReader.parse(new File(settings.getFile()));
			testListLoad(parsedStrings);
		}
		reset();
	}
	
	@Override
	public boolean isEOF() {
		return ((testList.size() <= 0)||(index == testList.size() - 1));
	}

	@Override
	public int count() {
		return testList.size();
	}

	@Override
	public TestUnit nextTest() {
		index += 1;
		return testList.get(index);
	}
	
	@Override
	public TestUnit getTest() {
		return testList.get(index);
	}	

	@Override
	public void reset() {
		index = 0;
	}		
	
}
