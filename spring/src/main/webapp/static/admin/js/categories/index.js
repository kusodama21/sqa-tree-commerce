const ar = document.getElementsByClassName("linkWrappedByForm");

for (let i = 0; i < ar.length; ++i) {
	ar[i].addEventListener('click', (event) => {
		event.currentTarget.parentNode.submit();
	});
}