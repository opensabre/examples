package com.opensabre.sample.cache.rest;

import com.opensabre.sample.cache.service.IConfigService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Resource
    private IConfigService configService;

    @GetMapping("/config")
    public String config(@RequestParam String key) {
        String value = configService.getConfig(key);
        return key + ":" + value;
    }

    @GetMapping("/user")
    public String getUser(@RequestParam("userId") String userId) {
        String user = configService.getUser(userId);
        return userId + ":" + user;
    }

    @PostMapping("/user")
    public String updateUser(@RequestParam("userId") String userId, String user) {
        configService.updateUser(userId, user);
        return userId + ":" + "update";
    }

    @DeleteMapping("/user")
    public String deleteUser(@RequestParam("userId") String userId) {
        configService.deleteUser(userId);
        return userId + ":" + "delete";
    }
}
