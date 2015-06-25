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
import models.Table_artists_month_score;
import models.Table_artists_news_comments;
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
			int artistId;

			insertNameIntoArtists(mysqlDb, name);
			artistId = selectIdFromArtists(mysqlDb, name);
			selectNewsCommentsAndInsert(mysqlDb, name, artistId);
			selectNewsCommentsAndCalculateScoresKeywords(mysqlDb, artistId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}
	}

	private void insertNameIntoArtists(MysqlDB mysqlDb, String name) {
		String table = Table_artists.TABLE_NAME;
		String[] cols = {Table_artists.COL_NAME};
		Object[] values = {name};

		mysqlDb.insert(table, cols, values, true);
	}

	private int selectIdFromArtists(MysqlDB mysqlDb, String name) throws SQLException {
		String[] cols = {Table_artists.COL_ID};
		String table = Table_artists.TABLE_NAME;
		String where = Table_artists.COL_NAME + " = '" + name + "'";
		ResultSet result = mysqlDb.select(cols, table, false, where, null);

		result.next();

		return result.getInt(Table_artists.COL_ID);
	}

	private void selectNewsCommentsAndInsert(MysqlDB mysqlDb, String name, int artistId) throws SQLException {
		String[] cols = {"*"};
		String table = Table_ts_page_content.TABLE_NAME;
		String where = Table_ts_page_content.COL_CONTENT + " like '%" + name + "%'";
		ResultSet result = mysqlDb.select(cols, table, false, where, null);

		while (result.next()) {
			String id = result.getString(Table_ts_page_content.COL_ID);
			Object[] values;

			table = Table_artists_news_comments.TABLE_NAME;
			cols = new String[] {
					Table_artists_news_comments.COL_ID,
					Table_artists_news_comments.COL_S_NAME,
					Table_artists_news_comments.COL_S_AREA_NAME,
					Table_artists_news_comments.COL_TITLE,
					Table_artists_news_comments.COL_AUTHOR,
					Table_artists_news_comments.COL_PAGE_URL,
					Table_artists_news_comments.COL_POST_TIME,
					Table_artists_news_comments.COL_CONTENT,
					Table_artists_news_comments.COL_COMMENT_COUNT,
					Table_artists_news_comments.COL_POSITIVE_SCORE,
					Table_artists_news_comments.COL_NEGATIVE_SCORE,
					Table_artists_news_comments.COL_TYPE,
					Table_artists_news_comments.COL_ARTIST_ID};
			values = new Object[] {
					id,
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
					(id.substring(id.length() - 3, id.length()).equals("N01"))? Table_artists_news_comments.TYPE_NEWS:Table_artists_news_comments.TYPE_COMMENTS,
					artistId};
			mysqlDb.insert(table, cols, values, true);
		}
	}

	private void selectNewsCommentsAndCalculateScoresKeywords(MysqlDB mysqlDb, int artistId) throws SQLException {
		String[] cols = {"YEAR(" + Table_artists_news_comments.COL_POST_TIME + ")", "MONTH(" + Table_artists_news_comments.COL_POST_TIME + ")"};
		String table = Table_artists_news_comments.TABLE_NAME;
		String where = Table_artists_news_comments.COL_ARTIST_ID + " = " + artistId;
		String orderBy = Table_artists_news_comments.COL_POST_TIME;
		ResultSet result = mysqlDb.select(cols, table, true, where, orderBy);

		while (result.next()) {
			String year = result.getString(1);
			String month = result.getString(2);
			String date = year + "-" + month + "-01";
			ResultSet innerResult;
			int starNewsCount, totalNewsCount;
			int commentNewsCount = 0;
			double totalPosScore = 0;
			double totalNegScore = 0;
			String heatId;
			List<String> newsContentList = new ArrayList<String>();
			List<Table_keywords> keywordList;
			Object[] values;

			cols = new String[] {"COUNT(" + Table_artists_news_comments.COL_ID + ")"};
			table = Table_artists_news_comments.TABLE_NAME;
			where = "YEAR(" + Table_artists_news_comments.COL_POST_TIME + ") = " + year +
					" AND MONTH(" + Table_artists_news_comments.COL_POST_TIME + ") = " + month +
					" AND " + Table_artists_news_comments.COL_ARTIST_ID + " = " + artistId +
					" AND " + Table_artists_news_comments.COL_TYPE + " = " + Table_artists_news_comments.TYPE_NEWS;
			innerResult = mysqlDb.select(cols, table, false, where, null);
			innerResult.next();
			starNewsCount = innerResult.getInt(1);

			cols = new String[] {Table_month_news_num.COL_DOC_NUM};
			table = Table_month_news_num.TABLE_NAME;
			where = "YEAR(" + Table_month_news_num.COL_MONTH + ") = " + year +
					" AND MONTH(" + Table_month_news_num.COL_MONTH + ") = " + month;
			innerResult = mysqlDb.select(cols, table, false, where, null);
			innerResult.next();
			totalNewsCount = innerResult.getInt(1);

			table = Table_artists_month_score.TABLE_NAME;
			cols = new String[] {
					Table_artists_month_score.COL_SCORE,
					Table_artists_month_score.COL_SCORE_DATE,
					Table_artists_month_score.COL_TYPE,
					Table_artists_month_score.COL_ARTIST_ID};
			values = new Object[] {starNewsCount / (double) totalNewsCount, date, Table_artists_month_score.TYPE_HEAT, artistId};
			mysqlDb.insert(table, cols, values, true);

			cols = new String[] {Table_artists_news_comments.COL_POSITIVE_SCORE, Table_artists_news_comments.COL_NEGATIVE_SCORE};
			table = Table_artists_news_comments.TABLE_NAME;
			where = "YEAR(" + Table_artists_news_comments.COL_POST_TIME + ") = " + year +
					" AND MONTH(" + Table_artists_news_comments.COL_POST_TIME + ") = " + month +
					" AND " + Table_artists_news_comments.COL_ARTIST_ID + " = " + artistId +
					" AND " + Table_artists_news_comments.COL_TYPE + " = " + Table_artists_news_comments.TYPE_COMMENTS;
			innerResult = mysqlDb.select(cols, table, false, where, null);

			while (innerResult.next()) {
				totalPosScore += innerResult.getDouble(Table_artists_news_comments.COL_POSITIVE_SCORE);
				totalNegScore += innerResult.getDouble(Table_artists_news_comments.COL_NEGATIVE_SCORE);
				commentNewsCount++;
			}

			table = Table_artists_month_score.TABLE_NAME;
			cols = new String[] {
					Table_artists_month_score.COL_SCORE,
					Table_artists_month_score.COL_SCORE_DATE,
					Table_artists_month_score.COL_TYPE,
					Table_artists_month_score.COL_ARTIST_ID};
			values = new Object[] {(commentNewsCount == 0)? 0:totalPosScore / commentNewsCount, date, Table_artists_month_score.TYPE_POSITIVE, artistId};
			mysqlDb.insert(table, cols, values, true);

			values = new Object[] {(commentNewsCount == 0)? 0:totalNegScore / commentNewsCount, date, Table_artists_month_score.TYPE_NEGATIVE, artistId};
			mysqlDb.insert(table, cols, values, true);

			cols = new String[] {Table_artists_month_score.COL_ID};
			table = Table_artists_month_score.TABLE_NAME;
			where = Table_artists_month_score.COL_SCORE_DATE + " = '" + date + "'" +
					" AND " + Table_artists_month_score.COL_ARTIST_ID + " = " + artistId +
					" AND " + Table_artists_month_score.COL_TYPE + " = " + Table_artists_month_score.TYPE_HEAT;
			innerResult = mysqlDb.select(cols, table, false, where, null);
			innerResult.next();
			heatId = innerResult.getString(Table_artists_month_score.COL_ID);

			cols = new String[] {Table_artists_news_comments.COL_TITLE, Table_artists_news_comments.COL_CONTENT};
			table = Table_artists_news_comments.TABLE_NAME;
			where = Table_artists_news_comments.COL_ARTIST_ID + " = " + artistId +
					" AND YEAR(" + Table_artists_news_comments.COL_POST_TIME + ") = " + year +
					" AND MONTH(" + Table_artists_news_comments.COL_POST_TIME + ") = " + month +
					" AND " + Table_artists_news_comments.COL_TYPE + " = " + Table_artists_news_comments.TYPE_NEWS;
			orderBy = Table_artists_news_comments.COL_POST_TIME;
			innerResult = mysqlDb.select(cols, table, false, where, orderBy);
			while (innerResult.next()) {
				newsContentList.add(innerResult.getString(Table_artists_news_comments.COL_TITLE) + innerResult.getString(Table_artists_news_comments.COL_CONTENT));
			}
			keywordList = Keywords.genKeywords(newsContentList);

			for (Table_keywords keyword : keywordList) {
				table = Table_keywords.TABLE_NAME;
				cols = new String[] {Table_keywords.COL_WORD, Table_keywords.COL_TF_IDF, Table_keywords.COL_HEAT_ID};
				values = new Object[] {keyword.getWord(), keyword.getTfIdf(), heatId};
				mysqlDb.insert(table, cols, values, true);
			}
		}
	}
}