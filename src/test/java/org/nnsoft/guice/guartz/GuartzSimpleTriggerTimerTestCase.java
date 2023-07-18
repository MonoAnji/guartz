package org.nnsoft.guice.guartz;

/*
 *    Copyright 2009-2012 The 99 Software Foundation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.*;

import static com.google.inject.Guice.createInjector;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuartzSimpleTriggerTimerTestCase {

	@Inject
	private SimpleTask timedTask;

	@Inject
	private Scheduler scheduler;


	@BeforeEach
	public void setUp() {
		createInjector(new QuartzModule() {

			@Override
			protected void schedule() {
				Trigger trigger =
						TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.repeatSecondlyForever()).build();

				scheduleJob(SimpleTask.class).withTrigger(trigger);
			}

		}).getMembersInjector(GuartzSimpleTriggerTimerTestCase.class).injectMembers(this);
	}


	@AfterEach
	public void tearDown()
			throws Exception {
		this.scheduler.shutdown();
	}


	@Test
	public void minimalTest()
			throws Exception {
		Thread.sleep(5000);
		assertTrue(this.timedTask.getInvocation() > 0);
	}


	@Singleton
	private static class SimpleTask
			implements Job {

		private int invocation = 0;


		public void execute(JobExecutionContext context) {
			invocation++;
		}


		public int getInvocation() {
			return invocation;
		}

	}

}
