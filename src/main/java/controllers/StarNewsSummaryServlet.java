package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.MysqlDB;
import models.Table_artists_news;

@WebServlet("/StarNewsSummaryServlet")
public class StarNewsSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int artist_id = Integer.parseInt(request.getParameter("artist_id")) ;
		String dateTime = request.getParameter("dateTime");
		String artist_Name = request.getParameter("artist_Name");
		MysqlDB mysqlDb = new MysqlDB();
		
		List<Table_artists_news> artistsNewsList = new ArrayList<Table_artists_news>();
	
		try {
			ResultSet result = mysqlDb
					.select(new String[] { "*" },
							Table_artists_news.TABLE_NAME, false,
							Table_artists_news.COL_ARTIST_ID + " = " + artist_id
									+ " AND "
									+ Table_artists_news.COL_POST_TIME
									+ " LIKE '" + dateTime + "%'",
							Table_artists_news.COL_POST_TIME);

			while (result.next()) {
				Table_artists_news artistsNews = new Table_artists_news(
						result.getString(Table_artists_news.COL_ID),
						result.getString(Table_artists_news.COL_S_NAME),
						result.getString(Table_artists_news.COL_S_AREA_NAME),
						result.getString(Table_artists_news.COL_TITLE),
						result.getString(Table_artists_news.COL_AUTHOR),
						result.getString(Table_artists_news.COL_PAGE_URL),
						result.getString(Table_artists_news.COL_POST_TIME),
						result.getString(Table_artists_news.COL_CONTENT),
						result.getInt(Table_artists_news.COL_COMMENT_COUNT),
						result.getDouble(Table_artists_news.COL_POSITIVE_SCORE),
						result.getDouble(Table_artists_news.COL_NEGATIVE_SCORE),
						result.getInt(Table_artists_news.COL_ARTIST_ID));

				artistsNewsList.add(artistsNews);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}

		request.setAttribute("artistsNewsList", artistsNewsList);
		request.setAttribute("artist_Name", artist_Name);
		request.setAttribute("dateTime", dateTime);		
		request.getRequestDispatcher("/templates/Star_News_Summary.jsp").forward(request, response);
	}

}
