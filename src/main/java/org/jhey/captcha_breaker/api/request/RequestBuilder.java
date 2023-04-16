package org.jhey.captcha_breaker.api.request;

import com.google.gson.Gson;
import org.jhey.captcha_breaker.api.request.dto.AssemblyResponseDTO;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class RequestBuilder {
   private static final String ASSEMBLY_AI_URL = "https://api.assemblyai.com/v2/transcript";
   private static final HttpClient httpClient = HttpClient.newHttpClient();

   private RequestBuilder() {
   }

   /**
    * @return the getHttpRequest that is used to check information about the transcribed audio.
    * Such as, the stage of transcription (e.g. QUEUE) or the transcribed audio as a whole
    * */
   public static HttpRequest buildGetAudioStateRequest(@Nonnull AssemblyResponseDTO assemblyResponseDTO, String assemblyAIToken){
      String requestId = assemblyResponseDTO.getId();

      return HttpRequest.newBuilder()
              .setHeader("Authorization", assemblyAIToken)
              .GET()
              .uri(URI.create(ASSEMBLY_AI_URL +"/"+ requestId))
              .build();
   }

   /**
    * @return the {@code httpRequest} of method {@code POST} that is used
    * to send to the AssemblyAPI the audioURL, so it can latter transcribe the audio
    * @param audioURL the captcha audioURL
    * */
   public static HttpRequest buildPostRequest(@Nonnull String audioURL, String aseemblyAiToken){
      HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers
              .ofString("{\"audio_url\" : \"" + audioURL + "\"}");
      return HttpRequest.newBuilder()
              .setHeader("Authorization", aseemblyAiToken)
              .POST(body)
              .uri(URI.create(ASSEMBLY_AI_URL))
              .build();
   }
   public static AssemblyResponseDTO getResponse(HttpRequest httpRequest) throws IOException, InterruptedException {
      Gson gson = new Gson();
      String result =
              httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
      return gson.fromJson(result, AssemblyResponseDTO.class);
   }
}
