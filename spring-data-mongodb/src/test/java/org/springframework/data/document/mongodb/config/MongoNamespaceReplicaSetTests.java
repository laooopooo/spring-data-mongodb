/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.document.mongodb.config;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.document.mongodb.MongoFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MongoNamespaceReplicaSetTests extends NamespaceTestSupport {

	@Autowired
	private ApplicationContext ctx;


	@Test
	public void testMongoWithReplicaSets() throws Exception {
		assertTrue(ctx.containsBean("replicaSetMongo"));
		MongoFactoryBean mfb = (MongoFactoryBean) ctx.getBean("&replicaSetMongo");
		String host = readField("host", mfb);
		Integer port = readField("port", mfb);
		List<ServerAddress> replicaSetSeeds = readField("replicaSetSeeds", mfb);
		assertNotNull(replicaSetSeeds);		
		
		assertEquals("127.0.0.1", replicaSetSeeds.get(0).getHost());
		assertEquals(27017, replicaSetSeeds.get(0).getPort());
		
    assertEquals("localhost", replicaSetSeeds.get(1).getHost());
    assertEquals(27018, replicaSetSeeds.get(1).getPort());
    
    Mongo mongo = mfb.getObject();
    
    //TODO test infrastructure to have replica sets
    //assertEquals(2, mongo.getAllAddress().size());
		
	}

}
