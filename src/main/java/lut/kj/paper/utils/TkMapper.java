package lut.kj.paper.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by Administrator on 2016/8/11.
 */
public interface TkMapper<T> extends Mapper<T>, MySqlMapper<T> {
    // 特别注意，该接口不能被扫描到，否则会出错
    //表中出现的desc字段必须加``，如`desc`
    //表中不存在的属性必须加@Transient
}
