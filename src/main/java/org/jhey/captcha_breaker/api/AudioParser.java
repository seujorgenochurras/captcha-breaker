package org.jhey.captcha_breaker.api;


import org.jhey.captcha_breaker.api.request.dto.AssemblyResponseDTO;
import org.jhey.captcha_breaker.api.request.RequestBuilder;
import org.jhey.captcha_breaker.api.handlers.ResponseHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class AudioParser {
   private String audioUrl;
   private Duration checkIfAudioIsProcessedTimer = Duration.ofSeconds(5);

   private final String assemblyAiToken;
   private final ResponseHandler responseHandler = new ResponseHandler(getCheckIfAudioIsProcessedTimer());
   public Duration getCheckIfAudioIsProcessedTimer() {
      return checkIfAudioIsProcessedTimer;
   }

   public AudioParser(String assemblyAiToken) {
      this.assemblyAiToken = assemblyAiToken;
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
      if(!isValidUrl(audioUrl)) throw new IllegalArgumentException("audioUrl is not a valid Url");
      this.audioUrl = audioUrl;
   }

   public AssemblyResponseDTO transcribeAudio() throws IOException, InterruptedException, ExecutionException {

      HttpRequest audioRequest = RequestBuilder.buildPostRequest(audioUrl, assemblyAiToken);
      responseHandler.setCheckDelay(getCheckIfAudioIsProcessedTimer());
      AssemblyResponseDTO responseDTO = RequestBuilder.getResponse(audioRequest);

      return responseHandler.handle(responseDTO, getCheckIfAudioIsProcessedTimer(), assemblyAiToken).get();
   }
   private boolean isValidUrl(String audioUrl) {
      try{
         new URL(audioUrl).toURI();
         return true;
      }catch (MalformedURLException | URISyntaxException e){
         return false;
      }
   }
}
