package lut.kj.choosepaper.core;

import lombok.Data;

import javax.persistence.Id;

/**
 * Created by kj on 2017/3/15.
 */
@Data
public class BaseInfo {
    @Id
    private String id;
    private String name;
    private int gender;
    private String tel;
}
