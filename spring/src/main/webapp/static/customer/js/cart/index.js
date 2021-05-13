// Get the form
const form = document.getElementById("shop-cart-form");

// Get the hidden input updateInfo
const updateInfo = document.getElementById("updateInfo");

// Get the submit link
const submitLink = document.getElementById("submit-link");

// Add event listener to submit link
submitLink.addEventListener("click", () => {

	// Initialize items object
	const items = {};

	// Get all cart item with class name
	const inputs = document.getElementsByClassName("item");
	
	// 

	// For each the inputs
	for (let i = 0; i < inputs.length; ++i) {
		items[inputs[i].dataset.productid] = inputs[i].value;
	}

	// Append this into the update Info
	updateInfo.value = JSON.stringify(items);

	// Submit the form
	form.submit();
});