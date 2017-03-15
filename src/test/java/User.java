import lombok.Data;

/**
 * Created by kj on 2017/3/15.
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private int visitor;
}
