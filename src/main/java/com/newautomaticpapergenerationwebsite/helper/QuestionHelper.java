package com.newautomaticpapergenerationwebsite.helper;

import com.newautomaticpapergenerationwebsite.model.Question;
import com.newautomaticpapergenerationwebsite.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.newautomaticpapergenerationwebsite.util.Constants.*;

@Component
public class QuestionHelper {

    @Autowired
    private QuestionRepository questionRepository;


    public List<Question> findQuestionsByCriteria(String branch, String semester, String subject) {
        return questionRepository.findByBranchAndSemesterAndSubject(branch, semester, subject);
    }


//	public List<Question> generateQuestionPaper(String branch, String semester, String subject, String difficulty) {
//		// Implement Ant Colony Algorithm here
//		
//		
//		return questionRepository.findByBranchAndSemesterAndSubject(branch, semester, subject);
//	}

    // ACO parameters


    // Initialize pheromone levels
    private Map<Long, Double> pheromoneLevels;

    public void initializePheromoneLevels(List<Question> questions) {
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

    public List<Question> constructSolution(List<Question> questions, String difficulty) {
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

    public void updatePheromones(List<List<Question>> solutions) {
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

    public double evaluateSolution(List<Question> solution) {
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
