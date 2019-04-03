package mustafa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {
	public static void main(String[] args) throws ParserConfigurationException,
	SAXException, IOException {
		File file = new File("C:\\Users\\SAR\\Desktop\\test.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(file);

		final NodeList listStrings = document.getElementsByTagName("string");
		final NodeList listPlurals = document.getElementsByTagName("plurals");

		final List<String> list = new ArrayList<>();

		for (int i = 0; i < listStrings.getLength(); i++) {
			final Node stringNode = listStrings.item(i);
			if (stringNode.getNodeType() == Node.ELEMENT_NODE) {
				final Element stringTag = (Element) stringNode;
				final String name = stringTag.getFirstChild().getTextContent();
				final String nameFinal = stringTag.getAttribute("name") + "=" + name;
				list.add(nameFinal);
			}

		}

		for (int i = 0; i < listPlurals.getLength(); i++) {
			final Node stringNode = listPlurals.item(i);
			if (stringNode.getNodeType() == Node.ELEMENT_NODE) {
				final Element stringTag = (Element) stringNode;
				final String name = stringTag.getAttribute("name");
				list.add(name);
				/***********
				 * We got the Plurals value nOw we move on to items children
				 ************/
				NodeList itemList = stringNode.getChildNodes();
				for (int j = 0; j < itemList.getLength(); j++) {
					final Node itemNode = (Node) itemList.item(j);
					if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
						final Element itemTag = (Element) itemNode;
						final String itemValue = itemTag.getFirstChild().getTextContent();
						final String itemAtrrValue = itemTag.getAttribute("quantity");
						list.add(itemAtrrValue + "=" + itemValue);
					}

				}

			}

		}
		writeListToFile(list, "mustafa");
		
	}
	
	public static void writeListToFile(List<String> list, String fileName) throws IOException {
		FileWriter writer = new FileWriter("C:\\Users\\SAR\\\\Desktop\\" + fileName + ".txt");
		for (String string : list) {
			writer.write(string);
			writer.write(System.getProperty( "line.separator" ));
		}
		writer.close();
	}
}
