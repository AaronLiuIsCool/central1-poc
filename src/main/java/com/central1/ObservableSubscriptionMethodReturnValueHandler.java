package com.central1;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.springframework.context.event.EventListener;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SubscriptionMethodReturnValueHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static org.springframework.messaging.handler.DestinationPatternsMessageCondition.LOOKUP_DESTINATION_HEADER;

@Component
public class ObservableSubscriptionMethodReturnValueHandler extends SubscriptionMethodReturnValueHandler {

    private final Multimap<String, Disposable> subscriptions = ArrayListMultimap.create();

    private final SimpMessagingTemplate template;

    /**
     * Construct a new SubscriptionMethodReturnValueHandler.
     *
     * @param template a messaging template to send messages to,
     *                 most likely the "clientOutboundChannel" (must not be {@code null})
     */
    public ObservableSubscriptionMethodReturnValueHandler(SimpMessagingTemplate template) {
        super(template);
        this.template = template;
    }


    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Observable.class.isAssignableFrom(returnType.getParameterType())
                && super.supportsReturnType(returnType);
    }


    @Override
    public MessageHeaderInitializer getHeaderInitializer() {
        return super.getHeaderInitializer();
    }


    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, Message<?> message) throws Exception {

        if (returnValue instanceof Observable) {

            final Observable<?> observable = (Observable) returnValue;

            //final String user = SecurityContextHolder.getContext().getAuthentication().getName();
            final String destination = (String) message.getHeaders().get(LOOKUP_DESTINATION_HEADER);
            final String sessionId = SimpMessageHeaderAccessor.getSessionId(message.getHeaders());


            final Disposable subscription = observable.subscribe(returnedObject -> {

                MessageHeaders headers = createHeaders(sessionId, returnType);
                template.convertAndSend( destination, returnedObject, headers);

            }, throwable -> {

                MessageHeaders headers = createErrorHeaders(sessionId, throwable);

                template.send( destination, MessageBuilder.createMessage(new byte[0], headers));

            });

            subscriptions.put(sessionId, subscription);

        }

    }

    private MessageHeaders createHeaders(String sessionId, MethodParameter returnType) {
        SimpMessageHeaderAccessor headerAccessor =
                SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        if (getHeaderInitializer() != null) {
            getHeaderInitializer().initHeaders(headerAccessor);
        }
        if (sessionId != null) {
            headerAccessor.setSessionId(sessionId);
        }
        headerAccessor.setHeader(SimpMessagingTemplate.CONVERSION_HINT_HEADER, returnType);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

    private MessageHeaders createErrorHeaders(String sessionId, Throwable error) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.ERROR);
        headerAccessor.setMessage(error.getMessage());
        headerAccessor.setSessionId(sessionId);

        if (getHeaderInitializer() != null) {
            getHeaderInitializer().initHeaders(headerAccessor);
        }
        if (sessionId != null) {
            headerAccessor.setSessionId(sessionId);
        }
        //headerAccessor.setHeader(SimpMessagingTemplate.CONVERSION_HINT_HEADER, returnType);
        //headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        final String sessionId = event.getSessionId();
        subscriptions.get(sessionId).forEach(Disposable::dispose);
        subscriptions.removeAll(sessionId);
    }

}
