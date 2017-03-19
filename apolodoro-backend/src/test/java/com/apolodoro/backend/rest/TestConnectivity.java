package com.apolodoro.backend.rest;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;

public class TestConnectivity {


    /**
     * docker run -p 9300:9300 -e "http.host=0.0.0.0" \
     *     -e "transport.host=0.0.0.0" -e "xpack.security.enabled=false" -e "cluster.name=docker-cluster" \
     *     docker.elastic.co/elasticsearch/elasticsearch:5.2.2
     */
    public static void main(String[] args) {


        Settings settings = Settings.builder()
                .put("client.transport.sniff",true)
                .put("cluster.name", "docker-cluster").build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("localhost", 9300)));


        try {
            SearchResponse response = client.prepareSearch().setQuery(QueryBuilders.matchQuery("url", "twitter")).setSize(5).execute().actionGet();//bunch of urls indexed
            String output = response.toString();
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }
}
