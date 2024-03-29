package com.opensabre.sample.rest;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.opensabre.sample.service.IUserService;
import io.github.opensabre.common.core.entity.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "用户")
@ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = Result.class)))
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Operation(summary = "获取用户", description = "hello user")
    @SentinelResource(value = "getUser", blockHandler = "exceptionHandler")
    @GetMapping("/get")
    public Map<String, String> getUser(@RequestParam String userId) {
        return userService.getUser(userId);
    }

    @Operation(summary = "获取用户订单", description = "hello order")
    @SentinelResource(value = "getOrder")
    @GetMapping("/order")
    public Map<String, String> getOrder(@RequestParam String userId) {
        return userService.getUser(userId);
    }

    @Operation(summary = "获取用户地址", description = "hello address")
    @GetMapping("/address")
    public Map<String, String> getAddress(@RequestParam String userId) {
        return userService.getUser(userId);
    }

    /**
     * Block 异常处理函数，参数最后可多一个 BlockException，其余与原函数一致.
     *
     * @param userId 用户id
     * @param ex     中断异常
     * @return 中断响应结果
     */
    public Map<String, String> exceptionHandler(String userId, BlockException ex) {
        log.warn("flow control param:{}, rule: {}", userId, ex.getRule());
        return new HashMap<>();
    }

    @PostConstruct
    public void initFlowRules() {
        //1.创建存放限流规则的集合
        List<FlowRule> rules = new ArrayList<>();
        //2.创建限流规则
        FlowRule rule = new FlowRule();
        //定义资源，表示sentinel会对这个资源生效
        rule.setResource("getOrder");
        //定义限流规则类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //定义QPS每秒能通过的请求个数
        rule.setCount(2);
        rule.setStrategy(RuleConstant.STRATEGY_DIRECT);
        //3.将限流规则放入集合中
        rules.add(rule);
        //4.加载限流规则
        FlowRuleManager.loadRules(rules);
    }
}
