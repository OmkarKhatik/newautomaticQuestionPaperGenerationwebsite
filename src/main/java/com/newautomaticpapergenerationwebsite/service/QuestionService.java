package com.newautomaticpapergenerationwebsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newautomaticpapergenerationwebsite.model.Question;
import com.newautomaticpapergenerationwebsite.repo.QuestionRepository;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;


@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	

    public List<Question> findQuestionsByCriteria(String branch, String semester, String subject) {
        return questionRepository.findByBranchAndSemesterAndSubjectAndDifficulty(branch, semester, subject, "all");
    }


//	public List<Question> generateQuestionPaper(String branch, String semester, String subject, String difficulty) {
//		// Implement Ant Colony Algorithm here
//		
//		
//		return questionRepository.findByBranchAndSemesterAndSubject(branch, semester, subject);
//	}

	// ACO parameters
	private int numAnts = 10;
	private double alpha = 1.0;
	private double beta = 2.0;
	private double evaporationRate = 0.5;
	private int numIterations = 100;

	// Initialize pheromone levels
	private Map<Long, Double> pheromoneLevels;

	public List<Question> generateQuestionPaper(String branch, String semester, String subject, String difficulty) {
		List<Question> questions = questionRepository.findByBranchAndSemesterAndSubjectAndDifficulty(branch, semester, subject, difficulty);
		if (questions.isEmpty()) {
			return new ArrayList<>(); // No questions found
		}

		initializePheromoneLevels(questions);

		List<Question> bestSolutions = new ArrayList<>();
		double bestSolutionScore = 0.0;

		for (int i = 0; i < numIterations; i++) {
			List<List<Question>> solutions = new ArrayList<>();

			// Each ant constructs a solution
			for (int j = 0; j < numAnts; j++) {
				List<Question> solution = constructSolution(questions, difficulty);
				solutions.add(solution);
			}

			// Update pheromones based on solutions
			updatePheromones(solutions);

			// Determine the best solution in this iteration
			for (List<Question> solution : solutions) {
				double solutionScore = evaluateSolution(solution);
				if (solutionScore > bestSolutionScore) {
					bestSolutionScore = solutionScore;
					bestSolutions = new ArrayList<>(solution);
				}
			}
		}

		return bestSolutions;
	}

	private void initializePheromoneLevels(List<Question> questions) {
		pheromoneLevels = new HashMap<>();
		for (Question question : questions) {
			pheromoneLevels.put(question.getId(), 1.0); // Initial pheromone level
		}
	}

//	private List<Question> constructSolution(List<Question> questions, String difficulty) {
//		List<Question> solution = new ArrayList<>();
//		Random random = new Random();
//
//		while (solution.size() < 5) { // Assuming we want a 10-question paper
//			double sum = 0.0;
//			for (Question question : questions) {
//				sum += Math.pow(pheromoneLevels.get(question.getId()), alpha)
//						* Math.pow(getHeuristicValue(question, difficulty), beta);
//			}
//
//			double rand = random.nextDouble() * sum;
//			double cumulativeProbability = 0.0;
//
//			for (Question question : questions) {
//				cumulativeProbability += Math.pow(pheromoneLevels.get(question.getId()), alpha)
//						* Math.pow(getHeuristicValue(question, difficulty), beta);
//				if (cumulativeProbability >= rand) {
//					solution.add(question);
//					break;
//				}
//			}
//		}
//
//		return solution;
//	}
//

	private List<Question> constructSolution(List<Question> questions, String difficulty) {
	    List<Question> solution = new ArrayList<>();
	    Set<Long> selectedQuestionIds = new HashSet<>();  // Track selected question IDs
	    Random random = new Random();

	    while (solution.size() < 8 && solution.size() < questions.size()) { // Assuming we want a 10-question paper
	        double sum = 0.0;

	        // Calculate the sum of probabilities, excluding already selected questions
	        for (Question question : questions) {
	            if (!selectedQuestionIds.contains(question.getId())) {
	                sum += Math.pow(pheromoneLevels.get(question.getId()), alpha) *
	                       Math.pow(getHeuristicValue(question, difficulty), beta);
	            }
	        }

	        double rand = random.nextDouble() * sum;
	        double cumulativeProbability = 0.0;

	        for (Question question : questions) {
	            if (!selectedQuestionIds.contains(question.getId())) {
	                cumulativeProbability += Math.pow(pheromoneLevels.get(question.getId()), alpha) *
	                                         Math.pow(getHeuristicValue(question, difficulty), beta);
	                if (cumulativeProbability >= rand) {
	                    solution.add(question);
	                    selectedQuestionIds.add(question.getId());  // Mark this question as selected
	                    break;  // Exit the loop to prevent further selection in this iteration
	                }
	            }
	        }
	    }

	    return solution;
	}

	private double getHeuristicValue(Question question, String difficulty) {
		return question.getDifficulty().equalsIgnoreCase(difficulty) ? 1.0 : 0.5;
	}

	private void updatePheromones(List<List<Question>> solutions) {
		// Evaporate pheromones
		for (Map.Entry<Long, Double> entry : pheromoneLevels.entrySet()) {
			pheromoneLevels.put(entry.getKey(), (1 - evaporationRate) * entry.getValue());
		}

		// Update based on solutions
		for (List<Question> solution : solutions) {
			double solutionScore = evaluateSolution(solution);
			for (Question question : solution) {
				pheromoneLevels.put(question.getId(), pheromoneLevels.get(question.getId()) + solutionScore);
			}
		}
	}

	private double evaluateSolution(List<Question> solution) {
		// Simple evaluation: count the number of questions that match the desired
		// difficulty
		double score = 0.0;
		for (Question question : solution) {
			score += 1.0;
		}
		return score;
	}

	public Question addQuestion(Question question) {
		return questionRepository.save(question);
	}

}
