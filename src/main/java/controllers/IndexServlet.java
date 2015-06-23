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

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MysqlDB mysqlDb = new MysqlDB();
		List<Table_artists> artistList = new ArrayList<Table_artists>();

		try {
			ResultSet result = mysqlDb.select( new String[] {"*"}, Table_artists.TABLE_NAME, false, null, Table_artists.COL_ID);

			while (result.next()) {
				Table_artists artist = new Table_artists(result.getInt(Table_artists.COL_ID), result.getString(Table_artists.COL_NAME));

				artistList.add(artist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mysqlDb.close();
		}

		request.setAttribute("artistList", artistList);
		request.getRequestDispatcher("/templates/index.jsp").forward(request, response);
	}
}
