package com.petrotec.documentms.dtos.documents;
import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nonnull;
import java.math.BigInteger;

@Introspected
public class DocumentSeriesDTO {

    public enum DocumentSeriesTypeEnum {
        NC, FT, FS, GT
    }
    private String uniqueId;
    private String code;
    @Nonnull
    private BigInteger lastDocumentNumber;
    @Nonnull
    private DocumentSeriesTypeEnum documentSeriesType;
    @Nonnull
    private String siteCode;
    @Nonnull
    private String posCode;
    private String lastDocumentSignature;
    private boolean enabled;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

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

    @Nonnull
    public DocumentSeriesTypeEnum getDocumentSeriesType() {
        return documentSeriesType;
    }

    public void setDocumentSeriesType(@Nonnull DocumentSeriesTypeEnum documentSeriesType) {
        this.documentSeriesType = documentSeriesType;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getLastDocumentSignature() {
        return lastDocumentSignature;
    }

    public void setLastDocumentSignature(String lastDocumentSignature) {
        this.lastDocumentSignature = lastDocumentSignature;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
