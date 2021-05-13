package com.shooterj.core.conf;

import com.shooterj.core.id.DefaultIdGenerator;
import com.shooterj.core.id.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * ID生成器配置
 * 
 * @company 广州宏天软件股份有限公司
 * @author heyifan
 * @email heyf@jee-soft.cn
 * @date 2018年4月19日
 */
//@Configuration
//@AutoConfigureAfter(JdbcTemplate.class)
public class IdGeneratorConfig {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Value("${system.id.machineName}")
    private String machineName;
	@Value("${system.id.idBase}")
    private Long idBase;
	@Value("${system.id.increaseBound}")
    private Integer increaseBound;
	
	@Bean
	public IdGenerator defaultIdGenerator(){
		DefaultIdGenerator idGenerator = new DefaultIdGenerator();
		idGenerator.setIdBase(idBase);
		idGenerator.setIncreaseBound(increaseBound);
		idGenerator.setJdbcTemplate(jdbcTemplate);
		idGenerator.setMachineName(machineName);
		return idGenerator;
	}
}
