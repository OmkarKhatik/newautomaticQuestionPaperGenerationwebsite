package com.newautomaticpapergenerationwebsite.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newautomaticpapergenerationwebsite.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findByBranchAndSemesterAndSubjectAndDifficulty(String branch, String semester, String subject, String difficulty);
	List<Question> findByBranchAndSemesterAndSubject(String branch, String semester, String subject);

}
