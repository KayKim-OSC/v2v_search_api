package com.samsung.v2v.v2v_search_api.config.opensearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
@Primary
public class OpensearchConfig {

    @Value("${opensearch.targets}")
    private String targets;
    @Value("${opensearch.port}")
    private int port;

    @Value("${opensearch.method}")
    private String method;

    @Bean
    public RestHighLevelClient openSearchClient()
    {
        RestClientBuilder builder = RestClient.builder(new HttpHost(targets, port, method));
        builder.setRequestConfigCallback(requestConfigBuilder ->
                requestConfigBuilder
                        .setConnectTimeout(10000)
                        .setSocketTimeout(60000)
                        .setConnectionRequestTimeout(0)
        );

        return new RestHighLevelClient(builder);
    }


}
