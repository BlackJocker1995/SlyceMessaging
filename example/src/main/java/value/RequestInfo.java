package value;

/**
 * Created by rain on 2016/11/6.
 */
public class RequestInfo {
    String uid;
    String name;
    String send_time;
    String type;
    String tid;
    String content;
    String email;

    public RequestInfo(String uid, String name, String send_time, String type, String tid, String content, String email) {
        this.uid = uid;
        this.name = name;
        this.send_time = send_time;
        this.type = type;
        this.tid = tid;
        this.content = content;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
