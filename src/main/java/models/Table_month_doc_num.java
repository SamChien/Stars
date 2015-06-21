package models;

public class Table_month_doc_num {
	public static final String TABLE_NAME = "month_doc_num";
	public static final String COL_ID = "id";
	public static final String COL_MONTH = "month";
	public static final String COL_DOC_NUM = "doc_num";
	private int id;
	private String month;
	private int docNum;

	public Table_month_doc_num(int id, String month, int docNum) {
		super();
		this.id = id;
		this.month = month;
		this.docNum = docNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getDocNum() {
		return docNum;
	}

	public void setDocNum(int docNum) {
		this.docNum = docNum;
	}
}