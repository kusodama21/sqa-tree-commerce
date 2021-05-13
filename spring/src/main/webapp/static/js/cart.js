const ar = document.getElementsByClassName("linkWrappedByForm");

for (let i = 0; i < ar.length; ++i) {
	ar[i].addEventListener('click', (event) => {
		const link = event.currentTarget.dataset.link;
		const form = document.getElementById("remove-cart-item-form");
		
		form.action = link;
		console.log(form.action);
		form.submit();
	});
}





function parseSubmit() {
	const holder = {};
	const items = document.getElementsByClassName("cart-item-input");
	
	for (let i = 0; i < items.length; ++i) {
		holder[items[i].id] = Number(items[i].value);
	}
	
	const updateString = JSON.stringify(holder);
	const hiddenInput = document.getElementById("update-string");
	hiddenInput.value = updateString;
	document.getElementById("shop-cart-form").submit();
}