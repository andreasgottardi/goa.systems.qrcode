package goa.systems.qrcode;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import goa.systems.commons.xml.XmlFramework;
import goa.systems.qrcode.exceptions.WrongDataException;

class GeneratorTest {

	private static final Logger logger = LoggerFactory.getLogger(GeneratorTest.class);

	/**
	 * Tests if base SVG is valid.
	 */
	@Test
	void testBaseSvg() {
		Generator generator = new Generator();
		assertDoesNotThrow(() -> {
			Document d = generator.getBaseSvg();
			Node node = d.getFirstChild();
			assertEquals("svg", node.getNodeName());
		});
	}

	/**
	 * Generates a SVG QR code into the temporary directory, tests its existence and
	 * deletes the file afterwards.
	 */
	@Test
	void testSvgGeneration() {

		Transfer tr = new Transfer();
		tr.setBic("00000000000 ");
		tr.setIban("AT000000000000000000");
		tr.setCreditor("Prename Surname");
		tr.setAmount("0000.00");
		tr.setText("TestTransfer");

		Generator generator = new Generator();
		String svg = generator.generateSvgString(tr);
		try {
			Document d = XmlFramework.getDocumentFromString(svg);
			assertEquals("svg", d.getFirstChild().getNodeName());
		} catch (SAXException | IOException | ParserConfigurationException e) {
			logger.error("Exception occured.", e);
		}
	}

	/**
	 * Generates a SVG QR code into the temporary directory, tests its existence and
	 * deletes the file afterwards.
	 */
	@Test
	void testStreamGeneration() {

		Transfer tr = new Transfer();
		tr.setBic("00000000000 ");
		tr.setIban("AT000000000000000000");
		tr.setCreditor("Prename Surname");
		tr.setAmount("0000.00");
		tr.setText("TestTransfer");

		Generator generator = new Generator();
		ByteArrayInputStream svg = generator.generateSvgStream(tr);
		assertNotNull(svg);
		assertTrue(svg.available() > 0);
	}

	/**
	 * Generates a SVG QR code into the temporary directory, tests its existence and
	 * deletes the file afterwards.
	 */
	@Test
	void testFileGeneration() {

		Transfer tr = new Transfer();
		tr.setBic("00000000000 ");
		tr.setIban("AT000000000000000000");
		tr.setCreditor("Prename Surname");
		tr.setAmount("0000.00");
		tr.setText("TestTransfer");

		Generator generator = new Generator();
		File svg = generator.generateQrCode(tr);
		assertTrue(svg.exists());
		svg.delete();
		assertFalse(svg.exists());
	}

	/**
	 * Tests if a exception is thrown when text and reference are set.
	 */
	@Test
	void testWrongDataException() {

		Transfer tr = new Transfer();
		tr.setBic("00000000000 ");
		tr.setIban("AT000000000000000000");
		tr.setCreditor("Prename Surname");
		tr.setAmount("0000.00");
		tr.setReference("TestReference");
		tr.setText("TestText");

		assertThrows(WrongDataException.class, () -> {
			tr.toQRContent();
		});
	}
}
