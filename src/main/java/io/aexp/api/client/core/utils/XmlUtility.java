package io.aexp.api.client.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import io.aexp.api.client.core.exceptions.JsonException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.StringReader;
import java.io.StringWriter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author gudongyang
 */
public class XmlUtility {
    private final XmlMapper xmlMapper;
    private final static XmlUtility INSTANCE = new XmlUtility();

    private XmlUtility() {
        xmlMapper = XmlMapper.builder()
                .propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
                .serializationInclusion(NON_NULL)
                .configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true)
                .configure(MapperFeature.SORT_CREATOR_PROPERTIES_FIRST, true)
                .configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true)
                .build();
        xmlMapper.getFactory().getXMLOutputFactory().setProperty("javax.xml.stream.isRepairingNamespaces", false);
    }

    public static XmlUtility getInstance() {
        return INSTANCE;
    }

    public String getString(Object object) {
        try {
            String xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            Document document = parseXmlString(xmlString);
            return formatXml(document);
        } catch (Exception e) {
            throw new JsonException("Exception writing object as string, caused by " + e.getMessage(), e);
        }
    }

    public String xmlFormat(String xml) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        StreamSource source = new StreamSource(new StringReader(xml));
        transformer.transform(source, result);
        String formattedXml = writer.toString();
        return formattedXml;


    }

    private static Document parseXmlString(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlString)));
    }

    private static String formatXml(Document document) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));

        // 删除空白和换行符
        String formattedXml = writer.toString();
        formattedXml = formattedXml.replaceAll("\\s+", "");
        return "AuthorizationRequestParam=<?xml version=\"1.0\" encoding=\"utf-8\"?>" + formattedXml;
    }


}
