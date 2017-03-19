package com.apolodoro.backend.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticsearchConfiguration {

    @Bean(destroyMethod = "close")
    public Client getClient() throws UnknownHostException {

        Settings settings = Settings.builder()
                .put("client.transport.sniff",true)
                .put("cluster.name", "docker-cluster").build();

        return new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch1"), 9300));

    }
}

