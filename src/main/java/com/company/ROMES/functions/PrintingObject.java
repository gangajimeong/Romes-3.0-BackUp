package com.company.ROMES.functions;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.PageRanges;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.printing.PDFPageable;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.company.ROMES.entity.LabelPrinter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrintingObject {
	static String BasicPath = System.getProperty("user.home")+"/ROMES_Util/";
	private int margin_w = 0;
	private int margin_h = 0;
	private int fontSize = 20;
	private List<String> printings = new ArrayList<>();
	private String barcode = "";
	private String printName;
	public static PrintService[] getPrintList() {
		return PrintServiceLookup.lookupPrintServices(null, null);
	}

	public static PrintService getPrintService(String printerName) {
		for(PrintService s : getPrintList()) {
			if(s.getName().equals(printerName))
				return s;
		}
		return null;
	}
	public void addPrintingLine(String label) {
		this.printings.add(label);
	}
	public void setPrintLabel(LabelPrinter printer) {
		this.margin_h = printer.getMargin_h();
		this.margin_w = printer.getMargin_w();
		this.fontSize = printer.getFontSize();
		this.printName = printer.getPrinterName();
	}
	private BufferedImage getBarcodeImage(float height) {
		Code128Bean bean = new Code128Bean();
		final int dpi = (int) height * 8;
		bean.setModuleWidth(UnitConv.in2mm(1.5f / dpi));
		bean.setBarHeight(height*3);
		bean.doQuietZone(false);
		bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
		BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
		
		bean.generateBarcode(canvas, barcode);
		return canvas.getBufferedImage();
	}
	private PDDocument drawPdf(int fontSize, int margin_w, int margin_h) {
		// PDF File ??????
		PDDocument doc = new PDDocument();
		try {
			// PDF Page ??????
			PDPage page = new PDPage();
			doc.addPage(page);
			String msg = "";
			File file = new File(BasicPath);
			if(!file.exists()) {
				file.mkdirs();
			}

			// ?????? ??????
			
			InputStream fontstream = new FileInputStream(new File(BasicPath+"NanumSquareR.ttf"));
			PDType0Font font = PDType0Font.load(doc, fontstream);

			// PDF ?????? ?????? ??????
			PDPageContentStream in = new PDPageContentStream(doc, page);
			// ??????, ??????????????? ??????
			in.setFont(font, fontSize);
			in.setLeading(fontSize);
			in.beginText();
			// PDF??? ?????? ????????? ???????????? 0~ , ???????????? 0~ ?????? ????????????????????? ?????? ????????? ?????? Page???????????? ?????? ????????? ??? ?????????.
			int height = (int) page.getMediaBox().getHeight() - margin_h;
			in.newLineAtOffset(margin_w, height);
			// list??? ???????????? text ????????? ??????
			for (int i = 0; i < printings.size(); i++) {
				String m = printings.get(i);
				in.showText(m);
				in.newLine();
				height = height - fontSize;
			}
			// ?????????????????? ????????? ?????? ???
			BufferedImage image = getBarcodeImage(fontSize);
			PDImageXObject img = JPEGFactory.createFromImage(doc, image);
			in.endText();
			// ????????? ????????? ??????
			in.drawImage(img, margin_w, height - img.getHeight() + 5);
			in.close();
			// ?????? ??????
			doc.save(new File("./"+barcode+".pdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	public void print(String printerName) {
		try {
			// ?????? ????????? ?????? ??????????????? ???
			PDDocument doc = drawPdf(fontSize, margin_w, margin_h);
			// ????????? ?????? ?????? Set
			PrintRequestAttributeSet sets = new HashPrintRequestAttributeSet();
			sets.add(new PageRanges(1, 1));
			sets.add(new Copies(1));
			// ????????? ???????????? ??????
			PrintService service = getPrintService(printerName);
			// ??????
			printPDFile(service, sets, doc);
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void print() {
		try {
			// ?????? ????????? ?????? ??????????????? ???
			PDDocument doc = drawPdf(fontSize, margin_w, margin_h);
			// ????????? ?????? ?????? Set
			PrintRequestAttributeSet sets = new HashPrintRequestAttributeSet();
			sets.add(new PageRanges(1, 1));
			sets.add(new Copies(1));
			// ????????? ???????????? ??????
			PrintService service = getPrintService(printName);
			// ??????
			printPDFile(service, sets, doc);
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void printPDFile(PrintService service, PrintRequestAttributeSet sets, PDDocument doc) {
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintService(service);
			job.setPageable(new PDFPageable(doc));
			job.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		PrintingObject o = new PrintingObject();
		o.setMargin_h(10);
		o.setMargin_w(20);
		o.addPrintingLine("test");
		o.addPrintingLine("this");
		o.addPrintingLine("te");
		o.setBarcode("123123123");
		o.print("1??????");
	}
}
