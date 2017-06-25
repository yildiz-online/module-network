package be.yildiz.module.network.protocol.mapper;

import be.yildiz.module.network.protocol.ListWrapper;

/**
 * @author Grégory Van den Borre
 */
public abstract class ListWrapperMapper<T> implements ObjectMapper<ListWrapper<T>> {

    protected final CollectionMapper<T> mapper;

    protected ListWrapperMapper(ObjectMapper<T> objectMapper) {
        super();
        this.mapper = new CollectionMapper<>(objectMapper);
    }

    @Override
    public String to(ListWrapper<T> listWrapper) {
        return this.mapper.to(listWrapper.getList());
    }
}
