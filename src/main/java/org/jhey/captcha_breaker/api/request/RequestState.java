package org.jhey.captcha_breaker.api.request;

import com.google.gson.annotations.SerializedName;

public enum RequestState {
      @SerializedName("completed")
      DONE,
      @SerializedName("queued")
      QUEUE,
      @SerializedName("processing")
      PROCESSING,
      @SerializedName("error")
      ERROR
}
