package binotify;

import binotify.controller.*;

import javax.xml.ws.Endpoint;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Endpoint.publish("http://localhost:8001/api/subscribe", new SubscribeController());

    }
}