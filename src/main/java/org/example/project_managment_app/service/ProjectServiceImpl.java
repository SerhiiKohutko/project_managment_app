package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Chat;
import org.example.project_managment_app.entities.Project;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.repository.ProjectRepository;
import org.example.project_managment_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setDescription(project.getDescription());
        createdProject.setCategory(project.getCategory());
        createdProject.getTeam().add(user);

        Chat chat = new Chat();
        chat.setProject(createdProject);
        Chat projectChat = chatService.createChat(chat);
        createdProject.setChat(projectChat);

        return projectRepository.save(createdProject);
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        if (category != null) {
            projects = projects.stream()
                    .filter(project -> project.getCategory().equals(category))
                    .toList();
        }

        if (tag != null) {
            projects = projects.stream()
                    .filter(project -> project.getTags().contains(tag))
                    .toList();
        }

        return projects;
    }


    @Override
    public Project getProjectById(long projectId) throws Exception {
        return projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project not found"));
    }

    @Override
    public void deleteProject(long projectId, long userId) throws Exception {
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, long id) throws Exception {
        Project project = projectRepository.getReferenceById(id);
        project.setTags(updatedProject.getTags());
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(long projectId, long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if (!project.getTeam().contains(user)) {
            project.getTeam().add(user);
            project.getChat().getUsers().add(user);
        }

        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(long projectId, long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (project.getTeam().contains(user)) {
            project.getTeam().remove(user);
            project.getChat().getUsers().remove(user);
        }

        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(long projectId) throws Exception {
        return getProjectById(projectId).getChat();
    }

    @Override
    public List<Project> searchProject(String query, User user) throws Exception {
        return projectRepository.findByNameContainingAndTeamContains(query, user);
    }
}
