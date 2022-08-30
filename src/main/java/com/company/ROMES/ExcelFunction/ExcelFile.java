package com.company.ROMES.ExcelFunction;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFShapeProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.AxisCrossBetween;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.Grouping;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData.Series;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.springframework.stereotype.Service;

public class ExcelFile {
	private String filePath;
	private XSSFWorkbook excelFile;

	public final static int CELL_STYLE_TITLE = 0;
	public final static int CELL_STYLE_BASIC = 1;
	public final static int CELL_STYLE_TABLE_HEADER = 2;
	public final static int CELL_STYLE_TABLE_DATA = 3;

	public final static int DATA_INTEGER = 0;
	public final static int DATA_DOUBLE = 1;
	public final static HorizontalAlignment ALIGN_CENTER = HorizontalAlignment.CENTER;
	public final static HorizontalAlignment ALIGN_LEFT = HorizontalAlignment.LEFT;
	public final static HorizontalAlignment ALIGN_RIGHT = HorizontalAlignment.RIGHT;

	// 파일 경로 + 이름
	public ExcelFile(String filePath, OutputStream out) throws InvalidFormatException, IOException {
		this.filePath = filePath;

		File file = new File(filePath);
		if (file.exists()) {
			excelFile = new XSSFWorkbook(new File(this.filePath));
			if (out != null) {
				getExcelForm(out);
			}
		}
	}

	public ExcelFile(String filePath) throws InvalidFormatException, IOException {
		this.filePath = filePath;

		File file = new File(filePath);
		if (file.exists())
			excelFile = new XSSFWorkbook(new File(this.filePath));
		else
			excelFile = new XSSFWorkbook();
	}

	// 새로운 sheet 추가하기
	public XSSFSheet makeNewSheet() {
		XSSFSheet sheet = excelFile.createSheet();
		return sheet;
	}

	public XSSFSheet makeNewSheet(String sheetName) {
		XSSFSheet sheet = excelFile.createSheet(sheetName);
		return sheet;
	}

