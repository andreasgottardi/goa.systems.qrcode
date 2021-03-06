package goa.systems.qrcode.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.qrcode.QRCodeReader;

import goa.systems.qrcode.Generator;
import goa.systems.qrcode.testlogic.CodeHelper;

class QRCodeWriterTest {

	@Test
	void test() {
		//@formatter:off
		String tr = "BCD\n"
				+ "001\n"
				+ "1\n"
				+ "SCT\n"
				+ "00000000000 \n"
				+ "Prename Surname\n"
				+ "AT000000000000000000\n"
				+ "EUR0000.00\n"
				+ "\n"
				+ "\n"
				+ "TestTransfer";
		//@formatter:on

		Generator generator = new Generator();

		Document d = generator.generateSvgDocument(tr, 1.0, BarcodeFormat.QR_CODE);
		Node node = d.getFirstChild();
		assertEquals("svg", node.getNodeName());

		assertDoesNotThrow(() -> {
			Result r = new QRCodeReader().decode(CodeHelper.toBinaryBitmap(d));
			assertEquals(tr, r.getText());
			CodeHelper.debugOutput(d);
		});
	}
}
