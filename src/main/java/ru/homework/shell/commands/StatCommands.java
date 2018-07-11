package ru.homework.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ru.homework.service.TestService;

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
    
    @ShellMethod("Вывод статистики. Параметры: имя - имя тестируемого")
    public void статистика(@ShellOption(defaultValue="") String имя) {
        service.statout(имя);
    }
}
