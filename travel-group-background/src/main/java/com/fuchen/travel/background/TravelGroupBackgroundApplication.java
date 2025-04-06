package com.fuchen.travel.background;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class TravelGroupBackgroundApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(TravelGroupBackgroundApplication.class, args);
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
        log.info("\n---------------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local访问网址: \t\thttp://localhost:" + port + path + "/login" + "\n\t" +
                "External访问网址: \thttp://" + ip + ":" + port + path + "/login" + "\n" +
                "---------------------------------------------------------------");
    }

}
