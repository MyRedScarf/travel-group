package com.fuchen.travel.event;

import com.alibaba.fastjson.JSONObject;
import com.fuchen.travel.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 * @author Fu chen
 * @date 2022/12/11
 * 生产者
 */
@Component
public class EventProducer {
	
	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	/**
	 * 处理事件
	 * @param event
	 */
	public void fireEvent(Event event){
		//将事件发布到指定的主题
		kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
	}
}
