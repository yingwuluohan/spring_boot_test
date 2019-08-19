package com.unisound.iot.common.config.db;

/**
 * @Created by yingwuluohan on 2018/10/18.
 * @Company 北京云知声技术有限公司
 */
//@Configuration
////扫描 Mapper 接口并容器管理
//@MapperScan(basePackages = "com.unisound.iot.dao.mapper.source2", sqlSessionTemplateRef  = "test2SqlSessionTemplate")
public class DataSource2Configuration {


//    @Bean(name = "test2DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.secondary")
//    public DataSource testDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "test2SqlSessionFactory")
//    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test2DataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/source2/*/*.xml"));//指定mapper.xml路径
//        return bean.getObject();
//    }
//
//    @Bean(name = "test2TransactionManager")
//    public DataSourceTransactionManager testTransactionManager(@Qualifier("test2DataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "test2SqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }




}
