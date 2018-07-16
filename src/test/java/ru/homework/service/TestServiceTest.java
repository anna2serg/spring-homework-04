package ru.homework.service;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;

import ru.homework.common.TestStatUnit;
import ru.homework.configuration.AppSettings;
import ru.homework.dao.TestBoxDao;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
	InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
	ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})	
public class TestServiceTest {
	
	@MockBean
	private AppSettings settings;
	
	@MockBean
	private MessageSource messageSource;
	
	/*Bean from TestBoxDaoTestConfiguration*/
	@Autowired
	private TestBoxDao dao;
	
	@Autowired
	private TestService service;	
	
	@Test
	public void test() {
		given(this.settings.getLocale()).willReturn("en");
		when(this.messageSource.getMessage(
											Mockito.any(String.class), 
											Mockito.any(), 
											Mockito.any(Locale.class)  
			 )).thenAnswer(new Answer<String>() {
			    @Override
			    public String answer(InvocationOnMock invocation) throws Throwable {
			      Object[] args = invocation.getArguments();
			      return (String) args[0];
			    }
			 });
		assertEquals(this.service.getLocalizedValue("boo"), "boo");
		assertTrue(this.dao.count() == 2);
		assertEquals(service.start("Casper"), TestService.TestState.isRunning);
		service.select("1");
		service.nextTest();
		service.select("1,3");
		assertEquals(service.stop(), TestService.TestState.isCompleted);
		List<TestStatUnit> statCasper = service.statout("Casper");
		assertTrue(statCasper.size() == 1);
		assertTrue(statCasper.get(0).getIsPassed());
		assertTrue(statCasper.get(0).getPercent() == 50);
	}

}
