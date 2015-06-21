package models;

public class Table_artists_heat {
	public static final String TABLE_NAME = "artists_heat";
	public static final String COL_ID = "id";
	public static final String COL_HEAT = "heat";
	public static final String COL_HEAT_DATE = "heat_date";
	public static final String COL_ARTIST_ID = "artist_id";
	private int id;
	private double heat;
	private String heatDate;
	private int artist_id;

	public Table_artists_heat(int id, double heat, String heatDate,
			int artist_id) {
		super();
		this.id = id;
		this.heat = heat;
		this.heatDate = heatDate;
		this.artist_id = artist_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getHeat() {
		return heat;
	}

	public void setHeat(double heat) {
		this.heat = heat;
	}

	public String getHeatDate() {
		return heatDate;
	}

	public void setHeatDate(String heatDate) {
		this.heatDate = heatDate;
	}

	public int getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(int artist_id) {
		this.artist_id = artist_id;
	}
}