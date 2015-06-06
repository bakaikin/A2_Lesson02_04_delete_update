package android_2.lesson02.app04;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class DBEmplProvider extends ContentProvider {

	/* ������ ������������� (�����������) ������� ���������� */
	private static final String AUTHORITY = "android_2.providers.dbempl";

	/* Uri Matcher ��� ����������� URI */
	private static final UriMatcher mUriMatcher;

	/* �������� URI */
	private static final int DB_ITEM = 1;	// ���� �������
	private static final int DB_ITEMS = 2;	// ��������� ���������

	private static final int DB_DEP_ALL = 3;	// ���� �������
	private static final int DB_DEP_ITEM = 4;	// ��������� ���������

	/* ������ ����������� ������������� */
	static {

		/* ������� URI */
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(AUTHORITY, "items", DB_ITEMS);
		mUriMatcher.addURI(AUTHORITY, "item/#", DB_ITEM);

		mUriMatcher.addURI(AUTHORITY, "dep", DB_ITEMS);
		mUriMatcher.addURI(AUTHORITY, "dep/#", DB_DEP_ITEM);

	}

	/* ������ ���� ������ */
	private DBEmpl mDBEmpl = null;

	/* URI �������� ���������� */
	public static final Uri CONTENT_URI = Uri.parse("content://" +
			AUTHORITY + "/items");

	/* URI �������� ���������� */
	public static final Uri CONTENT_URI_DEP = Uri.parse("content://" +
			AUTHORITY + "/dep");

	public static final Uri CONTENT_URI_DEP_ITEM = Uri.parse("content://" +
			AUTHORITY + "/dep/#");

	@Override
	public boolean onCreate() {

		/* �������� ������� �� */
		mDBEmpl = new DBEmpl(this.getContext());

		/* �� ������ ������ - ������ true */
		return true;

	}

	/**
	 * �������� ������ �� �� � ���� �������.
	 *
	 * @param projection �������, �������� ���������� ��������, null - ���
	 * �������
	 * */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		/* �������� ������ */
		Cursor c = mDBEmpl.getReadableCursor(DBEmpl.TableDep.T_NAME);

		/*
		 * ������������� �� ���������� ������ �� URI ��� �������-����������
		 * */
		c.setNotificationUri(this.getContext().getContentResolver(), uri);

		/* ���������� ������ */
		return c;

	}

	/**
	 * ���������� ��� ������ �� URI.
	 * */
	@Override
	public String getType(Uri uri) {
		if (uri != null)
			return "text/plain";
		else
			return null;
	}

	/*
	 * ��������� ������ � ��. ����� ������ URI �� ����� ������.
	 * */
	@Override
	public Uri insert(Uri uri, ContentValues values) {

		/* �������� URI */
		if (mUriMatcher.match(uri) != DB_ITEMS)
			throw new IllegalArgumentException("Unknow URI " + uri);

		if (mUriMatcher.match(uri) == DB_DEP_ALL) {

		}

		/* ��������� ������ � �� */
		long id = mDBEmpl.addDep(values);

		/*
		 * ���������� ������ ��� �������� �� ������ � ����������� �� ����������
		 * */
		if (id > 0) {
			Uri itemUri = ContentUris.withAppendedId(CONTENT_URI, id);
			getContext().getContentResolver().notifyChange(itemUri, null);
			return itemUri;
		}

		/* ���� ���-�� ����� �� ��� - null */
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

    	if (mUriMatcher.match(uri) == DB_DEP_ITEM) {
			long id = Long.parseLong(uri.getLastPathSegment());
			mDBEmpl.deleteDep(id);
		}

		getContext().getContentResolver().notifyChange(uri, null);

		return 1;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		long id = Long.parseLong(uri.getLastPathSegment());
		mDBEmpl.updateDep("upd dep","upd loc",id);

		return 0;
	}

}
