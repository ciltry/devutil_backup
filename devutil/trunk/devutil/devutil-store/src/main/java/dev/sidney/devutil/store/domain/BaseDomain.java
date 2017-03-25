/**
 * 
 */
package dev.sidney.devutil.store.domain;

import java.lang.reflect.ParameterizedType;

import dev.sidney.devutil.store.dao.CommonDAO;
import dev.sidney.devutil.store.dto.BaseDTO;
import dev.sidney.devutil.store.model.BaseModel;

/**
 * @author 杨丰光 2017年3月23日15:18:25
 *
 */
public abstract class BaseDomain <T extends BaseDTO<? extends BaseModel>> {
	
	protected abstract CommonDAO getDAO();

	public void insert(T dto) {
		BaseModel model = dto.toModel();
		this.getDAO().insert(model);
		dto.setId(model.getId());
	}
	public int update(T dto) {
		return this.getDAO().update(dto.toModel());
	}
	public int delete(T dto) {
		return this.getDAO().delete(dto.toModel());
	}
	public T queryById(String id) {
		BaseDTO<BaseModel> dto = null;
		Class<? extends BaseDTO<?>> dtoClazz = null;
		try {
			dtoClazz = this.getDtoClass();
			Class<? extends BaseModel> modelClass = (Class<? extends BaseModel>) ((ParameterizedType) dtoClazz.getGenericSuperclass()).getActualTypeArguments()[0];
			BaseModel query = modelClass.newInstance();
			query.setId(id);
			BaseModel model = this.getDAO().queryForObject(query);
			if (model != null) {
				dto = (BaseDTO<BaseModel>) dtoClazz.newInstance();
				dto.constructDTO(model);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("error quering %sobject id: %s", dtoClazz == null ? "" : dtoClazz.getName() + " ", id), e);
		}
		return (T) dto;
	}
	
	private Class<? extends BaseDTO<? extends BaseModel>> getDtoClass() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<? extends BaseDTO<?>> dtoClazz = (Class<? extends BaseDTO<? extends BaseModel>>) pt.getActualTypeArguments()[0];
		return dtoClazz;
	}
	
	public T peek(T dto) {
		BaseDTO<BaseModel> resultObject = null;
		Class<? extends BaseDTO<?>> dtoClazz = null;
		try {
			BaseModel model = this.getDAO().peekObject(dto.toModel());
			if (model != null) {
				dtoClazz = this.getDtoClass();
				resultObject = (BaseDTO<BaseModel>) dtoClazz.newInstance();
				resultObject.constructDTO(model);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("error peeking %sargs: %s", dtoClazz == null ? "" : dtoClazz.getName() + " ", dto == null ? "null" : dto.toString()), e);
		}
		
		return (T) resultObject;
	}
	
	public T query(T query) {
		BaseDTO<BaseModel> resultObject = null;
		Class<? extends BaseDTO<?>> dtoClazz = null;
		try {
			BaseModel model = this.getDAO().queryForObject(query.toModel());
			if (model != null) {
				dtoClazz = this.getDtoClass();
				resultObject = (BaseDTO<BaseModel>) dtoClazz.newInstance();
				resultObject.constructDTO(model);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("error quering %sargs: %s", dtoClazz == null ? "" : dtoClazz.getName() + " ", query == null ? "null" : query.toString()), e);
		}
		return (T) resultObject;
	}
	
	public int updateById(T dto) {
		return this.getDAO().update(dto.toModel());
	}
}
