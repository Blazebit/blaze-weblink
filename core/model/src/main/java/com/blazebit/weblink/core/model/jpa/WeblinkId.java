package com.blazebit.weblink.core.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class WeblinkId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String weblinkGroupId;
	private String name;
	
	public WeblinkId() {
	}
	
	public WeblinkId(WeblinkGroup weblinkGroup, String name) {
		if (weblinkGroup == null) {
			this.weblinkGroupId = null;
		} else {
			this.weblinkGroupId = weblinkGroup.getId();
		}
		this.name = name;
	}

	public WeblinkId(String weblinkGroupId, String name) {
		this.weblinkGroupId = weblinkGroupId;
		this.name = name;
	}

	@NotNull
	@Column(name = "weblink_group_id")
	public String getWeblinkGroupId() {
		return weblinkGroupId;
	}

	public void setWeblinkGroupId(String weblinkGroupId) {
		this.weblinkGroupId = weblinkGroupId;
	}

	@NotNull
	@Size(min = 1, max = RdbmsConstants.FILE_NAME_MAX_LENGTH)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((weblinkGroupId == null) ? 0 : weblinkGroupId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeblinkId other = (WeblinkId) obj;
		if (weblinkGroupId == null) {
			if (other.weblinkGroupId != null)
				return false;
		} else if (!weblinkGroupId.equals(other.weblinkGroupId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WeblinkId [weblinkGroupId=" + weblinkGroupId + ", name=" + name + "]";
	}
}
