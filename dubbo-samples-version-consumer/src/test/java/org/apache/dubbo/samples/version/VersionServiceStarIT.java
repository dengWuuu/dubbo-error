/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.samples.version;

import org.apache.dubbo.common.Version;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.version.api.VersionService;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(locations = {"classpath:/spring/version-consumer-star.xml"})
public class VersionServiceStarIT {
//    @DubboReference(version = "*", loadbalance = "roundrobin", client = "myNetty")
    private VersionService service;

    @BeforeClass
    public static void setUp() {
        MyNettyTransporter.reset();
    }

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("transporter connected count: " + MyNettyTransporter.getConnectedCount());
            if (2 == MyNettyTransporter.getConnectedCount()) {
                break;
            }
            Thread.sleep(200);
        }
        Assert.assertEquals(2, MyNettyTransporter.getConnectedCount());

        if (Version.getVersion().compareTo("3.1.0") > 0) {
            for (int i = 0; i < 10; i++) {
                System.out.println("address received: " + MyAddressListener.getAddressSize());
                if (2 == MyAddressListener.getAddressSize()) {
                    break;
                }
                Thread.sleep(200);
            }
            Assert.assertEquals(2, MyAddressListener.getAddressSize());
            Thread.sleep(100);
        }
    }
}
