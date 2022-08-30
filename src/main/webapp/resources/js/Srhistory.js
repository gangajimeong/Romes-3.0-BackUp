/**
 * 
 */
export class Srhistory {
	setTableColor() {
		var tableContents = document.getElementById("srhistory-table-Contents").children;
		for (var i = 0; i < tableContents.length; i++) {
			if (tableContents[i].children[2].innerText == '입고') {
				tableContents[i].children[2].style.color='blue';
			} else {
				tableContents[i].children[2].style.color='red';
			}
			
		}
	}
	setSearchEvt() {
		var searchText = document.getElementById('srhistory-searchText');
		var typeSelect = document.getElementById('srhistory-type-select');
	
		searchText.addEventListener('input', function() {
			repaintTable(searchText.value);
		})
		typeSelect.addEventListener('change',function(){
			repaintTable(searchText.value);
		})
	}
}
function repaintTable(value) {
	
	var typeValue = valueToString(getSelectedValue('srhistory-type-select'));
	var searchIdx = getSelectedValue('srhistory-select');
	var tableContents = document.getElementById("srhistory-table-Contents").children;
	var idx = 1;
	if (typeValue == undefined)
		typeValue = '';	
	console.log(getSelectedValue('srhistory-type-select'));
	for (var i = 0; i < tableContents.length; i++) {
		if (tableContents[i].children[2].innerText.includes(typeValue)) {
			if (tableContents[i].children[searchIdx].innerText.includes(value)) {
				tableContents[i].style.display = '';
				tableContents[i].children[1].innerText = idx;
				idx++;
			} else {
				tableContents[i].style.display = 'none';
			}
		} else {
			tableContents[i].style.display = 'none';
		}
	}

}
function valueToString(value) {
	switch (value) {
		case '0':
			return '';
		case '1':
			return '입고';
		case '2':
			return '출고';
	}
}
function getSelectedValue(selctionId) {
	var select = document.getElementById(selctionId);
	return select.options[select.selectedIndex].value;
}