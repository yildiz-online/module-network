/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
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

package be.yildizgames.module.network.exceptions;

import java.io.Serial;

/**
 * Thrown when parsing a network message fails.
 *
 * @author Grégory Van den Borre
 */
public final class InvalidNetworkMessage extends IllegalArgumentException {

    /**
     * Current version serial ID.
     */
    @Serial
    private static final long serialVersionUID = 8789862091452905131L;

    /**
     * Create a new exception instance.
     *
     * @param message Error message.
     */
    //@requires message != null
    public InvalidNetworkMessage(final String message) {
        super(message);
    }

    public InvalidNetworkMessage(final Exception cause) {
        super(cause);
    }

    /**
     * Full constructor.
     *
     * @param message   Error message.
     * @param exception Base exception.
     */
    public InvalidNetworkMessage(final String message, final Exception exception) {
        super(message, exception);
    }

}
