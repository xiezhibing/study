package io.study.fund;

import io.study.base.BaseBean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
public class FundInfo extends BaseBean {

    /** 用户ID */
    private String userId;
    /** 总金额 */
    private BigDecimal totalAmount;
    /** 可用的金额 */
    private BigDecimal availableAmount;
    /** 已冻结的金额 */
    private BigDecimal frozenAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }
}
