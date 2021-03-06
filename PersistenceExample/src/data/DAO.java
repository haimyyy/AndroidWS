package data;

import java.util.ArrayList;
import java.util.List;

import data.FriendsDbContract.FriendEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import Common.Friend;
import Common.User;

public class DAO implements IdataAcces {
	private static DAO instace;
	private Context context;
	private FriendsDBHelper dbHelper;
	private String[] friendsColumns = { FriendEntry._ID,
			FriendEntry.COLUMN_FRIEND_NAME,
			FriendEntry.COLUMN_FRIEND_PHONE_NUMBER, };
	// Database fields
	private SQLiteDatabase database;

	private DAO(Context context) {
		this.context = context;
		dbHelper = new FriendsDBHelper(this.context);
	}

	public static DAO getInstatnce(Context context) {
		if (instace == null)
			instace = new DAO(context);
		return instace;
	}

	@Override
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	@Override
	public void close() {
		dbHelper.close();
	}

	@Override
	public Friend addFriend(Friend friend) {
		if (friend == null)
			return null;
		//build the content values.
		ContentValues values = new ContentValues();
		values.put(FriendEntry.COLUMN_FRIEND_NAME, friend.getFriendName());
		values.put(FriendEntry.COLUMN_FRIEND_PHONE_NUMBER, friend.getPhoneNumber());
		
		//do the insert.
		long insertId = database.insert(FriendEntry.TABLE_NAME, null, values);
		
		//get the entity from the data base - extra validation, entity was insert properly.
		Cursor cursor = database.query(FriendEntry.TABLE_NAME, friendsColumns,
				FriendEntry._ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		//create the friend object from the cursor.
		Friend newFriend = cursorToFriend(cursor);
		cursor.close();
		return newFriend;

	}

	/*
	 * Create friend object from the cursor.
	 */
	private Friend cursorToFriend(Cursor cursor) {
		Friend f = new Friend();
		f.setId(cursor.getInt(cursor.getColumnIndex(FriendEntry._ID)));
		f.setFriendName(cursor.getString(cursor
				.getColumnIndex(FriendEntry.COLUMN_FRIEND_NAME)));
		f.setPhoneNumber(cursor.getString(cursor
				.getColumnIndex(FriendEntry.COLUMN_FRIEND_PHONE_NUMBER)));
		return f;
	}

	@Override
	public void removeFriend(Friend friend) {
		long id = friend.getId();
		database.delete(FriendEntry.TABLE_NAME, FriendEntry._ID + " = " + id,
				null);
	}

	@Override
	public List<Friend> getAllFriends() {
		List<Friend> friends = new ArrayList<Friend>();

		Cursor cursor = database.query(FriendEntry.TABLE_NAME, friendsColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Friend f = cursorToFriend(cursor);
			friends.add(f);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return friends;
	}

	@Override
	public User getUser(String userName, String password) {
		// Check in the storage (In the cloud, the database etc..)
		// if exists, return the user object,otherwise
		// return null.

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setMail("Cadan85@gmail.com");
		return user;
	}

}
