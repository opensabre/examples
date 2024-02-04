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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.opensabre.common.core.exception.SystemErrorType.SYSTEM_BUSY;

@Tag(name = "user")
@ApiResponse(responseCode = "200", description = "处理成功", content = @Content(schema = @Schema(implementation = Result.class)))
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Operation(summary = "用户管理", description = "hello user")
    @SentinelResource(value = "getUser", fallback = "exceptionHandler")
    @GetMapping("/get")
    public Map<String, String> getUser(@RequestParam String userId) {
        return userService.getUser(userId);
    }

    /**
     * Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
     *
     * @param ex 中断异常
     * @return 中断响应结果
     */
    public Result exceptionHandler(BlockException ex) {
        log.warn(SYSTEM_BUSY.getMesg(), ex.getMessage());
        return Result.fail(SYSTEM_BUSY);
    }

    @PostConstruct
    public void initFlowRules(){
        //1.创建存放限流规则的集合
        List<FlowRule> rules = new ArrayList<>();
        //2.创建限流规则
        FlowRule rule = new FlowRule();
        //定义资源，表示sentinel会对这个资源生效
        rule.setResource("getUser");
        //定义限流规则类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //定义QPS每秒能通过的请求个数
        rule.setCount(1);
        rule.setStrategy(RuleConstant.STRATEGY_DIRECT);
        //3.将限流规则放入集合中
        rules.add(rule);
        //4.加载限流规则
        FlowRuleManager.loadRules(rules);
    }
}
