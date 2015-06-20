package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.MysqlDB;
import models.Table_artists;
import models.Table_artists_heat;
import models.Table_artists_news;
import models.Table_ts_page_content;

@WebServlet("/add_artists_news")
public class AddArtistsNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/templates/add_artists_news.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		MysqlDB mysqlDb = new MysqlDB();

		try {
			ResultSet result;
			int artistId;

			mysqlDb.insert(Table_artists.TABLE_NAME, new String[] {Table_artists.NAME}, new Object[] {name}, true);
			result = mysqlDb.select(new String[] {Table_artists.ID}, Table_artists.TABLE_NAME, false, Table_artists.NAME + " = '" + name + "'", null);
			result.next();
			artistId = result.getInt(Table_artists.ID);

			result = mysqlDb.select(new String[] {"*"}, Table_ts_page_content.TABLE_NAME, false, Table_ts_page_content.CONTENT + " like '%" + name + "%'", null);
			while (result.next()) {
				mysqlDb.insert(Table_artists_news.TABLE_NAME, new String[] {
						Table_artists_news.ID,
						Table_artists_news.S_NAME,
						Table_artists_news.S_AREA_NAME,
						Table_artists_news.TITLE,
						Table_artists_news.AUTHOR,
						Table_artists_news.PAGE_URL,
						Table_artists_news.POST_TIME,
						Table_artists_news.CONTENT,
						Table_artists_news.COMMENT_COUNT,
						Table_artists_news.POSITIVE_SCORE,
						Table_artists_news.NEGATIVE_SCORE,
						Table_artists_news.ARTIST_ID}, new Object[] {
						result.getString(Table_ts_page_content.ID),
						result.getString(Table_ts_page_content.S_NAME),
						result.getString(Table_ts_page_content.S_AREA_NAME),
						result.getString(Table_ts_page_content.TITLE),
						result.getString(Table_ts_page_content.AUTHOR),
						result.getString(Table_ts_page_content.PAGE_URL),
						result.getString(Table_ts_page_content.POST_TIME),
						result.getString(Table_ts_page_content.CONTENT),
						result.getInt(Table_ts_page_content.COMMENT_COUNT),
						result.getDouble(Table_ts_page_content.POSITIVE_SCORE),
						result.getDouble(Table_ts_page_content.NEGATIVE_SCORE),
						artistId}, true);
			}

			result = mysqlDb.select(new String[] {"YEAR(" + Table_artists_news.POST_TIME + ")", "MONTH(" + Table_artists_news.POST_TIME + ")"}, Table_artists_news.TABLE_NAME, true, Table_artists_news.ARTIST_ID + " = " + artistId, Table_artists_news.POST_TIME);
			while (result.next()) {
				String year = result.getString(1);
				String month = result.getString(2);
				ResultSet innerResult;
				int starNewsCount, totalNewsCount;
				double heat;

				innerResult = mysqlDb.select(new String[] {"COUNT(" + Table_artists_news.ID + ")"}, Table_artists_news.TABLE_NAME, false, "YEAR(" + Table_artists_news.POST_TIME + ") = " + year + " AND MONTH(" + Table_artists_news.POST_TIME + ") = " + month + " AND " + Table_artists_news.ARTIST_ID + " = " + artistId, null);
				innerResult.next();
				starNewsCount = innerResult.getInt(1);

				innerResult = mysqlDb.select(new String[] {"COUNT(" + Table_ts_page_content.ID + ")"}, Table_ts_page_content.TABLE_NAME, false, "YEAR(" + Table_artists_news.POST_TIME + ") = " + year + " AND MONTH(" + Table_artists_news.POST_TIME + ") = " + month, null);
				innerResult.next();
				totalNewsCount = innerResult.getInt(1);

				heat = starNewsCount / (double) totalNewsCount;
				mysqlDb.insert(Table_artists_heat.TABLE_NAME, new String[] {Table_artists_heat.HEAT, Table_artists_heat.HEAT_DATE, Table_artists_heat.ARTISTS_ID}, new Object[] {heat, year + "-" + month + "-00", artistId}, true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}
	}
}