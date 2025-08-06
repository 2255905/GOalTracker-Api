package com.gulshair12.sdgapp.controller;

import com.gulshair12.sdgapp.model.ApiResponse;
import com.gulshair12.sdgapp.model.Goal;
import com.gulshair12.sdgapp.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Goal>> createGoal(@RequestBody Goal goal) {
        Goal savedGoal = goalRepository.save(goal);
        return new ResponseEntity<>(
                new ApiResponse<>("Goal created successfully", savedGoal),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Goal>>> getAllGoals() {
        List<Goal> goals = goalRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>("Fetched all goals successfully", goals),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Goal>> getGoalById(@PathVariable Long id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found with id " + id));

        return new ResponseEntity<>(
                new ApiResponse<>("Goal fetched successfully", goal),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Goal>> updateGoal(@PathVariable Long id, @RequestBody Goal updatedGoal) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found with id " + id));

        goal.setTitle(updatedGoal.getTitle());
        goal.setDescription(updatedGoal.getDescription());
        goal.setTargetDate(updatedGoal.getTargetDate());

        Goal savedGoal = goalRepository.save(goal);

        return new ResponseEntity<>(
                new ApiResponse<>("Goal updated successfully", savedGoal),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGoal(@PathVariable Long id) {
        if (!goalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found with id " + id);
        }
        goalRepository.deleteById(id);
        return new ResponseEntity<>(
                new ApiResponse<>("Goal deleted successfully with id " + id, null),
                HttpStatus.OK);
    }

}
