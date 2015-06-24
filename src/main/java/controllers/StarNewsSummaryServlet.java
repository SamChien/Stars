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
import models.Table_artists;
import models.Table_artists_news;
import models.Table_keywords;

@WebServlet("/StarNewsSummaryServlet")
public class StarNewsSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String artistId = request.getParameter("artist_id");
		String heatId = request.getParameter("heat_id");
		String dateTime = request.getParameter("date_time");
		String[] dateTimeArr = dateTime.split("-");
		String year = dateTimeArr[0];
		String month = dateTimeArr[1];
		String name = null;
		MysqlDB mysqlDb = new MysqlDB();
		List<String> keywordsList = new ArrayList<String>();
		List<Table_artists_news> artistsNewsList = new ArrayList<Table_artists_news>();
	
		try {
			String[] cols;
			String table;
			String where;
			String orderBy;
			ResultSet result;

			cols = new String[] {"*"};
			table = Table_keywords.TABLE_NAME;
			where = Table_keywords.COL_HEAT_ID + " = " + heatId;
			orderBy = Table_keywords.COL_TF_IDF;
			result = mysqlDb.select(cols, table, false, where, orderBy);
			while (result.next()) {
				keywordsList.add(result.getString(Table_keywords.COL_WORD));
			}

			cols = new String[] {"*"};
			table = Table_artists_news.TABLE_NAME + " an LEFT JOIN " + Table_artists.TABLE_NAME + " a ON an." + Table_artists_news.COL_ARTIST_ID + " = a." + Table_artists.COL_ID;
			where = Table_artists_news.COL_ARTIST_ID + " = " + artistId + " AND YEAR(" + Table_artists_news.COL_POST_TIME + ") = " + year + " AND MONTH(" + Table_artists_news.COL_POST_TIME + ") = " + month;
			orderBy = Table_artists_news.COL_POST_TIME;
			result = mysqlDb.select(cols, table, false, where, orderBy);

			while (result.next()) {
				Table_artists_news artistsNews = new Table_artists_news(null,
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

				name = result.getString(Table_artists.COL_NAME);
				artistsNewsList.add(artistsNews);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}

		request.setAttribute("artistsNewsList", artistsNewsList);
		request.setAttribute("keywordsList", keywordsList);
		request.setAttribute("artist_Name", name);
		request.setAttribute("dateTime", dateTime);		
		request.getRequestDispatcher("/templates/Star_News_Summary.jsp").forward(request, response);
	}

}
