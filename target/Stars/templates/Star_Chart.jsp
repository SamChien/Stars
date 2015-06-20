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
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
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
            <li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li>
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

				<div class="row placeholders">
					<div class="col-xs-6 col-sm-3 placeholder">
						<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
							alt="200x200"
							src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDIwMCAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iIzBEOEZEQiIvPjxnPjx0ZXh0IHg9Ijc0LjA1NDY4NzUiIHk9IjEwMCIgc3R5bGU9ImZpbGw6I0ZGRkZGRjtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMHB0O2RvbWluYW50LWJhc2VsaW5lOmNlbnRyYWwiPjIwMHgyMDA8L3RleHQ+PC9nPjwvc3ZnPg=="
							data-holder-rendered="true">
						<h4>最受關注藝人</h4>
						<span class="text-muted">Something else</span>
					</div>
					<div class="col-xs-6 col-sm-3 placeholder">
						<img data-src="holder.js/200x200/auto/vine" class="img-responsive"
							alt="200x200"
							src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDIwMCAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iIzM5REJBQyIvPjxnPjx0ZXh0IHg9Ijc0LjA1NDY4NzUiIHk9IjEwMCIgc3R5bGU9ImZpbGw6IzFFMjkyQztmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMHB0O2RvbWluYW50LWJhc2VsaW5lOmNlbnRyYWwiPjIwMHgyMDA8L3RleHQ+PC9nPjwvc3ZnPg=="
							data-holder-rendered="true">
						<h4>最受爭議新聞</h4>
						<span class="text-muted">Something else</span>
					</div>
					<div class="col-xs-6 col-sm-3 placeholder">
						<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
							alt="200x200"
							src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDIwMCAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iIzBEOEZEQiIvPjxnPjx0ZXh0IHg9Ijc0LjA1NDY4NzUiIHk9IjEwMCIgc3R5bGU9ImZpbGw6I0ZGRkZGRjtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMHB0O2RvbWluYW50LWJhc2VsaW5lOmNlbnRyYWwiPjIwMHgyMDA8L3RleHQ+PC9nPjwvc3ZnPg=="
							data-holder-rendered="true">
						<h4>藝人分析</h4>
						<span class="text-muted">Something else</span>
					</div>

				</div>

				<h2 class="sub-header">藝人:周杰倫</h2>
				<h4>熱度走勢圖	</h4>			
				<div id="myfirstchart" style="height: 250px;"></div>
				<div>
					
				</div>
				<footer class="footer">
					<div class="container">
						<p class="text-muted">copyright &copy; 2015 陳政謙、林宜駿 知識工訪</p>
					</div>
				</footer>
			</div>
		</div>
    </div>
	

    
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="../js/bootstrap.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>
    <script>
    function request(getValue) {//抓取url參數
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
    var StarId='s00001';//request(StarId);
    new Morris.Line({
    	
    	  // ID of the element in which to draw the chart.
    	  element: 'myfirstchart',
    	  // Chart data records -- each entry in this array corresponds to a point on
    	  // the chart.
		    	   data: [
		    { dateTime: '2015-01', value: 20 },
		    { dateTime: '2015-02', value: 20 },
		    { dateTime: '2015-03', value: 10 },
		    { dateTime: '2015-04', value: 5 },
		    { dateTime: '2015-05', value: 5 },
		    { dateTime: '2015-06', value: 20 },
		    { dateTime: '2015-08', value: 0 },
		    { dateTime: '2015-09', value: 20 },
		    { dateTime: '2015-010', value: 20 },
		    { dateTime: '2015-011', value: 80 },
		    { dateTime: '2015-012', value: 60 }
		  ],
    	  // The name of the data record attribute that contains x-values.
    	  xkey: 'dateTime',
    	  // A list of names of data record attributes that contain y-values.
    	  ykeys: ['value'],
    	  // Labels for the ykeys -- will be displayed when you hover over the
    	  // chart.
    	  labels: ['Value']
    	}).on('click', function(index,row){  		
    			alert("時間:"+row.dateTime+"熱度"+row.value);
    			var dateTime=row.dateTime;
    			var hotValue=row.value;
    			location.href='Star_News_Summary.jsp?StarId='+StarId+'&dateTime='+dateTime+'&hotValue='+hotValue+'';
    		});
    </script>
  </body>
</html>