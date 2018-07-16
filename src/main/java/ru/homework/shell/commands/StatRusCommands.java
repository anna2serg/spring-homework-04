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
public class StatRusCommands {
	private final TestService service;
	
    @Autowired
    public StatRusCommands(TestService service) {
        this.service = service;
    }
	
    @ShellMethod("Вывод статистики. Параметры: имя - имя тестируемого")
    public void статистика(@ShellOption(defaultValue="") String имя) {
        service.statout(имя);
    }
}
