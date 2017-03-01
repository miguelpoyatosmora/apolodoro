package com.apolodoro.backend.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class Dao {

    private final static Logger LOG = LoggerFactory.getLogger(Dao.class);


    @Autowired
    private Client client;

    public User executeUserRequest(String providedUsername) {

        LOG.info("Requested authentication for " + providedUsername);

        GetRequestBuilder getRequestBuilder = client.prepareGet("users", "user", providedUsername);

        try {

            GetResponse response = getRequestBuilder.get();
            if (!response.isExists() || response.getSource() == null) {
                throw new BadCredentialsException("");
            }

            byte[] sourceAsBytes = response.getSourceAsBytes();
            User user = ModelMapper.INSTANCE.map(sourceAsBytes, new TypeReference<User>() {});
            return user;

        } catch (ElasticsearchException e) {

            LOG.error("Error requesting user", e);
            throw new RuntimeException("Internal server error");
        }
    }

}
