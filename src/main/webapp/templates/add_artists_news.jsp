<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>建立與明星相關的新聞</title>
<link href="./css/bootstrap.min.css" rel="stylesheet">
<style>
body{

background:#444444;
}
</style>
</head>
<body>
	<div class="row">
		<div class="col-lg-6" style="float: none; margin: 0 auto; margin-top: 30px;">
			<div class="input-group">
				<input id="input_name" type="text" class="form-control" placeholder="請輸入明星名稱">
				<span class="input-group-btn">
					<button id="button_create_news" class="btn btn-default" type="button">建立相關新聞</button>
				</span>
			</div>
		</div>
	</div>

	<div id="div_progress" class="row" style="display: none;">
		<div class="col-lg-6" style="float: none; margin: 0 auto; margin-top: 30px;">
			<div class="progress">
				<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">正在建立新聞...</div>
			</div>
		</div>
	</div>

	<div id="div_create_complete" class="row" style="display: none;">
		<div class="col-lg-6" style="float: none; margin: 0 auto; margin-top: 30px;">
			<div class="alert alert-success" role="alert">建立完成</div>
		</div>
	</div>
		<img src="images/b1.jpg" class="img-circle" width="400" height="500" />
		<img src="images/b2.jpg" class="img-circle" width="400" height="500" />
		<img src="images/b3.jpg" class="img-circle" width="300" height="500"/>
		<img src="images/b4.JPG" class="img-circle" width="400" height="500"/>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="./js/bootstrap.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#button_create_news").click(function() {
				$(this).button("loading");
				$("#div_create_complete").css("display", "none");
				$("#div_progress").css("display", "block");

				$.ajax({
					url : "./add_artists_news",
					type : "post",
					data : {
						name : $(input_name).val()
					},
					success : function(respMsg) {
						$("#button_create_news").button("reset");
						$("#div_progress").css("display", "none");
						$("#div_create_complete").css("display", "block");
					}
				});
			});
		});
	</script>
</body>
</html>
