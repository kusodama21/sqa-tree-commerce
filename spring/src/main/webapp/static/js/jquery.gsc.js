(function($){

	$.fn.gscAddToCart = function( opt ) {

		// Defaults
		var defaults = {
			productID : shopVars.post_id,
			quantity: 1
		};
		var settings = $.extend( defaults, opt );

		return $(this).each(function(){
			$(this).live('click', function(){
				$(this).addClass('added');
			});
		});
	}

})(jQuery);

jQuery(document).ready( function($){

	$('a.addtocart').live('click', function() {
		var btn = $(this);
		var productID = btn.attr('rel');
		if( !productID ) {
			console.log('Incorrect product id');
			return false;
		}
		else {
			$.ajax({
				url: shopVars.ajax_url,
				type: 'POST',
				data: { action: 'add_to_cart', product_id: productID, quantity: 1 , nonce: shopVars.gsc_nonce },
				dataType: "json",
				beforeSend: function() {
					btn.after('<span class="ajax-loading"><img src="' + shopVars.loading_img + '" /></span>').show();
				},
				success: function(response) {
					$("#num-incart-products .num-products").text(response.numProducts);
					$("#total-incart-price .total-price").text(response.totalPrice);
					btn.addClass('added');
					btn.next('.ajax-loading').remove();
				}
			});
		}

		return false;
	});

	$('#shop-cart-form').on( 'change', 'input, textarea', function() {
		var $this = $( this ),
			$form = $("#shop-cart-form"),
			id = $this.parents( 'tr' ).find( 'input[name="pid[]"]' ).val(),
			data = $form.serialize();

		$.ajax({
			url: shopVars.ajax_url,
			type: 'POST',
			data: { action: 'update_shop_cart', data: data, item: $this.attr( 'name' ), pid: id },
			success: function(res){
				if ( 'qty[]' == $this.attr( 'name' ) )
					$this.parents( 'tr' ).find( '.row-price' ).text( res );

				$.ajax({
					url: shopVars.ajax_url,
					type: 'POST',
					data: { action: 'update_total_price' },
					dataType: "json",
					success: function(res){
						$("#num-incart-products .num-products").text(res.numProducts);
						$("#total-incart-price .total-price").text(res.totalPrice);
						$(".cart-total .num-products").text(res.numProducts);
						$(".cart-total .total-pricce").text(res.totalPrice);
					}
				});
			}
		});

	});

	$('#shop-cart-form').on( 'click', '.remove-product', function( event ) {
		event.preventDefault();

		var $this = $( this ),
			id = $( this ).data( 'id' );

		$.ajax({
			url: shopVars.ajax_url,
			type: 'POST',
			data: { action: 'delete_shop_cart_item', id: id },
			dataType: "json",
			success: function(res){
				$this.parents( 'tr' ).remove();
				$("#num-incart-products .num-products").text(res.numProducts);
				$("#total-incart-price .total-price").text(res.totalPrice);
				$(".cart-total .num-products").text(res.numProducts);
				$(".cart-total .total-pricce").text(res.totalPrice);
			}
		});
	} );

} );