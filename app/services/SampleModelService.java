package services;

import models.dtos.SampleModelDto;
import models.entities.SampleModel;

import java.util.List;

/**
 * Created by Dayel Ostraco
 * 10/28/15.
 */
public interface SampleModelService {

    List<SampleModel> findAll();

    SampleModelDto create(SampleModelDto sampleModelDto);

    SampleModelDto findById(Long id);

    SampleModelDto update(SampleModelDto sampleModelDto);

    void delete(Long id);
}
