package io.github.opensabre.sample.cache.rest;

import io.github.opensabre.sample.cache.service.IConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Resource
    private IConfigService configService;

    @GetMapping("/config")
    public String hello(@RequestParam String key) {
        String value = configService.getConfig(key);
        return key + ":" + value;
    }

    @GetMapping("/user")
    public String getUser(@RequestParam String userId) {
        String user = configService.getUser(userId);
        return userId + ":" + user;
    }

    @PostMapping("/user")
    public String updateUser(@RequestParam String userId, String user) {
        configService.updateUser(userId, user);
        return userId + ":" + "update";
    }

    @DeleteMapping("/user")
    public String deleteUser(@RequestParam String userId) {
        configService.deleteUser(userId);
        return userId + ":" + "delete";
    }
}
