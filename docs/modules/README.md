# 示例模块地图

| 示例 | 功能 | 使用与验证 | 规划 |
| --- | --- | --- | --- |
| `sample-boot` | 验证基础 Spring Boot 接入。 | 通过其构建脚本运行并检查应用启动。 | 补充基础自动配置断言。 |
| `sample-cache` | 验证缓存能力。 | 验证缓存读写、失效与配置。 | 增加常见缓存场景。 |
| `sample-cloud` | 验证 Cloud 集成。 | 需准备对应的注册/配置依赖。 | 补齐服务发现与配置刷新场景。 |
| `sample-provider` | 验证服务提供者能力。 | 参考模块内 `HELP.md` 和构建配置。 | 固化服务契约与调用验证。 |
| `sample-webflux` | 验证响应式 Web 能力。 | 启动后验证响应式端点。 | 增加非阻塞与错误处理用例。 |

[Servlet 内部 Token 实验](internal-token-servlet-poc.md) 联合验证 `sample-cloud` 与
`sample-provider`，不包含 `sample-webflux`。

每个示例的独立文档应覆盖：介绍、框架依赖、前置条件、启动、验证流程、预期结果和规划。
