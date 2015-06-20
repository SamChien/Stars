<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>明星紅不紅</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/dashboard.css" rel="stylesheet">
  	</head>
  <body>
     <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
         <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">知識工程期末專題</a>
        </div>
        
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">首頁</a></li>
            <li><a href="#about">開發團隊</a></li>
            <li><a href="#contact">後台管理</a></li>      
          </ul>
        </div><!--/.nav-collapse -->
        
      </div>
    </nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">Overview <span
							class="sr-only">(current)</span></a></li>
					<li><a href="#">Reports</a></li>
					<li><a href="#">Analytics</a></li>
					<li><a href="#">Export</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="">Nav item</a></li>
					<li><a href="">Nav item again</a></li>
					<li><a href="">One more nav</a></li>
					<li><a href="">Another nav item</a></li>
					<li><a href="">More navigation</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="">Nav item again</a></li>
					<li><a href="">One more nav</a></li>
					<li><a href="">Another nav item</a></li>
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">爭議新聞摘要</h1>
				<h2 class="sub-header">藝人:周杰倫</h2>
				<!-- Marketing Icons Section -->
				<div id="news_summary_list" class="row">
				
					
					
				</div>
				<!-- /.row -->
				<footer class="footer">
					<div class="container">
						<p class="text-muted">copyright &copy; 2015 陳政謙、林宜駿 知識工訪</p>
					</div>
				</footer>
			</div><!-- /.col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main -->
			
		</div>
	</div>




	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="../js/bootstrap.js"></script>
	<script>
		function request(getValue) {//抓取url參數
			var url = window.location.toString();
			var str = "";
			var str_value = "";
			if (url.indexOf("?") != -1) {
				var ary = url.split("?")[1].split("&");
				for ( var i in ary) {
					str = ary[i].split("=")[0];
					if (str == getValue) {//取得想要得參數
						str_value = decodeURI(ary[i].split("=")[1]);
					}
				}
			}

			return str_value;
		}

		jQuery(document).ready(function() {
			/* alert(request("StarId"));
			alert(request("hotValue"));
			alert(request("dateTime")); */
			showNewsSummary();
		});
		
		function showNewsSummary() {
			for (var index = 0; index < 9; index++) {
				$('#news_summary_list')
						.append(
								'<div class="col-md-4">'
										+ '<div class="panel panel-default">'
										+ '<div class="panel-heading">'
										+ '<h4><i class="fa fa-fw fa-check"></i>News_title</h4>'
										+ '</div>'
										+ '<div class="panel-body">'
										+ '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.'
										+ 'Itaque, optio corporis quae nulla aspernatur in alias at'
										+ 'numquam rerum ea excepturi expedita tenetur assumenda'
										+ 'voluptatibus eveniet incidunt dicta nostrum quod?</p>'
										+ '<a href="#" class="btn btn-default">Read More</a>'
										+ '</div>' + '</div>' + '</div>');
			}
		}
	</script>


</body>
</html>