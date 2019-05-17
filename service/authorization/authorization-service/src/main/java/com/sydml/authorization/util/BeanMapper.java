package com.sydml.authorization.util;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BeanMapper extends ConfigurableMapper implements ApplicationContextAware {

    private MapperFactory factory;
    private ApplicationContext applicationContext;

    public BeanMapper() {
        super(false);
    }

    @Override
    protected void configure(MapperFactory factory) {
        this.factory = factory;
        addAllSpringBeans(applicationContext);
    }

    @Override
    protected void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
        // Nothing to configure for now
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void addMapper(Mapper<?, ?> mapper) {
        ClassMapBuilder<?, ?> builder = factory.classMap(mapper.getAType(), mapper.getBType());
        if (mapper instanceof ICustomFieldMap) {
            ICustomFieldMap customerFieldMap = (ICustomFieldMap) mapper;
            Map<String, String> fieldMap = customerFieldMap.getFieldMap();
            for (String it : fieldMap.keySet()) {
                builder.field(it, fieldMap.get(it));
            }
        }
        builder.byDefault().customize((Mapper) mapper).register();
    }

    public void addConverter(Converter<?, ?> converter) {
        factory.getConverterFactory().registerConverter(converter);
    }

    @SuppressWarnings("rawtypes")
    private void addAllSpringBeans(final ApplicationContext applicationContext) {
        Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
        for (Mapper mapper : mappers.values()) {
            addMapper(mapper);
        }
        Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
        for (Converter converter : converters.values()) {
            addConverter(converter);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        init();
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
        return super.mapAsList(source, destinationClass);
    }
}
