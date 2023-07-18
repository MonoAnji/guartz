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

import com.google.inject.Guice;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManualStartTestCase {

	@Inject
	private Scheduler scheduler;

	@Inject
	private TimedTask timedTask;


	@BeforeEach
	public void setUp() {
		Guice.createInjector(new QuartzModule() {
			@Override
			protected void schedule() {
				configureScheduler().withManualStart();
				scheduleJob(TimedTask.class);
			}
		}).getMembersInjector(ManualStartTestCase.class).injectMembers(this);
	}


	@AfterEach
	public void tearDown()
			throws Exception {
		scheduler.shutdown();
	}


	@Test
	public void testManualStart()
			throws Exception {
		Thread.sleep(5000L);
		assertEquals(0, timedTask.getInvocationsTimedTaskA());
		scheduler.start();
		Thread.sleep(5000L);
		assertTrue(timedTask.getInvocationsTimedTaskA() > 0);
	}

}
