import lombok.Data;

/**
 * description:TODO
 *
 * @author caiqiang.wu
 * @create 2020/03/10
 **/
@Data
public class User {
    private String name;

    private int age;

    public User() {
    }

    public User(int age) {
        this.age = age;
    }

    private User(String name) {
        this.name = name;
    }
}
