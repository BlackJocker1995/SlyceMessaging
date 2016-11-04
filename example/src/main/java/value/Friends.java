package value;

/**
 * Created by rain on 2016/9/30.
 */
public class Friends implements Comparable{
    private String name;
    private int peopleID;
    private String icon;
    private int  messageNumber;
    private Boolean online;

    public Friends(String name, int peopleID, String icon, Boolean online, int messageNumber) {
        this.name = name;
        this.peopleID = peopleID;
        this.icon=icon;
        this.online = online;
        this.messageNumber = messageNumber;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeopleID() {
        return peopleID;
    }

    public void setPeopleID(int peopleID) {
        this.peopleID = peopleID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int compareTo(Object o) {
        Friends f=  (Friends)o;
        return -1*this.online.compareTo(f.online);
    }
}
