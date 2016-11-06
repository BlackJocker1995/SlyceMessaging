package value;

/**
 * Created by rain on 2016/11/5.
 */
public class Friend implements Comparable{
    int friendid    ;
    int unreadmessagesNum;
    String name;
    String avatar;
    int status;

    public Friend(int friendid, int unreadmessagesNum, String remarkname, String avatar, int status) {
        this.friendid = friendid;
        this.unreadmessagesNum = unreadmessagesNum;
        this.name = remarkname;
        this.avatar = avatar;
        this.status = status;
    }

    public int getFriendid() {
        return friendid;
    }

    public void setFriendid(int friendid) {
        this.friendid = friendid;
    }

    public int getUnreadmessagesNum() {
        return unreadmessagesNum;
    }

    public void setUnreadmessagesNum(int unreadmessagesNum) {
        this.unreadmessagesNum = unreadmessagesNum;
    }

    public String getRemarkname() {
        return name;
    }

    public void setRemarkname(String remarkname) {
        this.name = remarkname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int compareTo(Object o) {
        Friend f =  (Friend)o;
        if(this.status>f.status)
            return 1;
        return 0;
    }
}
