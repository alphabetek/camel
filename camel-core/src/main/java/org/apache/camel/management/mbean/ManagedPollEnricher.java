/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.management.mbean;

import org.apache.camel.CamelContext;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.api.management.mbean.ManagedPollEnricherMBean;
import org.apache.camel.model.PollEnrichDefinition;
import org.apache.camel.processor.PollEnricher;
import org.apache.camel.spi.ManagementStrategy;
import org.apache.camel.util.URISupport;

/**
 * @version 
 */
@ManagedResource(description = "Managed PollEnricher")
public class ManagedPollEnricher extends ManagedProcessor implements ManagedPollEnricherMBean {
    private final PollEnricher processor;
    private String uri;

    public ManagedPollEnricher(CamelContext context, PollEnricher processor, PollEnrichDefinition definition) {
        super(context, processor, definition);
        this.processor = processor;
    }

    public void init(ManagementStrategy strategy) {
        super.init(strategy);
        boolean sanitize = strategy.getManagementAgent().getMask() != null ? strategy.getManagementAgent().getMask() : false;
        uri = getDefinition().getExpression().getExpression();
        if (sanitize) {
            uri = URISupport.sanitizeUri(uri);
        }
    }

    @Override
    public PollEnrichDefinition getDefinition() {
        return (PollEnrichDefinition) super.getDefinition();
    }

    @Override
    public PollEnricher getProcessor() {
        return processor;
    }

    @Override
    public String getExpressionLanguage() {
        return getDefinition().getExpression().getLanguage();
    }

    @Override
    public String getExpression() {
        return uri;
    }

    @Override
    public Long getTimeout() {
        return processor.getTimeout();
    }

    @Override
    public Integer getCacheSize() {
        return processor.getCacheSize();
    }

    @Override
    public Boolean isIgnoreInvalidEndpoint() {
        return processor.isIgnoreInvalidEndpoint();
    }

    @Override
    public Boolean isAggregateOnException() {
        return processor.isAggregateOnException();
    }
}
