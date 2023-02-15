package org.jhey.captchaBreaker.speech2text.api.responseBodyHandler;

import com.google.gson.annotations.SerializedName;

public enum RequestState {
      @SerializedName("completed")
      DONE,
      @SerializedName("queued")
      QUEUE,
      @SerializedName("error")
      ERROR
}
