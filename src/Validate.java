/* validator */

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Validate {
	public static void main(String[] args) {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File xmlFile = new File("xml/POC_DIAGNOSIS_EVENT_FACT.xml");
		File xsdFile = new File("Main.xsd");

		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			DocumentBuilder parser = documentBuilderFactory.newDocumentBuilder();
			Document document = parser.parse(xmlFile);

			Schema schema = schemaFactory.newSchema(xsdFile);
			Validator validator = schema.newValidator();

			validator.validate(new DOMSource(document));

			System.out.println(xmlFile + " validates");
		} catch (SAXException sax) {
			System.out.println("SAX Error: " + sax.getMessage());
		} catch (ParserConfigurationException parser) {
			System.out.println("Parser Config Error: " + parser.getMessage());
		} catch (IOException io) {
			System.out.println("IO Error " + io.getMessage());
		}
	}
}