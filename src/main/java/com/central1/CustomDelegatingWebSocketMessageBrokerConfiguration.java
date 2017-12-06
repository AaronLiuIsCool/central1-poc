package com.central1;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageMappingInfo;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration;
import org.springframework.web.socket.messaging.WebSocketAnnotationMethodMessageHandler;

import java.util.List;
import java.util.Map;

@Configuration
public class CustomDelegatingWebSocketMessageBrokerConfiguration
        extends DelegatingWebSocketMessageBrokerConfiguration {


    @Override
    protected SimpAnnotationMethodMessageHandler createAnnotationMethodMessageHandler() {

        return new WebSocketAnnotationMethodMessageHandler(clientInboundChannel(),
                clientOutboundChannel(), brokerMessagingTemplate()) {


            /**
             * Add the return value handler to deal with Observables
             */
            @Override
            protected List<HandlerMethodReturnValueHandler> initReturnValueHandlers() {
                return ImmutableList.<HandlerMethodReturnValueHandler>builder()
                        .add(getApplicationContext()
                                .getBean(ObservableSubscriptionMethodReturnValueHandler.class))
                        .addAll(super.initReturnValueHandlers()).build();
            }

            /**
             * Add properties-id to invocation Data from stomp headers...
             *  ...this is equivalent to the http filter pulling it from the http header
             */
            protected void handleMatch(SimpMessageMappingInfo mapping, HandlerMethod handlerMethod,
                                       String lookupDestination, Message<?> message) {

                try {
                    final MultiValueMap<String, String> headers =
                            (MultiValueMap<String, String>) message.getHeaders().get("nativeHeaders");

                    final Map<String, Object> sessionAttributes =
                            SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());

                    final MultiValueMap nativeHeaders =
                            message.getHeaders().get("nativeHeaders", MultiValueMap.class);

                    final String propertiesId = (String) nativeHeaders.getFirst("X-PROPERTIES-ID");

//                    invocationDataManager.initializeInvocationData("", propertiesId, sessionAttributes::put,
//                            sessionAttributes::get);

                    super.handleMatch(mapping, handlerMethod, lookupDestination, message);
                } finally {
//                    invocationDataManager.clearInvocationData();
                }
            }
        };
    }
}
