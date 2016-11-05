package value;

/**
 * Created by rain on 2016/11/3.
 */
public class User implements Comparable {
    private String name;
    private int peopleID;
    private String icon;
    private Boolean online;

    public User(String name,int peopleID, String icon, Boolean online) {
        this.name = name;
        this.icon = icon;
        this.peopleID = peopleID;
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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    @Override
    public int compareTo(Object o) {
        User user=  (User)o;
        return -1*this.online.compareTo(user.online);
    }
}
