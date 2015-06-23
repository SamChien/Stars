<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>明星紅不紅</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet">
<link href="./css/dashboard.css" rel="stylesheet">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">知識工程期末專題</a>
			</div>

			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="./index">首頁</a></li>
					<li><a href="#about">開發團隊</a></li>
					<li><a href="#contact">後台管理</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->

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
				<h1 class="page-header">明星紅不紅</h1>
					
				<c:catch var="artist_Name" >
						<h2 class="sub-header">藝人:${artist_Name}</h2>
				</c:catch>
				
				<h4>熱度走勢圖</h4>
				<div id="myfirstchart" style="height: 250px;"></div>
				<div></div>
				<footer class="footer">
					<div class="container">
						<p class="text-muted">copyright &copy; 2015 陳政謙、林宜駿 知識工訪</p>
					</div>
				</footer>
			</div>
		</div>
	</div>

	<div id="div_heat_data" style="display: none;">
		<c:forEach var="artistsHeat" items="${requestScope.artistsHeatList}">
			<div data-heat="${artistsHeat.getHeat()}" data-heat_date="${artistsHeat.getHeatDate()}"></div>
		</c:forEach>
	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="./js/bootstrap.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>
	<script>
		function request(getValue) { //抓取url參數
			var url = window.location.toString();
			var str = "";
			var str_value = "";

			if (url.indexOf("?") != -1) {
				var ary = url.split("?")[1].split("&");

				for (var i in ary) {
					str = ary[i].split("=")[0];
					if (str == getValue) {//取得想要得參數
						str_value = decodeURI(ary[i].split("=")[1]);
					}
				}
			}
			return str_value;
		}

		var artist_id = request("artist_id");
		var artist_Name=request("artist_Name");
		var dataArr = [];

		$("#div_heat_data").children().each(function () {
			var obj = {dateTime: $(this).data("heat_date"), value: $(this).data("heat")}; 
			dataArr.push(obj);
		});

		new Morris.Line({
			// ID of the element in which to draw the chart.
			element : 'myfirstchart',
			// Chart data records -- each entry in this array corresponds to a point on
			// the chart.
			data : dataArr,
			// The name of the data record attribute that contains x-values.
			xkey : 'dateTime',
			// A list of names of data record attributes that contain y-values.
			ykeys : [ 'value' ],
			// Labels for the ykeys -- will be displayed when you hover over the
			// chart.
			labels : [ 'Value' ]
		}).on('click', function(index, row) {
			//alert("時間:" + row.dateTime + "熱度" + row.value);
			var dateTime = row.dateTime;
			var hotValue = row.value;
			location.href = './StarNewsSummaryServlet?artist_id='+artist_id+'&dateTime=' + dateTime+'&artist_Name='+artist_Name;
		});
	</script>
</body>
</html>