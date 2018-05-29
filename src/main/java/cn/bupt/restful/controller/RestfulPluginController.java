package cn.bupt.restful.controller;

import cn.bupt.restful.data.RequestMsg;
import cn.bupt.restful.pluginmanager.Plugin;
import cn.bupt.restful.service.RestfulService;
import cn.bupt.restful.service.Timer;
import com.codahale.metrics.Counter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.Future;

@RestController("RestfulPluginController")
@RequestMapping("/api/plugin")
@Plugin(pluginInfo = "RestfulPlugin", registerAddr = "172.30.26.7:2181,172.30.24.9:2181,172.30.26.10:2181", detailInfo = "use for sending HTTP Request")
@Slf4j
public class RestfulPluginController {

    @Autowired
    RestfulService restfulService;

    @Resource
    private Counter pendingJobs;

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

        pendingJobs.inc();

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
