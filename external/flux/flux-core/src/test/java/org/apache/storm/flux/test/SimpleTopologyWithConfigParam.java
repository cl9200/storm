/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.storm.flux.test;

import backtype.storm.Config;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import org.apache.storm.flux.wrappers.bolts.LogInfoBolt;
import org.apache.storm.flux.wrappers.spouts.FluxShellSpout;

import java.util.Map;

/**
 * Test topology source that does not implement TopologySource, but has the same
 * `getTopology()` method.
 */
public class SimpleTopologyWithConfigParam {


    public SimpleTopologyWithConfigParam(){}

    public SimpleTopologyWithConfigParam(String foo, String bar){}


    public StormTopology getTopology(Config config) {
        TopologyBuilder builder = new TopologyBuilder();

        // spouts
        FluxShellSpout spout = new FluxShellSpout(
                new String[]{"node", "randomsentence.js"},
                new String[]{"word"});
        builder.setSpout("sentence-spout", spout, 1);

        // bolts
        builder.setBolt("log-bolt", new LogInfoBolt(), 1)
                .shuffleGrouping("sentence-spout");

        return builder.createTopology();
    }
}
