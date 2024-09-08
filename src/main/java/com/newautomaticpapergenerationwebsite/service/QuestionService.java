package com.newautomaticpapergenerationwebsite.service;

import com.newautomaticpapergenerationwebsite.helper.QuestionHelper;
import com.newautomaticpapergenerationwebsite.model.Question;
import com.newautomaticpapergenerationwebsite.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.newautomaticpapergenerationwebsite.util.Constants.numAnts;
import static com.newautomaticpapergenerationwebsite.util.Constants.numIterations;


@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionHelper questionHelper;


    public List<Question> findQuestionsByCriteria(String branch, String semester, String subject) {
        return questionRepository.findByBranchAndSemesterAndSubject(branch, semester, subject);
    }


//	public List<Question> generateQuestionPaper(String branch, String semester, String subject, String difficulty) {
//		// Implement Ant Colony Algorithm here
//		
//		
//		return questionRepository.findByBranchAndSemesterAndSubject(branch, semester, subject);
//	}

    // Initialize pheromone levels
    private Map<Long, Double> pheromoneLevels;

    public List<Question> generateQuestionPaper(String branch, String semester, String subject, String difficulty) {
//        List<Question> questions = questionRepository.findByBranchAndSemesterAndSubject(branch, semester, subject);
        List<Question> questions = null;
        if ("all".equals(difficulty)) {
            questions = questionRepository.findByBranchAndSemesterAndSubject(branch, semester, subject);
        } else {
            questions = questionRepository.findByBranchAndSemesterAndSubjectAndDifficultyContains(branch, semester, subject, difficulty);
        }
        if (questions.isEmpty()) {
            return new ArrayList<>(); // No questions found
        }

        questionHelper.initializePheromoneLevels(questions);

        List<Question> bestSolutions = new ArrayList<>();
        double bestSolutionScore = 0.0;

        for (int i = 0; i < numIterations; i++) {
            List<List<Question>> solutions = new ArrayList<>();

            // Each ant constructs a solution
            for (int j = 0; j < numAnts; j++) {
                List<Question> solution = questionHelper.constructSolution(questions, difficulty);
                solutions.add(solution);
            }

            // Update pheromones based on solutions
            questionHelper.updatePheromones(solutions);

            // Determine the best solution in this iteration
            for (List<Question> solution : solutions) {
                double solutionScore = questionHelper.evaluateSolution(solution);
                if (solutionScore > bestSolutionScore) {
                    bestSolutionScore = solutionScore;
                    bestSolutions = new ArrayList<>(solution);
                }
            }
        }

        return bestSolutions;
    }

    public Question validateAndAddQuestion(Question question) {

        Question existingQuestion = questionRepository.findByQuestionText(question.getQuestionText());
        if (existingQuestion == null) {
            return questionRepository.save(question);
        } else {
            if (existingQuestion.getDifficulty().equals(question.getDifficulty())) {
                return existingQuestion;
            } else {
                existingQuestion.setDifficulty(existingQuestion.getDifficulty() + "|" + question.getDifficulty());
                return questionRepository.save(existingQuestion);
            }
        }
    }

}
