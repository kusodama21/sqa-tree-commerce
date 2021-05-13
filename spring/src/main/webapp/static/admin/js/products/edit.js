// Get image action
const imageAction = document.getElementById("imageAction");

// Get input file area
const imgSelectBox = document.getElementById("image-select");

// Get input file
const newImage = document.getElementById("newImage");

// Set event listener for imageAction
imageAction.addEventListener("change", (event) => {
	switch(event.currentTarget.value) {
        case '0':
            newImage.disabled = true;
            break;
        case '1':
            newImage.disabled = false;
            break;
	}
});

document.getElementById("form").onsubmit = (event) => {
	 // Remove the disabled option in categoryId select
	 document.getElementById("categoryId-null").disabled = false;

	 // Return true to submit
	 return true;
}