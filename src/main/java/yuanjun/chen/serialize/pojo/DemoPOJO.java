package yuanjun.chen.serialize.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 范例POJO. */
public class DemoPOJO implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 自增主键. */
    private Long id;
    /** 交货期. */
    private Date deliveryDate;
    /** 紧急程度. */
    private int urgentDegree;
    /** 原材料费用. */
    private BigDecimal materialCost;
    /** 人工费用. */
    private BigDecimal remunerateCost;
    /** 运输优先级. */
    private int transportPriority;
    /** 预留优先级A. */
    private int reservedPriorityA;
    /** 预留优先级B. */
    private int reservedPriorityB;
    /** 预留优先级C. */
    private int reservedPriorityC;

    private Map<Integer, String> mp = new HashMap<>();

    public Map<Integer, String> getMp() {
        return mp;
    }

    public void setMp(Map<Integer, String> mp) {
        this.mp = mp;
    }

    /** =====Getters and Setters below=====. */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getUrgentDegree() {
        return urgentDegree;
    }

    public void setUrgentDegree(int urgentDegree) {
        this.urgentDegree = urgentDegree;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getRemunerateCost() {
        return remunerateCost;
    }

    public void setRemunerateCost(BigDecimal remunerateCost) {
        this.remunerateCost = remunerateCost;
    }

    public int getTransportPriority() {
        return transportPriority;
    }

    public void setTransportPriority(int transportPriority) {
        this.transportPriority = transportPriority;
    }

    public int getReservedPriorityA() {
        return reservedPriorityA;
    }

    public void setReservedPriorityA(int reservedPriorityA) {
        this.reservedPriorityA = reservedPriorityA;
    }

    public int getReservedPriorityB() {
        return reservedPriorityB;
    }

    public void setReservedPriorityB(int reservedPriorityB) {
        this.reservedPriorityB = reservedPriorityB;
    }

    public int getReservedPriorityC() {
        return reservedPriorityC;
    }

    public void setReservedPriorityC(int reservedPriorityC) {
        this.reservedPriorityC = reservedPriorityC;
    }

    @Override
    public String toString() {
        return "DemoPOJO [id=" + id + ", deliveryDate=" + deliveryDate + ", urgentDegree=" + urgentDegree
                + ", materialCost=" + materialCost + ", remunerateCost=" + remunerateCost + ", transportPriority="
                + transportPriority + ", reservedPriorityA=" + reservedPriorityA + ", reservedPriorityB="
                + reservedPriorityB + ", reservedPriorityC=" + reservedPriorityC + ", mp=" + mp + "]";
    }
}
