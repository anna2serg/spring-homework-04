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
public class SelectRusCommands {
	private final TestService service;
	
    @Autowired
    public SelectRusCommands(TestService service) {
        this.service = service;
    }

    @ShellMethod("Задать вариант ответа. Параметры: вариант - выбранный вариант ответа")
    public void может(@ShellOption String вариант) {
        service.select(вариант);
    }
}
