package com.central1.demo1;

import com.central1.demo1.domain.HelloMessage;
import com.central1.demo1.domain.Message;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private MainService mainService;


//    @RequestMapping("/accounts/all")
//    public String getAccountsAll() {
//        // blocking i/o
//
//
//        return "done";
//    }


//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Message getAccountsSegmented1(HelloMessage message) throws InterruptedException {
//
//        mainService.respondTo(message);
//        return new Message(message.getSender(), message.getMessage());
//
//    }

    @SubscribeMapping(value = "/queue/greetings")
    public Observable<String> getObservablesSample() throws InterruptedException {

        System.out.println("bLAHBLAHBHLA");
        return Observable.range(0,10)
                .flatMap(integer -> {
                    return Observable.error(new Exception("Not working"));
                });
    }

//    @SubscribeMapping(value = "/queue/greetings")
//    public String getObservablesSample() throws InterruptedException {
//
//        System.out.println("bLAHBLAHBHLA");
//        return "hello";
//    }


}