	// 파일 저장하기
	public boolean saveExcel() {
		try {
			FileOutputStream stream = new FileOutputStream(filePath);
			excelFile.write(stream);
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean saveExcel(String output) {
		try {
			FileOutputStream stream = new FileOutputStream(output);
			excelFile.write(stream);
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean getExcelForm(OutputStream output) {
		try {
			excelFile.write(output);
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 차트 구역 만들기
	public XSSFChart createChartArea(XSSFSheet sheet, int x, int y, int width, int height) {
		XSSFDrawing area = sheet.createDrawingPatriarch();
		XSSFClientAnchor anchor = area.createAnchor(0, 0, 0, 0, x, y, x + width, y + height);
		XSSFChart chart = area.createChart(anchor);
		return chart;
	}

	public XSSFChart createChartArea(String sheetName, int x, int y, int width, int height) {
		XSSFDrawing area = excelFile.getSheet(sheetName).createDrawingPatriarch();
		XSSFClientAnchor anchor = area.createAnchor(0, 0, 0, 0, x, y, x + width, y + height);
		XSSFChart chart = area.createChart(anchor);
		return chart;
	}

	// Pie 차트 그리기
	public void drawPieChart(XSSFChart chart, String chartTitle, List<PieChartData> chartData) {
		chart.setTitleText(chartTitle);
		chart.setTitleOverlay(false);

		XDDFChartLegend legend = chart.getOrAddLegend();
		legend.setPosition(LegendPosition.TOP_RIGHT);
		String[] titles = new String[chartData.size()];
		Double[] values = new Double[chartData.size()];
		for (int i = 0; i < chartData.size(); i++) {
			titles[i] = chartData.get(i).getTitle();
			values[i] = chartData.get(i).getValue();
		}
		XDDFDataSource<String> testOutcomes = XDDFDataSourcesFactory.fromArray(titles);
		XDDFNumericalDataSource<Double> v = XDDFDataSourcesFactory.fromArray(values);

		XDDFChartData data = chart.createData(ChartTypes.PIE3D, null, null);
		chart.displayBlanksAs(null);
		data.setVaryColors(true);
		data.addSeries(testOutcomes, v);
		chart.plot(data);
		setPieDataLabel(chart, 0);
	}

	// Pie그래프 라벨 표시
	private void setPieDataLabel(XSSFChart chart, int index) {
		if (!chart.getCTChart().getPlotArea().getPie3DChartArray(0).getSerArray(index).isSetDLbls())
			chart.getCTChart().getPlotArea().getPie3DChartArray(0).getSerArray(index).addNewDLbls();
		chart.getCTChart().getPlotArea().getPie3DChartArray(0).getSerArray(index).getDLbls().addNewShowLegendKey()
				.setVal(true);
		chart.getCTChart().getPlotArea().getPie3DChartArray(0).getSerArray(index).getDLbls().addNewShowPercent()
				.setVal(true);
		chart.getCTChart().getPlotArea().getPie3DChartArray(0).getSerArray(index).getDLbls().addNewShowLeaderLines()
				.setVal(true);
		chart.getCTChart().getPlotArea().getPie3DChartArray(0).getSerArray(index).getDLbls().addNewShowVal()
				.setVal(false);
		chart.getCTChart().getPlotArea().getPie3DChartArray(0).getSerArray(index).getDLbls().addNewShowCatName()
				.setVal(false);
		chart.getCTChart().getPlotArea().getPie3DChartArray(0).getSerArray(index).getDLbls().addNewShowSerName()
				.setVal(false);
		chart.getCTChart().getPlotArea().getPie3DChartArray(0).getSerArray(index).getDLbls().addNewShowBubbleSize()
				.setVal(false);
	}

	// 선그래프 그리기
	public XDDFLineChartData drawLineGraph(XSSFChart chart, String chartTitle, String xAisTitle, String yAisTitle) {
		// 차트 타이틀 설정
		chart.setTitleText(chartTitle);
		chart.setTitleOverlay(false);
		// 좌측, 하단 라벨 title 설정
		XDDFChartLegend legend = chart.getOrAddLegend();
		legend.setPosition(LegendPosition.TOP_RIGHT);
		XDDFChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
		bottomAxis.setTitle(xAisTitle);
		XDDFValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
		leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
		leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
		leftAxis.setTitle(yAisTitle);
		// 차트 데이터 셋팅
		XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
		return data;
	}

	public void showLineChartLabel(XSSFChart chart) {
		List<CTLineSer> list = chart.getCTChart().getPlotArea().getLineChartArray(0).getSerList();
		for (CTLineSer c : list) {
			c.addNewDLbls();
			c.getDLbls().addNewShowVal().setVal(true);
			c.getDLbls().addNewShowLegendKey().setVal(false);
			c.getDLbls().addNewShowCatName().setVal(false);
			c.getDLbls().addNewShowSerName().setVal(false);
		}
	}

	public void addLine(XSSFChart chart, XDDFLineChartData data, String lineTitle, List<LineChartData> chartData) {

		String[] dates = new String[chartData.size()];
		Integer[] numbers = new Integer[chartData.size()];
		for (int i = 0; i < chartData.size(); i++) {
			dates[i] = chartData.get(i).getxValue();
			numbers[i] = chartData.get(i).getyValue();
		}
		XDDFDataSource<String> testOutcomes = XDDFDataSourcesFactory.fromArray(dates);
		XDDFNumericalDataSource<Integer> values = XDDFDataSourcesFactory.fromArray(numbers);
		XDDFLineChartData.Series series = (Series) data.addSeries(testOutcomes, values);

		// 라인 스타일 설정
		series.setTitle(lineTitle, null);
		series.setSmooth(false);
		series.setMarkerStyle(MarkerStyle.CIRCLE);

		chart.plot(data);
//		setLineDataLabel(chart, 0);

	}

	// 막대 그래프 그리기
	public XDDFBarChartData drawBarGraph(XSSFChart chart, String chartTitle, String xAisTitle, String yAisTitle) {
		chart.setTitleText(chartTitle);
		chart.setTitleOverlay(false);

		XDDFChartLegend legend = chart.getOrAddLegend();
		legend.setPosition(LegendPosition.TOP_RIGHT);
		XDDFChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
		bottomAxis.setTitle(xAisTitle);

		XDDFValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
		leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
		leftAxis.setTitle(yAisTitle);
		leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
		XDDFBarChartData data = (XDDFBarChartData) chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
		data.setBarDirection(BarDirection.COL);
		return data;
//		setBarDataLabel(chart, 0);
	}

	public void addBarData(XSSFChart chart, XDDFBarChartData data, String dataTitle, List<BarChartData> chartData,
			int type) {
		if (type == DATA_INTEGER) {
			String[] dates = new String[chartData.size()];
			dates[0] = "";
			Integer[] numbers = new Integer[chartData.size()];
			numbers[0] = 0;
			for (int i = 0; i < chartData.size(); i++) {
				dates[i] = chartData.get(i).getTitle();
				numbers[i] = chartData.get(i).getIntValue();
			}
			XDDFDataSource<String> testOutcomes = XDDFDataSourcesFactory.fromArray(dates);
			XDDFNumericalDataSource<Integer> values = XDDFDataSourcesFactory.fromArray(numbers);
			XDDFBarChartData.Series series = (XDDFBarChartData.Series) data.addSeries(testOutcomes, values);
			series.setTitle(dataTitle, null);
			chart.plot(data);
//			XDDFBarChartData bar = (XDDFBarChartData) data;
//            bar.setBarDirection(BarDirection.BAR);

		} else if (type == DATA_DOUBLE) {
			String[] dates = new String[chartData.size()];
			dates[0] = "";
			Double[] numbers = new Double[chartData.size()];
			numbers[0] = 0.0;
			for (int i = 0; i < chartData.size(); i++) {
				dates[i] = chartData.get(i).getTitle();
				numbers[i] = chartData.get(i).getDoubeValue();
			}
			XDDFDataSource<String> testOutcomes = XDDFDataSourcesFactory.fromArray(dates);
			XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromArray(numbers);
			XDDFBarChartData.Series series = (XDDFBarChartData.Series) data.addSeries(testOutcomes, values);
			series.setTitle(dataTitle, null);
			chart.plot(data);

//			XDDFBarChartData bar = (XDDFBarChartData) data;
//            bar.setBarDirection(BarDirection.BAR);
		}

	}

	// 바 차트 라벨 표시
	public void showBarDataLabel(XSSFChart chart) {
		List<CTBarSer> list = chart.getCTChart().getPlotArea().getBarChartArray(0).getSerList();
		for (CTBarSer s : list) {
			s.addNewDLbls();
			s.getDLbls().addNewShowVal().setVal(true);
			s.getDLbls().addNewShowLegendKey().setVal(false);
			s.getDLbls().addNewShowCatName().setVal(false);
			s.getDLbls().addNewShowSerName().setVal(false);
		}
	}

	// ***************************************************************************************************//

	// 폰트 얻기
	public XSSFFont makeFont(int size) {
		XSSFFont font = excelFile.createFont();
		font.setFontHeightInPoints((short) size);
		return font;
	}

	// 셀 스타일 얻기
	public XSSFCellStyle makeCellStyle(int type, XSSFFont font, HorizontalAlignment aligent) {
		XSSFCellStyle style = excelFile.createCellStyle();
		style.setAlignment(aligent);
		switch (type) {
		case CELL_STYLE_TITLE:
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setFillForegroundColor(new XSSFColor(Color.white));
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBottomBorderColor(new XSSFColor(Color.lightGray));
			style.setRightBorderColor(new XSSFColor(Color.lightGray));
			break;
		case CELL_STYLE_TABLE_HEADER:
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setFillForegroundColor(new XSSFColor(Color.lightGray));
			style.setBorderRight(BorderStyle.THICK);
			style.setBorderBottom(BorderStyle.THICK);
			style.setBorderTop(BorderStyle.THICK);
			style.setBorderLeft(BorderStyle.THICK);
			style.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.black));
			style.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.black));
			style.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.black));
			style.setBorderColor(BorderSide.TOP, new XSSFColor(Color.black));
			break;
		case CELL_STYLE_TABLE_DATA:
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setFillForegroundColor(new XSSFColor(Color.white));
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.black));
			style.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.black));
			style.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.black));
			break;
		case CELL_STYLE_BASIC:
			style.setFillForegroundColor(new XSSFColor(Color.white));
			break;
		}
		style.setFont(font);
		return style;
	}

	// 글자 쓰기
	public void writeText(XSSFSheet sheet, int x, int y, XSSFCellStyle style, String text) {
		XSSFRow row = sheet.getRow(y);
		if (row == null) {
			row = sheet.createRow(y);
			sheet.autoSizeColumn(y);
			sheet.setColumnWidth(y, (sheet.getColumnWidth(y)) + (style.getFont().getFontHeight() * 8));
		}
		XSSFCell cell = row.getCell(x);
		if (cell == null)
			cell = row.createCell(x);
		cell.setCellStyle(style);
		cell.setCellValue(text);
	}

	public void writeText(String sheetName, int x, int y, XSSFCellStyle style, String text) {
		XSSFSheet sheet = excelFile.getSheet(sheetName);
		XSSFRow row = sheet.getRow(y);
		if (row == null) {
			row = sheet.createRow(y);
			sheet.autoSizeColumn(y);
			sheet.setColumnWidth(y, (sheet.getColumnWidth(y)) + (style.getFont().getFontHeight() * 8));
		}
		XSSFCell cell = row.getCell(x);
		if (cell == null)
			cell = row.createCell(x);
		cell.setCellStyle(style);
		cell.setCellValue(text);

	}

}
