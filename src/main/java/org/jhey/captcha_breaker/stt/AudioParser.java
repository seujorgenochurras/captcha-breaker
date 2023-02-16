package org.jhey.captcha_breaker.stt;


import org.jhey.captcha_breaker.stt.api.request.dto.AssemblyResponseDTO;
import org.jhey.captcha_breaker.stt.api.request.RequestBuilder;
import org.jhey.captcha_breaker.stt.api.handlers.ResponseHandler;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class AudioParser {
   private String audioUrl;
   private Duration checkIfAudioIsProcessedTimer = Duration.ofSeconds(5);
   private final ResponseHandler responseHandler = new ResponseHandler(getCheckIfAudioIsProcessedTimer());
   public Duration getCheckIfAudioIsProcessedTimer() {
      return checkIfAudioIsProcessedTimer;
   }

   /**
    * Delay to check if audio has been processed.
    * @default: 5 seconds
    * */
   public void setCheckIfAudioIsProcessedTimer(Duration checkIfAudioIsProcessedTimer) {
      this.checkIfAudioIsProcessedTimer = checkIfAudioIsProcessedTimer;
   }

   public String getAudioUrl() {
      return audioUrl;
   }

   public void setAudioUrl(String audioUrl) {
      this.audioUrl = audioUrl;
   }
   public AssemblyResponseDTO transcribeAudio() throws IOException, InterruptedException, ExecutionException {
      HttpRequest audioRequest = RequestBuilder.buildPostRequest(audioUrl);
      responseHandler.setCheckDelay(getCheckIfAudioIsProcessedTimer());
      AssemblyResponseDTO responseDTO = RequestBuilder.getResponse(audioRequest);
      return responseHandler.handle(responseDTO, getCheckIfAudioIsProcessedTimer()).get();

   }
}
