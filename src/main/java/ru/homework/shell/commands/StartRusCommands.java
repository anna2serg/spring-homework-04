package ru.homework.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ru.homework.service.TestService;

@ConditionalOnProperty(
	value = "application.locale" ,
	havingValue = "ru"
)
@ShellComponent
public class StartRusCommands {
	
	private final TestService service;
	
    @Autowired
    public StartRusCommands(TestService service) {
        this.service = service;
    }   
    
    @ShellMethod("Запуск теста. Параметры: моеимя - имя тестируемого")
    public void тестмне(@ShellOption(defaultValue="") String моеимя) {
        service.start(моеимя);
    }
    
}