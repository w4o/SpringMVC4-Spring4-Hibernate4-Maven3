/**
 * Copyright &copy; 2014-2015 <a href="http://www.hr11.com">面试科技</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.ueedit.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 带有数据的Entity
 * 
 * @author xiaowu
 *
 */
@MappedSuperclass
public abstract class DataEntity<ID extends Serializable> extends BaseEntity<ID> {
	private static final long serialVersionUID = 1L;
	/**
	 * 删除标识(0:未删除；1:已删除),默认false
	 */
	@Column(name = "del_flag")
	private String delFlag = "0";
	/**
	 * 备注
	 */
	@Column(name = "remarks")
	private String remarks;
	/**
	 * 创建的用户
	 */
	@Column(name = "create_by")
	private String createBy;
	/**
	 * 创建时间(yyyy-mm-dd HH:mm:ss)
	 */
	@Column(name = "create_time")
	private Date createTime;
	/**
	 * 更新的用户
	 */
	@Column(name = "update_by")
	private String updateBy;
	/**
	 * 更新时间(yyyy-mm-dd HH:mm:ss)
	 */
	@Column(name = "update_time")
	private Date updateTime;

	public DataEntity() {
		super();
	}

	public DataEntity(String delFlag, String remarks, String createBy, Date createTime, String updateBy, Date updateTime) {
		super();
		this.delFlag = delFlag;
		this.remarks = remarks;
		this.createBy = createBy;
		this.createTime = new Date();
		this.updateBy = updateBy;
		this.updateTime = new Date();
	}

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert(String sysUserId) {
		if (sysUserId != null) {
			this.updateBy = sysUserId;
			this.createBy = sysUserId;
		}
		this.updateTime = new Date();
		this.createTime = this.updateTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
