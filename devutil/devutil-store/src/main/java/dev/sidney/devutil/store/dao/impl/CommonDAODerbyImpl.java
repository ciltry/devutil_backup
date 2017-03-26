/**
 * 
 */
package dev.sidney.devutil.store.dao.impl;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import dev.sidney.devutil.store.annotation.Model;
import dev.sidney.devutil.store.enums.DBTypeEnum;
import dev.sidney.devutil.store.enums.FieldType;
import dev.sidney.devutil.store.model.BaseModel;

/**
 * @author 杨丰光 2015年8月22日09:39:49
 *
 */
public class CommonDAODerbyImpl extends CommonDAOImpl {

	
	public CommonDAODerbyImpl() {
		this.setDbType(DBTypeEnum.DERBY);
	}

	@Override
	protected String getTableStructureSQL(Class<? extends BaseModel> modelClass) {
		List<Field> fieldList = getManagedFieldList(modelClass);

		Model model = modelClass.getAnnotation(Model.class);
		String[] primaryKey = model.primaryKey();
		String tableName = model.tableName();
		if (StringUtils.isBlank(tableName)) {
			tableName = modelClass.getSimpleName().toLowerCase();
		}
		String comment = model.comment();
		
		StringBuilder sql = new StringBuilder();
		
		String schema = this.getDefaultSchema();
		sql.append(String.format("CREATE TABLE %s.%s (", schema, tableName));
		
		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);
			dev.sidney.devutil.store.annotation.Field fieldAnnotation = field.getAnnotation(dev.sidney.devutil.store.annotation.Field.class);
			FieldType type = fieldAnnotation.type();
			int size = fieldAnnotation.size();
			String fieldComment = fieldAnnotation.comment();
			boolean nullable = fieldAnnotation.nullable();
			int p = fieldAnnotation.p();
			int s = fieldAnnotation.s();
			String columnName = getColumnName(field);
			if (i > 0) {
				sql.append(",");
			}
			sql.append("\n");
			String fieldSql = String.format("    %s %s%s%s", columnName, getActualDbType(type, size, p, s), nullable ? "" : " NOT NULL", fieldComment == null ? "" : "");
			sql.append(fieldSql);
		}
		
		if (primaryKey != null) {
			sql.append(",\n    ");
			sql.append("PRIMARY KEY (");
			for (int i = 0; i < primaryKey.length; i++) {
				if (i > 0) {
					sql.append(",");
				}
				sql.append(String.format("%s", primaryKey[i]));
			}
			sql.append(")");
		}
		
//		String[] unique1 = model.unique1();
		String[][] unique = new String[][]{model.unique1(), model.unique2(), model.unique3()};
		
//		int counter = 0;
		for (int i = 0; i < unique.length; i++) {
			String[] u = unique[i];
			if (u != null && u.length > 0 && StringUtils.isNotEmpty(u[0])) {
				sql.append(",\n    ");
				sql.append(String.format("CONSTRAINT %s UNIQUE (", "uk" + i + "_" + tableName.toLowerCase()));
				for (int j = 0; j < u.length; j++) {
					if (j > 0) {
						sql.append(", ");
					}
					sql.append(String.format("%s", u[j]));
				}
				sql.append(")");
//				counter++;
			}
		}
		
		sql.append("\n)");
		
		return sql.toString();
	}

	@Override
	protected String getActualDbType(FieldType type, int size, int p, int s) {
		String res = "";
		
		if (type == FieldType.VARCHAR2) {
			res = String.format("VARCHAR(%d)", size);
		} else if (type == FieldType.VARCHAR) {
			res = String.format("VARCHAR(%d)", size);
		} else if (type == FieldType.CHAR) {
			res = String.format("CHAR(%d)", size);
		} else if (type == FieldType.DATE) {
			res = "DATE";
		} else if (type == FieldType.TIMESTAMP) {
			res = "TIMESTAMP";
		} else if (type == FieldType.DOUBLE) {
			res = String.format("NUMBER(%d,%d)", p, s);
		} else if (type == FieldType.INTEGER) {
			res = "INT";
		} else if (type == FieldType.LONG) {
			res = "BIGINT";
		}
		return res;
	}

	@Override
	protected String getInsertSql(BaseModel model) {
		String schema = this.getDefaultSchema();
		String tableName = this.getTableName(model);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(String.format("INSERT INTO %s.%s (", schema, tableName));
		List<Field> fieldList = this.getManagedFieldList(model.getClass());
		
		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);
			
			if (i > 0) {
				sql.append(", ");
			}
			sql.append(field.getName().toLowerCase());
		}
		sql.append(") VALUES (");
		
		
		for (int i = 0; i < fieldList.size(); i++) {
			if (i > 0) {
				sql.append(", ");
			}
			sql.append("?");
		}
		
		sql.append(")");
		
		return sql.toString();
	}

	@Override
	protected void validateModel() {
		List<Class<? extends BaseModel>> modelClassList = scanModel();
		
	}

	@Override
	protected String getSqlForSeqNextVal(String seqName) {
		return String.format("VALUES NEXT VALUE FOR %s", seqName);
	}
}
