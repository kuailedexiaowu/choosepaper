package lut.kj.choosepaper.user.domin;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kj on 2017/3/18.
 */
@Data
@Table(name = "user")
public class User {
    @Id
    @Column(name = "username")
    private String userName;
    private String password;
}
