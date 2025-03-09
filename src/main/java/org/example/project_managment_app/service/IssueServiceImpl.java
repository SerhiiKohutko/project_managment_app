package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Issue;
import org.example.project_managment_app.entities.Project;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.repository.IssueRepository;
import org.example.project_managment_app.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public Issue getIssueById(long id) throws Exception {
        Optional<Issue> issue = issueRepository.findById(id);
        return issue.orElseThrow(Exception::new);
    }

    @Override
    public Issue createIssue(IssueRequest issue, User user) throws Exception {
        Project project = projectService.getProjectById(issue.getProjectId());

        Issue newIssue = new Issue();
        newIssue.setTitle(issue.getTitle());
        newIssue.setDescription(issue.getDescription());
        newIssue.setStatus(issue.getStatus());

        newIssue.setPriority(issue.getPriority());
        newIssue.setDueDate(issue.getDueDate());

        newIssue.setProject(project);

        return issueRepository.save(newIssue);
    }

    @Override
    public void deleteIssue(long issueId, long userId) {
        issueRepository.deleteById(issueId);
    }

    @Override
    public List<Issue> getIssuesByProjectId(long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue addUserToIssue(long issueId, long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);

        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
