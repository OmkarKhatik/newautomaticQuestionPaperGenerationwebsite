package com.newautomaticpapergenerationwebsite.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newautomaticpapergenerationwebsite.model.Question;

//public interface QuestionRepository extends JpaRepository<Question, Long> {
//	List<Question> findByBranchAndSemesterAndSubjectAndDifficultyContains(String branch, String semester, String subject, String difficulty);
//	List<Question> findByBranchAndSemesterAndSubject(String branch, String semester, String subject);
//
//	Question findByQuestionText(String questionText);
//}

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findByBranchAndSemesterAndSubjectAndDifficultyContains(String branch, String semester, String subject, String difficulty);
	List<Question> findByBranchAndSemesterAndSubject(String branch, String semester, String subject);

	Question findByQuestionText(String questionText);

	// New method to filter by topic
	List<Question> findByBranchAndSemesterAndSubjectAndTopicContains(String branch, String semester, String subject, String topic);
	List<Question> findByBranchAndSemesterAndSubjectAndTopicAndQuestionTypeContains(String branch, String semester, String subject, String topic, String questionType);

	List<Question> findByBranchAndSemesterAndSubjectAndTopicAndQuestionTypeAndDifficultyContains(
			String branch, String semester, String subject, String topic, String questionType, String difficulty);
}

