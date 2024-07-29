package com.moaaz.modernhome.Configuration;

import freemarker.cache.ClassTemplateLoader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FreemarkerConfiguration {
	@Bean
	public freemarker.template.Configuration configuration() {

		freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_30);
		configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/freemarkers/"));
		configuration.setDefaultEncoding("UTF-8");

		return configuration;
	}


}
