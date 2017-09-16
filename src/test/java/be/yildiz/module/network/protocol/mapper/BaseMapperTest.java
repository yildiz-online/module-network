package be.yildiz.module.network.protocol.mapper;

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void happyFlow() throws InvalidNetworkMessage {
        String to = mapper.to(baseObject);
        T from = mapper.from(to);
        assertEquals(baseObject, from);
    }

    @Test
    void tooShort() throws InvalidNetworkMessage {
        String to = mapper.to(baseObject);
        if (to.contains(MessageSeparation.OBJECTS_SEPARATOR)) {
            assertThrows(InvalidNetworkMessage.class, () -> mapper.from(to.substring(0, to.indexOf(MessageSeparation.OBJECTS_SEPARATOR))));
        } else if (to.contains(MessageSeparation.VAR_SEPARATOR)) {
            assertThrows(InvalidNetworkMessage.class, () -> mapper.from(to.substring(0, to.indexOf(MessageSeparation.VAR_SEPARATOR))));
        } else {
            assertThrows(InvalidNetworkMessage.class, () -> mapper.from(""));
        }
    }

    @Test
    void fromNull() throws InvalidNetworkMessage {
        assertThrows(AssertionError.class, () -> mapper.from(null));
    }

    @Test
    void toNull() {
        assertThrows(AssertionError.class, () -> mapper.to(null));
    }

}
