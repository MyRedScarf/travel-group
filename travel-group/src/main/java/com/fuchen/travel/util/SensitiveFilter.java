package com.fuchen.travel.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fu chen
 * @date 2022/12/6
 * 敏感词过滤
 */
@Slf4j
@Component
public class SensitiveFilter {
	
	//替换符
	private static final String REPLACEMENT = "***";
	
	//根节点
	private final TrieNode rootNode = new TrieNode();
	
	@PostConstruct
	public void init() {
		try(
				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
				BufferedReader reader =new BufferedReader(new InputStreamReader(inputStream))
		) {
			String keyword;
			while ((keyword = reader.readLine()) != null) {
				//添加到前缀树
				this.addKeyword(keyword);
			}
		} catch (IOException e) {
			log.error("加载敏感词文件失败！" + e.getMessage());
		}
	}

	/**
	 * 将一个敏感词添加到前缀树中
	 * @param keyword 关键词
	 */
	private void addKeyword(String keyword) {
	    TrieNode tempNode = rootNode;
		for (int i = 0; i < keyword.length(); i++) {
		    char c = keyword.charAt(i);
			TrieNode subNode = tempNode.getSubNode(c);
			
			//初始化子节点
			if (subNode == null) {
				subNode = new TrieNode();
				tempNode.addSubNode(c , subNode);
			}
			//指向子节点，进入下一轮循环
			tempNode = subNode;
			
			//设置结束标识
			if (i == keyword.length() - 1) {
				tempNode.setKeywordEnd(true);
			}
		}
	}
	
	/**
	 * 过滤敏感词
	 * @param text 待过滤的文本
	 * @return 过滤后的文本
	 */
	public String filter(String text) {
		if (StringUtils.isBlank(text)) {
		    return null;
		}
		
		//指针1
		TrieNode tempNode = rootNode;
		//指针2
		int begin = 0;
		//指针3
		int position = 0;
		//结果
		StringBuilder sb = new StringBuilder();
		
		while (position < text.length()) {
			char c = text.charAt(position);
			
			//跳过符号
			if (this.isSymbol(c)) {
				//若指针1处于根节点，则此符号计入结果，让指针2向下走一步
				if (tempNode == rootNode) {
					sb.append(c);
					begin ++;
				}
				// 无论符号在开头或中间，指针3都向下走一步
				position ++;
				continue;
			}
			//检查下一个节点
			tempNode = tempNode.getSubNode(c);
			if (tempNode == null){
				//以begin开头的字符串不是敏感词
				sb.append(text.charAt(begin));
				//进入下一个位置
				position = ++ begin;
				//重新指向根节点
				tempNode = rootNode;
			} else if (tempNode.isKeywordEnd()) {
				//发现敏感词，将begin--position替换
				sb.append(REPLACEMENT);
				//进入下一个位置
				begin = ++ position;
				//重新指向根节点
				tempNode = rootNode;
			} else {
				//检查下一个字符
				position ++;
			}
		}
		//将最后一批字符计入结果
		sb.append(text.substring(begin));
		
		return sb.toString();
	}
	
	/**
	 * 判断是否为符号
	 * @param c 字符
	 * @return 是字符返回true，是符号返回false
	 */
	private boolean isSymbol(Character c){
		return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c >0x9FFF);
	}
	
	/**
	 * 前缀树
	 */
	private class TrieNode{
		
		//关键词结束标识
		private boolean isKeywordEnd = false;
		
		//子节点 key是下级字符， value是下级节点
		private Map<Character, TrieNode> subNodes = new HashMap<>();
		
		public boolean isKeywordEnd() {
			return isKeywordEnd;
		}
		
		public void setKeywordEnd(boolean keywordEnd) {
			isKeywordEnd = keywordEnd;
		}
		
		//添加子节点
		public void addSubNode(Character c, TrieNode node){
			subNodes.put(c, node);
		}
		
		//获取子节点
		public TrieNode getSubNode(Character c) {
			return subNodes.get(c);
		}
	}

}
