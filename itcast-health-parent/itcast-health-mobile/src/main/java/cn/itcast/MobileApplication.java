package cn.itcast;

import cn.itcast.controller.WebSocketServer;
import cn.itcast.service.CheckPayLogService;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
@EnableWebSocket
@EnableDubbo
public class MobileApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext application = SpringApplication.run(MobileApplication.class, args);

        /*CheckPayLogService checkPayLogService = application.getBean(CheckPayLogService.class);
        WebSocketServer.setCheckPayLogService(checkPayLogService);*/
    }

    /**
     * 初始化Bean，它会自动注册使用了 @ServerEndpoint 注解声明的 WebSocket endpoint
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
