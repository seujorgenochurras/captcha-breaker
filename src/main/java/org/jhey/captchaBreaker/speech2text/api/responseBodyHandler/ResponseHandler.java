package org.jhey.captchaBreaker.speech2text.api.responseBodyHandler;

import org.jhey.captchaBreaker.speech2text.api.responseBodyHandler.DTO.AssemblyResponseDTO;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ResponseHandler {

      ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
      private Duration checkDelay;


      public ResponseHandler(Duration checkDelay) {
            this.checkDelay = checkDelay;
      }
      private String tmpAudioId; //I only need this because I have to use it on the runnable below
      Runnable checkAudioStatus = ()->{
            HttpRequest request = RequestBuilder.ofGetRequest(tmpAudioId);
            System.out.println("reached");
            try {
                  AssemblyResponseDTO responseDTO = RequestBuilder.getResponse(request);
                  System.out.println(responseDTO.getTranscribedAudio());
                  System.out.println(responseDTO.getRequestState());
                  if (responseDTO.getRequestState().equals(RequestState.DONE)) shutdownSchedule();
            } catch (IOException | InterruptedException e) {
                  throw new RuntimeException(e);
            }
      };
      public void handle(final String audioId, final Duration checkDelay){
            tmpAudioId = audioId;
            System.out.println(audioId);
            executorService.scheduleWithFixedDelay(checkAudioStatus,
                    1000,
                    checkDelay.get(ChronoUnit.SECONDS),
                    TimeUnit.SECONDS);
      }
      public void shutdownSchedule(){
            try{
                  executorService.shutdown();
            }catch (Exception e){
                  executorService.shutdownNow();
            }
      }
      public void setCheckDelay(Duration checkDelay) {
            this.checkDelay = checkDelay;
      }

      public Duration getCheckDelay() {
            return checkDelay;
      }
}
