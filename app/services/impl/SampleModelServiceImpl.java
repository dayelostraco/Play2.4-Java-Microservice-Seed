package services.impl;

import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import models.dtos.SampleModelDto;
import models.entities.SampleModel;
import services.SampleModelService;
import services.TransformService;

import java.util.List;

/**
 * Created by Dayel Ostraco
 * 10/28/15.
 */
@Transactional
public class SampleModelServiceImpl implements SampleModelService {

    @Inject
    @Named("OrikaTransformService")
    private TransformService transformService;

    @Override
    public List<SampleModel> findAll() {
        return transformService.mapList(SampleModel.findAll(), SampleModelDto.class);
    }

    @Override
    public SampleModelDto create(SampleModelDto sampleModelDto) {

        // Convert the DTO to an Entity
        SampleModel sampleModel = transformService.map(sampleModelDto, SampleModel.class);

        if(sampleModel.getId()!=null) {
            sampleModel.setId(null);
        }

        // Save the entity
        sampleModel.save();

        // Return the persisted entity as a transformed DTO
        return transformService.map(sampleModel, SampleModelDto.class);
    }

    @Override
    public SampleModelDto findById(Long id) {
        return transformService.map(SampleModel.findById(id), SampleModelDto.class);
    }

    @Override
    public SampleModelDto update(SampleModelDto sampleModelDto) {

        // Convert the DTO to an Entity
        SampleModel sampleModel = transformService.map(sampleModelDto, SampleModel.class);

        // Update the entity
        sampleModel.update();

        // Return the updated entity as a transformed DTO
        return transformService.map(sampleModel, SampleModelDto.class);
    }

    @Override
    public void delete(Long id) {

        // Retrieve the entity
        SampleModel sampleModel = SampleModel.findById(id);

        sampleModel.delete();
    }
}
