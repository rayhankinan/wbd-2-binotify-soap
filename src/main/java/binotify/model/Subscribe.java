package binotify.model;

import binotify.enums.Stat;

public class Subscribe {
    private int creatorID;
    private int subscriberID;
    private String creatorName;
    private String subscriberName;
    private Stat status;

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public int getSubscriberID() {
        return subscriberID;
    }

    public void setSubscriberID(int subscriberID) {
        this.subscriberID = subscriberID;
    }

    public Stat getStatus() {
        return status;
    }

    public void setStatus(Stat status) {
        this.status = status;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    @Override
    public String toString() {
        return "Subscribe{" +
                "creatorName=" + creatorName +
                ", subscriberName=" + subscriberName +
                ", status=" + status +
                '}';
    }
}
