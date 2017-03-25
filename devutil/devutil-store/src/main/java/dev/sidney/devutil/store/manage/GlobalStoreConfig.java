/**
 * 
 */
package dev.sidney.devutil.store.manage;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.sidney.devutil.store.enums.DBTypeEnum;

/**
 * @author 杨丰光 2015年8月22日10:10:21
 *
 */
public class GlobalStoreConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalStoreConfig.class);
	private static GlobalStoreConfig config;
	private Properties prop;
	
	private GlobalStoreConfig() {
		prop = new Properties();
		try {
			prop.load(GlobalStoreConfig.class.getResourceAsStream("/globalConfig.properties"));
		} catch (IOException e) {
			logger.error("初始化DB配置失败", e);
		} catch (Exception e) {
			logger.error("初始化DB配置失败", e);
		}
	}

	public static final GlobalStoreConfig getInstance(){
		if (config == null) {
			config = new GlobalStoreConfig();
		}
		return config;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}
	

	public DBTypeEnum getDBType() {
		return DBTypeEnum.getByCode(this.prop.getProperty("dbType"));
	}
}
