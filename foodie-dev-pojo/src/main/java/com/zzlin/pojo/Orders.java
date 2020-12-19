package com.zzlin.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

public class Orders {
    @Id
    private Integer id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "cus_id_card")
    private String cusIdCard;

    @Column(name = "room_id")
    private Integer roomId;

    /**
     * 0已完成 1进行中
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 已付金额
     */
    @Column(name = "amount_paid")
    private BigDecimal amountPaid;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 押金是否已退
     */
    @Column(name = "is_deposit_return")
    private Integer isDepositReturn;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "follow_cus_id")
    private String followCusId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return order_code
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * @param orderCode
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * @return cus_id_card
     */
    public String getCusIdCard() {
        return cusIdCard;
    }

    /**
     * @param cusIdCard
     */
    public void setCusIdCard(String cusIdCard) {
        this.cusIdCard = cusIdCard;
    }

    /**
     * @return room_id
     */
    public Integer getRoomId() {
        return roomId;
    }

    /**
     * @param roomId
     */
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    /**
     * 获取0已完成 1进行中
     *
     * @return order_status - 0已完成 1进行中
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置0已完成 1进行中
     *
     * @param orderStatus 0已完成 1进行中
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取已付金额
     *
     * @return amount_paid - 已付金额
     */
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    /**
     * 设置已付金额
     *
     * @param amountPaid 已付金额
     */
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    /**
     * 获取押金
     *
     * @return deposit - 押金
     */
    public BigDecimal getDeposit() {
        return deposit;
    }

    /**
     * 设置押金
     *
     * @param deposit 押金
     */
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    /**
     * 获取押金是否已退
     *
     * @return is_deposit_return - 押金是否已退
     */
    public Integer getIsDepositReturn() {
        return isDepositReturn;
    }

    /**
     * 设置押金是否已退
     *
     * @param isDepositReturn 押金是否已退
     */
    public void setIsDepositReturn(Integer isDepositReturn) {
        this.isDepositReturn = isDepositReturn;
    }

    /**
     * @return start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return follow_cus_id
     */
    public String getFollowCusId() {
        return followCusId;
    }

    /**
     * @param followCusId
     */
    public void setFollowCusId(String followCusId) {
        this.followCusId = followCusId;
    }
}