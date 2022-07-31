package com.petrotec.documentms.dtos.documents;

import com.petrotec.documentms.dtos.site.SiteDTO;
import com.petrotec.documentms.dtos.siteDevice.SiteDevicePosDTO;
import io.micronaut.core.annotation.Introspected;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Introspected
public class DocumentSerieExtendedDTO {
    private String code;
    private BigInteger lastDocumentNumber;
    private boolean isEnabled;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private DocumentTypeDTO documentType;
    private SiteDTO site;
    private DocumentPosDTO posDevice;
    private SiteDevicePosDTO siteDevicePos;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigInteger getLastDocumentNumber() {
        return lastDocumentNumber;
    }

    public void setLastDocumentNumber(BigInteger lastDocumentNumber) {
        this.lastDocumentNumber = lastDocumentNumber;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }


    public DocumentTypeDTO getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeDTO documentTypeByDocumentType) {
        this.documentType = documentTypeByDocumentType;
    }

    public SiteDTO getSite() {
        return site;
    }

    public void setSite(SiteDTO siteBySiteId) {
        this.site = siteBySiteId;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public DocumentPosDTO getPosDevice() {
        return posDevice;
    }

    public void setPosDevice(DocumentPosDTO siteDevicePosBySiteDevicePosId) {
        this.posDevice = siteDevicePosBySiteDevicePosId;
    }

    public SiteDevicePosDTO getSiteDevicePos() {
        return siteDevicePos;
    }

    public void setSiteDevicePos(SiteDevicePosDTO siteDevicePos) {
        this.siteDevicePos = siteDevicePos;
    }

    @Override
    public String toString() {
        return "DocumentSerieExtendedDTO{" +
                "code='" + code + '\'' +
                ", lastDocumentNumber=" + lastDocumentNumber +
                ", isEnabled=" + isEnabled +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", documentType=" + documentType +
                ", site=" + site +
                ", siteDevicePos=" + siteDevicePos +
                '}';
    }
}
