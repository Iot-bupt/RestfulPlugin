package cn.bupt.restful.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController("TestController")
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    @RequestMapping(value = "/sendPOSTRequest", method = RequestMethod.POST)
    @ResponseBody
    public String testPOSTRequest(@RequestBody String jsonStr)
    {
        return jsonStr;
    }

    @RequestMapping(value = "/sendGETRequest", method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String testGETRequest()
    {
        return "receive get success";
    }

    @RequestMapping(value = "/sendDELETERequest", method = RequestMethod.DELETE)
    @ResponseBody
    public String testDELETERequest()
    {
        return "receive delete success";
    }
}
