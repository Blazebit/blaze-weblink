package com.blazebit.weblink.core.model.jpa;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.blazebit.weblink.core.model.jpa.converter.StringMapConverter;

@Entity
public class WeblinkGroup extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;

	private Calendar creationDate;
	private Account owner;
	private String keyStrategyType;
	private String matcherType;
	private Map<String, String> keyStrategyConfiguration = new TreeMap<String, String>();
	private Map<String, String> matcherConfiguration = new TreeMap<String, String>();
	
	public WeblinkGroup() {
	}
	
	public WeblinkGroup(String id) {
		setId(id);
	}

	@Id
	@Override
	@Column(length = RdbmsConstants.NAME_MAX_LENGTH)
	@Size(min = 1, max = RdbmsConstants.NAME_MAX_LENGTH)
	@Pattern(regexp = "[^/]*", message = "The slash character is not allowed")
	public String getId() {
		return id();
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
	@JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX + "bucket_fk_owner"))
	public Account getOwner() {
		return owner;
	}
	
	public void setOwner(Account owner) {
		this.owner = owner;
	}

	@NotNull
	@Column(name = "key_strategy_type", nullable = false, length = RdbmsConstants.PROVIDER_NAME_MAX_LENGTH)
	public String getKeyStrategyType() {
		return keyStrategyType;
	}

	public void setKeyStrategyType(String keyStrategyType) {
		this.keyStrategyType = keyStrategyType;
	}

	@NotNull
	@Column(name = "matcher_type", nullable = false, length = RdbmsConstants.PROVIDER_NAME_MAX_LENGTH)
	public String getMatcherType() {
		return matcherType;
	}

	public void setMatcherType(String matcherType) {
		this.matcherType = matcherType;
	}

	@NotNull
	@Column(name = "key_strategy_configuration", nullable = false, columnDefinition = RdbmsConstants.CONFIGURATION_COLUMN_DEFINITION)
	@Convert(converter = StringMapConverter.class)
	public Map<String, String> getKeyStrategyConfiguration() {
		return keyStrategyConfiguration;
	}

	public void setKeyStrategyConfiguration(Map<String, String> keyStrategyConfiguration) {
		this.keyStrategyConfiguration = keyStrategyConfiguration;
	}

	@NotNull
	@Column(name = "matcher_configuration", nullable = false, columnDefinition = RdbmsConstants.CONFIGURATION_COLUMN_DEFINITION)
	@Convert(converter = StringMapConverter.class)
	public Map<String, String> getMatcherConfiguration() {
		return matcherConfiguration;
	}

	public void setMatcherConfiguration(Map<String, String> matcherConfiguration) {
		this.matcherConfiguration = matcherConfiguration;
	}

	@PrePersist
	private void prePersist() {
		if (creationDate == null) {
			creationDate = Calendar.getInstance();
		}
	}

	@Override
	public String toString() {
		return "WeblinkGroup [getId()=" + getId() + "]";
	}

}
