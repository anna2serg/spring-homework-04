package ru.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@ComponentScan("ru.homework")
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);	
	}
	
    @Bean
    public MessageSource messageSource() {
    	ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    	ms.setBasename("/i18n/bundle");
    	ms.setDefaultEncoding("UTF-8");
    	return ms;
    }
	
}
