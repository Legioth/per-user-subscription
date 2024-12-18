package com.example.application.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

@Service
public class FakeBackend {
  private Timer timer = new Timer();

  public Runnable subscribe(String username, Consumer<String> listener) {
    var task = new TimerTask() {
      @Override
      public void run() {
        listener.accept("State for " + username +  " at " + LocalDateTime.now());
      }
    };

    timer.scheduleAtFixedRate(task, 0, Duration.ofSeconds(5).toMillis());

    return () -> task.cancel();
  }
}
