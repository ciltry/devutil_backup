/**
 * 
 */
package dev.sidney.devutil.domain.logic.impl;

import dev.sidney.devutil.domain.logic.Context;
import dev.sidney.devutil.domain.logic.Logic;
import dev.sidney.devutil.domain.logic.LogicSystem;

/**
 * 
 * @author 杨丰光 2015年8月19日18:10:27
 *
 */
public class LogicSystemImpl extends Logic implements LogicSystem {

	public boolean needLogin(Context context) {
		return false;
	}
}
