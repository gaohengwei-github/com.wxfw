package com.wxfw.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.wxfw.util.jdbc.*;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig implements EnvironmentAware {

    private static final String MYBATIS_MAPPER_LOCATION = "classpath:mybatis/mapper/*.xml";
    private static final String MYBATIS_CONFIG_LOCATION = "classpath:mybatis/MybatisConfig.xml";

    private Environment env;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }

    /**
     * 主库配置
     */
    @Bean(name = "masterDataSource")
    public DataSource createMasterDataSource() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.druid.master.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.druid.master.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.druid.master.password"));
        dataSource.setTestWhileIdle(true);
        return dataSource;
    }


    @Bean(name = "dynamicDataSource")
    @Primary
    public DataSource createDynamicDataSource(
            @Qualifier("masterDataSource") DataSource masterDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.putMaster(masterDataSource);
        dynamicDataSource.setTargetDataSources();
        return dynamicDataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager createTransactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource);
        ResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setConfigLocation(pathResolver.getResource(MYBATIS_CONFIG_LOCATION));
        sessionFactory.setMapperLocations(pathResolver.getResources(MYBATIS_MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new JdbcTemplate(dynamicDataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dynamicDataSource);
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate;
    }
}
