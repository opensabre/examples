package com.opensabre.sample.eda;

import io.github.opensabre.eda.api.EdaEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 示例测试使用的事件记录器，不参与业务逻辑。
 */
@Component
public class SampleEventRecorder {

    private final BlockingQueue<EdaEvent<SampleCreatedEvent>> events = new LinkedBlockingQueue<>();

    public void record(EdaEvent<SampleCreatedEvent> event) {
        events.offer(event);
    }

    public EdaEvent<SampleCreatedEvent> poll(long timeoutMillis) throws InterruptedException {
        return events.poll(timeoutMillis, TimeUnit.MILLISECONDS);
    }
}
