/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.module.network.client;

/**
 * @author Grégory Van den Borre
 */
public class ConnectionRetryStrategy {

    private final int maxRetries;

    /**
     * Unit is milliseconds.
     */
    private final long interval;

    private int iteration;

    private long lastRunTime;

    private ConnectionRetryStrategy(int maxRetries, long interval) {
        this.maxRetries = maxRetries;
        this.interval = interval;
    }

    private ConnectionRetryStrategy(long interval) {
        this(Integer.MAX_VALUE, interval);
    }

    public static ConnectionRetryStrategy retryEvery(long interval) {
        return new ConnectionRetryStrategy(interval);
    }

    public static ConnectionRetryStrategy none() {
        return new ConnectionRetryStrategy(0, 0);
    }

    public static ConnectionRetryStrategy retryMaxEvery(int maxRetries, long interval) {
        return new ConnectionRetryStrategy(maxRetries, interval);
    }

    boolean canRetryToConnect() {
        if(this.iteration < this.maxRetries) {
            long now = System.currentTimeMillis();
            if(now >= this.lastRunTime + this.interval) {
                this.iteration++;
                this.lastRunTime = now;
                return true;
            }
        }
        return false;
    }
}
