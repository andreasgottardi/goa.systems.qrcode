package goa.systems.qrcode;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.pdf417.PDF417Reader;

class UPCATest {

	@Test
	void test() {

		//@formatter:off
		String tr = "0123456789012";
		//@formatter:on

		Generator generator = new Generator();

		Document d = generator.generateSvgDocument(tr, 10.0, BarcodeFormat.PDF_417);
		Node node = d.getFirstChild();
		assertEquals("svg", node.getNodeName());

		assertDoesNotThrow(() -> {
			Result r = new PDF417Reader().decode(CodeHelper.toImage(d));
			assertEquals(tr, r.getText());
		});
	}
}
