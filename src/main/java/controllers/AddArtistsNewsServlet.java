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
import models.Table_month_doc_num;
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

			mysqlDb.insert(Table_artists.TABLE_NAME, new String[] {Table_artists.COL_NAME}, new Object[] {name}, true);
			result = mysqlDb.select(new String[] {Table_artists.COL_ID}, Table_artists.TABLE_NAME, false, Table_artists.COL_NAME + " = '" + name + "'", null);
			result.next();
			artistId = result.getInt(Table_artists.COL_ID);

			result = mysqlDb.select(new String[] {"*"}, Table_ts_page_content.TABLE_NAME, false, Table_ts_page_content.COL_CONTENT + " like '%" + name + "%'", null);
			while (result.next()) {
				mysqlDb.insert(Table_artists_news.TABLE_NAME, new String[] {
						Table_artists_news.COL_ID,
						Table_artists_news.COL_S_NAME,
						Table_artists_news.COL_S_AREA_NAME,
						Table_artists_news.COL_TITLE,
						Table_artists_news.COL_AUTHOR,
						Table_artists_news.COL_PAGE_URL,
						Table_artists_news.COL_POST_TIME,
						Table_artists_news.COL_CONTENT,
						Table_artists_news.COL_COMMENT_COUNT,
						Table_artists_news.COL_POSITIVE_SCORE,
						Table_artists_news.COL_NEGATIVE_SCORE,
						Table_artists_news.COL_ARTIST_ID}, new Object[] {
						result.getString(Table_ts_page_content.COL_ID),
						result.getString(Table_ts_page_content.COL_S_NAME),
						result.getString(Table_ts_page_content.COL_S_AREA_NAME),
						result.getString(Table_ts_page_content.COL_TITLE),
						result.getString(Table_ts_page_content.COL_AUTHOR),
						result.getString(Table_ts_page_content.COL_PAGE_URL),
						result.getString(Table_ts_page_content.COL_POST_TIME),
						result.getString(Table_ts_page_content.COL_CONTENT),
						result.getInt(Table_ts_page_content.COL_COMMENT_COUNT),
						result.getDouble(Table_ts_page_content.COL_POSITIVE_SCORE),
						result.getDouble(Table_ts_page_content.COL_NEGATIVE_SCORE),
						artistId}, true);
			}

			result = mysqlDb.select(new String[] {"YEAR(" + Table_artists_news.COL_POST_TIME + ")", "MONTH(" + Table_artists_news.COL_POST_TIME + ")"}, Table_artists_news.TABLE_NAME, true, Table_artists_news.COL_ARTIST_ID + " = " + artistId, Table_artists_news.COL_POST_TIME);
			while (result.next()) {
				String year = result.getString(1);
				String month = result.getString(2);
				ResultSet innerResult;
				int starNewsCount, totalNewsCount;
				double heat;

				innerResult = mysqlDb.select(new String[] {"COUNT(" + Table_artists_news.COL_ID + ")"}, Table_artists_news.TABLE_NAME, false, "YEAR(" + Table_artists_news.COL_POST_TIME + ") = " + year + " AND MONTH(" + Table_artists_news.COL_POST_TIME + ") = " + month + " AND " + Table_artists_news.COL_ARTIST_ID + " = " + artistId, null);
				innerResult.next();
				starNewsCount = innerResult.getInt(1);

				innerResult = mysqlDb.select(new String[] {Table_month_doc_num.COL_DOC_NUM}, Table_month_doc_num.TABLE_NAME, false, "YEAR(" + Table_month_doc_num.COL_MONTH + ") = " + year + " AND MONTH(" + Table_month_doc_num.COL_MONTH + ") = " + month, null);
				innerResult.next();
				totalNewsCount = innerResult.getInt(1);

				heat = starNewsCount / (double) totalNewsCount;
				mysqlDb.insert(Table_artists_heat.TABLE_NAME, new String[] {Table_artists_heat.COL_HEAT, Table_artists_heat.COL_HEAT_DATE, Table_artists_heat.COL_ARTIST_ID}, new Object[] {heat, year + "-" + month + "-00", artistId}, true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}
	}
}