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
import models.Summary;
import models.Table_artists;
import models.Table_artists_news_comments;
import models.Table_keywords;

@WebServlet("/star_news_summary")
public class StarNewsSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String artistId = request.getParameter("artist_id");
		String heatId = request.getParameter("heat_id");
		String dateTime = request.getParameter("date_time");
		String type = request.getParameter("type");
		String[] dateTimeArr = dateTime.split("-");
		String year = dateTimeArr[0];
		String month = dateTimeArr[1];
		String name = null;
		MysqlDB mysqlDb = new MysqlDB();
		List<String> keywordsList = new ArrayList<String>();
		List<Table_artists_news_comments> artistsNewsList = new ArrayList<Table_artists_news_comments>();
	
		try {
			String[] cols;
			String table;
			String where;
			String orderBy;
			ResultSet result;

			cols = new String[] {"*"};
			table = Table_keywords.TABLE_NAME;
			where = Table_keywords.COL_HEAT_ID + " = " + heatId;
			orderBy = Table_keywords.COL_TF_IDF + " DESC";
			result = mysqlDb.select(cols, table, false, where, orderBy);
			while (result.next()) {
				keywordsList.add(result.getString(Table_keywords.COL_WORD));
			}

			cols = new String[] {"*"};
			table =
					Table_artists_news_comments.TABLE_NAME + " an" +
					" LEFT JOIN " + Table_artists.TABLE_NAME + " a" +
					" ON an." + Table_artists_news_comments.COL_ARTIST_ID + " = a." + Table_artists.COL_ID;
			where =
					Table_artists_news_comments.COL_ARTIST_ID + " = " + artistId +
					" AND YEAR(" + Table_artists_news_comments.COL_POST_TIME + ") = " + year +
					" AND MONTH(" + Table_artists_news_comments.COL_POST_TIME + ") = " + month;

			if (type.equals("news")) {
				where += " AND " + Table_artists_news_comments.COL_TYPE + " = " + Table_artists_news_comments.TYPE_NEWS;
				orderBy = Table_artists_news_comments.COL_POST_TIME;
			} else if (type.equals("positive")) {
				where += " AND " + Table_artists_news_comments.COL_TYPE + " = " + Table_artists_news_comments.TYPE_COMMENTS;
				orderBy = Table_artists_news_comments.COL_POSITIVE_SCORE + " DESC";
			} else if (type.equals("negative")) {
				where += " AND " + Table_artists_news_comments.COL_TYPE + " = " + Table_artists_news_comments.TYPE_COMMENTS;
				orderBy = Table_artists_news_comments.COL_NEGATIVE_SCORE + " DESC";
			}
			result = mysqlDb.select(cols, table, false, where, orderBy);
			
			while (result.next()) {			
				String summary=new Summary().getSummary(result.getString(Table_artists_news_comments.COL_CONTENT),keywordsList,4);
				Table_artists_news_comments artistsNews = new Table_artists_news_comments(null,
						result.getString(Table_artists_news_comments.COL_S_NAME),
						result.getString(Table_artists_news_comments.COL_S_AREA_NAME),
						result.getString(Table_artists_news_comments.COL_TITLE),
						result.getString(Table_artists_news_comments.COL_AUTHOR),
						result.getString(Table_artists_news_comments.COL_PAGE_URL),
						result.getString(Table_artists_news_comments.COL_POST_TIME),
						result.getString(Table_artists_news_comments.COL_CONTENT),
						result.getInt(Table_artists_news_comments.COL_COMMENT_COUNT),
						result.getDouble(Table_artists_news_comments.COL_POSITIVE_SCORE),
						result.getDouble(Table_artists_news_comments.COL_NEGATIVE_SCORE),
						Table_artists_news_comments.TYPE_NEWS,
						result.getInt(Table_artists_news_comments.COL_ARTIST_ID),
						summary);

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
