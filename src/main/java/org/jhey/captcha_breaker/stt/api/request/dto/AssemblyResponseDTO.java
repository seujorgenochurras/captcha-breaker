package org.jhey.captcha_breaker.stt.api.request.dto;

import com.google.gson.annotations.SerializedName;
import org.jhey.captcha_breaker.stt.api.request.RequestState;

import javax.annotation.Nullable;

public class AssemblyResponseDTO {
  /**
   *Resulted text of the transcribed audio
   */
     @Nullable
     @SerializedName("text")
     private String transcribedAudio;
     @SerializedName("status")
     private RequestState requestState;
     @SerializedName("id")
     private String id;
     public String getId() {
      return id;
     }

     @Nullable
     public String getTranscribedAudio() {
      return transcribedAudio;
     }

     public RequestState getRequestState() {
      return requestState;
     }

}
