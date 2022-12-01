package binotify;

import binotify.service.*;
import binotify.util.HibernateUtil;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();

        Endpoint.publish("http://localhost:8001/api/subscribe", new SubscribeService());
    }
}