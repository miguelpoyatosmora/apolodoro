package com.apolodoro.backend.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ApplicationTest {


    ObjectMapper mapper = new ObjectMapper();

	@LocalServerPort
	private int port;

	private TestRestTemplate template = new TestRestTemplate();

	@MockBean
    private Client client;

	@Test
	public void userEndpointProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/user", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void resourceEndpointProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/resource", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void loginSucceeds() throws JsonProcessingException {

        mockClient("user", "password", "admin");


		TestRestTemplate template = new TestRestTemplate("user", "password");
		ResponseEntity<String> response = template.getForEntity("http://localhost:" + port
				+ "/user", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

    @Test
    public void loginFailsIfElasticsearchIsDown() throws IOException {

        GetRequestBuilder getRequestBuilder = mock(GetRequestBuilder.class);
        when(client.prepareGet("users", "user", "user")).thenReturn(getRequestBuilder);

	    when(getRequestBuilder.get()).thenThrow(new ElasticsearchException("message"));

        TestRestTemplate template = new TestRestTemplate("user", "password");
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/user", String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error",
                mapper.readTree(response.getBody()).get("message").asText());
    }

    private void mockClient(String user, String password, String role) throws JsonProcessingException {

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("username", user);
        userData.put("password", password);
        userData.put("roles", Collections.singletonList(role));

        GetResponse getResponse = mock(GetResponse.class);
        when(getResponse.isExists()).thenReturn(true);
		byte[] valueAsBytes = mapper.writeValueAsBytes(userData);
		when(getResponse.getSourceAsBytes()).thenReturn(valueAsBytes);
        GetRequestBuilder getRequestBuilder = mock(GetRequestBuilder.class);
        when(getRequestBuilder.get()).thenReturn(getResponse) ;
        when(client.prepareGet("users", "user", user)).thenReturn(getRequestBuilder);
    }


}
