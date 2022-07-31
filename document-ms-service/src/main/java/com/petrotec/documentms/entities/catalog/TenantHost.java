package com.petrotec.documentms.entities.catalog;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tenant_host")
public class TenantHost implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "tenant_id", unique = true, nullable = false)
    private long tenantId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", referencedColumnName = "id", nullable = false)
    private Tenant tenant;

    @Column
    private String url;

    @Column
    private String user;

    @Column
    private String password;


    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
