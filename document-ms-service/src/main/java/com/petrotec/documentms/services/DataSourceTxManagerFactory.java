package com.petrotec.documentms.services;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.transaction.jdbc.DataSourceTransactionManager;

import javax.sql.DataSource;

@Factory
public class DataSourceTxManagerFactory {
    private DataSource dataSource;

    public DataSourceTxManagerFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource){
            @NonNull
            @Override
            public DataSource getDataSource() {
                return super.getDataSource();
            }
        };
    }
}
