package ru.homework.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.homework.service.TestService;

@ConditionalOnProperty(
		value = "application.locale" ,
		havingValue = "ru"
	)
@ShellComponent
public class StopRusCommands {
	private final TestService service;
	
    @Autowired
    public StopRusCommands(TestService service) {
        this.service = service;
    }
    
    @ShellMethod("Прервать тестирование")
    public void оставьменявпокое() {
        service.stop();
    }
}
