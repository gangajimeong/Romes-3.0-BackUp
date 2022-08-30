
import WorkOrder from "./WorkOrder.js";


document.addEventListener("DOMContentLoaded", function() {
	work();
});


	
function work(){
		const evt = new WorkOrder;
		evt.connectSse();
		evt.evt();
		evt.setmodal();
	}
	
	
