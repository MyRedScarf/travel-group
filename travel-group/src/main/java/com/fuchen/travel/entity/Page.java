package com.fuchen.travel.entity;

/**
 * @author Fu chen
 * @date 2022/12/30
 * 封装页面相关信息
 */
public class Page {
	//显示页数
	private Integer current = 1;
	//显示上限
	private Integer limit = 10;
	//数量总数（用于计算总页数）
	private Integer rows;
	//查询路径（用于复用分页链接）
	private String path;
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public Integer getCurrent() {
		return current;
	}
	
	public Integer getLimit() {
		return limit;
	}
	
	public Integer getRows() {
		return rows;
	}
	
	public void setCurrent(Integer current){
		if (current >= 1){
			this.current = current;
		}
	}
	
	public void setLimit(Integer limit){
		if (limit >= 1 && limit <= 100){
			this.limit = limit;
		}
	}
	
	public void setRows(Integer rows){
		if (rows >= 0){
			this.rows = rows;
		}
	}
	
	/**
	 * 获取当前页的起始行
	 * @return
	 */
	public Integer getOffset(){
		return (current - 1) * limit;
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public Integer getTotal(){
		if (rows % limit == 0){
			return rows / limit;
		} else {
			return rows / limit + 1;
		}
	}
	
	/**
	 * 获取起始页码
	 * @return
	 */
	public Integer getFrom(){
		int from = current - 2;
		return from < 1 ? 1 : from;
	}
	
	/**
	 * 获取结束页码
	 * @return
	 */
	public Integer getTo(){
		int to = current + 2;
		int total = getTotal();
		return to > total ? total : to;
	}
}
