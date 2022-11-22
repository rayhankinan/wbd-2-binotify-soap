package binotify.model;

import binotify.enums.Stat;

public class Subscribe {
    private int creator;
    private int subscriber;
    private Stat status;

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(int subscriber) {
        this.subscriber = subscriber;
    }

    public Stat getStatus() {
        return status;
    }

    public void setStatus(Stat status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Subscribe{" +
                "creator=" + creator +
                ", subscriber=" + subscriber +
                ", status=" + status +
                '}';
    }
}
