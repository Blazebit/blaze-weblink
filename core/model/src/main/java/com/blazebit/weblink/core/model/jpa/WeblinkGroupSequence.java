package com.blazebit.weblink.core.model.jpa;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class WeblinkGroupSequence extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	
	private WeblinkGroup weblinkGroup;
	private Long value = 1L;

	@Id
	@Size(min = 1, max = 256)
	@Pattern(regexp = "[^/]*", message = "The slash character is not allowed")
	@Override
	public String getId() {
		return id();
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX + "weblink_group_sequence_fk_weblink_group"))
	public WeblinkGroup getWeblinkGroup() {
		return weblinkGroup;
	}

	public void setWeblinkGroup(WeblinkGroup weblinkGroup) {
		this.weblinkGroup = weblinkGroup;
	}

	@NotNull
	@Column(nullable = false)
	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
