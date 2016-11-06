package value;

/**
 * Created by rain on 2016/11/6.
 */
public class RequestInfo {
    String uid;
    String id;
    String type;
    String send_time;
    String tid;
    String content;

    public RequestInfo(String uid, String id, String type, String send_time, String tid, String content) {
        this.uid = uid;
        this.id = id;
        this.type = type;
        this.send_time = send_time;
        this.tid = tid;
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
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
}
