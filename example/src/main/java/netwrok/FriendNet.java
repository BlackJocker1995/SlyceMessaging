package netwrok;

/**
 * Created by rain on 2016/11/4.
 */
public class FriendNet {
    private int id;
    private String name;

    public FriendNet(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
