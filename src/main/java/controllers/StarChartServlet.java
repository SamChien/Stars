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
import models.Table_artists_heat;

@WebServlet("/star_chart")
public class StarChartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		MysqlDB mysqlDb = new MysqlDB();
		String name = null;
		List<Table_artists_heat> artistsHeatList = new ArrayList<Table_artists_heat>();

		try {
			String[] cols = {Table_artists.COL_NAME, Table_artists_heat.COL_HEAT_DATE, Table_artists_heat.COL_HEAT};
			String artistsHeatLeftJoinArtists = Table_artists_heat.TABLE_NAME + " AS ah LEFT JOIN " + Table_artists.TABLE_NAME + " AS a ON ah." + Table_artists_heat.COL_ARTIST_ID + " = a." + Table_artists.COL_ID;
			String where = Table_artists_heat.COL_ARTIST_ID + " = " + id;
			String orderBy = Table_artists_heat.COL_HEAT_DATE;
			ResultSet result = mysqlDb.select(cols, artistsHeatLeftJoinArtists, false, where, orderBy);

			while (result.next()) {
				String date = new SimpleDateFormat("yyyy-MM").format(result.getDate(Table_artists_heat.COL_HEAT_DATE));
				Table_artists_heat artistsHeat = new Table_artists_heat(0, result.getDouble(Table_artists_heat.COL_HEAT), date, 0);

				name = result.getString(Table_artists.COL_NAME);
				artistsHeatList.add(artistsHeat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}

		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("artistsHeatList", artistsHeatList);
		request.getRequestDispatcher("/templates/star_chart.jsp").forward(request, response);
	}
}
