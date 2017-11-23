package deneme.codethebank.com.bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter to bind a ToDoItem List to a view
 */
public class ToDoItemAdapter extends ArrayAdapter<altinveboolean> {

    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public ToDoItemAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    /**
     * Returns the view for a specific item on the list
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final altinveboolean currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);
        final TextView textView = (TextView) row.findViewById(R.id.checkToDoItem);
        textView.setText(Integer.toString(currentItem.getAltin()) + " = " + currentItem.getAltin() / 100 + " ₺");//!!!!!!!!!!!!
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                textView.setEnabled(false);
                if (mContext instanceof MoneyRequest) {
                    MoneyRequest activity = (MoneyRequest) mContext;
                    activity.checkItem(currentItem);
                }
            }
        });
        return row;
    }}




