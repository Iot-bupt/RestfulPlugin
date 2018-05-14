package cn.bupt.restful.controller;

import cn.bupt.restful.data.RequestMsg;
import cn.bupt.restful.service.RestfulService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController("RestfulPluginController")
@RequestMapping("/api/restful")
@Slf4j
public class RestfulPluginController {

    @Autowired
    RestfulService restfulService;

    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    @ResponseBody
    public String sendRestfulRequest(@RequestBody String jsonStr) throws IOException {
        JsonObject jsonObj = (JsonObject)new JsonParser().parse(jsonStr);
        RequestMsg requestMsg = new RequestMsg(jsonObj);

        String s = restfulService.sendHTTPRequest(requestMsg);
        return s;
    }
}
