package services.impl;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import models.dtos.SampleModelDto;
import services.TransformService;

import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused", "unchecked"})
public class OrikaTransformServiceImpl implements TransformService {

    private MapperFacade mapper;

    public OrikaTransformServiceImpl() {

        // Instantiate the Mapper Factory
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        // Add Mappings to Mapper Factory here
        mapperFactory.classMap(
                models.entities.SampleModel.class,
                SampleModelDto.class)
                .field("id", "id")
                .field("name", "name")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        mapper = mapperFactory.getMapperFacade();
    }

    public <F, T> T map(F mapFromObject, Class mapToClass) {
        return (T) mapper.map(mapFromObject, mapToClass);
    }

    public <T> T mapList(List<?> mapFromList, Class mapToClass) {
        return (T) mapper.mapAsList(mapFromList, mapToClass);
    }

    public <T> T mapSet(Set<?> mapFromSet, Class mapToClass) {
        return (T) mapper.mapAsSet(mapFromSet, mapToClass);
    }
}
