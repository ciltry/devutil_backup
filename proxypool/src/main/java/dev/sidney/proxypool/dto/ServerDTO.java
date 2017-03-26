/**
 * 
 */
package dev.sidney.proxypool.dto;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dev.sidney.devutil.store.dto.BaseDTO;
import dev.sidney.proxypool.model.Server;

/**
 * @author Sidney
 *
 */
public class ServerDTO extends BaseDTO<Server> {

	private String ip;
	private Integer port;
	private String type;
	private String location;
	private Integer speed;
	private Date gmtLastTest;
	private String alias;
	private String memo;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getGmtLastTest() {
		return gmtLastTest;
	}
	public void setGmtLastTest(Date gmtLastTest) {
		this.gmtLastTest = gmtLastTest;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	@Override
	public Server toModel() {
		Server model = new Server();
		ServerDTO dto = this;
		model.setAlias(dto.getAlias());
		model.setGmtCreate(dto.getGmtCreate());
		model.setGmtLastTest(dto.getGmtLastTest());
		model.setGmtModified(dto.getGmtModified());
		model.setId(dto.getId());
		model.setIp(dto.getIp());
		model.setLocation(dto.getLocation());
		model.setMemo(dto.getMemo());
		model.setPort(dto.getPort());
		model.setSpeed(dto.getSpeed());
		model.setType(dto.getType());
		model.setStatus(dto.getStatus());
		return model;
	}
	@Override
	public void constructDTO(Server model) {
		ServerDTO dto = this;
		dto.setAlias(model.getAlias());
		dto.setGmtCreate(model.getGmtCreate());
		dto.setGmtLastTest(model.getGmtLastTest());
		dto.setGmtModified(model.getGmtModified());
		dto.setId(model.getId());
		dto.setIp(model.getIp());
		dto.setLocation(model.getLocation());
		dto.setMemo(model.getMemo());
		dto.setPort(model.getPort());
		dto.setSpeed(model.getSpeed());
		dto.setType(model.getType());
		dto.setStatus(model.getStatus());
	}
}
