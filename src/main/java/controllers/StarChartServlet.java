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
import models.Table_artists_heat;

@WebServlet("/star_chart")
public class StarChartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		MysqlDB mysqlDb = new MysqlDB();
		List<Table_artists_heat> artistsHeatList = new ArrayList<Table_artists_heat>();

		try {
			ResultSet result = mysqlDb.select(new String[] {Table_artists_heat.COL_HEAT_DATE, Table_artists_heat.COL_HEAT}, Table_artists_heat.TABLE_NAME, false, Table_artists_heat.COL_ARTIST_ID + " = " + id, Table_artists_heat.COL_HEAT_DATE);

			while (result.next()) {
				Table_artists_heat artistsHeat = new Table_artists_heat(0, result.getDouble(Table_artists_heat.COL_HEAT), result.getString(Table_artists_heat.COL_HEAT_DATE), 0);

				artistsHeatList.add(artistsHeat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}

		request.setAttribute("artistsHeatList", artistsHeatList);
		request.getRequestDispatcher("/templates/star_chart.jsp").forward(request, response);
	}
}
