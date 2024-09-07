package com.newautomaticpapergenerationwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.newautomaticpapergenerationwebsite.model.Question;
import com.newautomaticpapergenerationwebsite.service.PDFGenerator;
import com.newautomaticpapergenerationwebsite.service.QuestionService;
import org.springframework.http.MediaType;

//import java.awt.PageAttributes.MediaType;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	
	 @GetMapping
	    public List<Question> getQuestions(
	            @RequestParam String branch,
	            @RequestParam String semester,
	            @RequestParam String subject) {
	        return questionService.findQuestionsByCriteria(branch, semester, subject);
	    }

	@PostMapping("/add")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
		return ResponseEntity.ok(questionService.addQuestion(question));
	}

	@GetMapping("/generate")
	public ResponseEntity<List<Question>> generateQuestionPaper(@RequestParam String branch,
			@RequestParam String semester, @RequestParam String subject, @RequestParam String difficulty) {
		return ResponseEntity.ok(questionService.generateQuestionPaper(branch, semester, subject, difficulty));
	}

//	@GetMapping("/download")
//	public ResponseEntity<byte[]> downloadQuestionPaper(@RequestParam String branch, @RequestParam String semester,
//			@RequestParam String subject, @RequestParam String difficulty) {
//		List<Question> questions = questionService.generateQuestionPaper(branch, semester, subject, difficulty);
//		byte[] pdfBytes = PDFGenerator.generateQuestionPaperPDF(questions);
////		List<Question> questions = questionService.generateQuestionPaper(branch, semester, subject, difficulty);
////		questions.forEach(q -> System.out.println(q.getQuestionText()));
////		byte[] pdfBytes = PDFGenerator.generateQuestionPaperPDF(questions);
//
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_PDF);
//		headers.setContentDispositionFormData("attachment", "question_paper.pdf");
//
//		return ResponseEntity.ok().headers(headers).body(pdfBytes);
//	}
	
//	new getmapping
	@GetMapping("/download")
	public ResponseEntity<byte[]> downloadQuestionPaper(
	        @RequestParam String branch,
	        @RequestParam String semester,
	        @RequestParam String subject,
	        @RequestParam String difficulty,
	        @RequestParam String name,
	        @RequestParam String code,
	        @RequestParam int marks,
	        @RequestParam int choice,
	        @RequestParam String time) {

	    List<Question> questions = questionService.generateQuestionPaper(branch, semester, subject, difficulty);

	    // Pass these inputs to the PDF generator
	    byte[] pdfBytes = PDFGenerator.generateQuestionPaperPDF(questions, name, code, marks, choice, time);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDispositionFormData("attachment", "question_paper.pdf");

	    return ResponseEntity.ok().headers(headers).body(pdfBytes);
	}

}
