package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.Issue;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.request.IssueRequest;

import java.util.List;

public interface IssueService {

    Issue getIssueById(long id) throws Exception;
    Issue createIssue(IssueRequest issue, User user) throws Exception;
    void deleteIssue(long issueId, long userId);
    List<Issue> getIssuesByProjectId(long projectId);
    Issue addUserToIssue(long issueId, long userId) throws Exception;
    Issue updateStatus(Long issueId, String status) throws Exception;
}
