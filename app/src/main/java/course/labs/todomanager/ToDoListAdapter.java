package course.labs.todomanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	static class ToDoItemViewHolder {
		TextView titleView;
		CheckBox statusView;
		TextView priorityView;
		TextView dateView;

		ToDoItemViewHolder(RelativeLayout layout) {
			// DONE - Display Title in TextView
			titleView = (TextView) layout.findViewById(R.id.titleView);
			// DONE - Set up Status CheckBox
			statusView = (CheckBox) layout.findViewById(R.id.statusCheckBox);
			// DONE - Display Priority in a TextView
			priorityView = (TextView) layout.findViewById(R.id.priorityView);
			dateView = (TextView) layout.findViewById(R.id.dateView);
		}

		// Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file
		void setItem(ToDoItem toDoItem) {
			titleView.setText(toDoItem.getTitle());
			// Must remove listener before setting check to prevent it updating itself if this view is recycled
			statusView.setOnCheckedChangeListener(null);
			statusView.setChecked(toDoItem.getStatus() == ToDoItem.Status.DONE);
			priorityView.setText(toDoItem.getPriority().toString());
			// DONE - Display Time and Date.
			// Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and
			// time String
			dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));
		}
	}

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		RelativeLayout itemLayout = (RelativeLayout) convertView;

		// Use the view holder pattern to smooth out scrolling
		ToDoItemViewHolder viewHolder;

		if (itemLayout == null) {
			// DONE - Inflate the View for this ToDoItem
			// from todo_item.xml
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemLayout = (RelativeLayout) inflater.inflate(R.layout.todo_item, parent, false);

			viewHolder = new ToDoItemViewHolder(itemLayout);

			itemLayout.setTag(viewHolder);
		} else {

			viewHolder = (ToDoItemViewHolder) itemLayout.getTag();

		}

		// DONE - Get the current ToDoItem
		final ToDoItem toDoItem = (ToDoItem) getItem(position);

		viewHolder.setItem(toDoItem);

		// DONE - Must also set up an OnCheckedChangeListener,
		// which is called when the user toggles the status checkbox
		viewHolder.statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				if (isChecked)
					toDoItem.setStatus(ToDoItem.Status.DONE);
				else
					toDoItem.setStatus(ToDoItem.Status.NOTDONE);
			}
		});

		// Return the View you just created
		return itemLayout;

	}
}
