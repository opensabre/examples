# Servlet 内部 Token 实验

## 目标

验证 0.7.0 的 Servlet 调用链：

```text
外部 JWT -> sample-cloud -> x-client-token -> sample-provider
```

- `sample-cloud` 校验外部 JWT，完成 Spring Security 认证后通过 Feign 调用下游。
- Feign 删除外部 `Authorization`，面向 `sample-provider` 逐跳签发内部 Token。
- `sample-provider` 要求内部 Token，并在 Spring Security 权限判断前完成验签。
- `sample-webflux` 不参与本轮实验。

## 准备共享密钥

本地实验可生成一次临时 256 位密钥，并在启动两个应用的终端中使用同一组环境变量：

```bash
export OPENSABRE_SECURITY_INTERNAL_TOKEN_ACTIVE_KEY_ID=local-v1
export OPENSABRE_SECURITY_INTERNAL_TOKEN_ACTIVE_KEY="$(openssl rand -base64 32)"
export OPENSABRE_SECURITY_INTERNAL_TOKEN_KEY_CONFIG_VERSION=1
```

不要把生成值写入仓库、日志或截图。接入 Nacos 后应改为从共享
`opensabre-security.yml` 动态加载。

## 启动

先启动下游：

```bash
cd sample-provider
./gradlew bootRun
```

再启动首应用：

```bash
cd sample-cloud
AUTH_ISSUER_URI=http://localhost:8000 \
AUTH_JWK_SET_URI=http://localhost:8000/oauth2/jwks \
./gradlew bootRun
```

通过网关或携带有效 Bearer JWT 调用：

```text
GET http://localhost:8080/user/get?userId=101
```

预期：

- 无外部 JWT 调用 `sample-cloud` 被拒绝。
- 有效 JWT 通过首应用认证。
- `sample-cloud` 调用 `sample-provider` 时只发送新签发的 `x-client-token`。
- 直接匿名调用 `sample-provider/user/101` 返回 401。
- 两个应用使用不同密钥、错误 audience 或过期 Token 时，下游返回 401。

## 轮换实验

1. 两端配置 active=A。
2. 管理端轮换为 active=B、previous=A。
3. 等待两端刷新后，A/B 签发的未过期 Token 均可验证。
4. 超过 `previous-key-retire-after` 后退役 A。
5. 验证仅 B 可用，并在通用审计日志中确认轮换操作、结果和原因均已记录，
   且没有密钥内容。
