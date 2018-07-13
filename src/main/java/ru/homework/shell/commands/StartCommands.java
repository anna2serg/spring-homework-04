package ru.homework.shell.commands;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ru.homework.configuration.AppSettings;
import ru.homework.service.TestService;

@ConditionalOnProperty(
		value = "application.locale" ,
		havingValue = "en"
	)
@ShellComponent
public class StartCommands {
	
	private final TestService service;
	
    @Autowired
    public StartCommands(TestService service) {
        this.service = service;
    }
    
    @ShellMethod("Run test. Params: myname - test person name")
    public void testme(@ShellOption(defaultValue="") String myname) {
        service.start(myname);
    }
    
}
