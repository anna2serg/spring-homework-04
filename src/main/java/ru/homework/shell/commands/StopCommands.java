package ru.homework.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.homework.service.TestService;

@ShellComponent
public class StopCommands {
	private final TestService service;
	
    @Autowired
    public StopCommands(TestService service) {
        this.service = service;
    }
	
    @ShellMethod("Abort the test")
    public void leavemealone() {
        service.stop();
    }
    
    @ShellMethod("Прервать тестирование")
    public void оставьменявпокое() {
        service.stop();
    }
}
