/**
 * 
 */
package dev.sidney.devutil.store.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import dev.sidney.devutil.store.domain.DomainQuery;
import dev.sidney.devutil.store.model.BaseModel;

/**
 * @author 杨丰光 2015年8月27日16:24:06
 *
 */
public interface CommonDAO {

	/**
	 * 插入数据
	 * @param model
	 */
	String insert(BaseModel model);
	int deleteById(Class<? extends BaseModel> clazz, String id);
	int deleteById(BaseModel model);
	int delete(BaseModel model);
	int delete(BaseModel model, String extraCondition, Object ... extraArgs);
	int batchDeleteById(Class<? extends BaseModel> clazz, List<String> idList);
	
	/**
	 * 定制化查询
	 * @param model
	 * @param selectList
	 * @param extraCondition
	 * @param extraArgs
	 * @return
	 */
	<T> T queryCustom(BaseModel model, RowMapper<T> rowMapper,  List<String> selectList, String extraCondition, Object ... extraArgs);
	
	/**
	 * 根据字段查询数据
	 * 根据传入的对象中不为空的字段作为查询条件查询单条数据
	 * @param model
	 * @return
	 */
	<T extends BaseModel> T queryForObject(T model);
	
	/**
	 * 根据字段查询数据
	 * 根据传入的对象中不为空的字段作为查询条件查询gmtCreate最早的单条数据
	 * @param model
	 * @return
	 */
	<T extends BaseModel> T peekObject(T model);
	
	<T extends BaseModel> List<T> queryForList(T model);
	
	<T extends BaseModel> List<T> queryForList(Class<T> claxx, DomainQuery query);
	
	<T extends BaseModel> List<T> queryForList(T model, String orderBy);

	public abstract void init();
	public abstract void dropStore(Class<? extends BaseModel> clazz);
	
	int update(BaseModel model);
	int update(BaseModel model,  String extraConditions, Object ... extraArgs);
	long getSeqNextVal(String seqName);
	
	<T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper);
}
