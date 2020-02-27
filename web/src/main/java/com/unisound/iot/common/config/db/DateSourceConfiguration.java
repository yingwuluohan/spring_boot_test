package com.unisound.iot.common.config.db;

/**
 * @Created by yingwuluohan on 2018/10/18.
 * @Company fn
 */
//@Configuration
////扫描 Mapper 接口并容器管理
//@MapperScan(basePackages = "com.unisound.iot.dao.mapper.source1", sqlSessionTemplateRef  = "test1SqlSessionTemplate")

public class DateSourceConfiguration {



//
//
//    @Bean(name = "test1DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.primary")
//    @Primary
//    public DataSource testDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "test1SqlSessionFactory")
//    @Primary
//    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/source1/*/*.xml"));//指定mapper.xml路径
//        return bean.getObject();
//    }
//
//    @Bean(name = "test1TransactionManager")
//    @Primary
//    public DataSourceTransactionManager testTransactionManager(@Qualifier("test1DataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "test1SqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }


}
