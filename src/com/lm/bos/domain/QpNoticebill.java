package com.lm.bos.domain;
// Generated 2016-12-20 15:06:53 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * QpNoticebill generated by hbm2java
 */
public class QpNoticebill implements java.io.Serializable {

	private String id;
	private String staffId;
	private String customerId;
	private String customerName;
	private String delegater;
	private String telephone;
	private String pickaddress;
	private String arrivecity;
	private String product;
	private Date pickdate;
	private Integer num;
	private Double weight;
	private String volume;
	private String remark;
	private String ordertype;
	private String userId;
	private Set qpWorkbills = new HashSet(0);

	public QpNoticebill() {
	}

	public QpNoticebill(String id) {
		this.id = id;
	}

	public QpNoticebill(String id, String staffId, String customerId, String customerName, String delegater,
			String telephone, String pickaddress, String arrivecity, String product, Date pickdate, Integer num,
			Double weight, String volume, String remark, String ordertype, String userId, Set qpWorkbills) {
		this.id = id;
		this.staffId = staffId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.delegater = delegater;
		this.telephone = telephone;
		this.pickaddress = pickaddress;
		this.arrivecity = arrivecity;
		this.product = product;
		this.pickdate = pickdate;
		this.num = num;
		this.weight = weight;
		this.volume = volume;
		this.remark = remark;
		this.ordertype = ordertype;
		this.userId = userId;
		this.qpWorkbills = qpWorkbills;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDelegater() {
		return this.delegater;
	}

	public void setDelegater(String delegater) {
		this.delegater = delegater;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPickaddress() {
		return this.pickaddress;
	}

	public void setPickaddress(String pickaddress) {
		this.pickaddress = pickaddress;
	}

	public String getArrivecity() {
		return this.arrivecity;
	}

	public void setArrivecity(String arrivecity) {
		this.arrivecity = arrivecity;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Date getPickdate() {
		return this.pickdate;
	}

	public void setPickdate(Date pickdate) {
		this.pickdate = pickdate;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getVolume() {
		return this.volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set getQpWorkbills() {
		return this.qpWorkbills;
	}

	public void setQpWorkbills(Set qpWorkbills) {
		this.qpWorkbills = qpWorkbills;
	}

}
