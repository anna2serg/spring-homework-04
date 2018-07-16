package ru.homework.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ru.homework.service.TestService;

@ConditionalOnProperty(
		value = "application.locale" ,
		havingValue = "en"
	)
@ShellComponent
public class StatCommands {
	private final TestService service;
	
    @Autowired
    public StatCommands(TestService service) {
        this.service = service;
    }
	
    @ShellMethod("Statistics output. Params: name - test person name")
    public void stat(@ShellOption(defaultValue="") String name) {
        service.statout(name);
    }

}
