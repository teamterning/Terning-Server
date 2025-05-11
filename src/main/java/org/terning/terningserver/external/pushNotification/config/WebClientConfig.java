package org.terning.terningserver.external.pushNotification.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    private static final int CONNECT_TIMEOUT_MILLIS = 5000;
    private static final int READ_TIMEOUT_SECONDS = 2;
    private static final int WRITE_TIMEOUT_SECONDS = 2;

    private static final String DEFAULT_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @Value("${operation.base-url}")
    private String operationBaseUrl;

    @Bean
    public WebClient operationBaseUrlWebClient() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS))
                );

        HttpClient httpClient = HttpClient.from(tcpClient);

        return WebClient.builder()
                .baseUrl(operationBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, DEFAULT_CONTENT_TYPE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
