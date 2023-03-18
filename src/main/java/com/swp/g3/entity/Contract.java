package com.swp.g3.entity;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Nationalized
    private String pattern;
    private int typeId;
    private Date startDate;
    private Date endDate;
    @Nationalized
    private String status = "Đang chờ xử lý";
    private Integer managerId;
    private Integer staffId;
    private int customerId;
    private int buyerId;
    @Transient
    private ContractType contractType;
    @Transient
    private Buyer buyer;

    public Contract() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }
    public Buyer getBuyer() {
        return buyer;
    }
    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", pattern='" + pattern + '\'' +
                ", typeId=" + typeId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", managerId=" + managerId +
                ", staffId=" + staffId +
                ", customerId=" + customerId +
                ", buyerId=" + buyerId +
                ", contractType=" + contractType +
                ", buyer=" + buyer +
                '}';
    }
}
