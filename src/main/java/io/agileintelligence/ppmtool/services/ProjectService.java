package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exceptions.ProjectIdException;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch(Exception e){
            throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase()+ "'Already Exist");
        }

    }

    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProject(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectid){
        Project project = projectRepository.findByProjectIdentifier(projectid);

        if(project == null){
            throw new ProjectIdException("Cannot find project with ID '" + projectid);
        }

        projectRepository.delete(project);
    }
}
