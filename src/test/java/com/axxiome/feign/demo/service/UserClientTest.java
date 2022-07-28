package com.axxiome.feign.demo.service;

import com.axxiome.feign.demo.exception.UserNotAuthorizedException;
import com.axxiome.feign.demo.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9092)
class UserClientTest {

    @Autowired
    public UserClient userClient;

    @Test
    void getUsers_whenValidClient_returnValidResponse() {
        // given
        stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(readFile("getUsers_response.json"))));
        // when
        List<User> users = userClient.getUsers();

        // then
        assertThat(users).isNotNull().isNotEmpty();
        assertThat(users).hasSize(4);

        User user = users.get(0);
        assertThat(user.getUserId()).isEqualTo(1);
        assertThat(user.getLogin()).isEqualTo("user_feign_1");
    }

    @Test
    void getUsers_whenCredentialsAreNotValid_throwUserNotAuthorizedException() {
        // given
        stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.UNAUTHORIZED.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
        // when,then
       assertThrows(UserNotAuthorizedException.class, () -> userClient.getUsers());
    }

    @SneakyThrows
    private String readFile(String fileName) {
        return new String(getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes());

    }

}