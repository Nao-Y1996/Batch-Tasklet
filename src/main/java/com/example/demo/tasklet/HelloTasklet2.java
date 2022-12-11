package com.example.demo.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Component("hello2")
@StepScope
@Slf4j
public class HelloTasklet2  implements Tasklet{
		
	// ExexcutionContextから値を取り出す(別のステップから変数を受け取る)	
	@Value("#{JobExecutionContext['jobKey']}")
	private String jobValue;
	@Value("#{JobExecutionContext['stepKey']}")
	private String stepValue;
	
	
	
	@Override
	public RepeatStatus execute(StepContribution contoribution, ChunkContext chunkContext) throws Exception{
		
		log.info("This is HelloTaskLet2");
		log.info("jobValue = {}", jobValue);
		log.info("stepValue = {}", stepValue);
		
		// 以下のようにしても値を取り出すことができる
		String jobValue2 = (String) contoribution.getStepExecution().getJobExecution().getExecutionContext().get("jobKey");
		String stepValue2 = (String) contoribution.getStepExecution().getExecutionContext().get("stepKey");
		log.info("jobValue = {}", jobValue2);
		log.info("stepValue = {}", stepValue2);
		
		return RepeatStatus.FINISHED;
	}

}