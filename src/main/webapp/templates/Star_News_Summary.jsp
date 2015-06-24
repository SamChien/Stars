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
            <li class="active"><a href="./index">首頁</a></li>
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
				<c:catch var="dateTime" >
				<h1 class="page-header">時間點:
					${dateTime}
				</h1>
				</c:catch>
				
				
				<c:catch var="artist_Name" >
						<h2 class="sub-header">藝人:${artist_Name}</h2>
				</c:catch>
				<!-- Marketing Icons Section -->
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>熱門關鍵字</h4>
						</div>
						<div class="panel-body">
							<c:forEach var="keyword" items="${requestScope.keywordsList}">
								<button style="margin-bottom: 30px;" type="button" class="btn btn-danger">${keyword}</button>
							</c:forEach>
						</div>
					</div>
				</div>
				<div id="news_summary_list">
					<c:forEach var="artistsNewsList" items="${requestScope.artistsNewsList}" varStatus="status" >
						<div class="col-md-6">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 >${artistsNewsList.getTitle()}</h4>
									<p>${artistsNewsList.getPostTime()}</p>
								</div>
								<div class="panel-body">															
									<p>${artistsNewsList.getSummary()}</p>														
									<button type="button" class="btn btn-default" data-toggle="modal" data-target="#${status.count}">全文閱覽</button>
								</div>
							</div>
						</div>
						<!-- Modal -->
						<div class="modal fade" id="${status.count}" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">${artistsNewsList.getTitle()}</h4>
										<h5>${artistsNewsList.getsName()} ${artistsNewsList.getsAreaName()}</h5>
									</div>
									<div class="modal-body">${artistsNewsList.getContent()}</div>
									<div class="modal-footer">				
										<button type="button" class="btn btn-primary"
											data-dismiss="modal">Close</button>										
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
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
    <script src="./js/bootstrap.js"></script>
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
			
		});
		
		
	</script>


</body>
</html>