package org.jhey.captchaBreaker.speech2text;


import org.jhey.captchaBreaker.speech2text.api.responseBodyHandler.DTO.AssemblyResponseDTO;
import org.jhey.captchaBreaker.speech2text.api.responseBodyHandler.RequestBuilder;
import org.jhey.captchaBreaker.speech2text.api.responseBodyHandler.ResponseHandler;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

public class AudioParser {
   private String audioUrl;
   private Duration checkIfAudioIsProcessedTimer = Duration.ofSeconds(5);

   private final HttpClient httpClient = HttpClient.newHttpClient();

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
   public AssemblyResponseDTO transcribeAudio() throws IOException, InterruptedException {
      HttpRequest audioRequest = RequestBuilder.ofPostRequest(audioUrl);

      responseHandler.setCheckDelay(getCheckIfAudioIsProcessedTimer());

      AssemblyResponseDTO responseDTO = RequestBuilder.getResponse(audioRequest);

      responseHandler.handle(responseDTO.getId(), getCheckIfAudioIsProcessedTimer());
      return RequestBuilder.getResponse(audioRequest);
   }
}
