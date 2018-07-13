package ru.homework.shell.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CommandRusCondition implements Condition {
	
	//private final AppSettings appSettings;
	
	/*
	public CommandRusCondition(AppSettings appSettings) {
		this.appSettings = appSettings;
	}*/
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return context.getEnvironment().getProperty("os.name").contains("Linux");
		//AppSettings appSettings = (AppSettings) context.getBeanFactory().getBean("appSettings");
		//return appSettings.getLocale().equals("ru");
	}
	
}
