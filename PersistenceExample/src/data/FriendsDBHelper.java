package data;

import data.FriendsDbContract.FriendEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FriendsDBHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database
	// version.
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "friends.db";

	public FriendsDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create a table to hold the friends;
		final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE "
				+ FriendEntry.TABLE_NAME + " (" + FriendEntry._ID
				+ " INTEGER PRIMARY KEY," + FriendEntry.COLUMN_FRIEND_NAME
				+ " TEXT NOT NULL, " + FriendEntry.COLUMN_FRIEND_PHONE_NUMBER
				+ " TEXT NOT NULL)";
		db.execSQL(SQL_CREATE_LOCATION_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + FriendEntry.TABLE_NAME);
		onCreate(db);

	}

}
