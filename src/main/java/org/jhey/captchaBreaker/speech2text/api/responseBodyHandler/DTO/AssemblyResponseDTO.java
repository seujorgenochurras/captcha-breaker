package org.jhey.captchaBreaker.speech2text.api.responseBodyHandler.DTO;

import com.google.gson.annotations.SerializedName;
import org.jhey.captchaBreaker.speech2text.api.responseBodyHandler.RequestState;

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

     public void setId(String id) {
      this.id = id;
     }


     @Nullable
     public String getTranscribedAudio() {
      return transcribedAudio;
     }

     public void setTranscribedAudio(@Nullable String transcribedAudio) {
      this.transcribedAudio = transcribedAudio;
     }

     public RequestState getRequestState() {
      return requestState;
     }

     public void setRequestState(RequestState requestState) {
      this.requestState = requestState;
     }



}
