import ExcelFunctions from './ExcelFunction.js';
export default class KpiPageEvent {
	initBtnEvt() {
		var userSelecter = document.getElementById('kpiLineSearch');
		var preDate = document.getElementById('kpiPreDate');
		var lastDate = document.getElementById('kpiLastDate');
		var searchBtn = document.getElementById('kpiSearch');
		var tableBody = document.getElementById('kpiContents');
		var totals = document.getElementById('kpiTotalcount');
		var errors = document.getElementById('kpiErrorcount');
		var average = document.getElementById('kpiAverage');
		var averageLead = document.getElementById('kpiAverageLead');
		var excelExport = document.getElementById('kpiExcelExport');
		excelExport.addEventListener('click', function() {
			var pre = new Date(preDate.value);
			var last = new Date(lastDate.value);
			if (last < pre) {
				alert('먼저 데이터를 조회하세요');
			} else if (isNaN(last.getTime()) || isNaN(pre.getTime())) {
				alert('먼저 데이터를 조회하세요');
			} else {
				let data = { preTime: preDate.value, lastTime: lastDate.value, printerid: getSelectedValue(userSelecter) };
				axios.get('KpiData', { params: data }).then(response => {
					let result = response.data;

					var errors = result.errors;
					var chartRow = result.errorData;
					var header = [];
					var errorData = [];
					header.push('월')
					for (var i = 0; i < errors.length; i++) {
						header.push(errors[i])
					}
					header.push('전체');
					errorData.push(header);
					for (var i = 0; i < chartRow.length; i++) {
						var dataRow = [];
						dataRow.push(chartRow[i].date.year + '-' + chartRow[i].date.month);
						for (var j = 0; j < errors.length; j++) {
							var value = chartRow[i][errors[j]];
							dataRow.push(value);
						}
						dataRow.push(chartRow[i].total);
						errorData.push(dataRow);
					}

					var printers = result.printers;
					var printerRow = result.printerData;
					var printerHeader = [];
					var printerData = [];
					printerHeader.push('월')
					for (var i = 0; i < printers.length; i++) {
						printerHeader.push(printers[i])
					}
					printerHeader.push('전체');
					printerData.push(printerHeader);
					for (var i = 0; i < printerRow.length; i++) {
						var dataRow = [];
						dataRow.push(printerRow[i].date.year + '-' + printerRow[i].date.month);
						for (var j = 0; j < printers.length; j++) {
							var value = printerRow[i][printers[j]];
							dataRow.push(value);
						}
						dataRow.push(chartRow[i]);
						printerData.push(dataRow);
					}



					let tools = new ExcelFunctions;
					let workbook = new ExcelJS.Workbook();
					let worksheet = workbook.addWorksheet('KPI 보고서');
					//let graphsheet = workbook.addWorksheet('그래프 데이터');
					worksheet.addRows([
						['전체 작업지시 건수', result.totalCount + '건'],
						['작업지시 수정 휫수', result.errorCount + '건'],
						['작업지시 수정률', result.average + '%'],
						['평균 리드타임(일)', result.averageLead]]);

					worksheet.addRows([[], ['=====================KPI 데이터=====================']])
					tools.writeTableAuto(worksheet, document.getElementById('kpiInfoName').children[0], tableBody);

					worksheet.addRows([[], ['=====================오류별 발생 통계=====================']])
					worksheet.addRows(errorData);
					worksheet.addRows([[], ['=====================프린터별 발생 통계=====================']])
					worksheet.addRows(printerData);
					tools.autoWidth(worksheet);
					//tools.insertImage(workbook, worksheet, document.getElementById('error_linechart_area'), imageRange1);
					//tools.insertImage(workbook, worksheet, document.getElementById('printer_linechart_area'), imageRange2);
					tools.downloadWorkBook(workbook, 'KPI 통계_' + preDate.value + '_' + lastDate.value + '_'+getSelectedLabel(userSelecter));
				}).catch(e => {
					console.log(e);
					alert('오류 발생 \n' + e)
				});
			}
		});
	searchBtn.addEventListener('click', function() {
		var pre = new Date(preDate.value);
		var last = new Date(lastDate.value);
		if (last < pre) {
			let year = last.getFullYear();
			let month = last.getMonth() + 1;
			let date = last.getDate();
			alert('이전날짜를 ' + year + '년' + month + '월' + date + '일 이전으로 설정해 주세요.');
		} else if (isNaN(last.getTime()) || isNaN(pre.getTime())) {
			alert('날짜 범위 설정을 모두 완료해 주세요')
		} else {
			let data = { preTime: preDate.value, lastTime: lastDate.value, printerid: getSelectedValue(userSelecter) };
			axios.get('KpiData', { params: data }).then(response => {
				let result = response.data;
				delectAllRow();
				if (result.data.length == 0)
					alert('해당 기간안의 데이터가 없습니다.');
				else {
					for (var i = 0; i < result.data.length; i++) {
						let tr = document.createElement('tr');
						tr.appendChild(createTdElement(result.data[i].id, '', false));
						tr.appendChild(createTdElement(String(i + 1), '5%', true));
						tr.appendChild(createTdElement(result.data[i].title, '15%', true));
						tr.appendChild(createTdElement(result.data[i].brand, '10%', true));
						tr.appendChild(createTdElement(result.data[i].branch, '10%', true));
						tr.appendChild(createTdElement(result.data[i].product, '10%', true));
						tr.appendChild(createTdElement(result.data[i].printer, '10%', true));
						tr.appendChild(createTdElement(result.data[i].errors, '10%', true));
						tr.appendChild(createTdElement(result.data[i].orderDate, '10%', true));
						tr.appendChild(createTdElement(result.data[i].completeDate, '10%', true));
						tr.appendChild(createTdElement(result.data[i].lead, '10%', true));
						tableBody.appendChild(tr);
					}
				}
				totals.value = result.totalCount + '건';
				errors.value = result.errorCount + '건';
				average.value = result.average + '%';
				averageLead.value = result.averageLead;
				initGraphData(result);

			}).catch(e => {
				console.log(e);
				alert('오류 발생 \n' + e)
			});
		}
	});
	}
}
function getSelectedLabel(select){
	return select.options[select.selectedIndex].innerHTML;
}
function initGraphData(kpiData) {

	function drawGraph() {
		// errorchart
		var chartDataFormat = "yyyy년 MM월";
		var chart = new google.visualization.DataTable();
		var errors = kpiData.errors;
		var chartRow = kpiData.errorData;
		chart.addColumn('datetime', '날짜')
		for (var i = 0; i < errors.length; i++) {
			chart.addColumn('number', errors[i])
		}
		chart.addColumn('number', '전체');

		for (var i = 0; i < chartRow.length; i++) {
			var dataRow = [];
			dataRow.push(new Date(chartRow[i].date.year, chartRow[i].date.month - 1));
			for (var j = 0; j < errors.length; j++) {
				var value = chartRow[i][errors[j]];
				dataRow.push(value);
			}
			dataRow.push(chartRow[i].total);
			chart.addRow(dataRow);
		}

		var chartLineCount = errors.length;
		var controlLineCount = 12;
		var chartArea = new google.visualization.ChartWrapper({
			chartType: 'LineChart',
			containerId: 'error_linechart_area',
			options: {
				isStacked: 'persent',
				focusTarget: 'category',
				height: '100%',
				width: '100%',
				legend: { position: 'top', textStyle: { fontSize: 13 } },
				pointSize: 5,
				tooltip: { textStyle: { fontSize: 12 }, showColorCode: true, trigger: 'both' },
				hAxis: {
					format: chartDataFormat, gridlines: {
						count: chartLineCount, units: {
							years: { format: ['yyyy년'] },
							months: { format: ['MM월'] },
							days: { format: ['dd일'] },
							hours: { format: ['HH시'] }
						}
					}, textStyle: { fontSize: 12 }
				},
				vAxis: { minValue: 100, viewWindow: { min: 0 }, gridlines: { count: -1 }, textStyle: { fontSize: 12 } },
				animation: { startup: true, duration: 1000, easing: 'in' },
				annotations: {
					pattern: chartDataFormat,
					textStyle: {
						fontSize: 15,
						bold: true,
						italic: true,
						color: '#871b47',
						auraColor: '#d799ae',
						opacity: 0.8,
						pattern: chartDataFormat
					}
				}
			}
		});
		var control = new google.visualization.ControlWrapper({
			controlType: 'ChartRangeFilter',
			containerId: 'error_ctrl_area',  //control bar를 생성할 영역
			options: {
				ui: {
					chartType: 'LineChart',
					chartOptions: {
						chartArea: { 'width': '60%', 'height': 80 },
						hAxis: {
							'baselineColor': 'none', format: chartDataFormat, textStyle: { fontSize: 12 },
							gridlines: {
								count: controlLineCount, units: {
									years: { format: ['yyyy년'] },
									months: { format: ['MM월'] },
									days: { format: ['dd일'] },
									hours: { format: ['HH시'] }
								}
							}
						}
					}
				},
				filterColumnIndex: 0
			}
		});
		var date_formatter = new google.visualization.DateFormat({ pattern: chartDataFormat });
		date_formatter.format(chart, 0);
		var dashboard = new google.visualization.Dashboard(document.getElementById('error_chart'));
		window.addEventListener('resize', function() { dashboard.draw(chart); }, false); //화면 크기에 따라 그래프 크기 변경
		dashboard.bind([control], [chartArea]);
		dashboard.draw(chart);

		//printer chart
		var printerchart = new google.visualization.DataTable();
		var printers = kpiData.printers;
		var chartRow = kpiData.printerData;
		printerchart.addColumn('datetime', '날짜')
		for (var i = 0; i < printers.length; i++) {
			printerchart.addColumn('number', printers[i])
		}
		printerchart.addColumn('number', '전체');

		for (var i = 0; i < chartRow.length; i++) {
			var dataRow = [];
			dataRow.push(new Date(chartRow[i].date.year, chartRow[i].date.month - 1));
			for (var j = 0; j < printers.length; j++) {
				var value = chartRow[i][printers[j]];
				dataRow.push(value);
			}
			dataRow.push(chartRow[i].total);
			printerchart.addRow(dataRow);
		}

		chartLineCount = printers.length;
		var printerchartArea = new google.visualization.ChartWrapper({
			chartType: 'LineChart',
			containerId: 'printer_linechart_area',
			options: {
				isStacked: 'persent',
				focusTarget: 'category',
				height: '100%',
				width: '100%',
				legend: { position: 'top', textStyle: { fontSize: 13 } },
				pointSize: 5,
				tooltip: { textStyle: { fontSize: 12 }, showColorCode: true, trigger: 'both' },
				hAxis: {
					format: chartDataFormat, gridlines: {
						count: chartLineCount, units: {
							years: { format: ['yyyy년'] },
							months: { format: ['MM월'] },
							days: { format: ['dd일'] },
							hours: { format: ['HH시'] }
						}
					}, textStyle: { fontSize: 12 }
				},
				vAxis: { minValue: 100, viewWindow: { min: 0 }, gridlines: { count: -1 }, textStyle: { fontSize: 12 } },
				animation: { startup: true, duration: 1000, easing: 'in' },
				annotations: {
					pattern: chartDataFormat,
					textStyle: {
						fontSize: 15,
						bold: true,
						italic: true,
						color: '#871b47',
						auraColor: '#d799ae',
						opacity: 0.8,
						pattern: chartDataFormat
					}
				}
			}
		});
		var printercontrol = new google.visualization.ControlWrapper({
			controlType: 'ChartRangeFilter',
			containerId: 'printer_ctrl_area',  //control bar를 생성할 영역
			options: {
				ui: {
					chartType: 'LineChart',
					chartOptions: {
						chartArea: { 'width': '60%', 'height': 80 },
						hAxis: {
							'baselineColor': 'none', format: chartDataFormat, textStyle: { fontSize: 12 },
							gridlines: {
								count: controlLineCount, units: {
									years: { format: ['yyyy년'] },
									months: { format: ['MM월'] },
									days: { format: ['dd일'] },
									hours: { format: ['HH시'] }
								}
							}
						}
					}
				},
				filterColumnIndex: 0
			}
		});
		var printer_date_formatter = new google.visualization.DateFormat({ pattern: chartDataFormat });
		printer_date_formatter.format(printerchart, 0);
		var printer_dashboard = new google.visualization.Dashboard(document.getElementById('printer_chart'));
		window.addEventListener('resize', function() { printer_dashboard.draw(printerchart); }, false); //화면 크기에 따라 그래프 크기 변경
		printer_dashboard.bind([printercontrol], [printerchartArea]);
		printer_dashboard.draw(printerchart);
	}
	google.charts.load('50', { 'packages': ['line', 'controls'] });
	google.charts.setOnLoadCallback(drawGraph);
}
function createTdElement(text, width, display) {
	let tr = document.createElement('td');
	if (!display)
		tr.style.display = 'none';
	else {
		tr.width = width;
	}
	tr.innerText = text;
	return tr;
}
function getSelectedValue(select) {
	return select.options[select.selectedIndex].value;
}
function delectAllRow() {
	var tableBody = document.getElementById('kpiContents');
	var len = tableBody.children.length;
	for (var i = 0; i < len; i++) {
		tableBody.deleteRow(-1);
	}
}
