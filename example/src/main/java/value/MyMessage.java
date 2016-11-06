package value;

/**
 * Created by rain on 2016/11/6.
 */
public class MyMessage {
    int uid;
    String name;
    String type;
    String sendtime;
    String content;

    public MyMessage(int uid, String name, String type, String sendtime, String content) {
        this.uid = uid;
        this.name = name;
        this.type = type;
        this.sendtime = sendtime;
        this.content = content;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
