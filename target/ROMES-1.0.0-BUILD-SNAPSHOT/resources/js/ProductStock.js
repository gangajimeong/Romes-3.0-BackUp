/**
 * 
 */
export default class ProductStock {
	initTableEvt() {
		var table = document.getElementById('product-stock-table-Contents');
		var trs = table.children;
		for (var i = 0; i < trs.length; i++) {
			var tr = trs[i];

			if (tr.getAttribute('name') == 'contents') {
				tr.addEventListener('click', function() {
					initTableDisplay();
					var tds = this.getElementsByTagName('td');
					var id = tds[0].innerText;
					var contents = document.getElementsByName(id);
					for (var j = 0; j < contents.length; j++) {

						if (contents[j].style.display =='none')
							contents[j].style.display = '';
						else
							contents[j].style.display = 'none';
					}
				});
			}

		}
	}
	setFilterEvt(){
		var typeFilter = document.getElementById('productstock-type-select');
		var filterText = document.getElementById('productstock-searchText');
		var filterIdx = document.getElementById('productstock-select');
		typeFilter.addEventListener('change',function(){
			repaintTable(typeFilter,filterIdx,filterText);
		})
		filterText.addEventListener('input',function(){
			repaintTable(typeFilter,filterIdx,filterText);
		})
	}
	changeColor(){
		const contents = document.getElementById("product-stock-table-Contents").children;
		for (let i = 0; i < contents.length; i++) {
			if (contents[i].children[6].innerText <= '0') {
				contents[i].children[6].style.color='red';
			}
		}
	}
	
}
function initTableDisplay(){
	var trs = document.getElementById('product-stock-table-Contents').children;
	for(var i = 0; i < trs.length;i++){
		if(trs[i].getAttribute('name') != 'contents'){
			trs[i].style.display='none';
		}
	}
}
function repaintTable(type,idx,text){
	var trs = document.getElementById('product-stock-table-Contents').children;
	var types = '';
	var typesValue = getSelectedValue(type);
	if(typesValue == 1){
		types = '완제품';
	}else if(typesValue == 2){
		types = '원부자재'
	}
	
	var index = 1;
	for(var i = 0; i< trs.length;i++){
		if(trs[i].getAttribute('name') == 'contents'){
			var tds = trs[i].children;
			if(tds[2].innerText.includes(types)){
				if(getSelectedValue(idx) > 0 ){
					
					if(tds[getSelectedValue(idx)].innerText.includes(text.value)){
						trs[i].style.display='';
						tds[1].innerText = index;
						index++;

					}else{
						trs[i].style.display='none';
					}
				}else{
					var locations = document.getElementsByName(tds[0].innerText);
					var isVisible = false;
					for(var j = 0; j < locations.length;j++){
						if(locations[j].children[1].innerText.includes(text.value)){
							isVisible = true;
							break;
						}
					}
					if(isVisible){
						trs[i].style.display='';
						tds[1].innerText = index;
						index++;

					}else
						trs[i].style.display='none';
				}			
			}else{
				trs[i].style.display='none';
			}
			
		}else{
			trs[i].style.display='none';		
		}
	}
}
function getSelectedValue(select) {
	return select.options[select.selectedIndex].value;
}