
export default class ExcelFunctions {
	readExcel(fileName) {
	
		const wb = new ExcelJS.Workbook();


		wb.xlsx.readFile(fileName).then(() => {
			return wb;
		}).catch(err => {
			console.log(err.message);
		});
	}

	insertImage(workbook, worksheet, imgTag, range) {
		// 이미지 데이터 Base64 형식 로딩

		html2canvas(imgTag).then(function(canvas) {
			var data = canvas.toDataURL();
			var imageId1 = workbook.addImage({
				base64: data,
				extension: "jpeg",
			});
			worksheet.addImage(imageId1, range);
		});
	};
	writeCell(worksheet, cellNum, value) {
		worksheet.getCell(cellNum).value = value
	}
	getCellName(worksheet, row, column) {
		let cell = worksheet.getRow(row).getCell(column);
		return cell.name;
	}
	getDataCellRange(worksheet, startRow, startColumn, endRow, endColumn) {
		let startCell = worksheet.getRow(startRow).getCell(startColumn);
		let endCell = worksheet.getRow(endRow).getCell(endColumn);
		return startCell.name + ':' + endCell.name;
	}
	writeTableAuto(worksheet, tableColumnTag, tableBodyTag) {
		var data = [];
		var header = [];
		for (var i = 0; i < tableColumnTag.children.length; i++) {
			header.push(tableColumnTag.children[i].innerText);
		}
		data.push(header);
		for (var i = 0; i < tableBodyTag.children.length; i++) {
			var tableData = [];
			for (var j = 1; j < tableBodyTag.children[i].children.length; j++) {
				tableData.push(tableBodyTag.children[i].children[j].innerText);
			}
			data.push(tableData);
		}
		worksheet.addRows(data);
	}
	writeTable(worksheet, startRow, startColumn, tableColumnTag, tableBodyTag) {

		for (var i = 0; i < tableColumnTag.children.length; i++) {
			writeCell(worksheet, startRow, startColumn + i, tableColumnTag.children[i].innerText);
		}
		for (var i = 0; i < tableBodyTag.children.length; i++) {
			for (var j = 1; j < tableBodyTag.children[i].children.length; j++) {
				writeCell(worksheet, startRow + 1 + i, startColumn + j - 1, tableBodyTag.children[i].children[j].innerText);
			}
		}
	}
	autoWidth(worksheet) {
		worksheet.columns.forEach(function(column, i) {
			var maxLength = 0;
			column["eachCell"]({ includeEmpty: true }, function(cell) {

				var columnLength = 10;
				try {
					if (cell.value) {
						if (!cell.value.includes('==')) {
							columnLength = cell.value.toString().length;
						}
					}
					if (columnLength > maxLength) {
						maxLength = columnLength;
					}
				} catch (error) {

				}

			});
			column.width = maxLength < 10 ? 10 : maxLength;
		});
	}
	downloadWorkBook(workbook, filename) {
		workbook.xlsx.writeBuffer().then((data) => {

			let datas = new Blob([data]);
			let url = window.URL.createObjectURL(datas);
			let elem = document.createElement("a");
			elem.href = url;
			elem.download = `${filename}.xlsx`;
			document.body.appendChild(elem);
			elem.style = "display: none";
			elem.click();
			elem.remove();
		});
	}
}
function writeCell(worksheet, row, column, value) {
	worksheet.getRow(row).getCell(column).value = value
}
function getCellName(worksheet, row, column) {
	let cell = worksheet.getRow(row).getCell(column);
	return cell.name;
}
function downloadWorkbook(img) {

	let workbook = new ExcelJS.Workbook();
	//시트 생성
	const dataSheet = workbook.addWorksheet("DataSheet");

	//테이블 데이터 삽입
	rawDate.forEach((item, index) => {
		dataSheet.getColumn(index + 12).values = [item.header, ...item.data];
	});

	// 이미지 삽입
	var imageId1 = workbook.addImage({
		base64: img,
		extension: "png",
	});
	dataSheet.addImage(imageId1, 'A1:K22') // 범위 지정;

	//엑셀 출력
	workbook.xlsx.writeBuffer().then((data) => {

		let datas = new Blob([data]);
		let url = window.URL.createObjectURL(datas);

		let elem = document.createElement("a");
		elem.href = url;
		elem.download = `${new Date().toString().replaceAll(" ", "")}.xlsx`;
		document.body.appendChild(elem);
		elem.style = "display: none";
		elem.click();
		elem.remove();
	});



	return workbook;
}