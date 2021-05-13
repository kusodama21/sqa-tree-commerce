jQuery(document).ready(function($) {
	jQuery(function() {
        $(".slideShow2").jCarouselLite2RB({
            vertical:false,
            //circular: false, //không cho phép cuộn tròn
            visible:1,//số ảnh muốn hiển thị
            auto:0,//tự động chạy và tốc độ chạy || auto:0 đứng yên
            speed:800,
            btnNext: ".next2",//class thẻ a next
            btnPrev: ".prev2"//class thẻ a previous
        });
    });    
});

jQuery(document).ready(function($) {
	jQuery(function() {
		$(".slideShowproduct").jCarouselLite({
			vertical:true,
			//circular: false, //không cho phép cuộn tròn
			visible:4,//số ảnh muốn hiển thị
			auto:3000,//tự động chạy và tốc độ chạy || auto:0 đứng yên
			speed:2000,
		});
    });    
});

jQuery(document).ready( function($){
	$("#flexible-block-0 .main-posts").slick({
		dots: false,
		infinite: true,
		arrows: true,
		slidesToShow: 4,
		slidesToScroll: 4,
		rows: 2
		/*responsive: [
			{
			  breakpoint: 1171,
			  settings: {
				slidesToShow: 3,
				slidesToScroll: 3
			  }
			},
			{
			  breakpoint: 961,
			  settings: {
				slidesToShow: 4,
				slidesToScroll: 4
			  }
			},
			{
			  breakpoint: 890,
			  settings: {
				slidesToShow: 3,
				slidesToScroll: 3
			  }
			},
			{
			  breakpoint: 668,
			  settings: {
				slidesToShow: 2,
				slidesToScroll: 2
			  }
			},
			{
			  breakpoint: 460,
			  settings: {
				slidesToShow: 1,
				slidesToScroll: 1
			  }
			}
		]*/
	});
});

jQuery(document).ready( function($){
	for( i = 1; i < 15; i++ )
	{
		$(".flexible-block:nth-child("+(i+1)+") .main-posts").slick({
			dots: false,
			infinite: true,
			arrows: true,
			slidesToShow: 4,
			slidesToScroll: 4
			/*responsive: [
			{
			  breakpoint: 1171,
			  settings: {
				slidesToShow: 3,
				slidesToScroll: 3,
				infinite: true
			  }
			},
			{
			  breakpoint: 961,
			  settings: {
				slidesToShow: 4,
				slidesToScroll: 4
			  }
			},
			{
			  breakpoint: 890,
			  settings: {
				slidesToShow: 3,
				slidesToScroll: 3,
				infinite: true
			  }
			},
			{
			  breakpoint: 668,
			  settings: {
				slidesToShow: 2,
				slidesToScroll: 2,
				infinite: true
			  }
			},
			{
			  breakpoint: 460,
			  settings: {
				slidesToShow: 1,
				slidesToScroll: 1,
				infinite: true
			  }
			}
		  ]*/
		});
	}
});