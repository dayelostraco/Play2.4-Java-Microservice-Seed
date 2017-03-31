package controllers;

import org.junit.Test;
import play.mvc.Result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;

public class HeartBeatControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Test
    public void testHeartBeat() {
        Result result = new HeartBeatController().heartBeat();
        assertEquals(OK, result.status());
        assertEquals("text/plain", result.contentType());
        assertEquals("utf-8", result.charset());
        assertTrue(contentAsString(result).contains("Lub Dub"));
    }
}
