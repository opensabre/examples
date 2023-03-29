package io.github.opensabre.sample.cache.service;

import io.github.opensabre.sample.cache.provider.RemoteProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfigService implements IConfigService {
    @Resource
    RemoteProvider remoteProvider;

    @Override
    public String getConfig(String key) {
        return remoteProvider.getRemote(key);
    }

    @Override
    public String getUser(String userId) {
        return remoteProvider.getRemote(userId);
    }

    @Override
    public void updateUser(String userId, String user) {
    }

    @Override
    public void deleteUser(String userId) {
    }
}