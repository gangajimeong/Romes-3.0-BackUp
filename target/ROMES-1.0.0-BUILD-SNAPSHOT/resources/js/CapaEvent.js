
import ExcelFunctions from './ExcelFunction.js';
export default class CapaEvent {
	initBtnEvt() {
		var lineSelecter = document.getElementById('capaUserSearch');
		var preDate = document.getElementById('capaPreDate');
		var lastDate = document.getElementById('capaLastDate');
		var searchBtn = document.getElementById('capaSearch');
		var tableBody = document.getElementById('capaWorkInfoContents');
		var excelExport = document.getElementById('capaExcelExport');
		excelExport.addEventListener('click', function() {
			var pre = new Date(preDate.value);
			var last = new Date(lastDate.value);
			if (last < pre) {
				alert('먼저 데이터를 조회하세요');
			} else if (isNaN(last.getTime()) || isNaN(pre.getTime())) {
				alert('먼저 데이터를 조회하세요');
			} else {
				let data = { preTime: preDate.value, lastTime: lastDate.value, lineid: getSelectedValue(lineSelecter) };
				axios.get('CapaData', { params: data }).then(response => {
					let result = response.data;

					var designers = result.designers;
					var chartRow = result.chartRows;
					var chartData = [];
					var header = [];
					header.push('월')
					for (var i = 0; i < designers.length; i++) {
						header.push(designers[i])
					}
					header.push( '전체');
					chartData.push(header);
					for (var i = 0; i < chartRow.length; i++) {
						var dataRow = [];
						dataRow.push(new Date(chartRow[i].date.year, chartRow[i].date.month - 1));
						for (var j = 0; j < designers.length; j++) {
							var value = chartRow[i][designers[j]];
							dataRow.push(value);
						}
						dataRow.push(chartRow[i].total);
						chartData.push(dataRow);
					}

					let tools = new ExcelFunctions;
					let workbook = new ExcelJS.Workbook();
					let worksheet = workbook.addWorksheet('Capa 보고서');
					//let graphsheet = workbook.addWorksheet('그래프 데이터');
					worksheet.addRows([[], ['=====================작업지시 데이터=====================']])
					tools.writeTableAuto(worksheet, document.getElementById('capaWorkInfoName').children[0], tableBody);

					worksheet.addRows([[], ['=====================라인 투입 통계=====================']])
					worksheet.addRows(chartData);

					tools.autoWidth(worksheet);
					//tools.insertImage(workbook, worksheet, document.getElementById('error_linechart_area'), imageRange1);
					//tools.insertImage(workbook, worksheet, document.getElementById('printer_linechart_area'), imageRange2);
					tools.downloadWorkBook(workbook, '디자이너Capa_'+preDate.value+'~'+lastDate.value+'_'+getSelectedLabel(lineSelecter));

				}).catch(e => {
					console.log(e);
					alert('오류 발생 \n' + e)
				});
			}
			
		})
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
				let data = { preTime: preDate.value, lastTime: lastDate.value, lineid: getSelectedValue(lineSelecter) };
				axios.get('CapaData', { params: data }).then(response => {
					let result = response.data;
					delectAllRow();
					if (result.data.length == 0)
						alert('해당 기간안의 데이터가 없습니다.');
					else {
						for (var i = 0; i < result.data.length; i++) {
							let tr = document.createElement('tr');
							tr.appendChild(createTdElement(result.data[i].id, '', false));
							tr.appendChild(createTdElement(String(i + 1), '5%', true));
							tr.appendChild(createTdElement(result.data[i].title, '10%', true));
							tr.appendChild(createTdElement(result.data[i].designer, '10%', true));
							tr.appendChild(createTdElement(result.data[i].brand, '10%', true));
							tr.appendChild(createTdElement(result.data[i].branch, '15%', true));
							tr.appendChild(createTdElement(result.data[i].product, '25%', true));
							tr.appendChild(createTdElement(result.data[i].time, '25%', true));
							tableBody.appendChild(tr);
						}

					}
					initChart(result);

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
	var tableBody = document.getElementById('capaWorkInfoContents');
	var len = tableBody.children.length;
	for (var i = 0; i < len; i++) {
		tableBody.deleteRow(-1);
	}
}
function initChart(chartdata) {
	function drawDashboard() {
		var chartDataFormat = "yyyy년 MM월";
		var chart = new google.visualization.DataTable();
		var designers = chartdata.designers;
		var chartRow = chartdata.chartRows;
		chart.addColumn('datetime', '날짜')
		for (var i = 0; i < designers.length; i++) {
			chart.addColumn('number', designers[i])
		}
		chart.addColumn('number', '전체');

		for (var i = 0; i < chartRow.length; i++) {
			var dataRow = [];
			dataRow.push(new Date(chartRow[i].date.year, chartRow[i].date.month - 1));
			for (var j = 0; j < designers.length; j++) {
				var value = chartRow[i][designers[j]];
				dataRow.push(value);
			}
			dataRow.push(chartRow[i].total);
			chart.addRow(dataRow);
		}

		var chartLineCount = designers.length;
		var controlLineCount = 12;
		var chartArea = new google.visualization.ChartWrapper({
			chartType: 'LineChart',
			containerId: 'Capa_linechart_area',
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
			containerId: 'Capa_ctrl_area',  //control bar를 생성할 영역
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
		var dashboard = new google.visualization.Dashboard(document.getElementById('Capa_chart'));
		window.addEventListener('resize', function() { dashboard.draw(chart); }, false); //화면 크기에 따라 그래프 크기 변경
		dashboard.bind([control], [chartArea]);
		dashboard.draw(chart);
	}
	google.charts.load('50', { 'packages': ['line', 'controls'] });
	google.charts.setOnLoadCallback(drawDashboard);
}
