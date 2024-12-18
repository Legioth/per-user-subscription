package com.example.application.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.shared.Registration;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.EndpointSubscription;

import jakarta.annotation.security.PermitAll;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@PermitAll
@BrowserCallable
public class SubscriptionService {
    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private FakeBackend backend;

    public EndpointSubscription<String> subscribe() {
      String username = authenticatedUser.get().get().getUsername();

      Many<String> sink = Sinks.many().unicast().onBackpressureError();

      Runnable unsubscribe = backend.subscribe(username, sink::tryEmitNext);

      return EndpointSubscription.of(sink.asFlux(), unsubscribe);
    }
}
