package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;

public class HeartBeatController extends Controller {

    public Result heartBeat() {
        return ok("Lub Dub - " + new Date());
    }
}
