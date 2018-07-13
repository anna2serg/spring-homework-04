package ru.homework.shell.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import ru.homework.configuration.AppSettings;

public class CommandEngCondition implements Condition {
	
	private final AppSettings appSettings;
	
	public CommandEngCondition(AppSettings appSettings) {
		this.appSettings = appSettings;
	}
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return appSettings.getLocale().equals("en");
	}
}
