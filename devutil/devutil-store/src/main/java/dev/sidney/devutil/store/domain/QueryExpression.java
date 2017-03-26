/**
 * 
 */
package dev.sidney.devutil.store.domain;

/**
 * @author 杨丰光 2017年3月26日11:34:42
 *
 */
public class QueryExpression {

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
	
}
