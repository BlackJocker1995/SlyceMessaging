package value;

/**
 * Created by rain on 2016/10/26.
 */
public class Mess {
    private int send_id;
    private String name;

    public Mess(int send_id, String name) {
        this.send_id = send_id;
        this.name = name;
    }

    public int getSend_id() {
        return send_id;
    }

    public void setSend_id(int send_id) {
        this.send_id = send_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
