package com.fuchen.travel.background.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 伏辰
 * @date 2022/7/2
 * 加密工具类
 */
public class TravelUtil {
	
	/**
	 * 生成随机字符串
	 * @return UUID
	 */
	public static String generateUUID(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	
	/**
	 * MD5加密算法
	 * @param key 盐值
	 * @return 加密后的结果
	 */
	public static String md5(String key){
		if (StringUtils.isBlank(key)){
			return null;
		}
		return DigestUtils.md5DigestAsHex(key.getBytes());
	}
	
	/**
	 * 将数据加工成json数据
	 * @param code
	 * @param msg 加工结果
	 * @param map map集合数据
	 * @return json数据
	 */
	public static String getJsonString(Integer code, String msg, Map<String, Object> map){
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		if (map != null) {
			for (String key : map.keySet()) {
				json.put(key, map.get(key));
			}
		}
		return json.toJSONString();
	}
	
	public static String getJsonString(Integer code, String msg){
		return getJsonString(code, msg, null);
	}
	
	public static String getJsonString(Integer code){
		return getJsonString(code, null, null);
	}
	
	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", "zhangsan");
		map.put("age", "18");
		System.out.println(getJsonString(0, "ok", map));
		
		
	}


}
