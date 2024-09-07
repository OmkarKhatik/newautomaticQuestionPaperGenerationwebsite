package com.newautomaticpapergenerationwebsite.service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.newautomaticpapergenerationwebsite.model.Question;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PDFGenerator {

    public static byte[] generateQuestionPaperPDF(List<Question> questions, String name, String code, int marks, int choice, String time) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

//        document.add(new Paragraph("************************************************ "));
//        document.add(new Paragraph("Generated Question Paper"));
//        document.add(new Paragraph("Btech First Semester Examination  "));
//        document.add(new Paragraph("_________________________________________________"));
//        document.add(new Paragraph(""));
////        document.add(new Paragraph("************************************************")); // Add a blank line
////        
        // Add the custom inputs to the PDF
        document.add(new Paragraph("Question Paper Name: " + name));
        document.add(new Paragraph("Code: " + code));
        document.add(new Paragraph("Marks Per Module: " + marks));
        document.add(new Paragraph("Choice Per Module: " + choice));
        document.add(new Paragraph("Time for Exam: " + time));
        document.add(new Paragraph("************************************************"));
        document.add(new Paragraph(" ")); // Blank line
        
        for (Question question : questions) {
            document.add(new Paragraph("Question : "+question.getQuestionText()));
//            document.add(new Paragraph("Difficulty: " + question.getDifficulty()));
            document.add(new Paragraph(" "));
        }
        document.add(new Paragraph(" "));
        document.add(new Paragraph("-----------Best of luck----------- "));

        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}

