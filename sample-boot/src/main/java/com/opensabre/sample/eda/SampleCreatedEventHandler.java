package com.opensabre.sample.eda;

import io.github.opensabre.eda.api.EdaEvent;
import io.github.opensabre.eda.api.EdaEventHandler;
import org.springframework.stereotype.Component;

/**
 * 验证 EDA starter 自动发现并异步调用业务处理器。
 */
@Component
public class SampleCreatedEventHandler implements EdaEventHandler<SampleCreatedEvent> {

    public static final String EVENT_TYPE = "sample.created";

    private final SampleEventRecorder recorder;

    public SampleCreatedEventHandler(SampleEventRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

    @Override
    public void handle(EdaEvent<SampleCreatedEvent> event) {
        recorder.record(event);
    }
}
