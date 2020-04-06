package org.example;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/action")
public class GraphEndpoint {
    private Session session;
    private static Set<GraphEndpoint> graphEndpoints = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        graphEndpoints.add(this);
        //TODO: send message on connection
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        //TODO: handle new messages
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        //TODO: WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        //TODO: error handling
    }

    private static void broadcast(){
        //TODO: send message
    }
}