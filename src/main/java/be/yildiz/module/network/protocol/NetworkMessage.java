/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.module.network.protocol;

import be.yildiz.common.collections.Maps;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.mapper.ObjectMapper;

import java.util.Arrays;
import java.util.Map;

/**
 * Base class for all network messages.
 *
 * @author Grégory Van den Borre
 */
public abstract class NetworkMessage {

    private static final Map<Class, ObjectMapper> mappers = Maps.newMap();

    /**
     * Message parameters.
     */
    private final String[] params;

    /**
     * index of the current parameter read.
     */
    private int index;

    /**
     * Full constructor.
     *
     * @param param List of parameters contained in the message.
     */
    protected NetworkMessage(final String... param) {
        super();
        this.params = param;
    }

    /**
     * Build a NetworkMessage from a received message.
     *
     * @param message Received message, cannot be null.
     */
    protected NetworkMessage(final MessageWrapper message) {
        this(getParamsFromMessage(message));
        this.index = 0;
    }

    /**
     * Register a mapper for a given class, if a mapper is already existing for that class, nothing happens.
     * @param c Class to map.
     * @param m Mapper to use.
     * @param <T> Type to map.
     */
    public static <T> void registerMapper(Class<T> c, ObjectMapper<T> m) {
        mappers.putIfAbsent(c, m);
    }

    /**
     * Retrieve the command from the message, it is the first parameter and is in numeric format.
     *
     * @param message Message containing the command.
     * @return The number corresponding to the command.
     * @throws InvalidNetworkMessage If the message cannot be correctly parsed.
     */
    public static int getCommandFromMessage(final MessageWrapper message) throws InvalidNetworkMessage {
        final String[] base = message.message.split(MessageSeparation.COMMAND_SEPARATOR);
        try {
            return Integer.parseInt(base[0]);
        } catch (NumberFormatException e) {
            throw new InvalidNetworkMessage("Invalid command in message: " + message, e);
        }
    }

    /**
     * Split the message to extract its parameters.
     *
     * @param message Network message.
     * @return The message String value parameters.
     */
    private static String[] getParamsFromMessage(final MessageWrapper message) {
        assert message != null;
        final String[] base = message.message.split(MessageSeparation.COMMAND_SEPARATOR);
        return Arrays.copyOfRange(base, 1, base.length);
    }

    /**
     * Append all parameters to create a unique message string.
     *
     * @return the built message.
     */
    public final String buildMessage() {
        final StringBuilder message = new StringBuilder();
        message.append(MessageSeparation.MESSAGE_BEGIN);
        message.append(this.command());
        for (final Object o : this.params) {
            message.append(MessageSeparation.COMMAND_SEPARATOR);
            message.append(o);
        }
        message.append(MessageSeparation.MESSAGE_END);
        return message.toString();
    }

    @SuppressWarnings("unchecked")
    public final <T> T from(Class<T> c) throws InvalidNetworkMessage {
        try {
            this.positionCheck(this.index);
            this.nullCheck(this.params[this.index]);
            T result = (T) mappers.get(c).from(this.params[this.index]);
            this.index++;
            return result;
        } catch (ClassCastException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> String to(T t, Class<T> c) {
        return mappers.get(c).to(t);
    }

    /**
     * Check if the parameter is <code>null</code>.
     *
     * @param value Value to check.
     * @throws InvalidNetworkMessage If the given parameter is <code>null</code>.
     */
    private void nullCheck(final String value) throws InvalidNetworkMessage {
        if (value == null) {
            throw new InvalidNetworkMessage("Null value");
        }
    }

    /**
     * Check if the parameter position is correctly in the parameter array.
     *
     * @param value Value to check.
     * @throws InvalidNetworkMessage If the position is not correct.
     */
    private void positionCheck(final int value) throws InvalidNetworkMessage {
        if (value > this.params.length - 1 || value < 0) {
            throw new InvalidNetworkMessage("Message incomplete");
        }
    }

    /**
     * @return The command to use with the message type.
     */
    public abstract int command();

    @Override
    public final String toString() {
        return this.buildMessage();
    }
}
