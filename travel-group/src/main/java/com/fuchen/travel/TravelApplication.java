package com.fuchen.travel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Fu chen
 * @date 2022/10/25
 **/
@Slf4j
@SpringBootApplication
public class TravelApplication {
	
	@PostConstruct
	public void init(){
		//解决netty启动冲突问题
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(TravelApplication.class, args);
		/** TODO 打印成功启动日志 */
		log.info("\n" +
				"..######...##.....##...######....######...########...######....######.\n" +
				".##....##..##.....##..##....##..##....##..##........##....##..##....##\n" +
				".##........##.....##..##........##........##........##........##......\n" +
				"..######...##.....##..##........##........######.....######....######.\n" +
				".......##..##.....##..##........##........##..............##........##\n" +
				".##....##..##.....##..##....##..##....##..##........##....##..##....##\n" +
				"..######....#######....######....######...########...######....######.");
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		if (StringUtils.isEmpty(path)) {
			path = "";
		}
		log.info("\n----------------------------------------------------------\n\t" +
				"Application  is running! Access URLs:\n\t" +
				"Local访问网址: \t\thttp://localhost:" + port + path + "\n\t" +
				"External访问网址: \thttp://" + ip + ":" + port + path + "\n\t" +
				"----------------------------------------------------------");
	}
	
}
