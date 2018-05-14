package cn.bupt.restful.controller;

import cn.bupt.restful.data.RequestMsg;
import cn.bupt.restful.service.RestfulService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.*;
import cn.bupt.restful.service.Timer;

import java.io.IOException;
import java.util.concurrent.Future;

@RestController("RestfulPluginController")
@RequestMapping("/api/restful")
@Slf4j
public class RestfulPluginController {

    @Autowired
    RestfulService restfulService;

    @Timer
    @ApiOperation(value = "send a request", notes = "send a request api")
    @ApiImplicitParam(name = "jsonStr", value = "{\n" +
            "\t\"url\": \"http://localhost:8400/api/test/sendDELETERequest\",\n" +
            "\t\"method\": \"POST\",\n" +
            "\t\"text\": {\"result\":\"success\"}\n" +
            "}", required = true)
    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    @ResponseBody
    public Future<String> sendRestfulRequest(@RequestBody String jsonStr) throws IOException {
        JsonObject jsonObj = (JsonObject)new JsonParser().parse(jsonStr);
        RequestMsg requestMsg = new RequestMsg(jsonObj);

        String s = restfulService.sendHTTPRequest(requestMsg);
        return new AsyncResult<String>(s);
    }

    @RequestMapping(value = "/active", method = RequestMethod.POST)
    @ResponseBody
    public String setActive(){
        restfulService.setState("ACTIVE");
        return "Plugin active";
    }

    @RequestMapping(value = "/suspend", method = RequestMethod.POST)
    @ResponseBody
    public String setSuspend(){
        restfulService.setState("SUSPEND");
        return "Plugin suspended";
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getState(){
        return restfulService.getState();
    }
}
