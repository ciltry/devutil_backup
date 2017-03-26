/**
 * 
 */
package dev.sidney.devutil.store.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨丰光 2017年3月26日10:46:11
 *
 */
public class DomainQuery {

	private static final String OPERATOR_EQUEALS = "=";
	private static final String OPERTOR_LESS_THAN = "<";
	private static final String OPERATOR_GREATER_THAN = ">";
	private static final String OPERATOR_NO_LESS_THAN = ">=";
	private static final String OPERATOR_NO_GREATER_THAN = "<=";
	
//	private Class<?> targetClass;
	private String field;
	private String operator;
	private Object value;
	

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 
	 */
	private List<List<DomainQuery>> list = new ArrayList<List<DomainQuery>>(0);
	
	
	public DomainQuery equals(String field, Object value) {
		addAddition(field, OPERATOR_EQUEALS, value);
		return this;
	}
	
	public DomainQuery() {
		
	}
	private DomainQuery(String field, String operator, Object value) {
		this.field = field;
		this.operator = OPERATOR_EQUEALS;
		this.value = value;
		
	}
	
	private void addAddition(String field, String operator, Object value) {
		if (this.operator == null) {
			this.field = field;
			this.operator = operator;
			this.value = value;
		} else {
			this.and(new DomainQuery(field, operator, value));
		}
	}
	
	public DomainQuery and(DomainQuery query) {
		if (list.size() == 0) {
			list.add(new ArrayList<DomainQuery>());
		}
		list.get(list.size() - 1).add(query);
		return this;
	}
	
	
	public DomainQuery or(DomainQuery query) {
		if (list.size() == 0) {
			list.add(new ArrayList<DomainQuery>());
		}
		list.add(new ArrayList<DomainQuery>());
		this.and(query);
		return this;
	}
	
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		if (list.size() > 1) {
			res.append("(");
		}
		res.append(String.format("%s %s %s", this.field, this.operator, this.value == null ? "null" : value.toString()));
		if (list.size() > 0 && list.get(0).size() > 0) {
			for (int i = 0; i < list.get(0).size(); i++) {
				res.append(" AND ").append(list.get(0).get(i).toString());
			}
		}
		if (list.size() > 1) {
			for (int i = 1; i < list.size(); i++) {
				res.append(" OR ");
				for (int j = 0; j < list.get(i).size(); j++) {
					if (j > 0) {
						res.append(" AND ");
					}
					res.append(list.get(i).get(j).toString());
				}
			}
		}
		if (list.size() > 1) {
			res.append(")");
		}
		
		return res.toString();
	}
	public List<Object> getArgList() {
		List<Object> argList = new ArrayList<Object>();
		if (this.operator != null) {
			argList.add(this.value);
		}
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).size(); j++) {
				argList.addAll(list.get(i).get(j).getArgList());
			}
		}
		return argList;
	}
	public static void main(String[] args) {
		DomainQuery q = new DomainQuery().equals("a", 1).equals("b", 2).or(new DomainQuery().equals("c", 3).equals("d", 4));
		
		q = new DomainQuery().equals("c", 3).and(q);
		
		System.out.println(q);
		
		for (Object arg: q.getArgList()) {
			System.out.println(arg.toString() + ", ");
		}
	}
	public List<List<DomainQuery>> getList() {
		return list;
	}
	
}
