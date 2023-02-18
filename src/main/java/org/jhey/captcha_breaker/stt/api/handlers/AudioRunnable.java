package org.jhey.captcha_breaker.stt.api.handlers;

import org.jhey.captcha_breaker.stt.api.request.dto.AssemblyResponseDTO;
import org.jhey.captcha_breaker.stt.api.request.RequestBuilder;
import org.jhey.captcha_breaker.stt.api.request.RequestState;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;

public class AudioRunnable implements Runnable {
   private final CompletableFuture<AssemblyResponseDTO> responseDTOCompletableFuture;
   private AssemblyResponseDTO audioProcessingResponse;
   public AudioRunnable(CompletableFuture<AssemblyResponseDTO> responseDTOCompletableFuture, AssemblyResponseDTO audioProcessingResponse) {
      this.responseDTOCompletableFuture = responseDTOCompletableFuture;
      this.audioProcessingResponse = audioProcessingResponse;
   }

   @Override
   public void run() {
      HttpRequest transcribedAudioStatus = RequestBuilder.buildGetAudioStateRequest(audioProcessingResponse);
      try {
         audioProcessingResponse = RequestBuilder.getResponse(transcribedAudioStatus);
        RequestState processingStatus = audioProcessingResponse.getRequestState();
         if (processingStatus.equals(RequestState.DONE)){
            responseDTOCompletableFuture.complete(audioProcessingResponse);
         } else if (processingStatus.equals(RequestState.ERROR)) {
            throw new IllegalStateException("AssemblyAPI returned ERROR, check if the URL is valid");
         }
      } catch (IOException | InterruptedException e) {
            System.out.println("Exception " + e.getMessage());
      }
   }
}
