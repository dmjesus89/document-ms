package com.petrotec.documentms.repositories;

import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.filter.translator.JdbcStatementHelper;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.dtos.grade.GradeViewDTO;
import com.petrotec.documentms.dtos.grade.GradeViewProductDTO;
import com.petrotec.documentms.dtos.grade.GradeViewProductGroupDTO;
import com.petrotec.documentms.entities.Grade;
import io.micronaut.context.BeanLocator;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.transaction.SynchronousTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class GradeRepository extends MultitenantLazyRepository implements JpaRepository<Grade, Long> {
    private static final Logger LOG = LoggerFactory.getLogger(GradeRepository.class);

    private static String DEFAULT_QUERY = "SELECT site_grade_view.* FROM (select @var_char_p1:='@locale@') p, site_grade_view";

    @Inject
    private Connection connection;

    @Inject
    private SynchronousTransactionManager<Connection> transactionManager;


    public GradeRepository(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
        super(beanLocator, multitenantResolver);
    }

    public abstract Optional<Grade> findByCode(@NotBlank String code);

    public PageResponse<GradeViewDTO> findAll(@Nullable final PageAndSorting pageAndSorting,
                                               @Nullable final Filter filters, String locale) {
        long requestStartedTime = System.currentTimeMillis();

        final int limit = pageAndSorting.getLimit() + 1;

        final JdbcStatementHelper helper = new JdbcStatementHelper();
        final List<GradeViewDTO> grades = new ArrayList<GradeViewDTO>();

        String query = DEFAULT_QUERY.replace("@locale@",locale);
        transactionManager.executeRead(status -> {
            LOG.debug("Starting to process query:{}",System.currentTimeMillis() - requestStartedTime);
            try (PreparedStatement ps = super.generateStatement(connection, query, filters, null, pageAndSorting.getOffset(), limit, pageAndSorting.getSorting(),
                    GradeViewDTO.class, helper.getParameterValues().toArray())) {
                final ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    try {
                        GradeViewDTO g = mapResultSet(rs);
                        grades.add(g);
                    } catch (Exception ex) {
                        LOG.error(ex.getMessage(),ex);

                    }
                }
            }catch (Exception e){
                LOG.error(e.getMessage(),e);
            }
            return "";

        });

        String countQuery = filters == null ? ("SELECT * FROM grade ") : query;
        // Generate PageResponse from returned result and given pagination fields
        return PageResponse.from(grades, pageAndSorting, () -> countResult(countQuery,filters, GradeViewDTO.class));
    }

    private GradeViewDTO mapResultSet(ResultSet rs) throws SQLException {
        GradeViewDTO grade = new GradeViewDTO();

        grade.setCode(rs.getString("code"));
        grade.setDescription(rs.getString("description"));
        grade.setColor(rs.getString("color"));
        grade.setUnitPriceNet(rs.getBigDecimal("unit_price_net"));
        grade.setEnabled(rs.getBoolean("enabled"));
        if (rs.getTimestamp("updated_on") != null)
            grade.setUpdatedOn(rs.getTimestamp("updated_on").toLocalDateTime());

        if (rs.getTimestamp("created_on") != null)
            grade.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());

        if (rs.getString("product.code") != null)
        {
            GradeViewProductDTO product = new GradeViewProductDTO();
            grade.setProduct(product);

            product.setCode(rs.getString("product.code"));
            product.setColor(rs.getString("product.color"));
            product.setDescription(rs.getString("product.description"));
            product.setShortDescription(rs.getString("product.short_description"));
            product.setPercentage(rs.getBigDecimal("product.percentage"));
        }

        if (rs.getString("product_group.code") != null)
        {
            GradeViewProductGroupDTO group = new GradeViewProductGroupDTO();
            grade.setProductGroup(group);

            group.setCode(rs.getString("product_group.code"));
            group.setColor(rs.getString("product_group.color"));
            group.setDescription(rs.getString("product_group.description"));
        }

        return grade;
    }


}
