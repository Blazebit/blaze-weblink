package com.blazebit.weblink.core.model.jpa;

import com.blazebit.weblink.core.model.jpa.converter.StringMapListConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = RdbmsConstants.PREFIX + "weblink_security_group_uk_owner_name", columnNames = { "owner_id", "name" })
})
@SequenceGenerator(name = "idGenerator", sequenceName = RdbmsConstants.PREFIX + "weblink_security_group_seq")
public class WeblinkSecurityGroup extends SequenceBaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private Calendar creationDate;
	private Account owner;
	private List<Map<String, String>> constraintConfigurations = new ArrayList<>();
	private Map<String, String> tags = new TreeMap<String, String>();

	public WeblinkSecurityGroup() {
	}

	public WeblinkSecurityGroup(String name) {
		this.name = name;
	}

	@NotNull
	@Column(length = RdbmsConstants.NAME_MAX_LENGTH)
	@Size(min = 1, max = RdbmsConstants.NAME_MAX_LENGTH)
	@Pattern(regexp = "[^/]*", message = "The slash character is not allowed")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX + "weblink_security_group_fk_owner"))
	public Account getOwner() {
		return owner;
	}
	
	public void setOwner(Account owner) {
		this.owner = owner;
	}

	@NotNull
	@Column(name = "constraint_configurations", nullable = false, columnDefinition = RdbmsConstants.CONFIGURATION_COLUMN_DEFINITION)
	@Convert(converter = StringMapListConverter.class)
	public List<Map<String, String>> getConstraintConfigurations() {
		return constraintConfigurations;
	}

	public void setConstraintConfigurations(List<Map<String, String>> constraintConfigurations) {
		this.constraintConfigurations = constraintConfigurations;
	}

	@ElementCollection
	@CollectionTable(name = "weblink_security_group_tags",
			foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX + "weblink_security_group_tags_fk_security_group"),
			joinColumns = {
					@JoinColumn(name = "weblink_security_group_id", referencedColumnName = "id")
			})
	@MapKeyColumn(name = "tag", nullable = false)
	@Column(name = "value", nullable = false)
	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	@PrePersist
	private void prePersist() {
		if (creationDate == null) {
			creationDate = Calendar.getInstance();
		}
	}

	@Override
	public String toString() {
		return "WeblinkSecurityGroup [getId()=" + getId() + "]";
	}

}
