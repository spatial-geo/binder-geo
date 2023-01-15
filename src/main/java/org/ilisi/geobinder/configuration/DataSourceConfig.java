package org.ilisi.geobinder.configuration;

import javax.sql.DataSource;
import org.ilisi.geobinder.core.repositories.nativedao.GeoOpsDAO;
import org.ilisi.geobinder.core.repositories.nativedao.GeoOpsDAOImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

  @Value("${spring.datasource.url}")
  private String URL;

  @Value("${spring.datasource.username}")
  private String USER;

  @Value("${driver}")
  private String DRIVER;

  @Value("${spring.datasource.password}")
  private String PASSWORD;

  @Bean
  DataSource dataSource() {
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
    driverManagerDataSource.setUrl(this.URL);
    driverManagerDataSource.setUsername(this.USER);
    driverManagerDataSource.setPassword(this.PASSWORD);
    driverManagerDataSource.setDriverClassName(this.DRIVER);
    return driverManagerDataSource;
  }

  @Bean
  GeoOpsDAO geoOpsDAO(DataSource dataSource) {
    return new GeoOpsDAOImpl(dataSource);
  }
}
