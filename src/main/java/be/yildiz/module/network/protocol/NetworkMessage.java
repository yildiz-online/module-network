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

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.mapper.ObjectMapper;

/**
 * Base class for all network messages.
 *
 * @author Grégory Van den Borre
 */
public final class NetworkMessage<T> {

    /**
     * Message parameters.
     */
    private final T dto;

    private final int command;

    /**
     * Mapped dto, does not contain the command and begin/end message separators.
     */
    private final String message;

    /**
     * Build a NetworkMessage from an object.
     * @param dto Object to convert into a message.
     * @param mapper Mapper to convert the object.
     * @param command Command value to set to the message.
     */
    public NetworkMessage(final T dto, final ObjectMapper<T> mapper, int command) {
        super();
        this.dto = dto;
        this.command = command;
        this.message = mapper.to(this.dto);
    }

    /**
     * Build a NetworkMessage from a received message.
     *
     * @param message Received message, cannot be null.
     * @param mapper Mapper to transform the message into an object.
     * @param expectedCommand Command value expected.
     * @throws InvalidNetworkMessage If the message cannot be parsed by the mapper, or if the expected command does not match.
     */
    public NetworkMessage(final MessageWrapper message, final ObjectMapper<T> mapper, int expectedCommand) throws InvalidNetworkMessage {
        String[] msgs = message.message
                .replaceAll(MessageSeparation.MESSAGE_BEGIN, "")
                .replaceAll(MessageSeparation.MESSAGE_END, "")
                .split(MessageSeparation.COMMAND_SEPARATOR);
        if(msgs.length > 1) {
            this.message = msgs[1];
        } else {
            this.message = "";
        }
        try {
            this.command = Integer.valueOf(msgs[0]);
        } catch (NumberFormatException e) {
            throw new InvalidNetworkMessage(e);
        }
        if(this.command != expectedCommand) {
            throw new InvalidNetworkMessage("Expected command is " + expectedCommand + " received is " + command);
        }
        this.dto = mapper.from(this.message);
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
     * Build the message with the begin message separator, the command, the parsed dto and the end message separator.
     *
     * @return the built message.
     */
    public final String buildMessage() {
        return String.format("%s%d%s%s%s",
                MessageSeparation.MESSAGE_BEGIN,
                this.command(),
                MessageSeparation.COMMAND_SEPARATOR,
                this.message,
                MessageSeparation.MESSAGE_END);
    }


    /**
     * @return The command to use with the message type.
     */
    public int command() {
        return this.command;
    }

    @Override
    public final String toString() {
        return this.buildMessage();
    }

    public T getDto() {
        return dto;
    }
}
