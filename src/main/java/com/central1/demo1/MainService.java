package com.central1.demo1;

import com.central1.demo1.domain.HelloMessage;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MainService {



    @Autowired
    private SimpMessagingTemplate webSocket;


    @Async
    public void respondTo(HelloMessage helloMessage) {

        String botResponse = "no";
        try {

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://autobotsales.herokuapp.com/organizations/process_text");
            Gson gson = new Gson();

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("message_body", helloMessage.getMessage()));
            params.add(new BasicNameValuePair("api_key", "key"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            //httpPost.setHeader("Accept", "application/json");
            //httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                botResponse = gson.fromJson(EntityUtils.toString(response.getEntity()), BotResponse.class).getResponse();
            }

            client.close();

            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }


        webSocket.convertAndSend("/topic/greetings", "{\"sender\":\"talppolbot\",\"content\":\""+new String(botResponse)+"\"}");
    }

}
