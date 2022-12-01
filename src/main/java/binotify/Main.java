package binotify;

import binotify.middleware.LoggerServlet;
import binotify.service.SubscriptionService;
import binotify.util.HibernateUtil;

import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();

        Endpoint endpoint = Endpoint.create(new SubscriptionService());

        List<Handler> handlerChain = endpoint.getBinding().getHandlerChain();
        handlerChain.add(new LoggerServlet());
        endpoint.getBinding().setHandlerChain(handlerChain);

        endpoint.publish("http://localhost:8001/api/subscribe");
    }
}