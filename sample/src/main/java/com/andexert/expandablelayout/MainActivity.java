package com.andexert.expandablelayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.andexert.expandablelayout.library.ExpandableLayoutItem;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ArrayList<Folder> folders;
    private Folder folder1;
    private Folder folder2;
    private Folder folder3;
    private Folder folder4;
    private Folder folder5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        folder1 = new Folder(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        folder2 = new Folder(new int[]{1, 2, 3, 4});
        folder3 = new Folder(new int[]{1, 2, 3, 4, 5, 6});
        folder4 = new Folder(new int[]{1});


        folders = new ArrayList<Folder>();

        folders.add(folder1);
        folders.add(folder2);
        folders.add(folder3);
        folders.add(folder4);

        int [] items = {0, 1, 2, 3, 4, 5};
        ArrayList<Integer> itemsList = new ArrayList<Integer>();
        itemsList.add(0);
        itemsList.add(1);
        itemsList.add(2);
        itemsList.add(3);
        itemsList.add(4);
        itemsList.add(5);

      //  ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.view_folder, R.id.header_text, itemsList);
        ExpandableGridAdapter arrayAdapter = new ExpandableGridAdapter (this, R.layout.view_folder, R.id.header_text, folders);
        final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.listview);
        expandableLayoutListView.setAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

//        Toast.makeText(this,"TouchEvent" + ev.getSource(), Toast.LENGTH_LONG).show();

        return super.dispatchTouchEvent(ev);
    }


    public class Folder {
        public int [] items;

        public Folder(int [] items){
            this.items = items;
        }

    }

    public class ExpandableGridAdapter extends BaseAdapter {

        private Context ctx;
        private LayoutInflater inflater;
        public ArrayList<Folder> folders;
        public int header_text;

        public ExpandableGridAdapter(Context context, int resource, ArrayList<Folder> objects) {

            this.ctx = context;
            this.folders = objects;
            this.inflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        public ExpandableGridAdapter(Context context, int view_row, int header_text, ArrayList<Folder> folders) {
            this(context, view_row, folders);
            this.header_text = header_text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView != null) {
                return convertView;
            }

            convertView = inflater.inflate(R.layout.view_folder, null, false);

            final ExpandableLayoutItem folderView = (ExpandableLayoutItem) convertView.findViewById(R.id.row);
            LinearLayout folderContentLayout = (LinearLayout) folderView.getContentLayout().findViewById(R.id.folder_row_holder);

            View openCloseIcon = convertView.findViewById(R.id.ic_open);

            buildFolder(folderView, folderContentLayout, openCloseIcon, folders.get(position));

            return convertView;
        }

        private void buildFolder(final ExpandableLayoutItem folderView, LinearLayout folderContentLayout, View openCloseHandle, Folder folder) {
            int itemsSize = folder.items.length;

            LinearLayout mTabRow = (LinearLayout) folderView.findViewById(R.id.folder_tab_row);

            //Inflate the items in the top row (or tab row)
            for (int i = 0; i < 3; i++) {

                ((Button) mTabRow.getChildAt(i)).setText(i + "");

            }

            if (itemsSize > 3) {

                //Make the handle clickable
                openCloseHandle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (folderView.isOpened())
                            folderView.hide();
                        else
                            folderView.show();
                    }
                });

                LinearLayout folderRow = null;

                for (int i = 3; i < itemsSize; i++) {

                    int rowPos = i % 3;

                    //Add a new row to folder, add items that row
                    if (rowPos == 0) {
                        folderRow = (LinearLayout) inflater.inflate(R.layout.folder_row, null, false);
                        folderContentLayout.addView(folderRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    }

                    ((Button) folderRow.getChildAt(rowPos)).setText(i + "");

                }

            }

        }

        @Override
        public int getCount() {
            return folders.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

    }


}
