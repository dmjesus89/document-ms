package com.petrotec.documentms.entities.documents;


import com.petrotec.service.converters.JpaConverterJson;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Entity
@Table(name = "payment_mode")
public class PaymentMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "code")
    private String code;

    @Convert(converter = JpaConverterJson.class)
    @Column(name = "description", columnDefinition = "json", nullable = false)
    private Map<String, String> description;

    @Basic
    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Basic
    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Basic
    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "paymentMode")
    private Collection<DocumentPayment> payments;

    @ManyToOne
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    private DocumentType documentType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_mode_ref_id", referencedColumnName = "id", nullable = true)
    private PaymentMode paymentModeReference;

    @Column(name = "open_cash_drawer")
    private boolean openCashDrawer;

    @Column(name = "order_no")
    private int orderNo;

    @Column(name = "prepayment_allowed")
    private boolean prepaymentAllowed;

    @Column(name = "refund_allowed")
    private boolean refundAllowed;

    @Column(name = "change_allowed")
    private boolean changeAllowed;

    @Column(name = "cancel_allowed")
    private boolean cancelAllowed;

    @Column(name = "printer_allowed")
    private boolean printerAllowed;

    @Column(name = "pay_entire_transaction")
    private boolean payEntireTransaction;

    @Column(name = "receipt_copies")
    private int receiptCopies;

    @Column(name = "auto_receipt")
    private boolean autoReceipt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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

    public Collection<DocumentPayment> getPayments() {
        return payments;
    }

    public void setPayments(Collection<DocumentPayment> payments) {
        this.payments = payments;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public PaymentMode getPaymentModeReference() {
        return paymentModeReference;
    }

    public void setPaymentModeReference(PaymentMode paymentModeReference) {
        this.paymentModeReference = paymentModeReference;
    }

    public boolean isOpenCashDrawer() {
        return openCashDrawer;
    }

    public void setOpenCashDrawer(boolean openCashDrawer) {
        this.openCashDrawer = openCashDrawer;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public boolean isPrepaymentAllowed() {
        return prepaymentAllowed;
    }

    public void setPrepaymentAllowed(boolean prepaymentAllowed) {
        this.prepaymentAllowed = prepaymentAllowed;
    }

    public boolean isRefundAllowed() {
        return refundAllowed;
    }

    public void setRefundAllowed(boolean refundAllowed) {
        this.refundAllowed = refundAllowed;
    }

    public boolean isChangeAllowed() {
        return changeAllowed;
    }

    public void setChangeAllowed(boolean changeAllowed) {
        this.changeAllowed = changeAllowed;
    }

    public boolean isCancelAllowed() {
        return cancelAllowed;
    }

    public void setCancelAllowed(boolean cancelAllowed) {
        this.cancelAllowed = cancelAllowed;
    }

    public boolean isPrinterAllowed() {
        return printerAllowed;
    }

    public void setPrinterAllowed(boolean printerAllowed) {
        this.printerAllowed = printerAllowed;
    }

    public boolean isPayEntireTransaction() {
        return payEntireTransaction;
    }

    public void setPayEntireTransaction(boolean payEntireTransaction) {
        this.payEntireTransaction = payEntireTransaction;
    }

    public int getReceiptCopies() {
        return receiptCopies;
    }

    public void setReceiptCopies(int receiptCopies) {
        this.receiptCopies = receiptCopies;
    }

    public boolean isAutoReceipt() {
        return autoReceipt;
    }

    public void setAutoReceipt(boolean autoReceipt) {
        this.autoReceipt = autoReceipt;
    }
}
