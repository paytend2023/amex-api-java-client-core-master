package io.aexp.api.client.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import io.aexp.api.client.core.exceptions.JsonException;
import lombok.experimental.Tolerate;
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
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

/**
 * @author gudongyang
 */
public class XmlUtility {
    private final XmlMapper xmlMapper;
    private TransformerFactory transformerFactory = TransformerFactory.newInstance();

    private final static XmlUtility INSTANCE = new XmlUtility();

    private XmlUtility() {


        xmlMapper = XmlMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
                .serializationInclusion(NON_NULL)
                .configure(MapperFeature.SORT_CREATOR_PROPERTIES_FIRST, true)
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .disable(FAIL_ON_EMPTY_BEANS)
                .build();
        xmlMapper.getFactory().getXMLOutputFactory().setProperty("javax.xml.stream.isRepairingNamespaces", false);
    }

    public static XmlUtility getInstance() {
        return INSTANCE;
    }

    public String getString(Object object) {
        try {
            return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonException("Exception writing object as string, caused by " + e.getMessage(), e);
        }
    }

    public <T> T readFromXML(String str, Class<T> clazz) {
        try {
            return xmlMapper.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String xmlBeautifulFormat(String xml) throws Exception {
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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

    public String formatXml(String xml) {
        return xml.replaceAll("\\s+", "");
    }


}
