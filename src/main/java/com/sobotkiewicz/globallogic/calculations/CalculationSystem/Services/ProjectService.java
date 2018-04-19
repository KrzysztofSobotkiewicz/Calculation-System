package com.sobotkiewicz.globallogic.calculations.CalculationSystem.Services;


import com.sobotkiewicz.globallogic.calculations.CalculationSystem.Persistance.ProjectRepository;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.CalcProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<CalcProject> getAllProjects() {
        List<CalcProject> calcProjects = new ArrayList<>();
        projectRepository.findAll().forEach(calcProjects::add);
        return calcProjects;
    }


    public Optional<CalcProject> getProject(UUID id) {
        return projectRepository.findById(id);
    }

    public void addProject(CalcProject calcProject) {
        projectRepository.save(calcProject);
    }

    public void deleteProject(UUID projectId) {
        projectRepository.deleteById(projectId);
    }

}


