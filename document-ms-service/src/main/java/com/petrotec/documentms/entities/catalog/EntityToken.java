package com.petrotec.documentms.entities.catalog;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "entity_token", catalog="catalog", schema="catalog")
public class EntityToken implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", referencedColumnName = "id", nullable = false)
    private EntityCatalog entity;

    @Column(name = "token")
    private String token;

//    @Column
//    private String description;
//
//    @Column(name = "is_enabled")
//    private Boolean isEnabled;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EntityCatalog getEntity() {
        return entity;
    }

    public void setEntity(EntityCatalog entity) {
        this.entity = entity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
