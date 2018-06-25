/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.zookeeper.server;

import org.apache.zookeeper.server.ZooKeeperServer.State;

import java.util.concurrent.CountDownLatch;

/**
 * ZooKeeper server shutdown handler which will be used to handle ERROR or
 * SHUTDOWN server state transitions, which in turn releases the associated
 * shutdown latch.
 */
class ZooKeeperServerShutdownHandler {
    private final CountDownLatch shutdownLatch;

    ZooKeeperServerShutdownHandler(CountDownLatch shutdownLatch) {
        this.shutdownLatch = shutdownLatch;
    }

    /**
     * 在{@link ZooKeeperServer#setState(State)}中被调用
     * This will be invoked when the server transition to a new server state.
     *
     * @param state new server state
     */
    void handle(State state) {
        if (state == State.ERROR || state == State.SHUTDOWN) {
            shutdownLatch.countDown();
        }
    }
}