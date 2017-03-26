/**
 * 
 */
package dev.sidney.devutil.store.dao.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import dev.sidney.devutil.store.annotation.FieldMapping;
import dev.sidney.devutil.store.annotation.Model;
import dev.sidney.devutil.store.dao.CommonDAO;
import dev.sidney.devutil.store.domain.DomainQuery;
import dev.sidney.devutil.store.enums.DBTypeEnum;
import dev.sidney.devutil.store.enums.FieldType;
import dev.sidney.devutil.store.exception.StoreException;
import dev.sidney.devutil.store.manage.GlobalStoreConfig;
import dev.sidney.devutil.store.model.BaseModel;

/**
 * 
 * @author 杨丰光 2015年8月20日16:49:23
 *
 */
public abstract class CommonDAOImpl extends JdbcDaoSupport implements CommonDAO {

	
	private static final Logger logger = LoggerFactory.getLogger(CommonDAOImpl.class);
	
	private String scanPackageName;
	
	private String defaultSchema;
	
	
	private Map<FieldType, Method> insertMethodMap;
	
	private Map<FieldType, Method> selectMethodMap;
	
	private DBTypeEnum dbType;
	
	
	
	
	
	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}



	public String getScanPackageName() {
		return scanPackageName;
	}



	public void setScanPackageName(String scanPackageName) {
		this.scanPackageName = scanPackageName;
	}



	public DBTypeEnum getDbType() {
		return dbType;
	}



	public void setDbType(DBTypeEnum dbType) {
		this.dbType = dbType;
	}



	@Override
	public final void init() {
		List<Class<? extends BaseModel>> modelClassList = scanModel();
		logger.info("modelClassList" + modelClassList);
		buildTableStructure(modelClassList);
	}
	
	
	
	private void buildTableStructure(List<Class<? extends BaseModel>> modelClassList) {
		for (Class<? extends BaseModel> modelClass: modelClassList) {
			try {
				String sql = getTableStructureSQL(modelClass);
				System.out.println(sql);
				logger.debug(modelClass.getName() + "\n" + sql);
				this.getJdbcTemplate().execute(sql);
				logger.info(String.format("%s 表创建成功", modelClass.getName()));
			} catch (Exception e) {
				logger.error("创建表失败" + modelClass.getName(), e);
			}
		}
	}
	
	protected String getTableStructureSQL(Class<? extends BaseModel> modelClass) {
		throw new RuntimeException("not implemented");
	}
	
	protected String getActualDbType(FieldType type, int size, int p, int s) {
		String res = "";
		
		
		if (type == FieldType.VARCHAR2) {
			res = String.format("VARCHAR2(%d BYTE)", size);
		} else if (type == FieldType.VARCHAR) {
			res = String.format("VARCHAR(%d BYTE)", size);
		} else if (type == FieldType.CHAR) {
			res = String.format("CHAR(%d BYTE)", size);
		} else if (type == FieldType.DATE) {
			res = "DATE";
		} else if (type == FieldType.TIMESTAMP) {
			res = "TIMESTAMP";
		} else if (type == FieldType.DOUBLE) {
			res = String.format("NUMBER(%d,%d)", p, s);
		}
		return res;
	}
	
	protected final List<Field> getManagedFieldList(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		
		List<Field> fieldList = new ArrayList<Field>();
		
		List<String> primaryKeyList = null;
		if (clazz.getAnnotation(Model.class) != null) {
			primaryKeyList = Arrays.asList(clazz.getAnnotation(Model.class).primaryKey());
		}
		
		for (Field field: fields) {
			dev.sidney.devutil.store.annotation.Field fieldAnnotation = field.getAnnotation(dev.sidney.devutil.store.annotation.Field.class);
			if (fieldAnnotation != null && fieldAnnotation.saved()) {
				fieldList.add(field);
			}
		}
		if (clazz.getSuperclass() != Object.class) {
			fieldList.addAll(getManagedFieldList(clazz.getSuperclass()));
		}
		if (primaryKeyList != null) {
			for (int i = 0; i < fieldList.size(); i++) {
				Field field = fieldList.get(i);
				if (primaryKeyList != null && primaryKeyList.contains(field.getName())) {
					fieldList.set(i, fieldList.set(0, field));
				}
			}
		}
		return fieldList;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	protected List<Class<? extends BaseModel>> scanModel() {
		Set<Class<?>> classSet = getClasses(scanPackageName);
		logger.debug("classSet" + classSet);
		List<Class<? extends BaseModel>> list = new ArrayList<Class<? extends BaseModel>>();
		for (Class<?> clazz: classSet) {
			
			logger.debug(clazz.getName() + " assignableFrom BaseModel " + clazz.isAssignableFrom(BaseModel.class));
			
			if (BaseModel.class.isAssignableFrom(clazz) && clazz != BaseModel.class) {
				
				logger.info(clazz.getName());
				Annotation[] ans = clazz.getAnnotations();
				logger.info("ans " + ans);
				if (clazz.getAnnotation(Model.class) != null) {
					list.add((Class<? extends BaseModel>) clazz);
				}
			}
		}
		return list;
	}
	
	
	
	protected String getTableName(BaseModel model) {
		return getTableName(model.getClass());
	}
	
	protected String getTableName(Class<? extends BaseModel> clazz) {
		Model modelAnnotation = clazz.getAnnotation(Model.class);
		String tableName = modelAnnotation.tableName();
		if (StringUtils.isEmpty(tableName)) {
			tableName = clazz.getSimpleName().toLowerCase();
		}
		return tableName; 
	}
	
	protected String getDefaultSchema() {
		return defaultSchema;
	}
	
	private String generateId() {
		return UUID.randomUUID().toString();
	}
	
	private void setId(BaseModel model) {
		try {
			Field idField = BaseModel.class.getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(model, generateId());
		} catch (Exception e) {
			logger.error("设置主键失败 " + model, e);
			throw new StoreException("设置主键失败", e);
		}
	}
	
	@Override
	public String insert(final BaseModel model) {
		String sql = this.getInsertSql(model);
		setId(model);
		model.setGmtCreate(new Date());
		model.setGmtModified(model.getGmtCreate());
	
		List<Object> args = this.getInsertArgsFromModel(model);
		//logger.info(sql + String.format("", paramArrayOfObject));
		logger.info("【" + sql + "】 args:" + args);
		
		this.getJdbcTemplate().update(sql, args.toArray());
		return model.getId();
	}
	
	
	
	private Map<FieldType, Method> getInsertMethodMap() {
		if (this.insertMethodMap == null) {
			this.insertMethodMap = new HashMap<FieldType, Method>();
			
			Properties prop = GlobalStoreConfig.getInstance().getProp();
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String property = (String) e.nextElement();
				if (property.startsWith(String.format("%s.INSERT", this.dbType.getCode()))) {
					String type = property.substring(property.lastIndexOf(".") + 1);
					FieldType fieldType = FieldType.getByCode(type);
					Method method = null;
					Method[] methods = PreparedStatement.class.getDeclaredMethods();
					for (Method m: methods) {
						if (m.getName().equals(prop.getProperty(property)) && m.getParameterTypes().length == 2) {
							method = m;
							break;
						}
					}
					this.insertMethodMap.put(fieldType, method);
				}
			}
		}
		return this.insertMethodMap;
	}


	protected String getInsertSql(BaseModel model) {
		throw new RuntimeException("not implemented");
	}
	
	
	
	
	
	
	
	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	public final Set<Class<?>> getClasses(String pack) {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(
					packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					System.err.println("file类型的扫描");
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					System.err.println("jar类型的扫描");
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(
												packageName.length() + 1, name
														.length() - 6);
										try {
											// 添加到classes
											classes.add(Class
													.forName(packageName + '.'
															+ className));
										} catch (ClassNotFoundException e) {
											// log
											// .error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						// log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}
	
	
	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public final void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "."
						+ file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					// 添加到集合中去
					//classes.add(Class.forName(packageName + '.' + className));
                                         //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                                        classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));  
                         } catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}



	@Override
	public <T extends BaseModel> T queryForObject(final T model) {
		List<T> list = queryForList(model);
		T res = null;
		if (list.size() > 1) {
			throw new StoreException("查询结果不止一条");
		}
		if (list.size() == 1) {
			res = list.get(0);
		}
		return res;
	}
	
	@Override
	public <T extends BaseModel> T peekObject(T model) {
		String whereClause = this.getWhereClause(model, null);
		List<Object> args = getArgsFromModel(model);
		StringBuilder sql = new StringBuilder(String.format("SELECT * FROM (select ROW_NUMBER() OVER() AS R, t.* from %s.%s t %s order by t.gmtcreate) T WHERE T.R=1", this.getDefaultSchema(), this.getTableName(model), whereClause));
		List<T> list = this.queryForList(sql.toString(), model);
		T res = null;
		if (list.size() > 0) {
			res = list.get(0);
		}
		
		return res;
	}



	@Override
	public <T extends BaseModel> List<T> queryForList(final T model) {
		return queryForList(model, null);
	}

	
	
	private <T extends BaseModel> List<T> queryForList(String sql, final T model) {
		List<Object> args = getArgsFromModel(model);
		Class<T> claxx = (Class<T>) model.getClass();
		return this.queryForList(claxx, sql, args);
	}
	
	private <T extends BaseModel> List<T> queryForList(Class<T> claxx, String sql, List<Object> args) {
		logger.info("【" + sql + "】 args:" + args);
		List<T> res = new ArrayList<T>();
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql, args.toArray());
		List<Field> fieldList = getManagedFieldList(claxx);
		for (Map<String, Object> entry: list) {
			T record = null;
			try {
				record = (T) claxx.newInstance();
			} catch (Exception e) {
				logger.error("创建查询结果对象失败 " + claxx.getName(), e);
				throw new StoreException("创建查询结果对象失败", e);
			}
			
			for (Field field: fieldList) {
				field.setAccessible(true);
				FieldType fieldType = getFieldType(field);
				Object value = entry.get(field.getName());
				try {
					field.set(record, value);
				} catch (Exception e) {
					logger.error("给查询结果对象赋值失败 " + record.getClass().getName() + " " + field.getName() + " " + value, e);
					throw new StoreException("创建查询结果对象失败", e);
				}
				
//				logger.debug(field.getName() + " " + fieldType + " " + (method == null ? null : method.getName())  + " " + value);
			}
			res.add(record);
		}
		
		return res;
	}
	
	@Override
	public <T extends BaseModel> List<T> queryForList(final T model, String orderBy) {
		String sql = getQueryForObjectSql(model);
		if (!StringUtils.isEmpty(orderBy))  {
			sql += " " + orderBy;
		}
		return this.queryForList(sql, model);
	}

	@Override
	public <T extends BaseModel> List<T> queryForList(Class<T> claxx,
			DomainQuery query) {
		String sql = this.getQueryForObjectSql(claxx, query);
		List<Object> argList = query.getArgList();
		return this.queryForList(claxx, sql, argList);
	}


	private FieldType getFieldType(Field field) {
		return field.getAnnotation(dev.sidney.devutil.store.annotation.Field.class).type();
	}
	
	
	private <T extends BaseModel> String getSqlForDeleteById(T model) {
		String tableName = this.getTableName(model);
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("DELETE FROM %s.%s WHERE id=?", this.getDefaultSchema(), tableName));
		return sql.toString();
	}
	
	private <T extends BaseModel> String getSqlForDeleteById(Class<? extends BaseModel> clazz) {
		String tableName = this.getTableName(clazz);
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("DELETE FROM %s.%s WHERE id=?", this.getDefaultSchema(), tableName));
		return sql.toString();
	}
	
	private <T extends BaseModel> String getQueryForObjectSql(Class<T> claxx,
			DomainQuery query, String ... orderBy) {
		String tableName = this.getTableName(claxx);
		List<String> columnNamelist = getColumnNameList(this.getManagedFieldList(claxx));
		
		
		StringBuilder sql = new StringBuilder();
		
		// 字段列表
		StringBuilder columnNameListStr = new StringBuilder();
		for (int i = 0; i < columnNamelist.size(); i++) {
			if (i > 0) {
				columnNameListStr.append(", ");
			}
			columnNameListStr.append(columnNamelist.get(i));
		}
		
		String whereClause = getWhereClause(claxx, query);
		
		
		sql.append(String.format("SELECT %s FROM %s.%s %s", columnNameListStr, this.getDefaultSchema(), tableName, whereClause));
		
		return sql.toString();
	}
	
	private <T extends BaseModel> Map<String, Field> getFieldMapping(Class<T> claxx) {
		java.lang.reflect.Field[] fields = claxx.getDeclaredFields();
		Map<String, Field> map = new HashMap<String, Field>();
		for (Field field: fields) {
			FieldMapping fieldMappingAnnotation = field.getAnnotation(FieldMapping.class);
			if (fieldMappingAnnotation != null && (field.getModifiers() & Modifier.STATIC) > 0) {
				String fieldName = fieldMappingAnnotation.value();
				for (int i = 0;i < fields.length; i++) {
					if (fieldName.equals(fields[i].getName())) {
						map.put(ReflectionUtils.getField(field, null).toString(), fields[i]);
						break;
					}
				}
			}
		}
		if (claxx.getSuperclass() != Object.class) {
			map.putAll(getFieldMapping((Class<? extends BaseModel>) claxx.getSuperclass()));
		}
		return map;
	}
	
	private <T extends BaseModel> String getQueryForObjectSql(T model) {
		List<Field> fieldList = this.getManagedFieldList(model.getClass());
		String tableName = this.getTableName(model);
		List<String> columnNamelist = getColumnNameList(fieldList);
		
		
		StringBuilder sql = new StringBuilder();
		
		// 字段列表
		StringBuilder columnNameListStr = new StringBuilder();
		for (int i = 0; i < columnNamelist.size(); i++) {
			if (i > 0) {
				columnNameListStr.append(", ");
			}
			columnNameListStr.append(columnNamelist.get(i));
		}
		
		String whereClause = getWhereClause(model, null);
		
		
		sql.append(String.format("SELECT %s FROM %s.%s %s", columnNameListStr, this.getDefaultSchema(), tableName, whereClause));
		
		return sql.toString();
	}
	
	private List<Object> getInsertArgsFromModel(BaseModel model) {
		List<Object> list = new ArrayList<Object>();	
		
		List<Field> fieldList = this.getManagedFieldList(model.getClass());
		for (Field field: fieldList) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(model);
			} catch (Exception e) {
				logger.error("取得字段值失败 " + field.getName() + "\n" + model);
				throw new StoreException("构造查询WHERE条件异常", e);
			}
			list.add(value);
		}
		
		return list;
	}
	
	private List<Object> getArgsFromModel(BaseModel model, Object ... extraArgs) {
		List<Object> list = new ArrayList<Object>();
		
		List<Field> fieldList = this.getManagedFieldList(model.getClass());
		for (Field field: fieldList) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(model);
			} catch (Exception e) {
				logger.error("取得字段值失败 " + field.getName() + "\n" + model);
				throw new StoreException("构造查询WHERE条件异常", e);
			}
			if (value != null) {
				list.add(value);
			}
		}
		
		if (extraArgs != null) {
			list.addAll(Arrays.asList(extraArgs));
		}
		
		return list;
	}
	
	private String getWhereClause(Class<? extends BaseModel> claxx, DomainQuery query) {
		return "WHERE " + getWhereCondition(claxx, query);
	}
	private String getWhereCondition(Class<? extends BaseModel> claxx, DomainQuery query) {
		Map<String, Field> map = this.getFieldMapping(claxx);
		
		List<List<DomainQuery>> list = query.getList();
		StringBuilder whereClause = new StringBuilder();
		if (query.getOperator() != null) {
			if (list.size() > 1) {
				whereClause.append("(");
			}
			Field field = map.get(query.getField());
			String columnName = this.getColumnName(field);
			whereClause.append(String.format("%s %s ?", columnName, query.getOperator()));
			
			if (list.size() > 0 && list.get(0).size() > 0) {
				for (int i = 0; i < list.get(0).size(); i++) {
					whereClause.append(" AND ").append(this.getWhereCondition(claxx, list.get(0).get(i)));
				}
			}
			if (list.size() > 1) {
				for (int i = 1; i < list.size(); i++) {
					whereClause.append(" OR ");
					for (int j = 0; j < list.get(i).size(); j++) {
						if (j > 0) {
							whereClause.append(" AND ");
						}
						whereClause.append(this.getWhereCondition(claxx, list.get(i).get(j)));
					}
				}
				whereClause.append(")");
			}
		}
		return whereClause.toString();
	}
	
	private String getWhereClause(BaseModel model, String extraConditions) {
		StringBuilder where = new StringBuilder();
		List<Field> fieldList = this.getManagedFieldList(model.getClass());
		int counter = 0;
		for (Field field: fieldList) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(model);
			} catch (Exception e) {
				logger.error("取得字段值失败 " + field.getName() + "\n" + model);
				throw new StoreException("构造查询WHERE条件异常", e);
			}
			if (value != null) {
				if (counter > 0) {
					where.append(" AND ");
				}
				where.append(field.getName() + "=?");
				counter++;
			}
		}

		String whereClause = "";
		if (where.toString().length() > 0) {
			whereClause = "WHERE " + where.toString();
			if (!StringUtils.isEmpty(extraConditions)) {
				whereClause += " AND " + extraConditions;
			}
		} else if (!StringUtils.isEmpty(extraConditions)) {
			whereClause += "WHERE " + extraConditions;
		}
		
		
		return whereClause;
	}
	
	/**
	 * 获取数据表字段名列表
	 * @param fieldList
	 * @return
	 */
	private List<String> getColumnNameList(List<Field> fieldList) {
		List<String> list = new ArrayList<String>();
		for (Field field: fieldList) {
			list.add(getColumnName(field));
		}
		return list;
	}
	
	protected String getColumnName(Field field) {
		dev.sidney.devutil.store.annotation.Field fieldAnnotation = field.getAnnotation(dev.sidney.devutil.store.annotation.Field.class);
		fieldAnnotation.columnName();
		return StringUtils.isEmpty(fieldAnnotation.columnName()) ? field.getName() : fieldAnnotation.columnName();
	}
	
	
	/**
	 * 验证数据库与Model的兼容性
	 */
	protected abstract void validateModel();



	@Override
	public int deleteById(BaseModel model) {
		return deleteById(model.getClass(), model.getId());
	}
	
	@Override
	public int deleteById(Class<? extends BaseModel> clazz, String id) {
		String sql = getSqlForDeleteById(clazz);
		logger.info(sql + "\nargs:[" + id + "]");
		return this.getJdbcTemplate().update(sql, id);
	}



	private String getSqlForBatchDeleteById(Class<? extends BaseModel> clazz,
			List<String> idList) {
		StringBuilder sql = new StringBuilder();
		
		String tableName = this.getTableName(clazz);
		sql.append(String.format("DELETE FROM %s.%s WHERE id IN (", this.getDefaultSchema(), tableName));
		
		for (int i = 0; i < idList.size(); i++) {
			if (i > 0) {
				sql.append(", ");
			}
			sql.append("?");
		}
		sql.append(")");
		
		return sql.toString();
	}
	
	private List<List<String>> splitList(List<String> list, int size) {
		List<String> dest = new ArrayList<String>();
		dest.addAll(list);
		List<List<String>> res = new ArrayList<List<String>>();
		List<String> innerList = null;
		while (dest.size() > 0) {
			if (innerList == null) {
				innerList = new ArrayList<String>();
			}
			innerList.add(dest.remove(0));
			if (innerList.size() == size || dest.size() == 0) {
				res.add(innerList);
				innerList = null;
			}
		}
		return res;
	}

	@Override
	public int batchDeleteById(Class<? extends BaseModel> clazz,
			List<String> idList) {
		List<List<String>> idGroup = this.splitList(idList, 800);
		int updated = 0;
		for (List<String> list: idGroup) {
			String sql = this.getSqlForBatchDeleteById(clazz, list);
			Object[] args = list.toArray();
			logger.info(sql + "\nargs:" + Arrays.deepToString(args));
			updated += this.getJdbcTemplate().update(sql, args);
		}
		return updated;
	}



	@Override
	public int delete(BaseModel model) {
		int updated = 0;
		if (!StringUtils.isEmpty(model.getId())) {
			updated = this.deleteById(model);
		} else {
			updated = delete(model, null);
		}
		return updated;
	}



	@Override
	public int delete(BaseModel model, String extraCondition, Object ... extraArgs) {
		String tableName = this.getTableName(model);
		StringBuilder sql = new StringBuilder();
		String whereClause = this.getWhereClause(model, extraCondition);
		sql.append(String.format("DELETE FROM %s.%s %s", this.getDefaultSchema(), tableName, whereClause));
		List<Object> args = this.getArgsFromModel(model, extraArgs);
		logger.info(sql + "\nargs:" + args);
		return this.getJdbcTemplate().update(sql.toString(), args.toArray());
	}
	
	private String join(List<String> list, String token) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				res.append(token);
			}
			res.append(list.get(i));
		}
		return res.toString();
	}

	private String getSqlForQueryCustom(BaseModel model, List<String> selectList,
			String extraCondition, Object... extraArgs) {
		StringBuilder sql = new StringBuilder();
		
		String whereClause = this.getWhereClause(model, extraCondition);
		
		sql.append(String.format("SELECT %s FROM %s.%s %s", this.join(selectList, ", "), this.getDefaultSchema(), this.getTableName(model), whereClause));
		
		return sql.toString();
	}


	@Override
	public <T> T queryCustom(BaseModel model, RowMapper<T> rowMapper, List<String> selectList,
			String extraCondition, Object... extraArgs) {

		String sql = getSqlForQueryCustom(model, selectList, extraCondition, extraArgs);
		List<Object> args = this.getArgsFromModel(model, extraArgs);
		logger.info(sql + "\nargs:" + Arrays.deepToString(args.toArray()));
		return this.getJdbcTemplate().queryForObject(sql, args.toArray(), rowMapper);
	}


	private String getSqlForUpdate(BaseModel model, String extraConditions) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.%s SET ", this.getDefaultSchema(), this.getTableName(model)));
		List<Field> fieldList = this.getManagedFieldList(model.getClass());
		int counted = 0;
		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);
			if (field.getName().equals("id")) {
				continue;
			}
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(model);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (value != null) {
				if (counted > 0) {
					sql.append(", ");
				}
				sql.append(String.format("%s = ?", this.getColumnName(field)));
				counted++;
			}
		}
		
		String whereClause = "";
		if (model.getId() != null) {
			whereClause += "id = ?";
		}
		
