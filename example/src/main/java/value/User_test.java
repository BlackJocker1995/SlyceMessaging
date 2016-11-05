package value;

/**
 * Created by rain on 2016/11/5.
 */
public class User_test {
    private String avatar;
    private int id;
    private String name;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public User_test(String avatar, int id, String name) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
    }
}
