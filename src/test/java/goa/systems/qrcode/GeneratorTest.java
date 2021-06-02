/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package goa.systems.qrcode;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

class GeneratorTest {

	/**
	 * Generates a svg qr code into the temporary directory.
	 */
	@Test
	void testSomeLibraryMethod() {

		Transfer tr = new Transfer();
		tr.setBic("00000000000 ");
		tr.setIban("AT000000000000000000");
		tr.setRecipientname("Prename Surname");
		tr.setSum("0000.00");
		tr.setUsage("TestTransfer");

		Generator classUnderTest = new Generator();
		File svg = classUnderTest.generateQrCode(tr);
		assertTrue(svg.exists());
		svg.delete();
		assertFalse(svg.exists());

	}

	/**
	 * Tests if base svg is valid.
	 */
	@Test
	void testBaseSvg() {
		Generator classUnderTest = new Generator();
		assertDoesNotThrow(() -> {
			Document d = classUnderTest.getBaseSvg();
			Node node = d.getFirstChild();
			assertEquals("svg", node.getNodeName());
		});
	}
}