//		String whereClause = " WHERE id = ?";
		if (!StringUtils.isEmpty(extraConditions)) {
			if (!StringUtils.isEmpty(whereClause)) {
				whereClause += " AND";
			}
			whereClause += " " + extraConditions;
		}
		if (!StringUtils.isEmpty(whereClause)) {
			whereClause = " WHERE " + whereClause;
		}
		sql.append(whereClause);
		return sql.toString();
	}

	@Override
	public int update(BaseModel model, String extraConditions, Object ... extraArgs) {
		String id = model.getId();
		String sql = this.getSqlForUpdate(model, extraConditions);
		model.setId(null);
		List<Object> extraArgList = new ArrayList<Object>();
		if (id != null) {
			extraArgList.add(id);
		}
		if (extraArgs != null) {
			extraArgList.addAll(Arrays.asList(extraArgs));
		}
		List<Object> args = this.getArgsFromModel(model, extraArgList.toArray());
		logger.info(sql + "\nargs:" + Arrays.deepToString(args.toArray()));
		model.setId(id);
		int updated = this.getJdbcTemplate().update(sql, args.toArray());
		return updated;
	}



	@Override
	public int update(BaseModel model) {
		return update(model, null, null);
	}

	protected String getSqlForSeqNextVal(String seqName) {
		throw new RuntimeException("not implemented yet.");
	}
	
	
	@Override
	public void dropStore(Class<? extends BaseModel> clazz) {
		String tableName = this.getTableName(clazz);
		String sql = String.format("DROP TABLE %s.%s", this.getDefaultSchema(), tableName);
		logger.info(sql);
		this.getJdbcTemplate().execute(sql);
	}



	@Override
	public final long getSeqNextVal(String seqName) {
		Long value = this.getJdbcTemplate().query(this.getSqlForSeqNextVal(seqName), new ResultSetExtractor<Long>(){

			@Override
			public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
				Long val = null;
				if (rs.next()) {
					val = rs.getLong(1);
				}
				return val;
			}});
		return value;
	}



	@Override
	public <T> T queryForObject(String sql, Object[] args,
			RowMapper<T> rowMapper) {
		return this.getJdbcTemplate().queryForObject(sql, args, rowMapper);
	}

}
 