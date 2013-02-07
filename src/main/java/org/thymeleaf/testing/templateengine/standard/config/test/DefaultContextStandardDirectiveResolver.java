/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2012, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.testing.templateengine.standard.config.test;

import org.thymeleaf.context.IContext;



public class DefaultContextStandardDirectiveResolver extends AbstractStandardDirectiveResolver<IContext> {

    
    public static final DefaultContextStandardDirectiveResolver INSTANCE = new DefaultContextStandardDirectiveResolver();
    
    
    
    private DefaultContextStandardDirectiveResolver() {
        super(IContext.class);
    }



    @Override
    protected IContext getValue(final String executionId, final String documentName, 
            final String directiveName, final String directiveValue) {

        // TODO implement this!
        throw new RuntimeException("To be implemented!!");
        
    }
    
   
    
}