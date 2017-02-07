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

package org.camelcookbook.transformation.xquery;

import java.io.InputStream;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class XqueryTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new XqueryRouteBuilder();
    }

    @Test
    public void testXquery() throws Exception {
        final InputStream resource = getClass().getClassLoader().getResourceAsStream("bookstore.xml");
        final String request = context().getTypeConverter().convertTo(String.class, resource);

        String response = template.requestBody("direct:start", request, String.class);

        log.info("Response = {}", response);
        assertEquals("<books><title lang=\"en\">Apache Camel Developer's Cookbook</title><title lang=\"en\">Learning XML</title></books>", response);
    }
}
