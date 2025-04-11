package com.newautomaticpapergenerationwebsite.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.newautomaticpapergenerationwebsite.model.Question;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public class PDFGenerator {

    public static byte[] generateQuestionPaperPDF2(Map<String, List<Question>> questionsMap, String name, String subject, String code, int marks, String time) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // **Title Section**
        document.add(new Paragraph(name)
                .setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(14));
        document.add(new Paragraph("B.Tech. Examination")
                .setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(12));
        document.add(new Paragraph(name.toUpperCase())
                .setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(14));
        document.add(new Paragraph("Code: " + code + "   |   Maximum Marks: " + marks + "   |   Time: " + time)
                .setTextAlignment(TextAlignment.CENTER).setFontSize(10));

        document.add(new Paragraph("\nINSTRUCTIONS TO CANDIDATES").setBold().setUnderline().setFontSize(12));
        document.add(new Paragraph("1. All questions carry marks as indicated."));
        document.add(new Paragraph("2. Answer all questions within the given time."));
        document.add(new Paragraph("3. Assume suitable data wherever necessary."));
        document.add(new Paragraph("4. Write your answers neatly and legibly.\n"));

        // **Question Section**
        document.add(new Paragraph("\n\n----------------------")
                .setBold().setTextAlignment(TextAlignment.CENTER));

        String text1 = "SECTION A - Multiple Choice Questions";
        String text2 = "SECTION B - Descriptive Questions";
        if (questionsMap.size() != 2) {
            text1 = text1.replaceAll("SECTION A - ", "");
            text2 = text2.replaceAll("SECTION B - ", "");
        }

        List<Question> mcqQuestions = questionsMap.get("MCQ");
        List<Question> descriptiveQuestions = questionsMap.get("Descriptive");

        if (mcqQuestions != null) {
            document.add(new Paragraph(text1).setBold().setFontSize(11));
            int questionNumber = 1;
            for (Question question : mcqQuestions) {
                document.add(new Paragraph(questionNumber + ". " + question.getQuestionText())
                        .setBold().setFontSize(11));
////            document.add(new Paragraph("   (Choice Available: " + choice + ")\n"));
                questionNumber++;
            }
        }
        if (descriptiveQuestions != null) {
            document.add(new Paragraph(text2).setBold().setFontSize(11));
            int questionNo = 1;
            for (Question question : descriptiveQuestions) {
                document.add(new Paragraph(questionNo + ". " + question.getQuestionText())
                        .setBold().setFontSize(11));
////            document.add(new Paragraph("   (Choice Available: " + choice + ")\n"));
                questionNo++;
            }
        }
        // **Closing Message**
        document.add(new Paragraph("\n\n----------- Best of Luck! -----------")
                .setBold().setTextAlignment(TextAlignment.CENTER));

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] generateQuestionPaperPDF(List<Question> questions, String name, String subject, String code, int marks, String time) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // **Title Section**
        document.add(new Paragraph(name)
                .setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(14));
        document.add(new Paragraph("B.Tech. Examination")
                .setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(12));
        document.add(new Paragraph(name.toUpperCase())
                .setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(14));
        document.add(new Paragraph("Code: " + code + "   |   Maximum Marks: " + marks + "   |   Time: " + time)
                .setTextAlignment(TextAlignment.CENTER).setFontSize(10));

        document.add(new Paragraph("\nINSTRUCTIONS TO CANDIDATES").setBold().setUnderline().setFontSize(12));
        document.add(new Paragraph("1. All questions carry marks as indicated."));
        document.add(new Paragraph("2. Answer all questions within the given time."));
        document.add(new Paragraph("3. Assume suitable data wherever necessary."));
        document.add(new Paragraph("4. Write your answers neatly and legibly.\n"));

        // **Question Section**
        document.add(new Paragraph("\n\n----------------------")
                .setBold().setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("SECTION A - Multiple Choice Questions").setBold().setFontSize(11));
        int questionNumber = 1;
        for (Question question : questions) {
            document.add(new Paragraph(questionNumber + ". " + question.getQuestionText())
                    .setBold().setFontSize(11));
////            document.add(new Paragraph("   (Choice Available: " + choice + ")\n"));
            questionNumber++;
        }
        document.add(new Paragraph("SECTION B - Descriptive Questions").setBold().setFontSize(11));
        int questionNo = 1;
        for (Question question : questions) {
            document.add(new Paragraph(questionNo + ". " + question.getQuestionText())
                    .setBold().setFontSize(11));
////            document.add(new Paragraph("   (Choice Available: " + choice + ")\n"));
            questionNo++;
        }
        // **Closing Message**
        document.add(new Paragraph("\n\n----------- Best of Luck! -----------")
                .setBold().setTextAlignment(TextAlignment.CENTER));

        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}

