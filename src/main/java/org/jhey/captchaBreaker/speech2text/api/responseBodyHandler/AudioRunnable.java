package org.jhey.captchaBreaker.speech2text.api.responseBodyHandler;

import org.jhey.captchaBreaker.speech2text.api.responseBodyHandler.DTO.AssemblyResponseDTO;

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
         if (audioProcessingResponse.getRequestState().equals(RequestState.DONE)){
            responseDTOCompletableFuture.complete(audioProcessingResponse);
         }
      } catch (IOException | InterruptedException e) {
         throw new RuntimeException(e);
      }
   }
}
