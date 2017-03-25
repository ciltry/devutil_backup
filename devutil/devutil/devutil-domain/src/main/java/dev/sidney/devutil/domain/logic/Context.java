/**
 * 
 */
package dev.sidney.devutil.domain.logic;

import java.util.Map;

/**
 * @author 杨丰光 2015年8月19日17:57:22
 *
 */
public class Context implements java.io.Serializable {

	/**
	 * uid
	 */
	private static final long serialVersionUID = -7717310156572555665L;
	private Map<String, Object> parameters;

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
}
