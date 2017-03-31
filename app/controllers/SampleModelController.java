package controllers;

import annotations.ParseBodyTo;
import annotations.RequestBodyJsonRequired;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import models.dtos.SampleModelDto;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.SampleModelService;
import utils.ControllerUtil;

@Api(value = "/sampleModel", description = "Operations on SampleModel.")
public class SampleModelController extends Controller {

    @Inject
    @Named("SampleModelService")
    private SampleModelService sampleModelService;

    @ApiOperation(value = "Creates a new SampleModel record.",
            httpMethod = "POST", response = SampleModelDto.class, nickname = "createSampleModel")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "SampleModelDto representing the sampleModel that needs to be created.",
                    required = true, dataType = "SampleModelDto", paramType = "body")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The sampleModel record that has been created is returned.", response = SampleModelDto.class),
            @ApiResponse(code = 400, message = "The request has no JSON body or the JSON is invalid."),
    })
    @BodyParser.Of(BodyParser.Json.class)
    @RequestBodyJsonRequired
    @ParseBodyTo(SampleModelDto.class)
    public Result create() {

        SampleModelDto sampleModelDto = ControllerUtil.retrieveParsedBodyObject(ctx(), SampleModelDto.class);

        return ok(Json.toJson(sampleModelService.create(sampleModelDto)));
    }

    @ApiOperation(value = "Updates a SampleModel record.",
            httpMethod = "PUT", response = SampleModelDto.class, nickname = "updateSampleModel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id of the sampleModel that is being updated.",
                    required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(value = "SampleModelDto representing the sampleModel that is being updated.",
                    required = true, dataType = "SampleModelDto", paramType = "body")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The updated sampleModel.", response = SampleModelDto.class),
            @ApiResponse(code = 400, message = "The request has no JSON body or the JSON is invalid or the record to be updated to not match the request."),
    })
    @BodyParser.Of(BodyParser.Json.class)
    @RequestBodyJsonRequired
    @ParseBodyTo(SampleModelDto.class)
    public Result update(Long id) {

        SampleModelDto sampleModelDto = ControllerUtil.retrieveParsedBodyObject(ctx(), SampleModelDto.class);

        if(sampleModelDto==null || sampleModelDto.getId()==null || !sampleModelDto.getId().equals(id)) {
            return badRequest("The provided SampleModel to be updated does not match the request.");
        }

        return ok(Json.toJson(sampleModelService.update(sampleModelDto)));
    }

    @ApiOperation(value = "Deletes a SampleModel record by id.",
            httpMethod = "DELETE", response = SampleModelDto.class, nickname = "deleteSampleModel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id of the SampleModelDto to be deleted.",
                required = true, dataType = "Long", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The sampleModel record has been deleted.", response = SampleModelDto.class),
            @ApiResponse(code = 404, message = "The sampleModel record containing the requested id does not exist."),
    })
    public Result delete(Long id) {

        sampleModelService.delete(id);

        return ok();
    }

    @ApiOperation(value = "Finds a SampleModel record by id.",
            httpMethod = "GET", response = SampleModelDto.class, nickname = "findSampleModel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id of the sampleModel that is being requested.",
                    required = true, dataType = "Long", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The sampleModel record containing the requested id.", response = SampleModelDto.class),
            @ApiResponse(code = 404, message = "There is no SampleModel record that has the requested id.")
    })
    public Result findById(Long id) {

        SampleModelDto sampleModelDto = sampleModelService.findById(id);

        if(sampleModelDto==null) {
            return notFound("There is no SampleModel record that has the requested id.");
        }

        return ok(Json.toJson(sampleModelDto));
    }

    @ApiOperation(value = "Returns all SampleModel records.",
            httpMethod = "POST", responseContainer = "List", response = SampleModelDto.class, nickname = "findAllSampleModels")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns all SampleModels.", response = SampleModelDto.class),
    })
    public Result findAll() {
        return ok(Json.toJson(sampleModelService.findAll()));
    }
}
