/*
 * Copyright (C) Scott Cranton, Jakub Korab, and Christian Posta
 * https://github.com/CamelCookbook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camelcookbook.splitjoin.split;

import org.apache.camel.CamelExchangeException;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates that the remaining split elements will be not processed after an exception is thrown
 * when <code>stopOnException</code> is used on the split block.
 */
public class SplitExceptionHandlingStopOnExceptionTest extends CamelTestSupport {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new SplitExceptionHandlingStopOnExceptionRoute();
    }

    @Test
    public void testNoElementsProcessedAfterException() throws Exception {
        String[] array = new String[]{"one", "two", "three"};

        MockEndpoint mockSplit = getMockEndpoint("mock:split");
        mockSplit.expectedMessageCount(1);
        mockSplit.expectedBodiesReceived("one");

        MockEndpoint mockOut = getMockEndpoint("mock:out");
        mockOut.expectedMessageCount(0);

        try {
            template.sendBody("direct:in", array);
            fail("Exception not thrown");
        } catch (CamelExecutionException ex) {
            assertTrue(ex.getCause() instanceof CamelExchangeException);
            log.info(ex.getMessage());
            assertMockEndpointsSatisfied();
        }
    }
}
