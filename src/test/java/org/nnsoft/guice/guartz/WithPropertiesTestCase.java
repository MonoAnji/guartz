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

import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;

import java.util.Properties;

import static com.google.inject.Guice.createInjector;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithPropertiesTestCase {
	private static final String INSTANCE_NAME = "Guartz";

	@Inject
	private Scheduler scheduler;


	@BeforeEach
	public void setUp() {
		createInjector(new QuartzModule() {
			@Override
			protected void schedule() {
				Properties properties = new Properties() {
					private static final long serialVersionUID = 1L;

					{
						put("org.quartz.scheduler.instanceName", INSTANCE_NAME);
						put("org.quartz.threadPool.class", "org.quartz.simpl.ZeroSizeThreadPool");
					}
				};
				configureScheduler().withProperties(properties);
			}
		}).getMembersInjector(WithPropertiesTestCase.class).injectMembers(this);
	}


	@AfterEach
	public void tearDown()
			throws Exception {
		scheduler.shutdown();
	}


	@Test
	public void testPropertiesConfiguredInstanceName()
			throws Exception {
		assertEquals(scheduler.getSchedulerName(), INSTANCE_NAME);
	}

}
