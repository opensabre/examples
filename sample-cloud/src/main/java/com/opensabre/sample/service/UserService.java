package com.opensabre.sample.service;

import com.opensabre.sample.provider.UserProvider;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService implements IUserService {
    @Resource
    UserProvider userProvider;

    @Override
    public Map<String, String> getUser(String userId) {
        return userProvider.getUser(userId).getData();
    }
}