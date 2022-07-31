package com.petrotec.documentms.entities;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "notifiable_site")
public class NotifiableCustom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "code")
	private String code;

	@Column(name = "created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;

	@Column(name = "updated_on")
	@UpdateTimestamp
	private LocalDateTime updatedOn;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "notifiable", cascade = CascadeType.ALL)
	private List<NotifiableElement> notifiableElements;

	@Column(name = "entity_code")
	private String entityCode;

	@Column(name = "entity_rank_order")
	private Integer entityRankOrder;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<NotifiableElement> getNotifiableElements() {
		return this.notifiableElements;
	}

	public void setNotifiableElements(List<NotifiableElement> notifiableElements) {
		this.notifiableElements = notifiableElements;
	}

	public String getEntityCode() {
		return this.entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Integer getEntityRankOrder() {
		return this.entityRankOrder;
	}

	public void setEntityRankOrder(Integer entityRankOrder) {
		this.entityRankOrder = entityRankOrder;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof NotifiableCustom)) {
			return false;
		}
		NotifiableCustom notifiable = (NotifiableCustom) o;
		return id == notifiable.id && Objects.equals(code, notifiable.code)
				&& Objects.equals(createdOn, notifiable.createdOn) && Objects.equals(updatedOn, notifiable.updatedOn)
				&& enabled == notifiable.enabled && Objects.equals(notifiableElements, notifiable.notifiableElements)
				&& Objects.equals(entityCode, notifiable.entityCode)
				&& Objects.equals(entityRankOrder, notifiable.entityRankOrder);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code, createdOn, updatedOn, enabled, notifiableElements, entityCode, entityRankOrder);
	}

}
