package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Chat;
import org.example.project_managment_app.entities.Project;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//TODO
//finish all the methods and create project api
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        return null;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        return List.of();
    }

    @Override
    public Project getProjectById(long projectId) throws Exception {
        return null;
    }

    @Override
    public void deleteProject(long projectId, long userId) throws Exception {

    }

    @Override
    public Project updateProject(Project updatedProject, long id) throws Exception {
        return null;
    }

    @Override
    public void addUserToProject(long projectId, long userId) throws Exception {

    }

    @Override
    public void removeUserFromProject(long projectId, long userId) throws Exception {

    }

    @Override
    public Chat getChatByProjectId(long projectId) throws Exception {
        return null;
    }
}
