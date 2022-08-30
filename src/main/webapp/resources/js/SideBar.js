/**
 * 
 */


export function SideBar() {
	/*$(".menu-button").on("click",function() {
		var idx =$(".menu-button").index(this);
		var menuBar = $(".menu-bar").eq(idx);
		if(menuBar.hasClass("open")){
			$(".menu-bar").removeClass("open");
			console.log(idx)
		}
		
		$(".menu-bar").eq(idx).addClass("open");
		
		$('#mainContents').on("click",function(e) {
			if (!$(e.target).hasClass(".menu-bar")) { $(".menu-bar").removeClass("open"); }
		});
		
	})*/
	let sidebar = document.querySelector(".sidebar");
//	let sidebarBtn = document.querySelector(".openbtn");
	let Sidecol = document.querySelector("#SideMenu");
	let Menucol = document.querySelector(".page-content");
	var bool = true;
//	console.log(sidebarBtn);
//	sidebarBtn.addEventListener("click", function() {
//		sidebar.classList.toggle("close");
//		if (bool == true) {
//			Sidecol.classList.replace('col-1', 'col-2');
//			Menucol.classList.replace('col-11', 'col-10');
//			bool = false;
//		} else {
//			Sidecol.classList.replace('col-2', 'col-1');
//			Menucol.classList.replace('col-10', 'col-11');
//			bool = true;
//		}
//
//	});
	
	let arrow = document.querySelectorAll(".arrow");
	for (var i = 0; i < arrow.length; i++) {
		arrow[i].addEventListener("click", function(e) {
			let arrowParent = e.target.parentElement.parentElement; //selecting main parent of arrow
			arrowParent.classList.toggle("showMenu");
		});


	}
}

