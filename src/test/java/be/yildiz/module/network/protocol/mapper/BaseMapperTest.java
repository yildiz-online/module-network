package be.yildiz.module.network.protocol.mapper;

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Grégory Van den Borre
 */
public abstract class BaseMapperTest <T>{

    private final ObjectMapper<T> mapper;
    private final T baseObject;

    protected BaseMapperTest(ObjectMapper<T> mapper, T baseObject) {
        this.mapper = mapper;
        this.baseObject = baseObject;
    }

    @Test
    public void happyFlow() throws InvalidNetworkMessage {
        String to = mapper.to(baseObject);
        T from = mapper.from(to);
        Assert.assertFalse(baseObject == from);
        Assert.assertEquals(baseObject, from);
    }

    @Test(expected = InvalidNetworkMessage.class)
    public void tooShort() throws InvalidNetworkMessage {
        String to = mapper.to(baseObject);
        if (to.contains(MessageSeparation.OBJECTS_SEPARATOR)) {
            mapper.from(to.substring(0, to.indexOf(MessageSeparation.OBJECTS_SEPARATOR)));
        } else if (to.contains(MessageSeparation.VAR_SEPARATOR)) {
            mapper.from(to.substring(0, to.indexOf(MessageSeparation.VAR_SEPARATOR)));
        } else {
            mapper.from("");
        }
    }

    @Test(expected = AssertionError.class)
    public void fromNull() throws InvalidNetworkMessage {
        mapper.from(null);
    }

    @Test(expected = AssertionError.class)
    public void toNull() {
        mapper.to(null);
    }

}
