package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //このアノテーションを付けたクラス内では、@Beanアノテーションを使ってBeanを登録できる
@EnableBatchProcessing // SpringBatchの設定をするためのアノテーション. JobBuilderFactoryやStepBuilderFactoryがDIできるようになる
public class BatchConfig{
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	@Qualifier("hello")
	private Tasklet hello;
	@Autowired
	@Qualifier("hello2")
	private Tasklet hello2;
	
	/** Taskletのstepを生成 */
	@Bean
	public Step taskletStep1() {
		return stepBuilderFactory
				// builderの取得 引数にStepの名前を設定する。Step名はDBに登録され、どのStepがいつ実行されたかをDBで確認できる
				.get("HelloTaskletStep1")
				.tasklet(hello) // Taskletの指定. Taskletインタフェースの実装クラスを入れる.
				.build();  // stepの生成
	}
	
	@Bean
	public Step taskletStep2() {
		return stepBuilderFactory
				.get("HelloTaskletStep2")
				.tasklet(hello2) 
				.build();
	}
	
	/** Jobの生成 */
	@Bean
	public Job taskletJob() throws Exception{
		return jobBuilderFactory
				// Builderのセット. 引数にJob名を渡す.Job名はDBに登録されどのJobがいつ実行されたかをDBで確認できる.
				.get("HelloWorldTaskletJob")
				// JobIDのインクリメント. JobIDはDBに登録される. テーブルの主キーになっている.
				.incrementer(new RunIdIncrementer()) 
				// 実行するstepを指定
				.start(taskletStep1())
				.next(taskletStep2())
				 // jobの生成
				.build();
	}

}
