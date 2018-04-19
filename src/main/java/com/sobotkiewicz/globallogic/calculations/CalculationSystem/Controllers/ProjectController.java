package com.sobotkiewicz.globallogic.calculations.CalculationSystem.Controllers;

import com.sobotkiewicz.globallogic.calculations.CalculationSystem.CalcProject;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.Services.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Api(value="Projects", description="Operations done on projects")
@RestController
public class ProjectController {



    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @ApiOperation(value = "View a list of existing projects")
    @GetMapping("/projects")
    public List<CalcProject> getAllProjects() {
        return projectService.getAllProjects();
    }

    @ApiOperation(value = "View a project on given ID")
    @GetMapping("/projects/{projectId}")
    public Optional<CalcProject> getProject(@PathVariable UUID projectId) {
        return projectService.getProject(projectId);
    }

    @ApiOperation(value = "Create a new project")
    @PostMapping("/projects")
    public ResponseEntity<CalcProject> addProject(@ApiParam(value = "Mathematical expression and projectId", required = true, example = "{\"name\":\"example_name\"}")
    @RequestBody @Valid final CalcProject project, BindingResult result) {
        if (result.hasErrors()){
            final String responseMessage = "Invalid input";
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else {
            projectService.addProject(project);
            final String responseMessage = "Created project id: " + project.getId();
            return new ResponseEntity(responseMessage, HttpStatus.CREATED);
        }
    }

    @ApiOperation(value = "Delete a project on given ID")
    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<String> deletePerson(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId);
        final String responseMessage = "Project deleted";
        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }

}
