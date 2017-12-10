$(document).ready(function() {
	var x_pos = 0, y_pos = 0, width = 0, height = 0;
	$('#image_preview').imgAreaSelect({
		show: true,
		onSelectEnd: function(e, o) {
			x_pos = o.x1;
			y_pos = o.y1;
			width = o.width;
			height = o.height;
		}
	})
	
	function showImage(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#image_preview').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#fileToUpload").change(function(){
        showImage(this);
    });
    
    $("#tab1").click(function(){
    	$('#image_preview').imgAreaSelect({
    		show: true,
    		onSelectEnd: function(e, o) {
    			x_pos = o.x1;
    			y_pos = o.y1;
    			width = o.width;
    			height = o.height;
    		}
    	})
    });
    $("#tab2").click(function(){
    	$('#image_preview').imgAreaSelect({
    		hide: true,
    	})
    });
    
	function imageLoading() {
		$('#waiting').html(
			"<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			"Waiting .... </b><br/> " +
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			"Server is processing your image. " +
			"<br/> " +
			"<img src= 'https://www.prizerebel.com/assets/images/progress_bar.gif'/> ");
	}

	$('#submit_form').on('submit', function(e) {
		e.preventDefault();
		imageLoading();
		var formData = new FormData();
		// Attach file
		formData.append('image', $("#fileToUpload")[0].files[0]); 
		//crop data
		formData.append('x_pos', x_pos);
		formData.append('y_pos', y_pos);
		formData.append('width', width);
		formData.append('height', height);
		$.ajax({
			url : "SearchServlet",
			method : "POST",
			data : formData,
			contentType : false,
			// cache:false,
			processData : false,
			success : function(data) {
				window.location.href = "result.html";
			}
		})
	});
	
	$(document).on('click','#remove_button', function() {
		if (confirm("Are you sure you want to remove this image?")) {
			var path = $('#remove_button').data("path");
			$.ajax({
				url : "delete.php",
				type : "POST",
				data : {
					path : path
				},
				success : function(data) {
					if (data != '') {
						$('#image_preview').html('');
					}
				}
			});
		} else {
			return false;
		}
	});
});