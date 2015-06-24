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

import models.Keywords;
import models.MysqlDB;
import models.Table_artists;
import models.Table_artists_heat;
import models.Table_artists_news;
import models.Table_keywords;
import models.Table_month_news_num;
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
			String table;
			String[] cols;
			Object[] values;
			String where;
			String orderBy;
			int artistId;

			table = Table_artists.TABLE_NAME;
			cols = new String[] {Table_artists.COL_NAME};
			values = new Object[] {name};
			mysqlDb.insert(table, cols, values, true);

			cols = new String[] {Table_artists.COL_ID};
			table = Table_artists.TABLE_NAME;
			where = Table_artists.COL_NAME + " = '" + name + "'";
			result = mysqlDb.select(cols, table, false, where, null);
			result.next();
			artistId = result.getInt(Table_artists.COL_ID);

			cols = new String[] {"*"};
			table = Table_ts_page_content.TABLE_NAME;
			where = Table_ts_page_content.COL_ID + " like '%N01' and " + Table_ts_page_content.COL_CONTENT + " like '%" + name + "%'";
			result = mysqlDb.select(cols, table, false, where, null);
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

			cols = new String[] {"YEAR(" + Table_artists_news.COL_POST_TIME + ")", "MONTH(" + Table_artists_news.COL_POST_TIME + ")"};
			table = Table_artists_news.TABLE_NAME;
			where = Table_artists_news.COL_ARTIST_ID + " = " + artistId;
			orderBy = Table_artists_news.COL_POST_TIME;
			result = mysqlDb.select(cols, table, true, where, orderBy);
			while (result.next()) {
				String year = result.getString(1);
				String month = result.getString(2);
				String date = year + "-" + month + "-01";
				ResultSet innerResult;
				int starNewsCount, totalNewsCount;
				double heat;
				String heatId;
				List<String> newsContentList = new ArrayList<String>();
				List<Table_keywords> keywordList;

				cols = new String[] {"COUNT(" + Table_artists_news.COL_ID + ")"};
				table = Table_artists_news.TABLE_NAME;
				where = "YEAR(" + Table_artists_news.COL_POST_TIME + ") = " + year + " AND MONTH(" + Table_artists_news.COL_POST_TIME + ") = " + month + " AND " + Table_artists_news.COL_ARTIST_ID + " = " + artistId;
				innerResult = mysqlDb.select(cols, table, false, where, null);
				innerResult.next();
				starNewsCount = innerResult.getInt(1);

				cols = new String[] {Table_month_news_num.COL_DOC_NUM};
				table = Table_month_news_num.TABLE_NAME;
				where = "YEAR(" + Table_month_news_num.COL_MONTH + ") = " + year + " AND MONTH(" + Table_month_news_num.COL_MONTH + ") = " + month;
				innerResult = mysqlDb.select(cols, table, false, where, null);
				innerResult.next();
				totalNewsCount = innerResult.getInt(1);

				heat = starNewsCount / (double) totalNewsCount;
				table = Table_artists_heat.TABLE_NAME;
				cols = new String[] {Table_artists_heat.COL_HEAT, Table_artists_heat.COL_HEAT_DATE, Table_artists_heat.COL_ARTIST_ID};
				values = new Object[] {heat, date, artistId};
				mysqlDb.insert(table, cols, values, true);

				cols = new String[] {Table_artists_heat.COL_ID};
				table = Table_artists_heat.TABLE_NAME;
				where = Table_artists_heat.COL_HEAT_DATE + " = '" + date + "' AND " + Table_artists_heat.COL_ARTIST_ID + " = " + artistId;
				innerResult = mysqlDb.select(cols, table, false, where, null);
				innerResult.next();
				heatId = innerResult.getString(Table_artists_heat.COL_ID);

				cols = new String[] {Table_artists_news.COL_CONTENT};
				table = Table_artists_news.TABLE_NAME;
				where = Table_artists_news.COL_ARTIST_ID + " = " + artistId + " AND YEAR(" + Table_artists_news.COL_POST_TIME + ") = " + year + " AND MONTH(" + Table_artists_news.COL_POST_TIME + ") = " + month;
				orderBy = Table_artists_news.COL_POST_TIME;
				innerResult = mysqlDb.select(cols, table, false, where, orderBy);
				while (innerResult.next()) {
					newsContentList.add(innerResult.getString(Table_artists_news.COL_CONTENT));
				}
				keywordList = Keywords.genKeywords(newsContentList);

				for (Table_keywords keyword: keywordList) {
					table = Table_keywords.TABLE_NAME;
					cols = new String[] { Table_keywords.COL_WORD, Table_keywords.COL_TF_IDF, Table_keywords.COL_HEAT_ID};
					values = new Object[] {keyword.getWord(), keyword.getTfIdf(), heatId};
					mysqlDb.insert(table, cols, values, true);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}
	}
}