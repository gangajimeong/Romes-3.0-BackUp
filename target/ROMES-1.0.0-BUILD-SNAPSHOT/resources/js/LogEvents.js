/**
 * 
 */
export default class LogEvents {
	initBtnEvt() {
		var userSelecter = document.getElementById('logUserSearch');
		var categorySelecter = document.getElementById('logCategorySearch');
		var preDate = document.getElementById('logPreDate');
		var lastDate = document.getElementById('logLastDate');
		var searchBtn = document.getElementById('logSearch');
		var tableBody = document.getElementById('loghistory-table-Contents');
		searchBtn.addEventListener('click', function() {
			var pre = new Date(preDate.value);
			var last = new Date(lastDate.value);
			if (last < pre) {
				let year = last.getFullYear();
				let month = last.getMonth()+1;
				let date = last.getDate();
				alert('이전날짜를 ' + year + '년' + month + '월' + date + '일 이전으로 설정해 주세요.');
			} else if (isNaN(last.getTime()) || isNaN(pre.getTime())) {
				alert('날짜 범위 설정을 모두 완료해 주세요')
			} else {
				let data = { preTime: preDate.value, lastTime: lastDate.value, userid: getSelectedValue(userSelecter),category:getSelectedValue(categorySelecter) };
				axios.get('logData', { params: data}).then(response => {
					let result = response.data;
					delectAllRow();
					if(result.data.length == 0)
						alert('해당 기간안의 데이터가 없습니다.');
					else{
						for(var i = 0; i < result.data.length;i++){
							let tr = document.createElement('tr');
							tr.appendChild(createTdElement(result.data[i].id,'',false));
							tr.appendChild(createTdElement(String(i+1),'5%',true));
							tr.appendChild(createTdElement(result.data[i].user,'10%',true));
							tr.appendChild(createTdElement(result.data[i].loginId,'10%',true));
							tr.appendChild(createTdElement(result.data[i].division,'10%',true));
							tr.appendChild(createTdElement(result.data[i].category,'15%',true));
							tr.appendChild(createTdElement(result.data[i].action,'35%',true));
							tr.appendChild(createTdElement(result.data[i].time,'15%',true));
							tableBody.appendChild(tr);
						}
						
					}
					
					
				}).catch(e => {
					console.log(e);
					alert('오류 발생 \n' + e)
				});
			}
		});
	}
}
function createTdElement(text,width,display){
	let tr = document.createElement('td');
	if(!display)
		tr.style.display='none';
	else{
		tr.width=width;
	}
	tr.innerText=text;
	return tr;
}
function getSelectedValue(select) {
	return select.options[select.selectedIndex].value;
}
function delectAllRow(){
	var tableBody = document.getElementById('loghistory-table-Contents');
	var len = tableBody.children.length;
	for(var i = 0; i < len; i++){
		tableBody.deleteRow(-1);
	}
}