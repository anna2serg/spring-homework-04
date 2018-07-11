package ru.homework.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ru.homework.service.TestService;

@ShellComponent
public class SelectCommands {
	private final TestService service;
	
    @Autowired
    public SelectCommands(TestService service) {
        this.service = service;
    }
	
    @ShellMethod("Set answer option. Params: option - test person option")
    public void maybe(@ShellOption String option) {
        service.select(option);
    }
    
    @ShellMethod("Задать вариант ответа. Параметры: вариант - выбранный вариант ответа")
    public void может(@ShellOption String вариант) {
        service.select(вариант);
    }
}
