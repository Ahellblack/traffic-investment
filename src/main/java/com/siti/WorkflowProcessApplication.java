package com.siti;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@MapperScan("com.siti.*.mapper")
@ServletComponentScan
@EnableAutoConfiguration
@Slf4j
public class WorkflowProcessApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws UnknownHostException {

		ConfigurableApplicationContext application = SpringApplication.run(WorkflowProcessApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		log.info("\n----------------------------------------------------------\n\t" +
				"Application workflow is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + path + "/\n\t" +
				"External: \thttp://" + ip + ":" + port + path + "/\n\t" +
				"swagger-ui: \thttp://" + ip + ":" + port + path + "/swagger-ui.html\n\t" +
				"Doc: \t\thttp://" + ip + ":" + port + path + "/doc.html\n" +
				"----------------------------------------------------------");
	}

}
