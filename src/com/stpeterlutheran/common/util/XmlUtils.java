package com.stpeterlutheran.common.util;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtils {

	/**
	 * Converts an XML document to a string.
	 * @param doc
	 * @return String
	 */
	public static String toString(Document doc)
	{
		try
		{
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes" );
			transformer.transform(domSource, result);
			return writer.toString();
		}
		catch(TransformerException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Converts an XML document to a string.
	 * @param xml
	 * @throws Exception
	 */
	public static final void prettyPrint(Document xml) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes" );

		Writer out = new StringWriter();
		tf.transform(new DOMSource(xml), new StreamResult(out));
		System.out.println(out.toString());
	}

	/**
	 * Converts an XML document to a file.
	 * @param doc
	 * @throws Exception
	 */
	public static final void toFile(Document doc, String filename ) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes" );

		StreamResult result = new StreamResult(new File( "C:\\temp\\".concat( filename )));
		tf.transform(new DOMSource(doc), result);
	}

	public static Document toDom( Object obj ){
		// Create the JAXBContext
		Document document = null;
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(obj.getClass());

			// Create the Document
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.newDocument();

			// Marshal the Object to a Document
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal( obj, document);

			// Output the Document
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(System.out);
			t.transform(source, result);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return document;
	}

	public static Node findSubNode(String name, Node node) {
		if (node.getNodeType() != Node.DOCUMENT_NODE) {
			System.err.println("Error: Search node not of element type");
			System.exit(22);
		}

		if (! node.hasChildNodes()) return null;

		NodeList list = node.getChildNodes();
		for (int i=0; i < list.getLength(); i++) {
			Node subnode = list.item(i);
			if (subnode.getNodeType() == Node.DOCUMENT_NODE) {
				if (subnode.getNodeName().equals(name)) 
					return subnode;
			}
		}
		return null;
	}

	public static Node findNode(
			Node root,
			String elementName,
			boolean deep,
			boolean elementsOnly) {
		//Check to see if root has any children if not return null
		if (!(root.hasChildNodes()))
			return null;

		//Root has children, so continue searching for them
		Node matchingNode = null;
		String nodeName = null;
		Node child = null;

		NodeList childNodes = root.getChildNodes();
		int noChildren = childNodes.getLength();
		for (int i = 0; i < noChildren; i++) {
			if (matchingNode == null) {
				child = childNodes.item(i);
				nodeName = child.getNodeName();
				if ((nodeName != null) & (nodeName.equals(elementName)))
					return child;
				if (deep)
					matchingNode =
					findNode(child, elementName, deep, elementsOnly);
			} else
				break;
		}

		if (!elementsOnly) {
			NamedNodeMap childAttrs = root.getAttributes();
			noChildren = childAttrs.getLength();
			for (int i = 0; i < noChildren; i++) {
				if (matchingNode == null) {
					child = childAttrs.item(i);
					nodeName = child.getNodeName();
					if ((nodeName != null) & (nodeName.equals(elementName)))
						return child;
				} else
					break;
			}
		}
		return matchingNode;
	}

	public static Node selectSingleNode( Node node, String xpath ){

		Node result = null;

		//Evaluate XPath against Document
		XPath xPath = XPathFactory.newInstance().newXPath();

		try {
			result = (Node)xPath.evaluate( xpath, node, XPathConstants.NODE );
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	public static NodeList selectNodeList( Node context, String xpath )
	{
		try
		{
			XPath xPath = XPathFactory.newInstance().newXPath();

			NodeList results = (NodeList)xPath.evaluate( xpath, context, XPathConstants.NODESET );

			return results;

		}
		catch ( XPathExpressionException e )
		{
			throw new RuntimeException( "Cannot query node with '" + xpath + "'." );
		}
	}
}
