/**
 * 
 */
export default class ApprovalInfo {

	dragAndDropEvt(TableID) {
		$(TableID).sortable({
			items: 'tr',
			cursor: 'pointer',
			axis: 'y',
			dropOnEmpty: false,
			start: function(e, ui) {
				ui.item.addClass("selected");
			},
			stop: function(e, ui) {
				ui.item.removeClass("selected");
				let tr = document.querySelector(TableID).children
				for (let i = 0; i < tr.length; i++) {
					tr[i].children[0].innerText =i+ 1;
				}

			}
		});
	}
	drawDivisionSelect() {

		let select = document.getElementsByClassName("selectDivisionForApproval")[0];
		let select1 = document.getElementsByClassName("selectPositionForApproval")[0];
		let select2 = document.getElementsByClassName("selectUsersForApproval")[0];
		axios.get("getDivisionOrPositionOrUsers", {
			params: {
				option: 0,
				division_id: 0,
				position: 0
			}
		})
			.then(function(res) {
				console.log(res.data);
				let res0 = res.data;
				createSelectBox(select, res0);


			}).catch(function(e) {
				console.log(e);
			})
	}

	drawPosition(id) {
		let select = document.getElementsByClassName("selectPositionForApproval")[0];
		let select1 = document.getElementsByClassName("selectUsersForApproval")[0];
		if (select1.children.length > 1) {
			optionClear(select1);
		}
		let div_id = id;
		if (div_id === 0) return;
		axios.get("getDivisionOrPositionOrUsers", {
			params: {
				option: 1,
				division_id: div_id,
				position: 0
			}
		})
			.then(function(res) {
				console.log(res.data);
				let res1 = res.data;
				createSelectBoxForPosition(select, res1)

			}).catch(function(e) {
				console.log(e);
			})

	}

	drawUsers(id, position) {
		console.log(id + "," + position)
		const select = document.getElementsByClassName("selectUsersForApproval")[0];
		if (id === 0 || position === 0) return;
		else {
			axios.get("getDivisionOrPositionOrUsers", {
				params: {
					option: 2,
					division_id: id,
					position: position
				}
			})
				.then(function(res) {

					let res2 = res.data;
					if (res2.length === 0) return;
					createSelectBoxForUser(select, res2)
				}).catch(function(e) {
					console.log(e);
				})
		}
	}
	selectEvts() {
		let select = document.getElementsByClassName("selectDivisionForApproval")[0];
		let select1 = document.getElementsByClassName("selectPositionForApproval")[0];
		let select2 = document.getElementsByClassName("selectUsersForApproval")[0];

		const evt = new ApprovalInfo;
		select.addEventListener('change', function() {
			let id = this.value;
			if (select1.children.length > 1) {
				optionClear(select1.children);
			} 
			if (select2.children.length > 1) {
				optionClear(select2.children);
			}
			
			if (id != 0) {
				evt.drawPosition(id);
			} else {
				return;
			}

		})
		select1.addEventListener('change', function() {
			const evt = new ApprovalInfo;
			let position = this.value;
			if (position != 0) {
				evt.drawUsers(select.value, position);
			} else {
				return;
			}
		})

	}
}
function optionClear(options) {
	for (let i = 1; i < options.length; i++) {
		options[i].remove();
	}
	console.log(options.length+",지워짐")
}
function createSelectBox(select, datas) {

	let firstOption = select.children[0];

	let frag = document.createDocumentFragment();
	let options = select.children;
	if (options.length > 1) {
		for (let i = 1; i < options.length; i++) {
			options[i].remove();
		}

	}
	for (let i = 0; i < datas.length; i++) {
		let option = firstOption.cloneNode(true);
		let data = datas[i];
		option.value = data.id;
		option.innerText = data.division;
		frag.append(option);
	}
	select.appendChild(frag);
	firstOption.selected = true;
}
function createSelectBoxForPosition(select, datas) {
	let firstOption = select.children[0];

	let frag = document.createDocumentFragment();
	let options = select.children;
	if (options.length > 1) {
		for (let i = 1; i < options.length; i++) {
			options[i].remove();
		}
	}
	let result = datas[1];
	for (let i = 0; i < result.length; i++) {
		let option = firstOption.cloneNode(true);

		let data = result[i];
		console.log(data)
		option.value = data;
		option.innerText = data;
		frag.append(option);
	}
	select.appendChild(frag);
	firstOption.selected = true;
}
function createSelectBoxForUser(select, datas) {
	let firstOption = select.children[0];

	let frag = document.createDocumentFragment();
	let options = select.children;
	if (options.length > 1) {
		for (let i = 1; i < options.length; i++) {
			options[i].remove();
		}
	}
	for (let i = 0; i < datas.length; i++) {
		let option = firstOption.cloneNode(true);
		let data = datas[i];
		option.value = data.id;
		option.innerText = data.name;
		frag.append(option);
	}
	select.appendChild(frag);
	firstOption.selected = true;
}



