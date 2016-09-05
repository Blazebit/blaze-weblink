package com.blazebit.weblink.core.model.jpa;

import java.net.URI;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.blazebit.weblink.core.model.jpa.converter.StringMapConverter;
import com.blazebit.weblink.core.model.jpa.converter.URIConverter;

@Entity
public class Weblink extends BaseEntity<WeblinkId> {

	private static final long serialVersionUID = 1L;
	
	private Calendar creationDate;
	private WeblinkGroup weblinkGroup;
	private WeblinkSecurityGroup weblinkSecurityGroup;
	private URI targetUri;
	private String dispatcherType;
	private Calendar expirationTime;
	private Map<String, String> dispatcherConfiguration = new TreeMap<String, String>();
	private Map<String, String> tags = new TreeMap<String, String>();
	
	public Weblink() {
		super(new WeblinkId());
	}
	
	public Weblink(WeblinkId id) {
		super(id);
	}
	
	@EmbeddedId
	@Override
	public WeblinkId getId() {
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
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "weblink_group_id", foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX + "weblink_fk_weblink_group"), insertable = false, updatable = false)
	public WeblinkGroup getWeblinkGroup() {
		return weblinkGroup;
	}
	
	public void setWeblinkGroup(WeblinkGroup weblinkGroup) {
		this.weblinkGroup = weblinkGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "weblink_security_group_id", foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX + "weblink_fk_weblink_security_group"))
	public WeblinkSecurityGroup getWeblinkSecurityGroup() {
		return weblinkSecurityGroup;
	}

	public void setWeblinkSecurityGroup(WeblinkSecurityGroup weblinkSecurityGroup) {
		this.weblinkSecurityGroup = weblinkSecurityGroup;
	}

	@NotNull
	@Convert(converter = URIConverter.class)
	@Column(name = "target_uri", nullable = false, length = RdbmsConstants.URI_MAX_LENGTH)
	public URI getTargetUri() {
		return targetUri;
	}

	public void setTargetUri(URI targetUri) {
		this.targetUri = targetUri;
	}

	@NotNull
	@Column(name = "dispatcher_type", nullable = false, length = RdbmsConstants.PROVIDER_NAME_MAX_LENGTH)
	public String getDispatcherType() {
		return dispatcherType;
	}

	public void setDispatcherType(String dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiration_time")
	public Calendar getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Calendar expirationTime) {
		this.expirationTime = expirationTime;
	}

	@NotNull
	@Column(name = "dispatcher_configuration", nullable = false, columnDefinition = RdbmsConstants.CONFIGURATION_COLUMN_DEFINITION)
	@Convert(converter = StringMapConverter.class)
	public Map<String, String> getDispatcherConfiguration() {
		return dispatcherConfiguration;
	}

	public void setDispatcherConfiguration(Map<String, String> dispatcherConfiguration) {
		this.dispatcherConfiguration = dispatcherConfiguration;
	}

	@ElementCollection
	@CollectionTable(name = "weblink_tags", 
		foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX + "weblink_tags_fk_weblink"),
		joinColumns = {
			@JoinColumn(name = "weblink_group_id", referencedColumnName = "weblink_group_id"),
			@JoinColumn(name = "weblink_name", referencedColumnName = "name")
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
		return "Weblink [getId()=" + getId() + "]";
	}
}
