package lut.kj.paper.utils;

import lombok.Data;

import java.util.List;

/**
 * Created by kj on 2017/1/31.
 */
@Data
public class PageInfo<T> {
    /*当前页*/
    private int currentPage;
    /*每页记录数*/
    private int pageSize;
    /*当前页记录数*/
    private int size;
    /*总记录数*/
    private long total;
    /*总页数*/
    private int pageCount;
    private List<T> list;

    public PageInfo(List<T> list) {
       this.list=list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PageInfo{");
        sb.append("currentPage=").append(this.currentPage);
        sb.append(", pageSize=").append(this.pageSize);
        sb.append(", size=").append(this.size);
        sb.append(", total=").append(this.total);
        sb.append(", pageCount=").append(this.pageCount);
        sb.append(", list=").append(this.list);
        sb.append('}');
        return sb.toString();
    }
}
