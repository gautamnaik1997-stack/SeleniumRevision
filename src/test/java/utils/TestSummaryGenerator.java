package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestSummaryGenerator {

    private static class Summary {

        int total;
        int passed;
        int failed;
        int skipped;

    }

    public static void generateSummary() {

        try {

            Summary summary = readTestSummary();

            String html = generateHtml(summary);

            writeHtml(html);

            System.out.println("Test Summary Generated Successfully.");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private static Summary readTestSummary() throws Exception {

        Summary summary = new Summary();

        String xmlPath = System.getProperty("user.dir")
                + File.separator
                + "target"
                + File.separator
                + "surefire-reports"
                + File.separator
                + "testng-results.xml";

        File xmlFile = new File(xmlPath);

        if (!xmlFile.exists()) {

            throw new IOException("Unable to locate : " + xmlPath);

        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(xmlFile);

        Element root = document.getDocumentElement();

        summary.total = Integer.parseInt(root.getAttribute("total"));
        summary.passed = Integer.parseInt(root.getAttribute("passed"));
        summary.failed = Integer.parseInt(root.getAttribute("failed"));
        summary.skipped = Integer.parseInt(root.getAttribute("skipped"));

        return summary;

    }

    private static String generateHtml(Summary summary) {

        double passPercentage = 0;

        if (summary.total > 0) {

            passPercentage = ((double) summary.passed / summary.total) * 100;

        }

        StringBuilder html = new StringBuilder();

        html.append("<h3 style='color:#2E86C1;'>Test Summary</h3>");

        html.append("<table border='1' cellpadding='6' cellspacing='0' ")
            .append("style='border-collapse:collapse;width:60%;'>");

        html.append("<tr style='background:#F2F2F2;'>")
            .append("<th>Metric</th>")
            .append("<th>Count</th>")
            .append("</tr>");

        html.append("<tr>")
            .append("<td><b>Total</b></td>")
            .append("<td>")
            .append(summary.total)
            .append("</td>")
            .append("</tr>");

        html.append("<tr>")
            .append("<td><b>Passed</b></td>")
            .append("<td style='color:green;'>")
            .append(summary.passed)
            .append("</td>")
            .append("</tr>");

        html.append("<tr>")
            .append("<td><b>Failed</b></td>")
            .append("<td style='color:red;'>")
            .append(summary.failed)
            .append("</td>")
            .append("</tr>");

        html.append("<tr>")
            .append("<td><b>Skipped</b></td>")
            .append("<td style='color:orange;'>")
            .append(summary.skipped)
            .append("</td>")
            .append("</tr>");

        html.append("<tr>")
            .append("<td><b>Pass Percentage</b></td>")
            .append("<td style='color:blue;'>")
            .append(String.format("%.2f", passPercentage))
            .append("%</td>")
            .append("</tr>");

        html.append("</table>");

        return html.toString();

    }

    private static void writeHtml(String html) throws IOException {

        String reportFolder = System.getProperty("user.dir")
                + File.separator
                + "reports";

        File folder = new File(reportFolder);

        if (!folder.exists()) {

            folder.mkdirs();

        }

        File summaryFile = new File(folder, "TestSummary.html");

        FileWriter writer = new FileWriter(summaryFile);

        writer.write(html);

        writer.close();

    }
    

}