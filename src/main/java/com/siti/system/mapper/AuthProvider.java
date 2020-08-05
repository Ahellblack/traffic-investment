package com.siti.system.mapper;

import java.util.Map;

public class AuthProvider {
	
	public String updateSonSort(Map map){
		Integer oldPid = (Integer) map.get("oldPid");  
		StringBuffer buffer = new StringBuffer("update sys_auth set ");	
		if(oldPid != null){
			buffer.append("sort = CONCAT(#{sort},RPAD(right(sort,#{sortDigit}),#{updateSort},0)) where substring(sort,1,length(sort)-#{sortDigit})=#{oldSort} and pid != #{oldPid}");
		}else{
			buffer.append("sort = CONCAT(#{sort},RPAD(right(sort,#{sortDigit}),#{updateSort},0)) where substring(sort,1,length(sort)-#{sortDigit})=#{oldSort} and pid is not null");
		}
		return buffer.toString();  
	}
	
	public String updateSonSortOne(Map map){
		Integer oldPid = (Integer) map.get("oldPid");  
		StringBuffer buffer = new StringBuffer("update sys_auth set ");	
		if(oldPid != null){
			buffer.append("sort = right(sort,#{updateSort}) where substring(sort,1,length(sort)-#{sortDigit})=#{oldSort} and pid != #{oldPid}");
		}else{
			buffer.append("sort = right(sort,#{updateSort}) where substring(sort,1,length(sort)-#{sortDigit})=#{oldSort} and pid is not null");
		}
		return buffer.toString(); 			
	}
	
	public String updateSonSortTwo(Map map){
		StringBuffer buffer = new StringBuffer("update sys_auth set ");	
		buffer.append("sort = CONCAT(#{sort},if(length(sort)&1,concat('0',sort),sort)) where length(sort)<5");		
		return buffer.toString();  
	}
	
	public String updatePlusOrMinus(Map map){
		String type = (String) map.get("type");  
		StringBuffer buffer = new StringBuffer("update sys_auth set ");	
		if(type.equals("plus")){
			buffer.append("sort = sort+#{plusSort} where substring(sort,1,length(sort)-#{sortDigit})=#{sortLike}");
		}else{
			buffer.append("sort = sort-#{plusSort} where substring(sort,1,length(sort)-#{sortDigit})=#{sortLike}");
		}
		return buffer.toString();  
	}
	
	public String updateSortList(Map map){
		String type = (String) map.get("type"); 
		Integer insertType = (Integer) map.get("insertType");  
		Integer pid = (Integer) map.get("pid");  
		Integer sort = (Integer) map.get("sort");  
		Integer insertSort = (Integer) map.get("insertSort");  
		StringBuffer buffer = new StringBuffer("");	
		if(type.equals("insert")){
			if(insertType != null){
				if(insertType == 1){
					if(pid == null)
						buffer.append("select sort from sys_auth where pid is null and sort>=#{sort} order by sort desc");
					else
						buffer.append("select sort from sys_auth where pid = #{pid} and sort>=#{sort} order by sort desc");
				}else{
					if(pid == null)
						buffer.append("select sort from sys_auth where pid is null and sort>#{sort} order by sort desc");
					else
						buffer.append("select sort from sys_auth where pid = #{pid} and sort>#{sort} order by sort desc");
				}
			}			
		}else if(type.equals("delete")){
			if(pid == null)
				buffer.append("select sort from sys_auth where pid is null and sort>#{sort} order by sort asc");
			else
				buffer.append("select sort from sys_auth where pid = #{pid} and sort>#{sort} order by sort asc");
		}else{
			if(insertType == 1){
				if(sort>insertSort){
					if(pid == null)
						buffer.append("select sort from sys_auth where pid is null and sort>=#{insertSort} and sort<#{sort} order by sort desc");
					else
						buffer.append("select sort from sys_auth where pid = #{pid} and sort>=#{insertSort} and sort<#{sort} order by sort desc");
				}else{
					if(pid == null)
						buffer.append("select sort from sys_auth where pid is null and sort>#{sort} and sort<#{insertSort} order by sort asc");
					else
						buffer.append("select sort from sys_auth where pid = #{pid} and sort>#{sort} and sort<#{insertSort} order by sort asc");
				}
			}else{
				if(sort>insertSort){
					if(pid == null)
						buffer.append("select sort from sys_auth where pid is null and sort>#{insertSort} and sort<#{sort} order by sort desc");
					else
						buffer.append("select sort from sys_auth where pid = #{pid} and sort>#{insertSort} and sort<#{sort} order by sort desc");
				}else{
					if(pid == null)
						buffer.append("select sort from sys_auth where pid is null and sort>#{sort} and sort<=#{insertSort} order by sort asc");
					else
						buffer.append("select sort from sys_auth where pid = #{pid} and sort>#{sort} and sort<=#{insertSort} order by sort asc");
				}
			}			
		}
		return buffer.toString();
	}
}
