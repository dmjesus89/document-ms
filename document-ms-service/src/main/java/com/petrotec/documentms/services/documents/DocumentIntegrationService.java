package com.petrotec.documentms.services.documents;


import com.petrotec.multitenant.resolver.MultitenantContextResolver;
import com.petrotec.documentms.clients.ProductClient;
import com.petrotec.documentms.entities.documents.Document;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.http.simple.SimpleHttpHeaders;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Singleton
public class DocumentIntegrationService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentIntegrationService.class);

    private final MultitenantContextResolver multiTenantContext;
    private final ProductClient productClient;

    private final List<Document> documents = new ArrayList();

    public DocumentIntegrationService(MultitenantContextResolver multiTenantContext, ProductClient productClient) {
        this.multiTenantContext = multiTenantContext;
        this.productClient = productClient;
    }

    @Scheduled(initialDelay = "2m", fixedRate = "5m")
    public void integrateFailedIntegrations() {

        //TODO - got to save the header context when saving it
        multiTenantContext.setTenantId(null);
        ((SimpleHttpHeaders) multiTenantContext.getHeaders()).add("locale", "en-en");
        ((SimpleHttpHeaders) multiTenantContext.getHeaders()).add("entity_code", "1");
        ((SimpleHttpHeaders) multiTenantContext.getHeaders()).add("rank_order", "1");

        try {
            ServerRequestContext.with(multiTenantContext, new Callable<Object>() {
                @Override
                public Object call() throws Exception {

                    List<Document> processed = new ArrayList();
                    for (Document documents : documents) {
                        try {
                            DocumentIntegrationService.this.documents.add(documents);
                            processed.add(documents);
                        } catch (Exception e) {
                            LOG.error("Error processing stock movements", e);
                            e.printStackTrace();
                        }
                    }

                    for (Document savedDoc : processed) {
                        documents.remove(savedDoc);
                    }

                    return null;
                }
            });
        } catch (Exception ex) {
            LOG.error("Error while processing failed Integration", ex);
        }

    }

}
