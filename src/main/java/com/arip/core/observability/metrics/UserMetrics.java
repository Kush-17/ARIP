package com.arip.core.observability.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserMetrics {
    private final Counter userCreated;
    private final Counter userUpdated;
    private final Counter userDeleted;

    public UserMetrics(MeterRegistry registry) {
        this.userCreated = Counter.builder("arip_users_created")
                .description("Total users created")
                .tag("module", "user")
                .register(registry);

        this.userUpdated = Counter.builder("arip_users_updated")
                .description("Total users updated")
                .tag("module", "user")
                .register(registry);

        this.userDeleted = Counter.builder("arip_users_deleted")
                .description("Total users deleted")
                .tag("module", "user")
                .register(registry);
    }

    public void incrementUserCreated() {
        userCreated.increment();
    }

    public void incrementUserUpdated() {
        userUpdated.increment();
    }

    public void incrementUserDeleted() {
        userDeleted.increment();
    }
}
