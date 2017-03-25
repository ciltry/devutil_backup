/**
 * 
 */
package dev.sidney.devutil.web.form;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author 杨丰光 2015年8月27日17:52:57
 *
 */
public class BaseForm implements java.io.Serializable {

	/**
	 * uid
	 */
	private static final long serialVersionUID = -6879107063533163862L;
	private Date submitTime;

	
	public BaseForm() {
		submitTime = new Date();
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
