package ru.homework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.homework.common.Answer;
import ru.homework.common.TestStatUnit;
import ru.homework.common.TestUnit;
import ru.homework.configuration.AppSettings;
import ru.homework.dao.TestBoxDao;

@Service
public class TestService {
	
	public enum TestState {isUndefined, isRunning, isCompleted}
	
	private TestState state = TestState.isUndefined;
	private final AppSettings settings;
	private Locale locale = Locale.ENGLISH;
	private final TestBoxDao dao;
	private final MessageSource messageSource;
	
	private String user;
	private List<Integer> userAnswers;
	private int userScore = 0;
	private TestUnit testUnit;
	private int counter = 0;
	private boolean isAnswered = false;
	
	private List<Map<String,TestStatUnit>> statTable;
	
	@Autowired
    public TestService(AppSettings settings, TestBoxDao dao, MessageSource messageSource) {
		this.settings = settings;
		if (this.settings.getLocale()!=null) 
			this.locale = new Locale(this.settings.getLocale());
    	this.dao = dao;	
    	this.messageSource = messageSource;
    	userAnswers = new ArrayList<Integer>();
    	statTable = new ArrayList<Map<String,TestStatUnit>>();
    }	
     
	private boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}	
	
	protected String getLocalizedValue(String value) {
		return messageSource.getMessage(value, null, locale);
	}
	
	private String getLocalizedValue(String value, Object[] args) {
		return messageSource.getMessage(value, args, locale);
	}	
	
	private String getAnonymous() {
		return getLocalizedValue("test.anonymous");
	}
	
	private void reset() {
		counter = 0;
		user = "";
		userScore = 0;		
		userAnswers.clear();
		isAnswered = false;
		dao.open();
	}

	@SuppressWarnings("resource")
	public TestState start(String name) {
		reset();
	    if (dao.count()==0) return state;	

	    Scanner in = new Scanner(System.in);
	    System.out.println(getLocalizedValue("test.name"));
	    System.out.println();     
	    System.out.println(getLocalizedValue("test.description"));
	    System.out.println();
	    
	    if (name.equals("")) {
	    	System.out.println(getLocalizedValue("input.name"));
	    	String inputName = in.nextLine(); 
	    	this.user = inputName.equals("") ? getAnonymous()+"_"+java.util.UUID.randomUUID().toString() : inputName;
	    } else {
	    	this.user = name;
	    }

		System.out.println(getLocalizedValue("hello.user", new String[] {this.user}));
		System.out.println();

	   	if (!dao.isEOF()) {
	   		getTest();
	   		state = TestState.isRunning;
	   	} 
	   	
	   	return state;
	}
	
	public void getTest() {
		TestUnit tu = dao.getTest();
	   	String question = "";
	   	String answers = "";
	   	boolean multi = false;
	   	String singleHint = getLocalizedValue("hint.answer.single");
	   	String multiHint = getLocalizedValue("hint.answer.multi");
	   	    	 
		question = getLocalizedValue(tu.getQuestion());
		multi = tu.isMultiChoice();
		answers = "";
		
		List<Answer> answerList = tu.getAnswers();
		for (Answer ans : answerList) {
			String ansStr = tryParseInt(ans.toString()) ? ans.toString() : getLocalizedValue(ans.toString());
			answers += ans.getId() + " - " + ansStr + "\r\n";
		}	 
		System.out.println(question);
		if (multi) System.out.println(multiHint);
		else System.out.println(singleHint);
		System.out.println(answers);
		System.out.println(getLocalizedValue("user.answer"));
		testUnit = tu;
		counter += 1;
	}
	
	private void scoring() {
	    if (dao.count() > 0) {
	    	System.out.println();
	    	String scoreResult = getLocalizedValue("score.result",  new Object[]{user, userScore, 10*dao.count()});
	    	System.out.println(scoreResult);
		 
	    	
	    	int userScorePercent = 10 * userScore / dao.count();
	    	String rankResult = userScorePercent>80 ? getLocalizedValue("score.result.excellent") :
	    						userScorePercent>55 ? getLocalizedValue("score.result.good") :
	    						userScorePercent>20 ? getLocalizedValue("score.result.notbad") :	 
	    											  getLocalizedValue("score.result.soso"); 	 	
	    	System.out.println(rankResult);
	    	
	    	TestStatUnit statUnit = new TestStatUnit(counter==dao.count(), userScorePercent);
	    	Map<String,TestStatUnit> userStat = new HashMap<String,TestStatUnit>();
			userStat.put(user, statUnit);
			statTable.add(userStat);	    	
	    	
	    }	
	    state = TestState.isCompleted;		
	}
	
	public void nextTest() {
		if (state!=TestState.isRunning) {
			System.out.println(getLocalizedValue("test.state.isNotRunning"));
			return;
		}		
		if (!dao.isEOF()) {
			dao.nextTest();
			getTest();
			isAnswered = false;
		} else scoring();
	}
	
	public void select(String option) {
		if (state!=TestState.isRunning) {
			System.out.println(getLocalizedValue("test.state.isNotRunning"));
			return;
		}
		if (isAnswered) {
			System.out.println(getLocalizedValue("hint.answered"));
			return;			
		} else isAnswered = true;
		userAnswers.clear();
		String[] userInput = option.split(","); 
		for (String ui : userInput) {
			int uiId = -1;
			try {
			 	uiId = Integer.parseInt(ui.trim());	 
			} catch (NumberFormatException e) {
				uiId = -1;
			}	
			if (uiId != -1) {
				userAnswers.add(uiId);
			}	
			if (!testUnit.isMultiChoice()) break;
		}	
		if (testUnit.isRightAnswers(userAnswers)) {
			userScore += 10;
		}		
		if (dao.isEOF()) scoring();
		else {
			System.out.println(getLocalizedValue("hint.position", new Object[]{dao.count() - counter, dao.count()}));
		}
	}
	
	public TestState stop() {
		if (state!=TestState.isRunning) {
			System.out.println(getLocalizedValue("test.state.isNotRunning"));
			return state;
		}	
		scoring();	
		reset();
		return state;
	}
	
	private TestStatUnit statoutUser(Map<String, TestStatUnit> userStat) {
		TestStatUnit stat = null;
		for (String user : userStat.keySet()) {
			stat = userStat.get(user);
			System.out.print(user+": ");
			System.out.print(stat.getIsPassed() ? getLocalizedValue("test.passed") : getLocalizedValue("test.failed"));
			System.out.print(", ");
			System.out.print(getLocalizedValue("test.rightanswers")+": ");
			System.out.print(stat.getPercent()+"%");	
			System.out.println();
			break;
		}	
		return stat;
	}
    
	public List<TestStatUnit> statout(String name) {
		List<TestStatUnit> result = new ArrayList<TestStatUnit>();
		//для всех
		if (name.equals("")) {
			if (statTable.size()==0) {
				System.out.println(getLocalizedValue("score.hint.empty"));
			} else {
				for (Map<String, TestStatUnit> userStat : statTable) {
					result.add(statoutUser(userStat));
				}			
			}
		} else {
			for (Map<String, TestStatUnit> userStat : statTable) {
				if (userStat.containsKey(name)) {
					result.add(statoutUser(userStat));
				}
			}
		}
		return result;
	}
	
}
