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

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.util.Literals;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Base class for all network messages.
 *
 * @author Grégory Van den Borre
 */
public abstract class NetworkMessage {

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
        this(getParamsFromMessage(message.message));
        this.index = 0;
    }

    /**
     * Retrieve the command from the message, it is the first parameter and is in numeric format.
     *
     * @param message Message containing the command.
     * @return The number corresponding to the command.
     * @throws InvalidNetworkMessage If the message cannot be correctly parsed.
     */
    public static int getCommandFromMessage(final MessageWrapper message) throws InvalidNetworkMessage {
        final String[] base = message.message.split(Literals.COMMAND_SEPARATOR);
        try {
            return Integer.parseInt(base[0]);
        } catch (NumberFormatException e) {
            throw new InvalidNetworkMessage("Invalid command in message: " + message, e);
        }
    }

    /**
     * Build a list of String from a list of objects. List can be used but not arrays
     *
     * @param o Objects to convert.
     * @return A list containing all converted objects.
     */
    @SuppressWarnings({"rawtypes"})
    protected static String[] convertParams(final Object... o) {
        final String[] s = new String[o.length];
        for (int i = 0; i < s.length; i++) {
            if (o[i] instanceof Collection) {
                @SuppressWarnings("unchecked")
                final List l = Lists.newList((Collection) o[i]);
                final StringBuilder sb = new StringBuilder();
                for (final Object obj : l) {
                    sb.append(obj.toString());
                    sb.append(MessageSeparation.COLLECTION_SEPARATOR);
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                s[i] = sb.toString();
            } else if (o[i] instanceof Point3D) {
                Point3D p = (Point3D) o[i];
                final float eps = 0.001f;
                if (p.y < eps && p.y > -eps) {
                    String ssb = String.valueOf(p.x) +
                            Literals.VECTOR_SEPARATOR +
                            p.z;
                    s[i] = ssb;
                } else {
                    s[i] = p.toString();
                }
            } else {
                s[i] = o[i].toString();
            }

        }
        return s;
    }

    /**
     * Split the message to extract its parameters.
     *
     * @param message Network message.
     * @return The message String value parameters.
     */
    private static String[] getParamsFromMessage(final String message) {
        final String[] base = message.split(Literals.COMMAND_SEPARATOR);
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
            message.append(Literals.COMMAND_SEPARATOR);
            message.append(o);
        }
        message.append(MessageSeparation.MESSAGE_END);
        return message.toString();
    }

    /**
     * Convert the parameter into a String.
     *
     * @return The String value of the parameter.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into String.
     */
    protected final String getString() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        String result = this.params[this.index];
        this.index++;
        return result;
    }

    /**
     * Convert the parameter into a boolean.
     *
     * @return The boolean value of the parameter.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into a boolean.
     */
    protected final boolean getBoolean() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        boolean result = Boolean.parseBoolean(this.params[this.index]);
        this.index++;
        return result;
    }

    /**
     * Convert the parameter into an integer.
     *
     * @return The integer value of the parameter.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into an integer.
     */
    protected final int getInt() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        try {
            int result = Integer.parseInt(this.params[this.index]);
            this.index++;
            return result;
        } catch (final NumberFormatException nfe) {
            throw new InvalidNetworkMessage("Error parsing int", nfe);
        }
    }

    /**
     * Convert the parameter into a long.
     *
     * @return The long value of the parameter.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into a long.
     */
    protected final long getLong() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        try {
            long result = Long.parseLong(this.params[this.index]);
            this.index++;
            return result;
        } catch (final NumberFormatException nfe) {
            throw new InvalidNetworkMessage("Error parsing long", nfe);
        }
    }

    /**
     * Convert the parameter into a float.
     *
     * @return The float value of the parameter.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into a float.
     */
    protected final float getFloat() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        try {
            float result = Float.parseFloat(this.params[this.index]);
            this.index++;
            return result;
        } catch (final NumberFormatException nfe) {
            throw new InvalidNetworkMessage("Error parsing float", nfe);
        }
    }

    /**
     * Convert the parameter into a Point3D.
     *
     * @return The Point3D value of the parameter.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into a Point3D.
     */
    protected final Point3D getPoint3D() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        try {
            Point3D result = new Point3D(this.params[this.index]);
            this.index++;
            return result;
        } catch (final InvalidParameterException e) {
            throw new InvalidNetworkMessage("Error retrieving Point3D", e);
        }
    }

    /**
     * Convert the parameter into an Id.
     *
     * @return The Id value of the parameter.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into an Id.
     */
    protected final EntityId getEntityId() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        try {
            EntityId result = EntityId.get(Long.parseLong(this.params[this.index]));
            this.index++;
            return result;
        } catch (final NumberFormatException nfe) {
            throw new InvalidNetworkMessage("Error retrieving id", nfe);
        }
    }

    /**
     * Convert the parameter into an Id.
     *
     * @return The Id value of the parameter.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into an Id.
     */
    protected final ActionId getActionId() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        try {
            ActionId result = ActionId.get(Integer.parseInt(this.params[this.index]));
            this.index++;
            return result;
        } catch (final NumberFormatException nfe) {
            throw new InvalidNetworkMessage("Error retrieving id", nfe);
        }
    }

    /**
     * Convert the parameter into an Id.
     *
     * @return The Id value of the parameter.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into an Id.
     */
    protected final PlayerId getPlayerId() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        try {
            PlayerId result = PlayerId.get(Integer.parseInt(this.params[this.index]));
            this.index++;
            return result;
        } catch (final NumberFormatException nfe) {
            throw new InvalidNetworkMessage("Error retrieving id", nfe);
        }
    }

    /**
     * Convert the parameter into a List of Float.
     *
     * @return A list of Float objects.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into a List of Float.
     */
    protected final List<Float> getFloatList() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        String param = this.params[this.index];
        param = param.replace("[", "");
        param = param.replace("]", "");
        final String[] values = param.split(MessageSeparation.COLLECTION_SEPARATOR);
        final List<Float> result = Lists.newList(values.length);
        for (final String current : values) {
            result.add(Float.valueOf(current));
        }
        this.index++;
        return result;
    }

    /**
     * Convert the parameter into a List of String.
     *
     * @return A list of String objects.
     * @throws InvalidNetworkMessage If the parameter cannot be correctly parsed into a List of String.
     */
    protected final List<String> getStringList() throws InvalidNetworkMessage {
        this.nullCheck(this.params[this.index]);
        String param = this.params[this.index];
        param = param.replace("[", "");
        param = param.replace("]", "");
        final String[] values = param.split(MessageSeparation.COLLECTION_SEPARATOR);
        final List<String> result = Lists.newList(values.length);
        Collections.addAll(result, values);
        this.index++;
        return result;
    }

    protected final List<EntityId> getEntityIdList() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        String param = this.params[this.index];
        param = param.replace("[", "");
        param = param.replace("]", "");
        final String[] values = param.split(MessageSeparation.COLLECTION_SEPARATOR);
        final List<EntityId> result = Lists.newList(values.length);
        for (final String current : values) {
            result.add(EntityId.get(Long.parseLong(current.trim())));
        }
        this.index++;
        return result;
    }

    protected final List<ActionId> getActionIdList() throws InvalidNetworkMessage {
        this.positionCheck(this.index);
        this.nullCheck(this.params[this.index]);
        String param = this.params[this.index];
        param = param.replace("[", "");
        param = param.replace("]", "");
        final String[] values = param.split(MessageSeparation.COLLECTION_SEPARATOR);
        final List<ActionId> result = Lists.newList(values.length);
        for (final String current : values) {
            result.add(ActionId.get(Integer.parseInt(current.trim())));
        }
        this.index++;
        return result;
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
