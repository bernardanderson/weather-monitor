package com.pythorex.weathermonitor.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

@Configuration
public class Sql2oConfiguration {

    @Bean
    public Sql2o sql2o(DataSource dataSource){
        Sql2o sql2o = new Sql2o(dataSource);
        return sql2o;
    }
}
