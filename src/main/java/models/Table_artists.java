package models;

public class Table_artists {
	public static final String TABLE_NAME = "artists";
	public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
	private int id;
	private String name;

	public Table_artists(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}