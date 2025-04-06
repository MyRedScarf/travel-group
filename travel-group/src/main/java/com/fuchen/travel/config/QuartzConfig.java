package com.fuchen.travel.config;

import com.fuchen.travel.quartz.PostScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @author Fu chen
 * @date 2022/12/25
 */
@Configuration
public class QuartzConfig {

	/**
	 * 刷新帖子数据
	 * @return 数据
	 */
	@Bean
	public JobDetailFactoryBean postScoreRefreshJobDetail(){
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(PostScoreRefreshJob.class);
		factoryBean.setName("postScoreRefreshJob");
		factoryBean.setGroup("communityJobGroup");
		factoryBean.setDurability(true);
		factoryBean.setRequestsRecovery(true);
		return factoryBean;
	}
	
	@Bean
	public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(postScoreRefreshJobDetail);
		factoryBean.setName("postScoreRefreshTrigger");
		factoryBean.setGroup("communityTriggerGroup");
		factoryBean.setRepeatInterval(1000 * 60 * 5);
		factoryBean.setJobDataMap(new JobDataMap());
		return factoryBean;
		
	}
}
