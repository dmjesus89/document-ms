package com.petrotec.documentms.services.documents;

import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.repositories.AbstractLazyRepository;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.documents.search.DocumentHeaderDTO;
import io.micronaut.transaction.SynchronousTransactionManager;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Singleton
public class DocumentHeaderService extends AbstractLazyRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentHeaderService.class);

    private final Connection connection;
    private final SynchronousTransactionManager<Connection> transactionManager;
    // BuildMyString.com generated code. Please enjoy your string responsibly.
    String DOCUMENT_HEADER_QUERY = "SELECT \n" +
            "\tdoc.id\t\t\t\t\tas `id`,\n" +
            "\tdoc.document_date \t\tas `document_date`,      #1\n" +
            "\ts.code \t\t\t\t\tas `site_code`,\n" +
            "\ttranslate( s.description, '@@lang_code@@' ) \t\t\tas `site_description`,   #2\n" +
            "\tst.code \t\t\t\tas `sale_type_code`,   \n" +
            "\ttranslate(st.description, '@@lang_code@@' ) \t\t\tas `sale_type_description`,   #3\n" +
            "\tdoc.pos_number\t\t\t\tas `pos_number`,\t\t\t#4\n" +
            "\tdoc.code\t\t\t\tas `document_code`,\n" +
            "\tdoc.number_of_lines \tas `document_number_of_lines`, #5\n" +
            "\tdoc.net_amount\t\t\tas `document_net_amount`,\t\n" +
            "\tdoc.tax_amount\t\t\tas `document_tax_amount`,\n" +
            "\tdoc.gross_amount\t\tas `document_gross_amount`,\n" +
            "\tdoc.discount_amount\t\tas `document_discount_amount`,\n" +
            "\tdoc.total_amount\t\tas `document_total_amount`,\t\t#4\n" +
            "\tdla.invoice_number\t\tas `document_invoice_number`,\t#6\n" +
            "\tdla.hash\t\t\t\tas `document_serie_hash`,\n" +
            "\tdla.hash_print\t\t\tas `document_serie_hash_print`,\n" +
            "\tdla.hash_control\t\tas `document_serie_hash_control`,\n" +
            "\tdla.certificate_number\tas `document_serie_certificate_number`,\n" +
            "\tparty.party_code\t\tas `party_code`,\t\t\t#6\n" +
            "\tparty.name\t\t\t\tas `party_name`,\t\t\t#7\n" +
            "\tparty.vatin\t\t\t\tas `party_vatin`,\t\t\t#7\n" +
            "\tparty.street\t\t\tas `party_street`,\t\t\t#8\n" +
            "\tparty.postal_zip_code\tas `party_postal_zip_code`,\t\n" +
            "\tparty.city\t\t\t\tas `party_city`,\t\t\t#9\n" +
            "\tparty.iso_country_code\tas `party_iso_country_code`,\n" +
            "\tdt.code\t\t\t\t\tas `document_type_code`,\t#10\n" +
            "\tdt.prefix\t\t\t\tas `document_type_prefix`,\t\t\t\n" +
            "\ttranslate(dt.description, '@@lang_code@@' )\t\t\tas `document_type_description`,\t\t#11\n" +
            "\tserie.code\t\t\t\tas `document_serie_code`\n" +
            "FROM document doc\n" +
            "LEFT JOIN document_legal_authority dla ON dla.document_id = doc.id\n" +
            "LEFT JOIN document_party party ON party.document_id = doc.id\n" +
            "LEFT JOIN document_sale_type st ON st.id =  doc.document_sale_type_id \n" +
            "LEFT JOIN document_serie serie ON doc.document_serie_id = serie.id\n" +
            "LEFT JOIN document_type dt ON serie.document_type = dt.id\n" +
            "LEFT JOIN site s ON s.id = doc.site_id\n" +
            "LEFT JOIN site_device sd ON serie.site_device_pos_id = sd.id\n" +
            "LEFT JOIN site_device_pos pos ON sd.id = pos.id ";

    public DocumentHeaderService(final Connection connection, final SynchronousTransactionManager<Connection> transactionManager, final EntityManager entityManager) {
        super(entityManager);
        this.connection = connection;
        this.transactionManager = transactionManager;
    }

    public PageResponse<DocumentHeaderDTO> getDocumentHeaders(PageAndSorting pageAndSorting,
                                                              Filter filters) {
        final String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        final int limit = pageAndSorting.getLimit() + 1;

        final String query = DOCUMENT_HEADER_QUERY.replaceAll("@@lang_code@@", locale);

        LOG.trace(query);

        final List<DocumentHeaderDTO> cards = new ArrayList<DocumentHeaderDTO>();

        // Retrieve all parties with pagination and filtering
        transactionManager.executeRead(status -> {

            try (PreparedStatement ps = super.generateStatement(connection, query, filters, null,
                    pageAndSorting.getOffset(), limit, pageAndSorting.getSorting(), DocumentHeaderDTO.class)) {
                final ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    try {
                        cards.add(getCustomCardFromRs(rs));
                    } catch (Exception ex) {
                        LOG.error(ex.getMessage());
                    }
                }
            }
            return "";
        });

        String countQuery = filters == null ? ("SELECT * FROM document ") : query;

        // Generate PageResponse from returned result and given pagination fields
        return PageResponse.from(cards, pageAndSorting, () -> countResult(countQuery, filters, DocumentHeaderDTO.class));
    }

    private DocumentHeaderDTO getCustomCardFromRs(ResultSet rs) throws SQLException {
        return DocumentHeaderDTO.builder()
                .id(rs.getLong("id"))
                .documentDate(rs.getTimestamp("document_date") != null ? rs.getTimestamp("document_date").toLocalDateTime() : null)
                .siteCode(rs.getString("site_code"))
                .siteDescription(rs.getString("site_description"))
                .saleTypeCode(rs.getString("sale_type_code"))
                .saleTypeDescription(rs.getString("sale_type_description"))
                .posNumber(rs.getInt("pos_number"))
                .documentCode(rs.getString("document_code"))
                .documentNumberOfLines(rs.getInt("document_number_of_lines"))
                .documentNetAmount(rs.getBigDecimal("document_net_amount"))
                .documentTaxAmount(rs.getBigDecimal("document_tax_amount"))
                .documentGrossAmount(rs.getBigDecimal("document_gross_amount"))
                .documentDiscountAmount(rs.getBigDecimal("document_discount_amount"))
                .documentTotalAmount(rs.getBigDecimal("document_total_amount"))
                .documentInvoiceNumber(rs.getString("document_invoice_number"))
                .documentSerieHash(rs.getString("document_serie_hash"))
                .documentSerieHashPrint(rs.getString("document_serie_hash_print"))
                .documentSerieHashControl(rs.getString("document_serie_hash_control"))
                .documentSerieCertificateNumber(rs.getString("document_serie_certificate_number"))
                .partyCode(rs.getString("party_code"))
                .partyVatin(rs.getString("party_vatin"))
                .partyName(rs.getString("party_name"))
                .partyStreet(rs.getString("party_street"))
                .partyPostalZipCode(rs.getString("party_postal_zip_code"))
                .partyCity(rs.getString("party_city"))
                .partyIsoCountryCode(rs.getString("party_iso_country_code"))
                .documentTypeCode(rs.getString("document_type_code"))
                .documentTypePrefix(rs.getString("document_type_prefix"))
                .documentTypeDescription(rs.getString("document_type_description"))
                .documentSerieCode(rs.getString("document_serie_code"))
                .build();
    }

}
