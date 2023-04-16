package org.jhey.captcha_breaker.api.handlers;

import org.jhey.captcha_breaker.api.request.dto.AssemblyResponseDTO;
import org.jhey.captcha_breaker.api.request.RequestBuilder;
import org.jhey.captcha_breaker.api.request.RequestState;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AudioRunnable implements Runnable {
   private final CompletableFuture<AssemblyResponseDTO> responseDTOCompletableFuture;
   private AssemblyResponseDTO audioProcessingResponse;

   private final String assemblyAIToken;
   public AudioRunnable(CompletableFuture<AssemblyResponseDTO> responseDTOCompletableFuture, AssemblyResponseDTO audioProcessingResponse, String assemblyAIToken) {
      this.responseDTOCompletableFuture = responseDTOCompletableFuture;
      this.audioProcessingResponse = audioProcessingResponse;
      this.assemblyAIToken = assemblyAIToken;
   }

   @Override
   public void run() {
      HttpRequest transcribedAudioStatus = RequestBuilder.buildGetAudioStateRequest(audioProcessingResponse, assemblyAIToken);
      try {
         audioProcessingResponse = RequestBuilder.getResponse(transcribedAudioStatus);
        RequestState processingStatus = audioProcessingResponse.getRequestState();
         if (processingStatus.equals(RequestState.DONE)){
            responseDTOCompletableFuture.complete(audioProcessingResponse);
         } else if (processingStatus.equals(RequestState.ERROR)) {
            throw new IllegalStateException("AssemblyAPI returned ERROR, check if the URL is valid");
         }
      } catch (IOException | InterruptedException e) {
         Logger logger = Logger.getLogger(AudioRunnable.class.getName());
         logger.log(Level.WARNING, "Error ".concat(e.getMessage()));
         Thread.currentThread().interrupt();
      }
   }
}
