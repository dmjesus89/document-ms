package com.petrotec.documentms.entities.documents;

import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteDevicePos;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity(name = "document_serie")
public class DocumentSerie {
    private short id;
    private String code;
    private BigInteger lastDocumentNumber;
    private boolean enabled;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Collection<Document> documents;
    private DocumentType documentType;
    private Site site;
    private SiteDevicePos siteDevicePos;


    public DocumentSerie() {
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "last_document_number")
    public BigInteger getLastDocumentNumber() {
        return lastDocumentNumber;
    }

    public void setLastDocumentNumber(BigInteger lastDocumentNumber) {
        this.lastDocumentNumber = lastDocumentNumber;
    }

    @Basic
    @Column(name = "is_enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "created_on")
    @CreationTimestamp
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "updated_on")
    @UpdateTimestamp
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentSerie that = (DocumentSerie) o;

        if (id != that.id) return false;
        if (enabled != that.enabled) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (lastDocumentNumber != null ? !lastDocumentNumber.equals(that.lastDocumentNumber) : that.lastDocumentNumber != null)
            return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (updatedOn != null ? !updatedOn.equals(that.updatedOn) : that.updatedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (lastDocumentNumber != null ? lastDocumentNumber.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    public Collection<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<Document> documentsById) {
        this.documents = documentsById;
    }

    @ManyToOne
    @JoinColumn(name = "document_type", referencedColumnName = "id", nullable = false)
    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentTypeByDocumentType) {
        this.documentType = documentTypeByDocumentType;
    }

    @ManyToOne
    @JoinColumn(name = "site_id", referencedColumnName = "id", nullable = false)
    public Site getSite() {
        return site;
    }

    public void setSite(Site siteBySiteId) {
        this.site = siteBySiteId;
    }

    @ManyToOne
    @JoinColumn(name = "site_device_pos_id", referencedColumnName = "id", nullable = false)
    public SiteDevicePos getSiteDevicePos() {
        return siteDevicePos;
    }

    public void setSiteDevicePos(SiteDevicePos siteDevicePos) {
        this.siteDevicePos = siteDevicePos;
    }
}
