package org.jhey;

import org.jhey.captcha_breaker.api.AudioParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

   @Test
   void testTranscriber() throws IOException, ExecutionException, InterruptedException {
      AudioParser audioParser = new AudioParser("TOKEN");
      audioParser.setAudioUrl("https://s3-us-west-2.amazonaws.com/blog.assemblyai.com/audio/8-7-2018-post/7510.mp3");
      String transcribedAudio = audioParser.transcribeAudio().getTranscribedAudio();
      String whatWasActualSaid = "You know, demons on TV like that. And and for people to expose themselves to being rejected on TV or, you know, humil humiliated by Fear Factor or, you know.";

      assertEquals(whatWasActualSaid, transcribedAudio);
   }
}
