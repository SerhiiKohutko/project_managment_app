package org.example.project_managment_app.controller;

import org.example.project_managment_app.dto.IssueDTO;
import org.example.project_managment_app.entities.Issue;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.request.IssueRequest;
import org.example.project_managment_app.response.AuthResponse;
import org.example.project_managment_app.service.IssueService;
import org.example.project_managment_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable long issueId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssuesByProjectId(@PathVariable long projectId) throws Exception {
        return ResponseEntity.ok(issueService.getIssuesByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue,
                                                @RequestHeader("Authorization") String token) throws Exception {

        User tokenuser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenuser.getId());

            Issue createdIssue = issueService.createIssue(issue, user);
            IssueDTO issueDTO = new IssueDTO();
            issueDTO.setDescription(createdIssue.getDescription());
            issueDTO.setDueDate(createdIssue.getDueDate());
            issueDTO.setId(createdIssue.getId());
            issueDTO.setPriority(createdIssue.getPriority());
            issueDTO.setTitle(createdIssue.getTitle());
            issueDTO.setStatus(createdIssue.getStatus());
            issueDTO.setProject(createdIssue.getProject());
            issueDTO.setProjectId(createdIssue.getProjectId());
            issueDTO.setAssignee(createdIssue.getAssignee());
            issueDTO.setTags(createdIssue.getTags());

            return ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<AuthResponse> deleteIssue(@PathVariable long issueId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        issueService.deleteIssue(issueId, user.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Successfully deleted issue");

        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable long issueId, @PathVariable long userId) throws Exception {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable String status, @PathVariable long issueId) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }
}
