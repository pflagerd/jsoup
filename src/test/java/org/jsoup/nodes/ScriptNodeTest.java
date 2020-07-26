package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.integration.ParseTest;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 Tests for Script Nodes.

 @author daniel p. pflager daniel@pflager.net */
public class ScriptNodeTest {
    private static final String charsetUtf8 = "UTF-8";
    private static final String charsetIso8859 = "ISO-8859-1";


    @Test public void respectScriptTagsNotEscapedForHtml() {
        String inputHtml = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <script src=\"something.js\"></script>\n" +
                "</body>\n" +
                "</html>";
        Document doc = Jsoup.parse(inputHtml);
        for (Element element : doc.select("script")) { // DPP: 200726042111Z: I realize (and assume) there is only one script tag.
            String src = element.attr("src");
            if (src != null) {
                // Pretend we now read in the script something.js, which contains this content:
                String srcFileContents = "if (x<23) { console.out('Holy Cow!'); }";
                // delete the src attribute
                element.removeAttr("src");
                // insert srcFileContents as the contents of the script tag
                element.html(srcFileContents);
            }
        }

        doc.outputSettings().prettyPrint(false);
        //System.out.println(doc.outerHtml());
        assertTrue(!doc.outerHtml().contains("&lt;"));
    }
}
