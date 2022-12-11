package com.example.demo.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Component("hello")
@StepScope
@Slf4j
public class HelloTasklet implements Tasklet{

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception{
		
		log.info("This is HelloTasklet.");
		
		// HelloTaskLet2に値を渡す
		// JobやStepの実行結果などを持つExecutionContextクラスに値を格納する。
		// ExecutionContextは、JobExecutionContext, StepExecutionContextの2つがある。
		
		//JobExecutionContextの取得（実行中のJobで参照できる、異なるstep間で参照できる）
		ExecutionContext jobContext = contribution
				.getStepExecution()//StepExecution
				.getJobExecution() //JobExecution
				.getExecutionContext(); //JobExecutionContext
		jobContext.put("jobKey","Hello");
		
		//StepExecutionContextの取得（実行中のstep内で参照できる、異なるstep間では参照できない）。
		ExecutionContext stepContext = contribution
				.getStepExecution()
				.getExecutionContext(); //StepExecutionContext
		stepContext.put("stepKey","World");

		return RepeatStatus.FINISHED;
	}

}
