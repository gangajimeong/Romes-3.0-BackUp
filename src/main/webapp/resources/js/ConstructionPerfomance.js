export class ConstructionPerformance {
	setFilterEvt() {
		var previous = document.getElementById("constPreDate");
		var last = document.getElementById("constLastDate");
		var searchText = document.getElementById('ConstructionPerformanceSearchText');
		previous.addEventListener('change', function(event) {
			var date = new Date(previous.value + ' 0:0:0');
			if (last.value != '') {
				var date2 = new Date(last.value + ' 23:59:59');
				if (date > date2) {
					alert('시간 선택이 잘못되었습니다.');
					previous.value = '';
				} else {
					repaintTable(date, date2, searchText.value);
				}
			} else {
				if (previous.value != '')
					repaintTable(date, null, searchText.value);
				else
					repaintTable(null, null, searchText.value);
			}
		});
		last.addEventListener('change', function(event) {
			var date = new Date(last.value + ' 23:59:59');

			if (previous.value != '') {
				var date2 = new Date(previous.value + ' 0:0:0');
				if (date < date2) {
					alert('시간 선택이 잘못되었습니다.');
					last.value = '';
				} else {
					repaintTable(date2, date, searchText.value);
				}
			} else {
				if (last.value != '')
					repaintTable(null, date, searchText.value);
				else
					repaintTable(null, null, searchText.value);
			}
		});
		searchText.addEventListener('input', function() {
			if (previous.value != '' && last.value != '') {
				repaintTable(new Date(previous.value + ' 0:0:0'), new Date(last.value + ' 23:59:59'), searchText.value);
			} else {
				repaintTable(null, null, searchText.value);

			}
		});
	}


	setTableClickEvt() {
		var tableRows = document.getElementById('ConstructionContents').children;
		for (var i = 0; i < tableRows.length; i++) {
			var row = tableRows[i];
			if (!row.classList.contains("construcionContent")) {
				row.addEventListener('click', function() {
					var idx = [...this.parentElement.children].indexOf(this) + 1;
					hideAllCollapse();
					if (tableRows[idx].getElementsByTagName('tbody')[0].children.length != 0) {
						var id = '#' + this.children[0].innerText;
						$(id).collapse('toggle');
					} else {
						alert('시공 정보가 없습니다.')
					}
				});

			} else {
				var items = row.getElementsByTagName("tbody")[0].children;
				for (var j = 0; j < items.length; j++) {
					items[j].addEventListener('click', function() {
						var tds = this.children;
						var urls = tds[1].innerText.replace("[", "").replace("]", "").split(",");
						changeImage(urls);
					});
				}
			}
		}
	}
	setTableClickEvtOld() {
		const rows = document.getElementById("ConstructionContents");
		for (var i = 0; i < rows.children.length; i++) {

			rows.children[i].addEventListener('click', function() {
				var thlist = this.getElementsByTagName('td');
				var list = thlist[8].innerText.replace("[", "").replace("]", "").split(",");
				changeImage(list);


			});


		}
	}
	detailImage() {
		const indiBoxItems = document.getElementById("constPerform-indi-Items");
		document.getElementById("detailConstructionImg").addEventListener('click', function() {
			$('#myModal').modal('show');
			for (var i = 0; i < indiBoxItems.children.length; i++) {
				if (indiBoxItems.children[i].classList.contains('active')) {
					document.getElementById("detailView").src = indiBoxItems.children[i].children[0].src;
					break;
				} else {
					document.getElementById("detailView").src = 'image?imagename=noimage.png'
				}
			}

		})
	}
}
function changeImage(imgUrlList) {
	const indiBox = document.getElementById("constPerformIndi");
	const indiBoxItems = document.getElementById("constPerform-indi-Items");

	if (indiBox.children.length > 1) {
		while (indiBox.children.length > 1) {
			indiBox.lastChild.remove();
		}
		while (indiBoxItems.children.length > 1) {
			indiBoxItems.lastChild.remove();
		}
	}
	let firstIndi = document.getElementsByClassName("template-indi")[0];
	let firstItem = document.getElementsByClassName("indi-item")[0];
	firstIndi.classList.add('active');
	firstItem.classList.add('active');
	if (indiBox.children.length === 1) {

		[...imgUrlList].forEach(function(url, i) {
			if (i === 0) {
				indiBoxItems.children[0].children[0].src = "image?imagename=" + encodeURI(url);
			} else {
				let indiCator = firstIndi.cloneNode(true);
				let item = firstItem.cloneNode(true);
				let indis = indiCator;
				let items = item;
				indis.classList.remove('active')
				indis.setAttribute("data-slide-to", i);
				items.classList.remove('active')
				items.children[0].src = "image?imagename=" + encodeURI(url);
				indiBox.appendChild(indis);
				indiBoxItems.appendChild(items);
			}
		})

	}
}
function hideAllCollapse() {
	var tableRows = document.getElementById('ConstructionContents').children;
	for (var i = 0; i < tableRows.length; i++) {
		var row = tableRows[i];
		if (row.classList.contains("construcionContent")) {
			var d = row.getElementsByTagName('div')[0];
			var id = '#' + d.getAttribute('id');
			$(id).collapse('hide');
		}
	}
}
function repaintTable(date1, date2, changeValue) {
	hideAllCollapse();
	var rows = document.getElementById("ConstructionContents").rows;
	var select = document.getElementById('constructionPerformanceSearch');
	var selectIdx = select.options[select.selectedIndex].value.split('-');
	var idx = 1;
	for (var i = 0; i < rows.length; i += 2) {
		var thlist = rows[i].getElementsByTagName('td');
		var sublist = rows[i + 1].getElementsByTagName('tbody')[0].children;
		var isvisible = false;
		if (date1 != null && date2 != null) {
			for (var j = 0; j < sublist.length; j++) {
				if (!sublist[j].children[6].innerText != '-') {
					var date = new Date(sublist[j].children[6].innerText);
					if (date > date1 && date < date2) {
						isvisible = true;
						break;
					}
				}
			}
		} else if (date1 != null && date2 == null) {
			for (var j = 0; j < sublist.length; j++) {
				if (!sublist[j].children[6].innerText != '-') {
					var date = new Date(sublist[j].children[6].innerText);
					if (date > date1) {
						isvisible = true;
						break;
					}
				}
			}
		} else if (date1 == null && date2 != null) {
			for (var j = 0; j < sublist.length; j++) {
				if (!sublist[j].children[6].innerText != '-') {
					console.log(sublist[j].children[6].innerText);
					var date = new Date(sublist[j].children[6].innerText);
					if (date < date2) {
						isvisible = true;
						break;
					}
				}
			}
		} else {
			isvisible = true;
		}
		if (isvisible) {
			if (Number(selectIdx[0]) == 0) {
				console.log("HHEERREE")
				if (!thlist[Number(selectIdx[1])].innerText.includes(changeValue))
					isvisible = false;
			} else {
				for (var k = 0; k < sublist.length; k++) {
					if (!sublist[k].children[Number(selectIdx[1])].innerText.includes(changeValue)) {
						console.log(sublist[k].children[Number(selectIdx[1])].innerText);
						isvisible = false;
						break;
					}
				}
			}
		}
		if (isvisible) {
			rows[i].style.display = '';
			rows[i + 1].style.display = '';
			thlist[1].innerText = String(idx);
			idx++;
		} else {
			rows[i].style.display = 'none';
			rows[i + 1].style.display = 'none';
		}
	}
}

function valueToIdx(value) {
	switch (value) {
		case "title":
			return 2;
		case "constructionDivision":
			return 3;
		case "company":
			return 4;
		default:
			return 0;
	}
}