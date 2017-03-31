package controllers;

import com.google.common.collect.ImmutableList;
import org.junit.After;
import org.junit.Before;
import play.test.TestServer;

import java.util.ArrayList;

import static play.test.Helpers.*;

public abstract class BaseControllerIntegrationTest {

    protected TestServer testServer;

    @Before
    public void before() {
        testServer = testServer(3333, fakeApplication(inMemoryDatabase(),
                new ArrayList<>(), ImmutableList.of("play.modules.swagger.SwaggerPlugin")));
        start(testServer);
    }

    @After
    public void after() {
        stop(testServer);
    }
}
