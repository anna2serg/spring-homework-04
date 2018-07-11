package ru.homework.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.homework.service.TestService;

@ShellComponent
public class AskCommands {
	private final TestService service;
	
    @Autowired
    public AskCommands(TestService service) {
        this.service = service;
    }
	
    @ShellMethod("Get the next question")
    public void askmore() {
        service.nextTest();
    }
    
    @ShellMethod("Получить следующий вопрос")
    public void спросиеще() {
        service.nextTest();
    }
}
