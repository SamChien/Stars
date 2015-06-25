package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.MysqlDB;
import models.Table_artists;
import models.Table_artists_month_score;

@WebServlet("/star_chart")
public class StarChartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		MysqlDB mysqlDb = new MysqlDB();
		String name = null;
		List<Table_artists_month_score> artistsHeatList = new ArrayList<Table_artists_month_score>();
		List<Table_artists_month_score> artistsPosScoreList = new ArrayList<Table_artists_month_score>();
		List<Table_artists_month_score> artistsNegScoreList = new ArrayList<Table_artists_month_score>();

		try {
			String[] cols = {
					"ams." + Table_artists_month_score.COL_ID + " AS ams_id",
					Table_artists.COL_NAME,
					Table_artists_month_score.COL_SCORE_DATE,
					Table_artists_month_score.COL_SCORE,
					Table_artists_month_score.COL_TYPE};
			String table =
					Table_artists_month_score.TABLE_NAME + " AS ams" +
					" LEFT JOIN " + Table_artists.TABLE_NAME + " AS a" +
					" ON ams." + Table_artists_month_score.COL_ARTIST_ID + " = a." + Table_artists.COL_ID;
			String where = Table_artists_month_score.COL_ARTIST_ID + " = " + id;
			String orderBy = Table_artists_month_score.COL_SCORE_DATE;
			ResultSet result = mysqlDb.select(cols, table, false, where, orderBy);

			while (result.next()) {
				int type = result.getInt(Table_artists_month_score.COL_TYPE);
				String date = new SimpleDateFormat("yyyy-MM").format(result.getDate(Table_artists_month_score.COL_SCORE_DATE));
				Table_artists_month_score artistsMonScore = new Table_artists_month_score(
						result.getInt("ams_id"),
						result.getDouble(Table_artists_month_score.COL_SCORE),
						date,
						type,
						Integer.parseInt(id));

				name = result.getString(Table_artists.COL_NAME);
				if (type == Table_artists_month_score.TYPE_HEAT) {
					artistsHeatList.add(artistsMonScore);
				} else if (type == Table_artists_month_score.TYPE_POSITIVE) {
					artistsPosScoreList.add(artistsMonScore);
				} else if (type == Table_artists_month_score.TYPE_NEGATIVE) {
					artistsNegScoreList.add(artistsMonScore);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}

		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("artistsHeatList", artistsHeatList);
		request.setAttribute("artistsPosScoreList", artistsPosScoreList);
		request.setAttribute("artistsNegScoreList", artistsNegScoreList);
		request.getRequestDispatcher("/templates/star_chart.jsp").forward(request, response);
	}
}
