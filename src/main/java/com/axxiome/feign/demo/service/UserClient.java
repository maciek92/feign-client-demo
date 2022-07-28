package com.axxiome.feign.demo.service;

import com.axxiome.feign.demo.config.UserClientConfiguration;
import com.axxiome.feign.demo.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="users", url = "${client.url}", configuration = UserClientConfiguration.class)
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    List<User> getUsers();
}
