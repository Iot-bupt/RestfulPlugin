package cn.bupt.restful.service;

import cn.bupt.restful.data.RequestMsg;
import cn.bupt.restful.data.State;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RestfulService extends State{

    public String sendHTTPRequest(RequestMsg msg) throws IOException {
        String response = null;

        switch(msg.getMethod()){
            case "GET":
                response = sendGETRequest(msg);
                break;
            case "POST":
                response = sendPOSTRequest(msg);
                break;
            case "DELETE":
                response = sendDELETERequest(msg);
                break;
        }

        return  response;
    }


    public String sendPOSTRequest(RequestMsg msg) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , msg.getBody());

        Request request = new Request.Builder()
                .url(msg.getUrl())
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    public String sendGETRequest(RequestMsg msg) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(msg.getUrl())
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    public String sendDELETERequest(RequestMsg msg) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , msg.getBody());

        Request request = new Request.Builder()
                .url(msg.getUrl())
                .delete(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }
}
