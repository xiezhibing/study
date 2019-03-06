package io.study.base;

import java.util.Date;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
public class BaseBean {

    private String Id;
    private Date createTime;
    private Date modifyTime;
    private Integer dr;
    private Integer pageNum = 0;
    private Integer pageSize = 10;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
