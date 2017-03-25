/**
 * 
 */
package dev.sidney.devutil.domain.logic.impl;

import dev.sidney.devutil.domain.logic.Context;
import dev.sidney.devutil.domain.logic.LogicDB;
import dev.sidney.devutil.domain.logic.Logic;

/**
 * @author 杨丰光 2015年8月19日17:59:07
 *
 */
public class LogicDBImpl extends Logic implements LogicDB {

	
	public boolean accessEntitled(Context context) {
		return false;
	}
}
