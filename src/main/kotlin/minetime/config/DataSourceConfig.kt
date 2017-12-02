package minetime.config

import org.h2.jdbcx.JdbcDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DataSourceConfig {
    @Bean
    fun createDataSource(): DataSource {
        val dataSource = JdbcDataSource()
        dataSource.url = "jdbc:h2:" + System.getProperty("java.io.tmpdir") + "/database"
        return dataSource
    }
}