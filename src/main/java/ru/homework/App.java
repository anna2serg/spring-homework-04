package ru.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import ru.homework.service.TestService;

@ComponentScan("ru.homework")
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(App.class, args);
    	TestService t = context.getBean(TestService.class);
    	t.startTest();		
	}
	
    @Bean
    public MessageSource messageSource() {
    	ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    	ms.setBasename("/i18n/bundle");
    	ms.setDefaultEncoding("UTF-8");
    	return ms;
    }
	
}
