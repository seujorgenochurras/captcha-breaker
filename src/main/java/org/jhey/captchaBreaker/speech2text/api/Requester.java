package org.jhey.captchaBreaker.speech2text.api;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Requester {
   private final Dotenv dotenv = Dotenv.load();
  private String audioUrl;
   private final String assemblyAiToken = dotenv.get("ASSEMBLYAI_TOKEN");

   private final HttpClient httpClient = HttpClient.newHttpClient();

   private final String assemblyAiURL = "https://api.assemblyai.com/v2/transcript";

   private HttpRequest createRequest() {
     HttpRequest.BodyPublisher body =  HttpRequest.BodyPublishers.ofString("audio_url : " + getAudioUrl());
     return  HttpRequest.newBuilder()
              .setHeader("Authorization", assemblyAiToken)
              .POST(body)
              .uri(URI.create(assemblyAiURL))
              .build();
   }
   public String transcribeAudio() throws IOException, InterruptedException {
      return httpClient.send(createRequest(), HttpResponse.BodyHandlers.ofString()).body();
   }

   public String getAudioUrl() {
      return audioUrl;
   }

   public void setAudioUrl(String audioUrl) {
      this.audioUrl = audioUrl;
   }
}
