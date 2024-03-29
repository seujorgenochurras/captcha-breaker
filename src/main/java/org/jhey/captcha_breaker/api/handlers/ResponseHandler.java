package org.jhey.captcha_breaker.api.handlers;

import org.jhey.captcha_breaker.api.request.dto.AssemblyResponseDTO;

import java.time.Duration;
import java.util.concurrent.*;

public class ResponseHandler {
      final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
      private Duration checkDelay;

      public ResponseHandler(Duration checkDelay) {
            this.checkDelay = checkDelay;
      }

      public CompletableFuture<AssemblyResponseDTO> handle(AssemblyResponseDTO responseDTO, final Duration checkDelay, final String assemblyAiToken){

            CompletableFuture<AssemblyResponseDTO> completableFuture = new CompletableFuture<>();

            AudioRunnable audioRunnable = new AudioRunnable(completableFuture, responseDTO, assemblyAiToken);

            executorService.scheduleWithFixedDelay(
                   audioRunnable,
                    1,
                    checkDelay.getSeconds(),
                    TimeUnit.SECONDS);
           completableFuture.thenRun(executorService::shutdownNow);
            return completableFuture;
      }
      public void setCheckDelay(Duration checkDelay) {
            this.checkDelay = checkDelay;
      }

      public Duration getCheckDelay() {
            return checkDelay;
      }
}
