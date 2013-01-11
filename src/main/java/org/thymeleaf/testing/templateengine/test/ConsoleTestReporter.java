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
package org.thymeleaf.testing.templateengine.test;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.thymeleaf.util.Validate;



public class ConsoleTestReporter implements ITestReporter {

    private static final String NOW_FORMAT = "yyyy-MM-dd HH:mm:ss"; 
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(NOW_FORMAT);
    private static final BigInteger NANOS_IN_MILLIS = BigInteger.valueOf(1000000);
    
    
    private String reportName;
    
    
    
    public ConsoleTestReporter(final String reportName) {
        super();
        Validate.notEmpty(reportName, "Report name cannot be null or empty");
        this.reportName = reportName;
    }
    
    
    
    public String getReportName() {
        return this.reportName;
    }
    
    

    public void suiteStart() {
        output("[START] REPORT START");
    }

    public void suiteEnd(final long executionTimeNanos) {
        output("[END] REPORT END [" + duration(executionTimeNanos) + "]");
    }

    public void sequenceStart(final ITestSequence sequence) {
        final StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[SEQSTART]");
        strBuilder.append("[" + (sequence.hasName()? sequence.getName() : "*") + "]");
        strBuilder.append("[" + sequence.getIterations() + "]");
        strBuilder.append("[" + sequence.getSize() + "]");
        strBuilder.append("[" + (sequence.hasMaxTimeNanos()? sequence.getMaxTimeNanos() : "*") + "]");
        output(strBuilder.toString());
    }

    public void sequenceEnd(final ITestSequence sequence, final long executionTimeNanos) {
        
        final boolean execTimeOK =
                (sequence.hasMaxTimeNanos()? (sequence.getMaxTimeNanos().longValue() >= executionTimeNanos) : true);
        
        final StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[SEQEND]");
        strBuilder.append("[" + (sequence.hasName()? sequence.getName() : "*") + "]");
        strBuilder.append("[" + sequence.getIterations() + "]");
        strBuilder.append("[" + sequence.getSize() + "]");
        strBuilder.append("[" + (sequence.hasMaxTimeNanos()? sequence.getMaxTimeNanos() : "*") + "]");
        strBuilder.append("[" + executionTimeNanos + "]");
        strBuilder.append("[" + (execTimeOK? "EXECTIMEOK" : "EXECTIMEKO") + "]");
        if (!sequence.hasMaxTimeNanos()) {
            strBuilder.append(
                    " Sequence executed in " + duration(executionTimeNanos));
        } else {
            if (execTimeOK) {
                strBuilder.append(
                        " Sequence executed on time. Executed in " + 
                        duration(executionTimeNanos) + " (max: " + duration(sequence.getMaxTimeNanos()) + ")");
            } else {
                strBuilder.append(
                        " Sequence FAILED to execute on time. Executed in " + 
                        duration(executionTimeNanos) + " (max: " + duration(sequence.getMaxTimeNanos()) + ")");
            }
        }
        output(strBuilder.toString());
    }

    
    public void test(final ITest test, final ITestResult result, final long executionTimeNanos) {
        
        final boolean execTimeOK =
                (test.hasMaxTimeNanos()? (test.getMaxTimeNanos().longValue() >= executionTimeNanos) : true);
        
        final StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[TEST]");
        strBuilder.append("[" + (result.isOK()? "OK" : "KO") + "]");
        strBuilder.append("[" + (test.hasName()? test.getName() : "*") + "]");
        strBuilder.append("[" + test.getIterations() + "]");
        strBuilder.append("[" + (test.hasMaxTimeNanos()? test.getMaxTimeNanos() : "*") + "]");
        strBuilder.append("[" + executionTimeNanos + "]");
        strBuilder.append("[" + (execTimeOK? "EXECTIMEOK" : "EXECTIMEKO") + "]");
        strBuilder.append(' ');
        
        if (result.isOK()) {
            strBuilder.append("Test executed OK");
        } else {
            strBuilder.append("Test FAILED");
        }
        if (result.hasMessage()) {
            strBuilder.append(": " + result.getMessage());
        } else {
            strBuilder.append(". ");
        }
        if (result.hasThrowable()) {
            strBuilder.append(" [Exception thrown: " +  result.getThrowable().getClass().getName() + ": " + result.getThrowable().getMessage() + "]");
        }

        if (!test.hasMaxTimeNanos()) {
            strBuilder.append(
                    " Test executed in " + duration(executionTimeNanos));
        } else {
            if (execTimeOK) {
                strBuilder.append(
                        " Test executed on time. Executed in " + 
                        duration(executionTimeNanos) + " (max: " + duration(test.getMaxTimeNanos()) + ")");
            } else {
                strBuilder.append(
                        " Test FAILED to execute on time. Executed in " + 
                        duration(executionTimeNanos) + " (max: " + duration(test.getMaxTimeNanos()) + ")");
            }
        }
        
        output(strBuilder.toString());
        
    }


    
    private void output(final String message) {
        System.out.println("[" + now() + "][" + this.reportName + "] " + message);
    }
    
    
    private String now() {
        final Calendar cal = Calendar.getInstance();
        synchronized (DATE_FORMAT) {
            return DATE_FORMAT.format(cal.getTime());
        }
    }
    
    
    private String duration(final Long nanos) {
        if (nanos == null) {
            return null;
        }
        return duration(nanos.longValue());
    }
    
    private String duration(final long nanos) {
        final BigInteger nanosBI = BigInteger.valueOf(nanos);
        return nanosBI.toString() + "ns (" + nanosBI.divide(NANOS_IN_MILLIS) + "ms)";
    }
    
    
}