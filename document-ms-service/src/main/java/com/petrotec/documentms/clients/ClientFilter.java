package com.petrotec.documentms.clients;

import com.petrotec.api.PCSConstants;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.micronaut.http.HttpRequest.POST;


@Filter("/api/**")
public class ClientFilter implements HttpClientFilter {
    private static final Logger LOG = LoggerFactory.getLogger(ClientFilter.class);

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        /*TODO - Estes valores deviam ser enviados directed para o client*/
        request.getHeaders().add(PCSConstants.ATTR_LOCALE, "en-en");
        request.getHeaders().add(PCSConstants.ATTR_RANK_ORDER, "1");
        request.getHeaders().add(PCSConstants.ATTR_TENANT_ID, "1");
        request.getHeaders().add(PCSConstants.ATTR_TENANT_CODE, "1");
        return chain.proceed(request);
    }



}