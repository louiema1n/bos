package com.lm.bos.domain;
// Generated 2016-12-20 15:06:53 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * QpWorkbill generated by hbm2java
 */
public class QpWorkbill implements java.io.Serializable {

	private String id;
	private QpNoticebill qpNoticebill;
	private String type;
	private String pickstate;
	private Date buildtime;
	private Integer attachbilltimes;
	private String remark;
	private String staffId;

	public QpWorkbill() {
	}

	public QpWorkbill(String id, Date buildtime) {
		this.id = id;
		this.buildtime = buildtime;
	}

	public QpWorkbill(String id, QpNoticebill qpNoticebill, String type, String pickstate, Date buildtime,
			Integer attachbilltimes, String remark, String staffId) {
		this.id = id;
		this.qpNoticebill = qpNoticebill;
		this.type = type;
		this.pickstate = pickstate;
		this.buildtime = buildtime;
		this.attachbilltimes = attachbilltimes;
		this.remark = remark;
		this.staffId = staffId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public QpNoticebill getQpNoticebill() {
		return this.qpNoticebill;
	}

	public void setQpNoticebill(QpNoticebill qpNoticebill) {
		this.qpNoticebill = qpNoticebill;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPickstate() {
		return this.pickstate;
	}

	public void setPickstate(String pickstate) {
		this.pickstate = pickstate;
	}

	public Date getBuildtime() {
		return this.buildtime;
	}

	public void setBuildtime(Date buildtime) {
		this.buildtime = buildtime;
	}

	public Integer getAttachbilltimes() {
		return this.attachbilltimes;
	}

	public void setAttachbilltimes(Integer attachbilltimes) {
		this.attachbilltimes = attachbilltimes;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

}