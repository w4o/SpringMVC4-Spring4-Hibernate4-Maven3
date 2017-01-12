package com.ueedit.common.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 抽象实体类，如果主键是数据库端自动生成 请使用{@link BaseEntity}
 * @author xiaowu
 *
 * @param <ID>
 */
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 8823369348869597385L;

	public abstract ID getId();

	public abstract void setId(final ID id);

	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}
		AbstractEntity<?> that = (AbstractEntity<?>) obj;
		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {

		int hashCode = 17;

		hashCode += null == getId() ? 0 : getId().hashCode() * 31;

		return hashCode;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
