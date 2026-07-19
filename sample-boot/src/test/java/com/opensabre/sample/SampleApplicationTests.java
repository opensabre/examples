package com.opensabre.sample;

import com.opensabre.sample.eda.SampleCreatedEvent;
import com.opensabre.sample.eda.SampleCreatedEventHandler;
import com.opensabre.sample.eda.SampleEventRecorder;
import io.github.opensabre.eda.api.EdaEvent;
import io.github.opensabre.eda.api.EdaEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SampleApplicationTests {

    @Autowired
    private EdaEventPublisher eventPublisher;

    @Autowired
    private SampleEventRecorder recorder;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldPublishAndHandleLocalEdaEvent() throws Exception {
        EdaEvent<SampleCreatedEvent> event = EdaEvent.of(SampleCreatedEventHandler.EVENT_TYPE,
                "sample-boot", new SampleCreatedEvent("opensabre"));

        eventPublisher.publishLocal(event);

        EdaEvent<SampleCreatedEvent> handled = recorder.poll(2_000);
        assertThat(handled).isNotNull();
        assertThat(handled.eventId()).isEqualTo(event.eventId());
        assertThat(handled.payload().name()).isEqualTo("opensabre");
    }

}
