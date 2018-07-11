package ru.homework.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ru.homework.service.TestService;

@ShellComponent
public class StartCommands {
	
	private final TestService service;
	
    @Autowired
    public StartCommands(TestService service) {
        this.service = service;
    }
	
    @ShellMethod("Run test. Params: name - test person name")
    public void start(@ShellOption String name) {
        service.start(name);
    }
    
    @ShellMethod("Запуск теста. Параметры: имя - имя тестируемого")
    public void старт(@ShellOption String имя) {
        service.start(имя);
    }
    
}
